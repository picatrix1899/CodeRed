package com.codered.engine;

import com.codered.utils.Time;

public class EngineRoutine
{
	private Engine engine;
	private boolean isRunning;
	private Time time;
	
	private boolean forcedShutdown = false;
	
	public EngineRoutine(Engine engine)
	{
		this.isRunning = false;
		this.time = new Time();
		this.engine = engine;
	}


	public void start()
	{
		run();
	}
	
	public void stop(boolean forced)
	{
		this.isRunning = false;
		this.forcedShutdown = forced;
	}

	
	public void run()
	{
		this.engine.init();
		
		isRunning = true;
		
		final double frameTime = 1.0d / this.time.getFPSCap();
		
		long lastTime = this.time.getTime();
		double unprocessedTime = 0;
		
		long startTime;
		long passedTime;
		
		while(isRunning)
		{
			startTime = this.time.getTime();
			passedTime = startTime - lastTime;
			lastTime = startTime;
			
			unprocessedTime += passedTime / (double) this.time.SECOND;
			
			while(unprocessedTime > frameTime)
			{
				this.engine.update(frameTime);
				
				unprocessedTime -= frameTime;			

			}

			this.engine.render(frameTime, unprocessedTime / frameTime);
		}
		
		this.engine.release(this.forcedShutdown);
	}

}
