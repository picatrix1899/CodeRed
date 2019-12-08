package com.codered.engine;

import com.codered.resource.loader.ResourceLoader;
import com.codered.resource.loader.ResourceLoaderImpl;

public abstract class Engine implements EngineObject, EngineTickUpdater
{
	private static Engine instance;
	
	private EngineTickRoutine routine;
	
	private ResourceLoader resourceLoader;
	
	private boolean isRunning;
	private boolean forcedShutdown = false;
	
	public static Engine getInstance()
	{
		return instance;
	}
	
	public Engine(EngineTickRoutine routine)
	{
		instance = this;
		this.routine = routine;
		this.routine.setEngine(this);
		this.resourceLoader = new ResourceLoaderImpl();
	}
	
	public abstract void init();
	
	public abstract void preUpdate();
	
	public abstract void update(double timestep);
	
	public abstract void render(double timestep, double alpha);
	
	public abstract void release(boolean forced);
	
	private void run()
	{
		try
		{
			init();
			
			this.isRunning = true;
			
			this.routine.init();
			
			while(isRunning)
			{
				this.routine.run();
			}
		}
		catch(CriticalEngineStateException e)
		{
			e.printStackTrace();
			this.forcedShutdown = true;
		}
		
		try
		{
			release(this.forcedShutdown);
		}
		catch(CriticalEngineStateException e)
		{
			throw new Error(e);
		}

	}
	
	public ResourceLoader getResourceLoader()
	{
		return this.resourceLoader;
	}
	
	public void start()
	{
		this.resourceLoader.start();
		
		run();
	}
	
	public void stop(boolean forced)
	{
		this.resourceLoader.stop();
		
		this.isRunning = false;
		this.forcedShutdown = forced;
	}
}
