package com.codered.model;

import java.util.List;

import com.codered.ResourceHolder;

public class Model implements ResourceHolder
{
	private List<Mesh> meshes;
	
	public Model(List<Mesh> meshes)
	{
		this.meshes = meshes;
	}
	
	public List<Mesh> getMeshes()
	{
		return this.meshes;
	}

	public void release(boolean forced)
	{
		for(Mesh mesh : this.meshes)
			mesh.release(forced);
	}
}
