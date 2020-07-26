package com.codered.window;

import com.codered.engine.EngineRegistry;
import com.codered.input.Input;
import com.codered.input.Mouse;
import com.codered.resource.registry.ResourceRegistryImpl;
import com.codered.utils.GLCommon;

public class WindowContext
{
	private Window window;
	
	private WindowBoundResourceManager resourceManager;
	
	private Input input;
	private Mouse mouse;
	
	public WindowContext(Window window)
	{
		this.window = window;
	}

	public void init()
	{
		long windowId = getWindowId();
		
		GLCommon.registerContext(windowId);
		
		EngineRegistry.registerWindow(windowId, this.window);
		EngineRegistry.registerResourceRegistry(windowId, new ResourceRegistryImpl());
		
		this.input = new Input(this);
		this.mouse = new Mouse(this);
	}
	
	public Mouse getMouse()
	{
		return this.mouse;
	}

	public void postUpdate(double delta)
	{
		this.input.update();
		this.mouse.update();
	}

	public void release(boolean forced)
	{
		EngineRegistry.getResourceRegistry().release(forced);
		
		EngineRegistry.releaseCurrentContext();
	}

	public Window getWindow() { return this.window; }

	public Input getInputManager() { return this.input; }
	
	/*
	 * #####################
	 * #  DIRECT-ACCESSES  #
	 * #####################
	 */
	
	public long getWindowId() { return this.window.getId(); }
}
