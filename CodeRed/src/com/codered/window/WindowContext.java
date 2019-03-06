package com.codered.window;

import com.codered.engine.EngineRegistry;
import com.codered.input.Input;
import com.codered.input.NInput;
import com.codered.managing.VAOManager;
import com.codered.resource.DRM;
import com.codered.resource.ResourceManager;
import com.codered.shader.ShaderList;
import com.codered.shader.ShaderParts;

public class WindowContext
{
	private String name;
	
	private Window window;
	private WindowRoutine routine;

	private ResourceManager resourceManager;
	private DRM drm;
	
	private Input input;
	private NInput ninput;
	
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
		EngineRegistry.registerShaderList(name, getWindowId(), new ShaderList(this));
		EngineRegistry.registerShaderParts(name, getWindowId(), new ShaderParts());
		EngineRegistry.registerVAOManager(name, getWindowId(), new VAOManager());
		this.resourceManager = new ResourceManager();
		this.drm = new DRM();
		this.input = new Input();
		this.ninput = new NInput(this);
		this.routine.init();
	}
	
	public void update(double delta)
	{
		makeContextCurrent();
		this.window.update(delta);
		
		if(this.window.isReleased()) return;
		
		this.input.update(delta);
		this.drm.update(delta);
		this.routine.update(delta);	
		this.ninput.update();
	}
	
	public void render(double delta)
	{
		makeContextCurrent();
		this.window.render(delta);
		this.routine.render(delta);
	}
	
	public void release()
	{
		makeContextCurrent();
		this.routine.release();
		
		EngineRegistry.getShaderParts().release();
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
	public NInput getInputManager2() { return this.ninput; }
	
	public String getName() { return this.name; }
	
	/*
	 * #####################
	 * #  DIRECT-ACCESSES  #
	 * #####################
	 */
	
	public long getWindowId() { return this.window.getWindowId(); }
}
