package com.codered.window;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import com.codered.input.Input;
import com.codered.managing.VAO;
import com.codered.ppf.PPF;
import com.codered.resource.ResourceManager;
import com.codered.shader.ShaderList;
import com.codered.shader.ShaderParts;

import cmn.utilslib.math.vector.Vector2f;
import cmn.utilslib.math.vector.api.Vec2f;

public class WindowContextImpl implements WindowContext
{
	private Window window;

	private int width = 1600;
	private int height = 1000;
	private String title = "Window";
	
	private ResourceManager resourceManager;
	
	private ShaderParts shaderParts;
	
	private Input input;
	
	private ShaderList shaders;
	
	private HashMap<Class<? extends PPF>, PPF> ppfs = new HashMap<Class<? extends PPF>, PPF>(); 
	
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
	
	public void setWidth(int width) { this.width = width; }
	public void setHeight(int height) { this.height = height; }
	public void setTitle(String title) { this.title = title; }
	public void setWindow(WindowImpl w) { this.window = w; }
	
	public String getTitle() { return this.title; }
	public int getWidth() { return this.width; }
	public int getHeight() { return this.height; }
	public Vec2f getSize() { return new Vector2f(this.width, this.height); }
	public void getSize(Vector2f v) { v.set(this.width, this.height); }
	public ResourceManager getResourceManager() { return this.resourceManager; }
	public Window getWindow() { return this.window; }
	public ShaderParts getShaderParts() { return this.shaderParts; }

	public Input getInputManager() { return this.input; }

	public ShaderList getShaders() { return this.shaders; }
	
	@SuppressWarnings("unchecked")
	public <T extends PPF> T getPPF(Class<T> clazz)
	{
		return (T) this.ppfs.get(clazz);
	}

	public void addPPF(Class<? extends PPF> clazz)
	{
		try
		{
			Constructor<? extends PPF> constructor = clazz.getDeclaredConstructor(WindowContext.class);
			
			this.ppfs.put(clazz, constructor.newInstance(this));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
