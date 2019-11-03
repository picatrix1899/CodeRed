package com.codered.engine;

public interface EngineObject
{
	void init();
	void update(double timestep);
	void render(double timestep, double alpha);
	void release(boolean forced);
}
