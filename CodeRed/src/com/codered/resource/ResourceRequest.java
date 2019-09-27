package com.codered.resource;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import com.codered.engine.CriticalEngineStateException;

public class ResourceRequest
{
	public String key;
	public URL url;
	public boolean created;
	public ResourceResponse response;
	
	private ResourceRequest() { }
	
	public static ResourceRequest getFile(String file)
	{
		try
		{
		ResourceRequest request = new ResourceRequest();
		request.key = file;
		request.url = new File(file).toURI().toURL();
		return request;
		
		} catch (MalformedURLException e)
		{
			throw new CriticalEngineStateException(e);
		}
	}
	
	public static ResourceRequest getURL(String key, URL url)
	{
		ResourceRequest request = new ResourceRequest();
		request.key = key;
		request.url = url;
		return request;
	}
}
