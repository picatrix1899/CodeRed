package com.codered.resource.model;

import java.util.List;

public class ModelData
{
	private List<MeshData> meshes;
	
	public ModelData(List<MeshData> meshes)
	{
		this.meshes = meshes;
	}
	
	public List<MeshData> getMeshes()
	{
		return this.meshes;
	}
}
