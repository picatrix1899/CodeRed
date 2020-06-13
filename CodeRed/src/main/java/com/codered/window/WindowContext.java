package com.codered.window;

import com.codered.engine.EngineRegistry;
import com.codered.input.Input;
import com.codered.input.Mouse;
import com.codered.resource.registry.ResourceRegistryImpl;
import com.codered.utils.GLCommon;

public class WindowContext
{
	private String name;
	
	private Window window;
	private WindowRoutine routine;
	
	private Input input;
	private Mouse mouse;

	public WindowContext(String name, Window window, WindowRoutine routine)
	{
		this.window = window;
		this.routine = routine;
		this.name = name;
		this.routine.setContext(this);
	}

	public void initWindow()
	{
		this.window.init();
	}
	
	public void init()
	{
		makeContextCurrent();

		long windowId = getWindowId();
		
		GLCommon.registerContext(windowId);
		
		EngineRegistry.registerWindowContext(name, windowId, this);
		EngineRegistry.registerResourceRegistry(name, windowId, new ResourceRegistryImpl());
		
		this.input = new Input(this);
		this.mouse = new Mouse(this);
		this.routine.init();
	}
	
	public Mouse getMouse()
	{
		return this.mouse;
	}
	
	public void preUpdate()
	{
		this.routine.preUpdate();
	}
	
	public void update(double delta)
	{
		makeContextCurrent();
		this.window.update(delta);
		
		if(this.window.isReleased()) return;
		
		this.routine.update(delta);	
		this.input.update();
		this.mouse.update();
	}
	
	public void render(double delta, double alpha)
	{
		makeContextCurrent();
		this.window.render(delta);
		this.routine.render(delta, alpha);
	}
	
	public void release(boolean forced)
	{
		makeContextCurrent();
		this.routine.release(forced);

		EngineRegistry.getResourceRegistry().release(forced);
		
		this.window.release();
		
		EngineRegistry.releaseCurrentContext();
	}
	
	public void makeContextCurrent()
	{
		this.window.makeContextCurrent();
		EngineRegistry.setCurrentWindowContext(this);
		GLCommon.updateWindowId();
	}
	
	public Window getWindow() { return this.window; }

	public Input getInputManager() { return this.input; }
	
	public String getName() { return this.name; }
	
	/*
	 * #####################
	 * #  DIRECT-ACCESSES  #
	 * #####################
	 */
	
	public long getWindowId() { return this.window.getId(); }
}
