package com.codered.engine;

public class FixedTimestepTickRoutine extends TickRoutine
{
	private static final double SECOND = 1000000000;
	
	private double frameTime;
	
	private long lastTime;
	private double unprocessedTime;
	
	private long startTime;
	private long passedTime;
	
	protected void init()
	{
		this.frameTime = 1.0d / Engine.getInstance().getEngineSetup().fpscap;
		this.lastTime = System.nanoTime();
		this.unprocessedTime = 0;
	}

	protected void cycle()
	{
		this.startTime = System.nanoTime();
		this.passedTime = this.startTime - this.lastTime;
		this.lastTime = this.startTime;
		
		this.unprocessedTime += this.passedTime / SECOND;
		
		while(this.unprocessedTime >= this.frameTime)
		{
			preUpdate();
			update(this.frameTime);
			
			this.unprocessedTime -= this.frameTime;	
		}
		
		render(this.frameTime, this.unprocessedTime / this.frameTime);
	}
}
