package com.codered.engine.window;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;

public class Window
{

	
	private long window;
	
	private GLCapabilities capabilities;


	
	private WindowContext context;
	
	private IWindowTickRoutine tickRoutine;
	
	private IWindowRoutine routine;
	
	public Window(int width, int height, String title, IWindowRoutine routine)
	{
		this.context = new WindowContext(this);
		
		this.context.setWidth(width);
		this.context.setHeight(height);
		this.context.setTitle(title);
		
		this.routine = routine;
		this.routine.setWindow(this.context);
		this.tickRoutine = new WindowTickRoutine();
		this.tickRoutine.setWindow(this);
	}
	
	public void init()
	{
		this.routine.initWindowHints();
		this.window = GLFW.glfwCreateWindow(this.context.getWidth(), this.context.getWidth(), this.context.getTitle(), 0, 0);
		
		if(window == 0)
		{
			System.exit(-1);
		}
		
		GLFW.glfwMakeContextCurrent(this.window);
		this.capabilities = GL.createCapabilities();
		
		GLFW.glfwShowWindow(this.window);
		
		this.routine.preloadResources();
		this.routine.init();
	}
	
	public GLCapabilities getCapabilities()
	{
		return this.capabilities;
	}
	
	public long getWindowId()
	{
		return this.window;
	}
	
	public void setWindowShouldClose()
	{
		GLFW.glfwSetWindowShouldClose(this.window, true);
	}
	
	public void update(double delta)
	{
		GLFW.glfwPollEvents();
		GLFW.glfwSwapBuffers(this.window);
		
		if(GLFW.glfwWindowShouldClose(this.window))
		{
			release();
			return;
		}
		
		this.context.update(delta);
		
		this.routine.update(delta);
	}
	
	public void render(double delta)
	{
		this.routine.render(delta);
	}
	
	public IWindowRoutine getRoutine() { return this.routine; }
	
	public IWindowTickRoutine getTickRoutine() { return this.tickRoutine; }

	public void release()
	{
		WindowManager.end();
	}
	
	public void internalRelease()
	{
		this.routine.release();
		this.context.release();
		this.tickRoutine.end();
		
		GLFW.glfwDestroyWindow(this.window);
	}
}
