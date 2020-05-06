package com.codered.engine;


public interface ITickReceiver
{
	void preUpdate();
	
	void update(double timestep);
	
	void render(double timestep, double alpha);
}
