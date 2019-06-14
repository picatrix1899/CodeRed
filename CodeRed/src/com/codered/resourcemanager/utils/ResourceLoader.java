package com.codered.resourcemanager.utils;

import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

import org.resources.errors.ResourceErrorThrownException;
import org.resources.errors.ResourcePendingException;

public abstract class ResourceLoader<T>
{
	private ManagedFuturePool<Void> pool;
	private Set<String> pendingIds = new HashSet<>();

	public ResourceLoader(String threadname)
	{
		this.pool = new ManagedFuturePool<Void>("ResourceManager - " + threadname);
	}
	
	public ResourceLoader()
	{
		this.pool = new ManagedFuturePool<Void>("ResourceManager - Unknown Loader");
	}
	
	public int pendingFutures()
	{
		return this.pool.pendingFutures();
	}
	
	public void startPool()
	{
		this.pool.start();
	}
	
	public void stopPool()
	{
		this.pool.stop();
	}
	
	public void cancelPool()
	{
		this.pool.cancel();
	}
	
	public void loadResource(String id, URL url, LoaderCallback<T> callback) throws Exception
	{
		if(this.pendingIds.contains(id)) throw new ResourcePendingException(id);
		
		this.pendingIds.add(id);
		
		Callable<Void> c = () -> {
			
			Exception exception = null;
			T data = null;
			
			try
			{
				try(InputStream stream = url.openStream())
				{
					data = loadResource(stream);
				}
			}
			catch(Exception e)
			{
				exception = e;
			} 
			
			ResourceResponse<T> response = new ResourceResponse<T>();
			response.id = id;
			response.resourceData = data;
			
			if(exception != null)
				response.resourceError = new ResourceErrorThrownException(exception);
			
			this.pendingIds.remove(id);
			
			callback.callback(response);
			
			return null;
		};
		
		this.pool.submit(c);
	}
	
	public T directLoadResource(String id, URL url) throws Exception
	{
		if(this.pendingIds.contains(id)) throw new ResourcePendingException(id);
		
		try(InputStream stream = url.openStream())
		{
			return this.loadResource(stream);
		}
	}
	
	protected abstract T loadResource(InputStream stream) throws Exception;
	
}
