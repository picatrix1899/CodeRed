package com.codered.window;

import com.codered.utils.Time;

public class WindowTickRoutineImpl extends Thread implements WindowTickRoutine 
{
	private WindowImpl window;
	private boolean isRunning;
	private Time time;
	
	private WindowContextImpl context;
	
	public WindowTickRoutineImpl(WindowContextImpl context)
	{
		this.isRunning = false;
		this.time = new Time();
		this.context = context;
		this.setName("WindowTickRoutine");
		this.setDaemon(false);
	}

	public WindowContext getWindowContext()
	{
		return this.context;
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
		
		window.end();
	}

	public void init()
	{
		this.window.init();
	}

	public void setWindow(WindowImpl w)
	{
		this.window = w;
	}

	public void end()
	{
		this.isRunning = false;
	}
}
