package com.codered.resource.loader;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class ResourceLoadingProcess
{
	public String path;
	public String type;
	
	public Optional<Object> data = Optional.empty();
	public Optional<Exception> exception = Optional.empty();
	
	public Callable<ResourceLoadingProcess> loaderCall;
	public Supplier<Boolean> creatorCall;
}
