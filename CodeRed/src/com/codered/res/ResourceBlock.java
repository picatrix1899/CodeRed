package com.codered.res;

import java.util.LinkedList;
import java.util.List;

public class ResourceBlock
{
	private boolean isCritical = false;
	
	private List<String> pendingTextures = new LinkedList<>();
 	private List<String> pendingStaticMeshes = new LinkedList<>();
 	private List<String> pendingMaterials = new LinkedList<>();
	private List<String> pendingVertexShaderParts = new LinkedList<>();
	private List<String> pendingFragmentShaderParts = new LinkedList<>();
	private List<String> pendingGeometryShaderParts = new LinkedList<>();
	private List<String> pendingTessellationControlShaderParts = new LinkedList<>();
	private List<String> pendingTessellationEvaluationShaderParts = new LinkedList<>();
	
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
	
	public List<String> getPendingTextures()
	{
		return this.pendingTextures;
	}
	
	public List<String> getPendingStaticMeshes()
	{
		return this.pendingStaticMeshes;
	}
	
	public List<String> getPendingMaterials()
	{
		return this.pendingMaterials;
	}
	
	public List<String> getPendingVertexShaderParts()
	{
		return this.pendingVertexShaderParts;
	}
	
	public List<String> getPendingFragmentShaderParts()
	{
		return this.pendingFragmentShaderParts;
	}
	
	public List<String> getPendingGeometryShaderParts()
	{
		return this.pendingGeometryShaderParts;
	}
	
	public List<String> getPendingTessellationControlShaderParts()
	{
		return this.pendingTessellationControlShaderParts;
	}
	
	public List<String> getPendingTessellationEvaluationShaderParts()
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
