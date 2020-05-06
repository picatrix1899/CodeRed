package com.codered.resource.loader;

import com.codered.resource.ResourceRequest;

public interface IResourceLoadingProcessFactory
{
	void init();
	
	ResourceLoadingProcess generate(ResourceRequest request);
}
