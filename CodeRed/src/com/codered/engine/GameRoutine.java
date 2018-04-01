package com.codered.engine;

public class GameRoutine
{
	
	private Game game;
	private boolean isRunning;
	
	public GameRoutine(Game game)
	{
		this.game = game;
		this.isRunning = false;
	}
	
	public void start()
	{
		init();
		isRunning = true;
		run();
		release();
	}
	
	public void run()
	{
		final double frameTime = 1.0d / Time.getFPSCap();
		
		long lastTime = Time.getTime();
		double unprocessedTime = 0;
		
		long startTime;
		long passedTime;
		
		boolean render = false;
		
		int frames = 0;
		long frameCounter = 0;
		while(isRunning)
		{
			render = false;
			startTime = Time.getTime();
			passedTime = startTime - lastTime;
			lastTime = startTime;
			
			unprocessedTime += passedTime / (double) Time.SECOND;
			frameCounter += passedTime;
			
			while(unprocessedTime > frameTime)
			{
				render = true;
				
				Time.setDelta(frameTime);
				
				unprocessedTime -= frameTime;
				
				if(frameCounter >= Time.SECOND)
				{
					Time.setFPS(frames);
					
					frames = 0;
					frameCounter = 0;
				}				
				
				this.game.update();
				
			}
			
			if(render)
			{
				this.game.render();
				frames++;
			}
		}
	}
	
	public void stop()
	{
		this.isRunning = false;
	}
	
	public void init()
	{
		this.game.preInit();
		this.game.loadStaticResources();
		this.game.initShaderParts();	
		this.game.init();
	}
	
	public void release()
	{
		this.game.release();
	}
}
