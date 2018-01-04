package com.codered.engine.rendering;


public interface CustomRenderer
{
	
	public abstract void init(MasterRenderer master);
	
	public abstract void prepare();	
	
	public abstract void render();
	

}
