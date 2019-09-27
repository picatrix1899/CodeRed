package com.codered.resource;

import java.util.ArrayList;
import java.util.List;

public class ResourceRequestBlock
{
	List<ResourceRequest> textures = new ArrayList<>();
	List<ResourceRequest> shaderPartsFragment = new ArrayList<>();
	List<ResourceRequest> shaderPartsVertex = new ArrayList<>();
	List<ResourceRequest> staticMeshes = new ArrayList<>();
	List<ResourceRequest> materials = new ArrayList<>();
	
	private boolean loadAsync;
	
	private LoadingStage stage;
	
	public ResourceRequestBlock(boolean loadAsync)
	{
		this.loadAsync = loadAsync;
	}
	
	public List<ResourceRequest> textures()
	{
		return this.textures;
	}
	
	public List<ResourceRequest> fragmentShaderParts()
	{
		return this.shaderPartsFragment;
	}
	
	public List<ResourceRequest> vertexShaderParts()
	{
		return this.shaderPartsVertex;
	}
	
	public List<ResourceRequest> staticMeshes()
	{
		return this.staticMeshes;
	}
	
	public List<ResourceRequest> materials()
	{
		return this.materials;
	}
	
	public void addTexture(ResourceRequest request)
	{
		this.textures.add(request);
	}
	
	public void addFragmentShaderPart(ResourceRequest request)
	{
		this.shaderPartsFragment.add(request);
	}
	
	public void addVertexShaderPart(ResourceRequest request)
	{
		this.shaderPartsVertex.add(request);
	}
	
	public void addStaticMesh(ResourceRequest request)
	{
		this.staticMeshes.add(request);
	}
	
	public void addMaterial(ResourceRequest request)
	{
		this.materials.add(request);
	}
	
	public LoadingStage stage()
	{
		return this.stage;
	}
	
	public void stage(LoadingStage stage)
	{
		this.stage = stage;
	}
	
	public boolean loadAsync()
	{
		return this.loadAsync;
	}
	
	public boolean areTexturesFinished()
	{
		return this.textures.stream().allMatch((p) -> p.created);
	}
	
	public boolean areFragmentShaderPartsFinished()
	{
		return this.shaderPartsFragment.stream().allMatch((p) -> p.created);
	}
	
	public boolean areVertexShaderPartsFinished()
	{
		return this.shaderPartsVertex.stream().allMatch((p) -> p.created);
	}
	
	public boolean areStaticMeshesFinished()
	{
		return this.staticMeshes.stream().allMatch((p) -> p.created); 
	}
	
	public boolean areMaterialsFinished()
	{
		return this.materials.stream().allMatch((p) -> p.created); 
	}
	
	public boolean isFinished()
	{
		return areTexturesFinished() &&
				areFragmentShaderPartsFinished() &&
				areVertexShaderPartsFinished() &&
				areStaticMeshesFinished() &&
				areMaterialsFinished();
	}
}
