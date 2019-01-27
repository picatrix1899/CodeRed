package com.codered.resource;

import java.util.Set;

import com.google.common.collect.Sets;

public class ResourceBlock
{
	private boolean isCritical = false;
	
	private Set<String> pendingTextures = Sets.newHashSet();
 	private Set<String> pendingStaticMeshes = Sets.newHashSet();
 	private Set<String> pendingMaterials = Sets.newHashSet();
 	
	public ResourceBlock(boolean critical)
	{
		this.isCritical = critical;
	}
	
	public void addTexture(String name)
	{
		this.pendingTextures.add(name);
	}
	
	public void addStaticMesh(String name)
	{
		this.pendingStaticMeshes.add(name);
	}
	
	public void addMaterial(String name)
	{
		this.pendingMaterials.add(name);
	}
	
	public boolean isCritical()
	{
		return this.isCritical;
	}
	
	public Set<String> getPendingTextures()
	{
		return this.pendingTextures;
	}
	
	public Set<String> getPendingStaticMeshes()
	{
		return this.pendingStaticMeshes;
	}
	
	public Set<String> getPendingMaterials()
	{
		return this.pendingMaterials;
	}
	
	public boolean isFinished()
	{
		return this.pendingTextures.isEmpty() &&
				this.pendingStaticMeshes.isEmpty() &&
				this.pendingMaterials.isEmpty();
	}
}
