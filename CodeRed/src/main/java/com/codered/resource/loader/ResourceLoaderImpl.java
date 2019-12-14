package com.codered.resource.loader;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import org.barghos.core.FunctionWithException;
import org.barghos.core.future.ManagedFuturePool;
import org.barghos.core.thread.ExecutorServices;

import com.codered.resource.ResourceRequest;
import com.codered.resource.ResourceResponse;


public class ResourceLoaderImpl implements ResourceLoader
{
	private ExecutorService executorService;
	
	private ManagedFuturePool<Void> futurePool;

	public ResourceLoaderImpl()
	{
		this.executorService = ExecutorServices.newLimitedCachedThreadPool(5);
		
		this.futurePool = new ManagedFuturePool<>(this.executorService, "CodeRed - Resource Loader");
	}

	public void start()
	{
		this.futurePool.start();
	}
	
	public void stop()
	{
		this.futurePool.stop();
	}

	public void loadResource(ResourceRequest request, FunctionWithException<String,Object> processor, boolean async)
	{
		Callable<Void> c = () -> {
			ResourceResponse response = new ResourceResponse();
			
			try
			{
				response.data = processor.apply(request.file);
			}
			catch (Exception e)
			{
				response.exception = e;
			}
			
			synchronized(request)
			{
				request.response = response;
			}
		
			return null;
		};
		
		if(async)
		{
			this.futurePool.submit(c);
		}
		else
		{
			try
			{
				c.call();
			}
			catch(Exception e) { }
		}
	}
}
