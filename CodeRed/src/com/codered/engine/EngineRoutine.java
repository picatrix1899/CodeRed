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
		
		boolean render = false;
		
		int frames = 0;
		long frameCounter = 0;
		while(isRunning)
		{
			render = false;
			startTime = this.time.getTime();
			passedTime = startTime - lastTime;
			lastTime = startTime;
			
			unprocessedTime += passedTime / (double) this.time.SECOND;
			frameCounter += passedTime;
			
			while(unprocessedTime > frameTime)
			{
				render = true;
				
				this.time.setDelta(frameTime);
				
				unprocessedTime -= frameTime;
				
				if(frameCounter >= this.time.SECOND)
				{
					this.time.setFPS(frames);
					
					frames = 0;
					frameCounter = 0;
				}				
				
				this.engine.update(frameTime);
				
			}
			
			if(render)
			{
				this.engine.render(frameTime);
				frames++;
			}
		}
		
		this.engine.release(this.forcedShutdown);
	}

}
