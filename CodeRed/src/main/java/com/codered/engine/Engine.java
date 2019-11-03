package com.codered.engine;

import com.codered.resource.loader.ResourceLoader;
import com.codered.resource.loader.ResourceLoaderImpl;

public abstract class Engine implements EngineObject
{
	private static Engine instance;
	
	private EngineRoutine routine;
	
	private ResourceLoader resourceLoader;
	
	public static Engine getInstance()
	{
		return instance;
	}
	
	public Engine()
	{
		instance = this;
		this.routine = new EngineRoutine(this);
		this.resourceLoader = new ResourceLoaderImpl();
	}
	
	public abstract void init();
	
	public abstract void preUpdate();
	
	public abstract void update(double timestep);
	
	public abstract void render(double timestep, double alpha);
	
	public abstract void release(boolean forced);
	
	public ResourceLoader getResourceLoader()
	{
		return this.resourceLoader;
	}
	
	public void start()
	{
		this.resourceLoader.start();
		this.routine.start();
	}
	
	public void stop(boolean forced)
	{
		this.resourceLoader.stop();
		this.routine.stop(forced);
	}
}
