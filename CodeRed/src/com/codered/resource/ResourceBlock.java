package com.codered.resource;

import java.util.Set;

import com.google.common.collect.Sets;

public class ResourceBlock
{
	private boolean isCritical = false;
	
	private Set<String> pendingTextures = Sets.newHashSet();
 	private Set<String> pendingStaticMeshes = Sets.newHashSet();
 	private Set<String> pendingMaterials = Sets.newHashSet();
	private Set<String> pendingVertexShaderParts = Sets.newHashSet();
	private Set<String> pendingFragmentShaderParts = Sets.newHashSet();
	private Set<String> pendingGeometryShaderParts = Sets.newHashSet();
	private Set<String> pendingTessellationControlShaderParts = Sets.newHashSet();
	private Set<String> pendingTessellationEvaluationShaderParts = Sets.newHashSet();
	
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
	
	public void addVertexShaderPart(String name)
	{
		this.pendingVertexShaderParts.add(name);
	}
	
	public void addFragmentShaderPart(String name)
	{
		this.pendingFragmentShaderParts.add(name);
	}
	
	public void addGeometryShaderPart(String name)
	{
		this.pendingGeometryShaderParts.add(name);
	}
	
	public void addTessellationControlShaderPart(String name)
	{
		this.pendingTessellationControlShaderParts.add(name);
	}
	
	public void addTessellationEvaluationShaderPart(String name)
	{
		this.pendingTessellationEvaluationShaderParts.add(name);
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
	
	public Set<String> getPendingVertexShaderParts()
	{
		return this.pendingVertexShaderParts;
	}
	
	public Set<String> getPendingFragmentShaderParts()
	{
		return this.pendingFragmentShaderParts;
	}
	
	public Set<String> getPendingGeometryShaderParts()
	{
		return this.pendingGeometryShaderParts;
	}
	
	public Set<String> getPendingTessellationControlShaderParts()
	{
		return this.pendingTessellationControlShaderParts;
	}
	
	public Set<String> getPendingTessellationEvaluationShaderParts()
	{
		return this.pendingTessellationEvaluationShaderParts;
	}
	
	public boolean isFinished()
	{
		return this.pendingTextures.isEmpty() &&
				this.pendingStaticMeshes.isEmpty() &&
				this.pendingMaterials.isEmpty() &&
				this.pendingVertexShaderParts.isEmpty() &&
				this.pendingFragmentShaderParts.isEmpty() &&
				this.pendingGeometryShaderParts.isEmpty() &&
				this.pendingTessellationControlShaderParts.isEmpty() &&
				this.pendingTessellationEvaluationShaderParts.isEmpty();
	}
}
