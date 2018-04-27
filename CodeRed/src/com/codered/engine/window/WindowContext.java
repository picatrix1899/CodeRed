package com.codered.engine.window;

import com.codered.engine.input.Input;
import com.codered.engine.resource.ResourceManager;
import com.codered.engine.shader.ShaderList;
import com.codered.engine.shader.ShaderParts;

import cmn.utilslib.math.vector.Vector2f;
import cmn.utilslib.math.vector.api.Vec2f;
import cmn.utilslib.math.vector.api.Vec2fBase;

public class WindowContext implements IWindowContext
{
	private Window window;

	private int width = 1600;
	private int height = 1000;
	private String title = "Window";
	
	private ResourceManager resourceManager;
	
	private ShaderParts shaderParts;
	
	private Input input;
	
	private ShaderList objectShaders;
	
	public WindowContext(Window window)
	{
		this.window = window;
		this.resourceManager = new ResourceManager(this);
		this.shaderParts = new ShaderParts();
		this.input = new Input(this);
		this.objectShaders = new ShaderList();
	}

	public void update()
	{
		this.input.update();
	}
	
	public void release()
	{
		this.shaderParts.builtIn().clear();
		this.shaderParts.custom().clear();
		this.resourceManager.clear();
	}
	
	public void setWidth(int width) { this.width = width; }
	public void setHeight(int height) { this.height = height; }
	public void setTitle(String title) { this.title = title; }
	public void setWindow(Window w) { this.window = w; }
	
	public String getTitle() { return this.title; }
	public int getWidth() { return this.width; }
	public int getHeight() { return this.height; }
	public Vec2fBase getSize() { return new Vector2f(this.width, this.height); }
	public void getSize(Vec2f v) { v.set(this.width, this.height); }
	public ResourceManager getResourceManager() { return this.resourceManager; }
	public Window getWindow() { return this.window; }
	public ShaderParts getShaderParts() { return this.shaderParts; }

	public Input getInputManager() { return this.input; }

	public ShaderList getObjectShaders() { return this.objectShaders; }
	
}
