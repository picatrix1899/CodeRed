package com.codered.window;

import com.codered.input.Input;
import com.codered.managing.VAO;
import com.codered.resource.ResourceManager;
import com.codered.shader.ShaderList;
import com.codered.shader.ShaderParts;

public class WindowContextImpl implements WindowContext
{
	private Window window;
	
	private ResourceManager resourceManager;
	
	private ShaderParts shaderParts;
	
	private Input input;
	
	private ShaderList shaders;
	
	public WindowContextImpl(Window window)
	{
		this.window = window;
	}

	public void init()
	{
		this.resourceManager = new ResourceManager();
		this.shaderParts = new ShaderParts();
		this.input = new Input();
		this.shaders = new ShaderList();
	}
	
	public void update(double delta)
	{
		this.input.update(delta);
	}
	
	public void release()
	{
		this.shaderParts.builtIn().clear();
		this.shaderParts.custom().clear();
		this.resourceManager.clear();
		VAO.clearAll();
	}
	public void setWindow(Window w) { this.window = w; }
	
	public ResourceManager getResourceManager() { return this.resourceManager; }
	public Window getWindow() { return this.window; }
	public ShaderParts getShaderParts() { return this.shaderParts; }

	public Input getInputManager() { return this.input; }

	public ShaderList getShaders() { return this.shaders; }
}
