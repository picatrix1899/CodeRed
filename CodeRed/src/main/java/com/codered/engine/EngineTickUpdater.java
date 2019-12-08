package com.codered.engine;


public interface EngineTickUpdater
{
	void preUpdate();
	
	void update(double timestep);
	
	void render(double timestep, double alpha);
}
