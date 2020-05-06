package com.codered.resource;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.barghos.core.thread.ExecutorServices;

import com.codered.engine.Engine;
import com.codered.engine.TickRoutine;
import com.codered.resource.loader.IResourceLoadingProcessFactory;
import com.codered.resource.loader.ResourceLoadingProcess;

public class ResManager
{
	public static final String TEXTURE = "texture";
	public static final String SHADERPART_VERTEX = "shaderpart_vertex";
	public static final String SHADERPART_FRAGMENT = "shaderpart_fragment";
	public static final String MATERIAL = "material";
	public static final String MODEL = "model";
	
	private ExecutorService threadPool;
	
	private CompletionService<ResourceLoadingProcess> resourceCompletionService;
	private int pendingResources = 0;

	private TickRoutine routine;
	
	private IResourceLoadingProcessFactory factory;
	
	public void init()
	{
		threadPool = ExecutorServices.newLimitedCachedThreadPool(1, 10, 10, TimeUnit.SECONDS);
		resourceCompletionService = new ExecutorCompletionService<>(threadPool);
		
		factory = Engine.getInstance().getEngineSetup().resourceLoadingProcessFactory;
	}
	
	private void load(ResourceRequest request)
	{
		ResourceLoadingProcess process = factory.generate(request);
		
		this.resourceCompletionService.submit(process.loaderCall);
		this.pendingResources++;
	}
	
	public void loadAndWait(ResourceRequest request)
	{
		load(request);
		
		create();
	}
	
	public void loadAndWait(ResourceRequestBlock block)
	{
		if(block.getRequests().size() == 0) return;
		
		for(ResourceRequest r : block.getRequests())
		{
			load(r);
		}
		
		create();
	}
	
	public void loadAndBlock(ResourceRequest request)
	{
		this.routine = Engine.getInstance().getEngineSetup().resourceTickRoutine;
		
		load(request);
		
		this.routine.start();
	}
	
	public void loadAndBlock(ResourceRequestBlock block)
	{
		this.routine = Engine.getInstance().getEngineSetup().resourceTickRoutine;
		
		for(ResourceRequest r : block.getRequests())
		{
			load(r);
		}
		
		this.routine.start();
	}
	
	public void update()
	{
		long startTime = System.nanoTime();
		long currentTime = System.nanoTime();
		long passedTime = 0;
		double frameTime = 1.0d / Engine.getInstance().getEngineSetup().fpscap;
		double SECOND = 1000000000;
		
		while(this.pendingResources > 0)
		{
			passedTime = currentTime - startTime;
			if(passedTime / SECOND >= frameTime) break;
			
			Future<ResourceLoadingProcess> f = this.resourceCompletionService.poll();
			
			if(f != null)
			{
				try
				{
					ResourceLoadingProcess process = f.get();
					
					if(process.exception.isPresent())
					{
						throw process.exception.get();
					}
						
					process.creatorCall.get();
					
					pendingResources--;
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				
				currentTime = System.nanoTime();
			}
			else
			{
				break;
			}
		}
	
		if(this.pendingResources == 0) this.routine.stop();
	}
	
	private void create()
	{
		while(this.pendingResources > 0)
		{
			try
			{
				Future<ResourceLoadingProcess> f = this.resourceCompletionService.take();
				
				ResourceLoadingProcess process = f.get();
				
				process.creatorCall.get();
				
				pendingResources--;
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
		}
	}
	
	public void release()
	{
		this.threadPool.shutdownNow();
	}
}
