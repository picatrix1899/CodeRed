package com.codered.resourcemanager.utils;

public interface LoaderCallback<R>
{
	public void callback(ResourceResponse<R> response);
}
