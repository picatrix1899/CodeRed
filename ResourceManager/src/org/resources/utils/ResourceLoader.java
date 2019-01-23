package org.resources.utils;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

import org.resources.errors.ResourceErrorThrownException;
import org.resources.errors.ResourcePendingException;

public abstract class ResourceLoader<T>
{
	private ManagedFuturePool<Void> pool;
	private Set<String> pendingIds = new HashSet<String>();

	public ResourceLoader(String threadname)
	{
		this.pool = new ManagedFuturePool<Void>("ResourceManager - " + threadname);
	}
	
	public ResourceLoader()
	{
		this.pool = new ManagedFuturePool<Void>("ResourceManager - Unknown Loader");
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
	
	public void loadResource(String id, ResourcePath path, LoaderCallback<T> callback) throws Exception
	{
		if(this.pendingIds.contains(id)) throw new ResourcePendingException(id);
		
		this.pendingIds.add(id);
		
		Callable<Void> c = () -> {
			
			Exception exception = null;
			T data = null;
			try
			{
				data = loadResource(StreamUtility.getStreamForResource(path));
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
			
			callback.callback(response);
			
			this.pendingIds.remove(id);
			return null;
		};
		
		this.pool.submit(c);
	}
	
	public T directLoadResource(String id, ResourcePath path) throws Exception
	{
		if(this.pendingIds.contains(id)) throw new ResourcePendingException(id);
		
		return this.loadResource(StreamUtility.getStreamForResource(path));
	}
	
	protected abstract T loadResource(InputStream stream) throws Exception;
	
}
