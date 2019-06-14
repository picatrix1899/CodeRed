package com.codered.resourcemanager.utils;

import com.codered.resourcemanager.errors.ResourceError;

public class ResourceResponse<R>
{
	public String id;
	public R resourceData;
	public ResourceError resourceError;
	
}
