package com.codered.engine;

public interface EngineObjectAdapter extends EngineObject
{
	default void init() {}
	default void update(double timestep) {}
	default void render(double timestep, double alpha) {}
	default void release(boolean forced) {}
}
