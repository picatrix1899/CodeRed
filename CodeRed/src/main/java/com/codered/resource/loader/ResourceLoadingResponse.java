package com.codered.resource.loader;

import java.util.Optional;

public class ResourceLoadingResponse
{
	public String path;
	public String type;
	public Optional<Object> data = Optional.empty();
}
