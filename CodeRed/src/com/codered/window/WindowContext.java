package com.codered.window;

import com.codered.engine.EngineObject;
import com.codered.engine.EngineRegistry;
import com.codered.input.Input;
import com.codered.input.Mouse;
import com.codered.managing.VAOManager;
import com.codered.resource.DRM;
import com.codered.resource.ResourceManager;

public class WindowContext implements EngineObject
{
	private String name;
	
	private Window window;
	private WindowRoutine routine;

	private ResourceManager resourceManager;
	private DRM drm;
	
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
		
		EngineRegistry.registerWindowContext(name, getWindowId(), this);
		EngineRegistry.registerVAOManager(name, getWindowId(), new VAOManager());
		this.resourceManager = new ResourceManager();
		this.drm = new DRM();
		this.input = new Input(this);
		this.mouse = new Mouse(this);
		this.routine.init();
	}
	
	public Mouse getMouse()
	{
		return this.mouse;
	}
	
	public void update(double delta)
	{
		makeContextCurrent();
		this.window.update(delta);
		
		if(this.window.isReleased()) return;
		
		this.drm.update(delta);
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
		
		this.resourceManager.clear();
		EngineRegistry.getVAOManager().release();
		
		this.window.release();
		
		EngineRegistry.releaseCurrentContext();
	}
	
	public void makeContextCurrent()
	{
		this.window.makeContextCurrent();
		EngineRegistry.setCurrentWindowContext(this);
	}
	
	public ResourceManager getResourceManager() { return this.resourceManager; }
	
	public DRM getDRM() { return this.drm; }
	
	public Window getWindow() { return this.window; }

	public Input getInputManager() { return this.input; }
	
	public String getName() { return this.name; }
	
	/*
	 * #####################
	 * #  DIRECT-ACCESSES  #
	 * #####################
	 */
	
	public long getWindowId() { return this.window.getWindowId(); }
}
