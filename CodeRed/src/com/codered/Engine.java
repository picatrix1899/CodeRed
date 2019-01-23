package com.codered;


public abstract class Engine
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
	
	public abstract void update(double delta);
	
	public abstract void render(double delta);
	
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
