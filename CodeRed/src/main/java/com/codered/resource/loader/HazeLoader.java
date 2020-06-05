package com.codered.resource.loader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.barghos.math.geometry.ConvexTriangleMesh3;
import org.barghos.math.geometry.Triangle3;
import org.barghos.math.vector.vec3.Vec3;
import org.haze.mtl.MaterialList;
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
		org.haze.obj.OBJLoader objloader = new org.haze.obj.OBJLoader();
		org.haze.mtl.MTLReader mtlreader = new org.haze.mtl.MTLReader();
		org.haze.png.PNGReader pngreader = new org.haze.png.PNGReader();

		String parentPath = new File(path).getParent();
		
		org.haze.obj.Model objmodel = objloader.read(path);
		
		MaterialList mtl = null;
		
		boolean hasMaterialList = objmodel.materialList.isPresent();
		
		if(hasMaterialList) mtl = mtlreader.read(parentPath + File.separator + objmodel.materialList.get());
		
		List<MeshData> meshes = new ArrayList<>();
		
		for(org.haze.obj.Mesh objmesh : objmodel.meshes)
		{
			List<FaceData> faces = new ArrayList<>();
			List<Triangle3> triangles = new ArrayList<>();
			
			for(org.haze.obj.Face face : objmesh.faces)
			{
				VertexData va = new VertexData(face.vertexA.position, face.vertexA.normal, face.vertexA.tangent, face.vertexA.uv);
				VertexData vb = new VertexData(face.vertexB.position, face.vertexB.normal, face.vertexB.tangent, face.vertexB.uv);
				VertexData vc = new VertexData(face.vertexC.position, face.vertexC.normal, face.vertexC.tangent, face.vertexC.uv);
				Vec3 normal = face.normal;
				
				faces.add(new FaceData(va, vb, vc, normal));
				
				triangles.add(new Triangle3(face.vertexA.position, face.vertexB.position, face.vertexC.position));
			}
			
			int vertexCount = objmesh.faces.size() * 3;
			
			Optional<MaterialData> material = Optional.empty();
			
			if(hasMaterialList)
			{
				if(objmesh.material.isPresent())
				{
					org.haze.mtl.Material m = mtl.materials.get(objmesh.material.get());
					
					Optional<TextureData> diffuse = Optional.empty();
					Optional<TextureData> normal = Optional.empty();
					
					if(m.mapDiffuse.isPresent())
					{
						Image img = pngreader.read(parentPath + File.separator + m.mapDiffuse.get());
						
						TextureData data = new TextureData();
						data.pixels = img.pixels;
						data.height = img.height;
						data.width = img.width;
						diffuse = Optional.of(data);
					}
					
					if(m.mapNormal.isPresent())
					{
						Image img = pngreader.read(parentPath + File.separator + m.mapNormal.get());
						
						TextureData data = new TextureData();
						data.pixels = img.pixels;
						data.height = img.height;
						data.width = img.width;
						normal = Optional.of(data);
					}
					
					material = Optional.of(new MaterialData(diffuse, normal));
				}
			}	
			
			Optional<ConvexTriangleMesh3> collision = Optional.of(new ConvexTriangleMesh3(triangles));
			
			meshes.add(new MeshData(vertexCount, faces, collision, material));
		}
		
		ModelData model = new ModelData(meshes);
		
		return model;
	}
}
