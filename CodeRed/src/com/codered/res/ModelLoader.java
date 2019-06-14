package com.codered.res;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.AIMaterial;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AIString;
import org.lwjgl.assimp.Assimp;

public class ModelLoader
{
	public static void load(String path)
	{
		AIScene aiScene = Assimp.aiImportFile(path, 0);
		
		int numMaterials = aiScene.mNumMeshes();
		PointerBuffer aiMaterials = aiScene.mMaterials();
		List<Material> materials = new ArrayList<>();
		for(int i = 0; i < numMaterials; i++)
		{
			AIMaterial aiMaterial = AIMaterial.create(aiMaterials.get(i));
			processMaterial(aiMaterial, materials);
		}
		
		int numMeshes = aiScene.mNumMeshes();
		PointerBuffer aiMeshes = aiScene.mMeshes();
		List<Mesh> meshes = new ArrayList<>();
		for(int i = 0; i < numMeshes; i++)
		{
			AIMesh mesh = AIMesh.create(aiMeshes.get(i));
			processMesh(mesh, meshes, materials);
		}
	}
	
	public static void processMaterial(AIMaterial aiMaterial, List<Material> materials)
	{
		AIString path = AIString.calloc();
		Assimp.aiGetMaterialTexture(aiMaterial, Assimp.aiTextureType_DIFFUSE, 0, path, (IntBuffer) null, null, null, null, null, null);
		
		String textPath = path.dataString();

		if(textPath != null && textPath.length() > 0)
		{
			
		}
	}
	
	public static void processMesh(AIMesh aiMesh, List<Mesh> meshes, List<Material> materials)
	{
		aiMesh.mT
	}
}
