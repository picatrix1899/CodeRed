package com.codered.engine.window;

import com.codered.engine.utils.Time;

public class WindowTickRoutine implements IWindowTickRoutine
{
	private Window window;
	private boolean isRunning;
	private Time time;
	
	public WindowTickRoutine()
	{
		this.isRunning = false;
		this.time = new Time();
	}

	public void run()
	{
		init();
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
				
				this.window.update(this.time.getDelta());
				
			}
			
			if(render)
			{
				this.window.render(this.time.getDelta());
				frames++;
			}
		}
	}

	public void init()
	{
		this.window.init();
	}

	public void setWindow(Window w)
	{
		this.window = w;
	}

	public void end()
	{
		this.isRunning = false;
	}
}
