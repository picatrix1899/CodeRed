package com.codered.resourcemanager.utils;

import org.resources.errors.ResourceError;

public class ResourceResponse<R>
{
	public String id;
	public R resourceData;
	public ResourceError resourceError;
	
}
