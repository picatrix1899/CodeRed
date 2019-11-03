package com.codered;

import java.util.function.Consumer;

public class TickUpdater
{
	private long duration;
	private long ticks;
	private boolean active;
	private boolean finished;
	private Consumer<TickUpdater> process;
	
	public TickUpdater(long duration, Consumer<TickUpdater> process)
	{
		this.duration = duration;
		this.process = process;
	}
	
	public void update()
	{
		if(this.active && ! this.finished)
		{
			this.ticks++;
			
			if(this.ticks <= this.duration)
			{
				this.process.accept(this);
			}
			else
			{
				this.finished = true;
				this.active = false;
			}
		}
	}
	
	public void trigger()
	{
		if(!this.active && !this.finished)
			this.active = true;
	}
	
	public boolean isActive()
	{
		return this.active;
	}
	
	public long getTicks()
	{
		return this.ticks;
	}
	
	public long getDuration()
	{
		return this.duration;
	}
	
	public boolean isFinished()
	{
		return this.finished;
	}
	
	public void finish()
	{
		this.finished = true;
		this.active = false;
	}
	
	public void reset()
	{
		this.finished = false;
		this.active = false;
		this.ticks = 0l;
	}
}
