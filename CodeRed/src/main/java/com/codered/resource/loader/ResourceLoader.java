package com.codered.resource.loader;

import org.barghos.core.FunctionWithException;

import com.codered.resource.ResourceRequest;

public interface ResourceLoader
{
	void start();
	void stop();
	
	void loadResource(ResourceRequest request, FunctionWithException<String,Object> processor, boolean async);
}
