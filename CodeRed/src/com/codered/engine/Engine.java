package com.codered.engine;


public abstract class Engine implements EngineObject
{
	private static Engine instance;
	
	private EngineRoutine routine;
	
	public static Engine getInstance()
	{
		return instance;
	}
	
	public Engine()
	{
		instance = this;
		this.routine = new EngineRoutine(this);
	}
	
	public abstract void init();
	
	public abstract void preUpdate();
	
	public abstract void update(double timestep);
	
	public abstract void render(double timestep, double alpha);
	
	public abstract void release(boolean forced);
	
	public void start()
	{
		this.routine.start();
	}
	
	public void stop(boolean forced)
	{
		this.routine.stop(forced);
	}
}
