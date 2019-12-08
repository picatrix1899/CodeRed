package com.codered.engine;

public abstract class EngineTickRoutine
{
	protected EngineTickUpdater engine;

	void setEngine(EngineTickUpdater engine)
	{
		this.engine = engine;
	}
	
	public abstract void init();

	public abstract void run();

}
