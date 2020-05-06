package com.codered.resource;

import java.util.ArrayList;
import java.util.List;

public class ResourceRequestBlock
{
	private List<ResourceRequest> requests = new ArrayList<>();
	
	public List<ResourceRequest> getRequests()
	{
		return this.requests;
	}
	
	public void loadTexture(String resourcePath)
	{
		requests.add(ResourceRequest.loadTexture(resourcePath));
	}
	
	public void loadVertexShaderPart(String resourcePath)
	{
		requests.add(ResourceRequest.loadVertexShaderPart(resourcePath));
	}
	
	public void loadFragmentShaderPart(String resourcePath)
	{
		requests.add(ResourceRequest.loadFragmentShaderPart(resourcePath));
	}
	
	public void loadMaterial(String resourcePath)
	{
		requests.add(ResourceRequest.loadMaterial(resourcePath));
	}
	
	public void loadModel(String resourcePath)
	{
		requests.add(ResourceRequest.loadModel(resourcePath));
	}
}
