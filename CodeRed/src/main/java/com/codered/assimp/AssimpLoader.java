package com.codered.assimp;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.barghos.math.geometry.Triangle3f;
import org.barghos.math.vector.vec2.Vec2;
import org.barghos.math.vector.vec3.Vec3;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMaterial;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AINode;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AIVector3D;
import org.lwjgl.assimp.Assimp;

import com.codered.resource.object.TriangleData;

public class AssimpLoader
{
	public static ModelData load(String file)
	{
		AIScene scene = Assimp.aiImportFile(file, Assimp.aiProcess_Triangulate | Assimp.aiProcess_CalcTangentSpace);
		
		if(scene == null)
		{
			System.err.println("cannot load model");
			return null;
		}
		
		List<MaterialData> materials = new ArrayList<>();
		List<MeshData> meshes = new ArrayList<>();
		
		PointerBuffer bufferMaterials = scene.mMaterials();
		if(bufferMaterials != null)
			while(bufferMaterials.hasRemaining())
				processMaterial(AIMaterial.create(bufferMaterials.get()), materials);
		
		PointerBuffer bufferMeshes = scene.mMeshes();
		if(bufferMeshes != null)
			while(bufferMeshes.hasRemaining())
				processMesh(AIMesh.create(bufferMeshes.get()), meshes);
		
		ModelNodeData rootnode = processNode(scene.mRootNode(), scene);
		
		Assimp.aiReleaseImport(scene);
		
		ModelData model = new ModelData();
		model.materials = materials;
		model.meshes = meshes;
		model.rootNode = rootnode;
		
		return model;
	}
	
	private static void processMesh(AIMesh mesh, List<MeshData> meshes)
	{
		MeshData meshData = new MeshData();
		meshData.materialIndex = mesh.mMaterialIndex();
	
		List<Triangle3f> triangles = new ArrayList<>();
		List<TriangleData> triangleDatas = new ArrayList<>();
		List<Integer> indices = new ArrayList<>(); 
		List<VertexData> vertices = new ArrayList<>();
		
		AIVector3D.Buffer bufferVertices = mesh.mVertices();
		AIVector3D.Buffer bufferNormals = mesh.mNormals();
		AIVector3D.Buffer bufferTexCoords = mesh.mTextureCoords(0);
		AIVector3D.Buffer bufferTangents = mesh.mTangents();
		
		AIFace.Buffer bufferFaces = mesh.mFaces();
		for(int f = 0; f < mesh.mNumFaces(); f++)
		{
			AIFace face = bufferFaces.get();
			Triangle3f tr = new Triangle3f();
			TriangleData td = new TriangleData();
			
			IntBuffer bufferIndices = face.mIndices();
			int i1 = bufferIndices.get(0);
			int i2 = bufferIndices.get(1);
			int i3 = bufferIndices.get(2);
			
			indices.add(i1);
			indices.add(i2);
			indices.add(i3);

			AIVector3D v1 = bufferVertices.get(i1);
			tr.setP1(v1.x(), v1.y(), v1.z());
			AIVector3D v2 = bufferVertices.get(i2);
			tr.setP2(v2.x(), v2.y(), v2.z());
			AIVector3D v3 = bufferVertices.get(i3);
			tr.setP3(v3.x(), v3.y(), v3.z());
			
			AIVector3D n1 = bufferNormals.get(i1);
			td.normalA = new Vec3(n1.x(), n1.y(), n1.z());
			AIVector3D n2 = bufferNormals.get(i2);
			td.normalB = new Vec3(n2.x(), n2.y(), n2.z());
			AIVector3D n3 = bufferNormals.get(i3);
			td.normalC = new Vec3(n3.x(), n3.y(), n3.z());
			
			AIVector3D tc1 = bufferTexCoords.get(i1);
			td.uvA = new Vec2(tc1.x(), tc1.y());
			AIVector3D tc2 = bufferTexCoords.get(i2);
			td.uvB = new Vec2(tc2.x(), tc2.y());
			AIVector3D tc3 = bufferTexCoords.get(i3);
			td.uvC = new Vec2(tc3.x(), tc3.y());
			
			AIVector3D t1 = bufferTangents.get(i1);
			td.tangentA = new Vec3(t1.x(), t1.y(), t1.z());
			AIVector3D t2 = bufferTangents.get(i2);
			td.tangentB = new Vec3(t2.x(), t2.y(), t2.z());
			AIVector3D t3 = bufferTangents.get(i3);
			td.tangentC = new Vec3(t3.x(), t3.y(), t3.z());
			
			triangles.add(tr);
			triangleDatas.add(td);
		}
		
		for(int i = 0; i < mesh.mNumVertices(); i++)
		{
			VertexData vertex = new VertexData();
			
			AIVector3D v1 = bufferVertices.get();
			vertex.pos = new Vec3(v1.x(), v1.y(), v1.z());
			AIVector3D n1 = bufferNormals.get();
			vertex.normal = new Vec3(n1.x(), n1.y(), n1.z());
			AIVector3D t1 = bufferTangents.get();
			vertex.tangent = new Vec3(t1.x(), t1.y(), t1.z());
			AIVector3D tc1 = bufferTexCoords.get();
			vertex.texCoord = new Vec2(tc1.x(), tc1.y());
			
			vertices.add(vertex);
		}
		
		meshData.indices = indices;
		meshData.triangles = triangles;
		meshData.triangleData = triangleDatas;
		meshData.vertices = vertices;
		
		meshes.add(meshData);
	}
	
	private static void processMaterial(AIMaterial material, List<MaterialData> materials)
	{
		
	}
	
	private static ModelNodeData processNode(AINode node, AIScene scene)
	{
		List<Integer> meshes = new ArrayList<>();
		List<ModelNodeData> children = new ArrayList<>();

		IntBuffer bufferMeshes = node.mMeshes();
		if(bufferMeshes != null)
			while(bufferMeshes.hasRemaining())
				meshes.add(bufferMeshes.get());
		
		PointerBuffer bufferNodes = node.mChildren();
		if(bufferNodes != null)
			while(bufferNodes.hasRemaining())
				children.add(processNode(AINode.create(bufferNodes.get()), scene));
		
		ModelNodeData nodeData = new ModelNodeData();
		nodeData.meshes = meshes;
		nodeData.childen = children;
			
		return nodeData;
	}
	
	
}
