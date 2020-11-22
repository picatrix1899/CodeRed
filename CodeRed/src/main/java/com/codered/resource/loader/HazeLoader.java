package com.codered.resource.loader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.barghos.math.geometry.ConvexTriangleMesh3f;
import org.barghos.math.geometry.Triangle3f;
import org.barghos.math.vec3.Vec3f;
import org.haze.mtl.MTLReader;
import org.haze.mtl.MaterialList;
import org.haze.obj.OBJLoader;
import org.haze.png.Image;
import org.haze.png.PNGReader;

import com.codered.resource.material.MaterialData;
import com.codered.resource.model.FaceData;
import com.codered.resource.model.MeshData;
import com.codered.resource.model.ModelData;
import com.codered.resource.model.VertexData;
import com.codered.resource.texture.TextureData;

public class HazeLoader implements IResourceLoader
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
		OBJLoader objloader = new OBJLoader();
		MTLReader mtlreader = new MTLReader();
		PNGReader pngreader = new PNGReader();

		String parentPath = new File(path).getParent();
		
		org.haze.obj.Model objmodel = objloader.read(path);
		
		MaterialList mtl = null;
		
		boolean hasMaterialList = !objmodel.materialList.isEmpty();
		
		if(hasMaterialList) mtl = mtlreader.read(parentPath + File.separator + objmodel.materialList);
		
		List<MeshData> meshes = new ArrayList<>();
		
		for(org.haze.obj.Mesh objmesh : objmodel.meshes)
		{
			List<FaceData> faces = new ArrayList<>();
			List<Triangle3f> triangles = new ArrayList<>();
			
			for(org.haze.obj.Face face : objmesh.faces)
			{
				VertexData va = new VertexData(face.vertexA.position, face.vertexA.normal, face.vertexA.tangent, face.vertexA.uv);
				VertexData vb = new VertexData(face.vertexB.position, face.vertexB.normal, face.vertexB.tangent, face.vertexB.uv);
				VertexData vc = new VertexData(face.vertexC.position, face.vertexC.normal, face.vertexC.tangent, face.vertexC.uv);
				Vec3f normal = face.normal;
				
				faces.add(new FaceData(va, vb, vc, normal));
				
				triangles.add(new Triangle3f(face.vertexA.position, face.vertexB.position, face.vertexC.position));
			}
			
			int vertexCount = objmesh.faces.size() * 3;
			
			MaterialData material = null;
			
			if(hasMaterialList)
			{
				if(!objmesh.material.isEmpty())
				{
					org.haze.mtl.Material m = mtl.materials.get(objmesh.material);
					
					TextureData diffuse = null;
					TextureData normal = null;
					
					if(!m.mapDiffuse.isEmpty())
					{
						Image img = pngreader.read(parentPath + File.separator + m.mapDiffuse);
						
						TextureData data = new TextureData();
						data.pixels = img.pixels;
						data.height = img.height;
						data.width = img.width;
						diffuse = data;
					}
					
					if(!m.mapNormal.isEmpty())
					{
						Image img = pngreader.read(parentPath + File.separator + m.mapNormal);
						
						TextureData data = new TextureData();
						data.pixels = img.pixels;
						data.height = img.height;
						data.width = img.width;
						normal = data;
					}
					
					material = new MaterialData(diffuse, normal);
				}
			}	
			
			ConvexTriangleMesh3f collision = new ConvexTriangleMesh3f(triangles);
			
			meshes.add(new MeshData(vertexCount, faces, collision, material));
		}
		
		ModelData model = new ModelData(meshes);
		
		return model;
	}
}
