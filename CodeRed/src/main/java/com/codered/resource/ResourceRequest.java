package com.codered.resource;

public class ResourceRequest
{
	public String resourcePath;
	public String resourceType;

	public static ResourceRequest loadTexture(String resourcePath)
	{
		ResourceRequest request = new ResourceRequest();
		request.resourcePath = resourcePath;
		request.resourceType = ResManager.TEXTURE;
		return request;
	}
	
	public static ResourceRequest loadVertexShaderPart(String resourcePath)
	{
		ResourceRequest request = new ResourceRequest();
		request.resourcePath = resourcePath;
		request.resourceType = ResManager.SHADERPART_VERTEX;
		return request;
	}
	
	public static ResourceRequest loadFragmentShaderPart(String resourcePath)
	{
		ResourceRequest request = new ResourceRequest();
		request.resourcePath = resourcePath;
		request.resourceType = ResManager.SHADERPART_FRAGMENT;
		return request;
	}
	
	public static ResourceRequest loadMaterial(String resourcePath)
	{
		ResourceRequest request = new ResourceRequest();
		request.resourcePath = resourcePath;
		request.resourceType = ResManager.MATERIAL;
		return request;
	}
	
	public static ResourceRequest loadModel(String resourcePath)
	{
		ResourceRequest request = new ResourceRequest();
		request.resourcePath = resourcePath;
		request.resourceType = ResManager.MODEL;
		return request;
	}
}
