package com.codered.engine;

public abstract class Engine implements ITickReceiver
{
	private static Engine instance;
	
	private TickRoutine routine;

	private boolean forcedShutdown = false;
	
	private EngineSetup setup;

	public static Engine getInstance()
	{
		return instance;
	}
	
	public Engine()
	{
		instance = this;
	}
	
	protected void setup(EngineSetup setup)
	{
		this.setup = setup;
		
		this.routine = setup.mainTickRoutine;
		this.routine.setTickReceiver(this);
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
			
			this.routine.start();
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

	public void start()
	{
		run();
	}
	
	public void stop(boolean forced)
	{
		this.routine.stop();
		this.forcedShutdown = forced;
	}
	
	public EngineSetup getEngineSetup()
	{
		return this.setup;
	}
}
