package com.codered.engine;

public abstract class TickRoutine
{
	protected boolean isRunning;
	
	protected ITickReceiver receiver;
	
	public void setTickReceiver(ITickReceiver receiver)
	{
		this.receiver = receiver;
	}
	
	public void start()
	{
		if(this.receiver == null) return;
		
		if(!this.isRunning)
		{
			init();
			
			this.isRunning = true;
			
			run();
		}
	}
	
	public void stop()
	{
		this.isRunning = false;
	}
	
	protected void run()
	{
		while(this.isRunning)
		{
			cycle();
		}
	}
	
	protected void preUpdate()
	{
		this.receiver.preUpdate();
	}
	
	protected void update(double timestep)
	{
		this.receiver.update(timestep);
	}
	
	protected void render(double timestep, double alpha)
	{
		this.receiver.render(timestep, alpha);
	}
	
	protected abstract void init();
	protected abstract void cycle();
}
