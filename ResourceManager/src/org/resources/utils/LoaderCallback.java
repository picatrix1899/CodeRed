package org.resources.utils;

public interface LoaderCallback<R>
{
	public void callback(ResourceResponse<R> response);
}
