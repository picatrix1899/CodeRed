package com.codered.engine;

import com.codered.utils.Time;

public class FixedTimestepTickRoutine extends EngineTickRoutine
{
	private Time time;
	
	private double frameTime;
	
	private long lastTime;
	private double unprocessedTime;
	
	private long startTime;
	private long passedTime;
	
	public FixedTimestepTickRoutine()
	{
		this.time = new Time();
	}
	
	public void init()
	{
		frameTime = 1.0d / this.time.getFPSCap();
		lastTime = this.time.getTime();
		unprocessedTime = 0;
	}

	public void run()
	{
		startTime = this.time.getTime();
		passedTime = startTime - lastTime;
		lastTime = startTime;
		
		unprocessedTime += passedTime / (double) this.time.SECOND;
		
		while(unprocessedTime >= frameTime)
		{
			this.engine.preUpdate();
			this.engine.update(frameTime);
			
			unprocessedTime -= frameTime;	
		}
		
		this.engine.render(frameTime, unprocessedTime / frameTime);
	}
}
