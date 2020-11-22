package com.codered.model;

import java.util.List;

import org.barghos.math.boundary.AABB3f;
import org.barghos.math.boundary.AABB3fHelper;

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
	
	public AABB3f getAABB()
	{
		AABB3f out = new AABB3f(this.meshes.get(0).getCollisionMesh().get().getAABBf());
		
		for(int i = 1; i < this.meshes.size(); i++)
		{
			AABB3fHelper.merge(out, this.meshes.get(i).getCollisionMesh().get().getAABBf(), out);
		}
		
		return out;
	}
}
