package com.codered.resource.loader;

import java.io.File;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.barghos.math.geometry.ConvexTriangleMesh3f;
import org.barghos.math.geometry.Triangle3f;
import org.barghos.math.vector.vec2.Vec2f;
import org.barghos.math.vector.vec2.pool.Vec2fPool;
import org.barghos.math.vector.vec3.Vec3f;
import org.barghos.math.vector.vec3.Vec3fPool;
import org.barghos.math.vector.vec3.Vec3fR;

import org.haze.png.Image;
import org.haze.png.PNGReader;

import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMaterial;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AINode;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AIString;
import org.lwjgl.assimp.AIVector3D;
import org.lwjgl.assimp.Assimp;

import com.codered.engine.CriticalEngineStateException;
import com.codered.resource.material.MaterialData;
import com.codered.resource.model.FaceData;
import com.codered.resource.model.MeshData;
import com.codered.resource.model.ModelData;
import com.codered.resource.model.VertexData;
import com.codered.resource.texture.TextureData;

public class AssimpLoader implements IResourceLoader
{
	public TextureData loadTexture(String path) throws Exception
	{
		PNGReader reader = new PNGReader();
		Image img = reader.read(path);
		
		TextureData data = new TextureData();
		data.pixels = img.pixels;
		data.height = img.height;
		data.width = img.width;
		
		return data;
	}
	
	public ModelData loadModel(String path) throws Exception
	{
		String parentPath = new File(path).getParent();
		
		AIScene scene = Assimp.aiImportFile(path, Assimp.aiProcess_Triangulate | Assimp.aiProcess_FlipUVs | Assimp.aiProcess_CalcTangentSpace);
		
		if(scene == null || (scene.mFlags() & Assimp.AI_SCENE_FLAGS_INCOMPLETE) > 0 || scene.mRootNode() == null)
		{
			throw new CriticalEngineStateException(new Exception(Assimp.aiGetErrorString()));
		}
		
		List<MeshData> meshes = new ArrayList<>();
		
		processNode(scene.mRootNode(), scene, parentPath, meshes);
		
		ModelData model = new ModelData(meshes);
		return model;
	}
	
	public void processNode(AINode node, AIScene scene, String parentPath, List<MeshData> meshes) throws Exception
	{
		for(int i = 0; i < node.mNumMeshes(); i++)
		{
			int index = node.mMeshes().get(i);
			AIMesh mesh = AIMesh.create(scene.mMeshes().get(index));
			processMesh(scene, mesh, parentPath, meshes);
		}
		
		for(int i = 0; i < node.mNumChildren(); i++)
		{
			AINode childNode = AINode.create(node.mChildren().get(i));
			processNode(childNode, scene, parentPath, meshes);
		}
	}
	
	public void processMesh(AIScene scene, AIMesh rawMesh, String parentPath, List<MeshData> meshes) throws Exception
	{
		org.haze.png.PNGReader pngreader = new org.haze.png.PNGReader();
		
		List<VertexData> vertices = new ArrayList<>();
		
		Vec3f pos = Vec3fPool.get();
		Vec3f nrm = Vec3fPool.get();
		Vec3f tng = Vec3fPool.get();
		Vec2f uv = Vec2fPool.get();
		
		for(int i = 0; i < rawMesh.mNumVertices(); i++)
		{
			AIVector3D vertex = rawMesh.mVertices().get(i);
			AIVector3D normal = rawMesh.mNormals().get(i);
			AIVector3D tangent = rawMesh.mTangents().get(i);
			
			
			pos.set(vertex.x(), vertex.y(), vertex.z());
			nrm.set(normal.x(), normal.y(), normal.z());
			tng.set(tangent.x(), tangent.y(), tangent.z());
			
			if(rawMesh.mTextureCoords(0) != null)
			{
				AIVector3D texCoords = rawMesh.mTextureCoords(0).get(i);
				uv.set(texCoords.x(), texCoords.y());
			}
			
			
			VertexData v = new VertexData(pos, nrm, tng, uv);
			vertices.add(v);
		}
		
		List<FaceData> faces = new ArrayList<>();
		List<Triangle3f> triangles = new ArrayList<>();
		
		for(int i = 0; i < rawMesh.mNumFaces(); i++)
		{
			AIFace face = rawMesh.mFaces().get(i);
			
			VertexData v1 = vertices.get(face.mIndices().get(0));
			VertexData v2 = vertices.get(face.mIndices().get(1));
			VertexData v3 = vertices.get(face.mIndices().get(2));
			
			Vec3f tv1 = Vec3fPool.get(v2.getPosition()).sub(v1.getPosition());
			Vec3f tv2 = Vec3fPool.get(v3.getPosition()).sub(v1.getPosition());
			
			Vec3f n = tv1.cross(tv2);
			
			Vec3fR vn1 = v1.getNormal();
			Vec3fR vn2 = v2.getNormal();
			Vec3fR vn3 = v3.getNormal();
			
			if((n.dot(vn1) < 0.0f && n.dot(vn2) < 0.0f) ||
				(n.dot(vn2) < 0.0f && n.dot(vn3) < 0.0f) ||
				(n.dot(vn1) < 0.0f && n.dot(vn3) < 0.0f))
			{
				n = tv2.cross(tv1);
			}

			FaceData f = new FaceData(v1, v2, v3, n);
			faces.add(f);
			
			Triangle3f tr = new Triangle3f(v1.getPosition(), v2.getPosition(), v3.getPosition());
			triangles.add(tr);
		}
		
		ConvexTriangleMesh3f collision = new ConvexTriangleMesh3f(triangles);
		
		AIMaterial mat = AIMaterial.create(scene.mMaterials().get(rawMesh.mMaterialIndex()));
		AIString pathDiffuse = AIString.calloc();
		Assimp.aiGetMaterialTexture(mat, Assimp.aiTextureType_DIFFUSE, 0, pathDiffuse, (IntBuffer) null, null, null, null, null, null);
		
		TextureData diffuse = null;
		TextureData normals = null;
		
		if(!pathDiffuse.dataString().isEmpty())
		{
			Image img = pngreader.read(parentPath + File.separator + pathDiffuse.dataString());
						
						TextureData data = new TextureData();
						data.pixels = img.pixels;
						data.height = img.height;
						data.width = img.width;
						diffuse = data;
		}
		
		
		AIString pathNormals = AIString.calloc();
		Assimp.aiGetMaterialTexture(mat, Assimp.aiTextureType_NORMALS, 0, pathNormals, (IntBuffer) null, null, null, null, null, null);
		
		if(!pathNormals.dataString().isEmpty())
		{
			Image img = pngreader.read(parentPath + File.separator + pathNormals.dataString());
						
						TextureData data = new TextureData();
						data.pixels = img.pixels;
						data.height = img.height;
						data.width = img.width;
						normals = data;
		}
		
		MaterialData material = new MaterialData(diffuse, normals);
		
		MeshData mesh = new MeshData(vertices.size(), faces, collision, material);
		meshes.add(mesh);
		
		Vec3fPool.store(pos, nrm, tng);
		Vec2fPool.store(uv);
	}
}
