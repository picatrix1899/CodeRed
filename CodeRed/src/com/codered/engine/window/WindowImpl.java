package com.codered.engine.window;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;

public class WindowImpl implements Window
{
	private long window;
	
	private GLCapabilities capabilities;

	private WindowContextImpl context;
	
	private WindowTickRoutine tickRoutine;
	
	private WindowRoutine routine;
	
	public WindowImpl(int width, int height, String title, WindowRoutine routine)
	{
		this.context = new WindowContextImpl(this);
		
		this.context.setWidth(width);
		this.context.setHeight(height);
		this.context.setTitle(title);
		
		this.routine = routine;
		this.routine.setWindowContext(this.context);
		this.tickRoutine = new WindowTickRoutineImpl(this.context);
		this.tickRoutine.setWindow(this);
	}
	
	public void init()
	{
		this.context.init();
		this.routine.initWindowHints();
		
		this.window = GLFW.glfwCreateWindow(this.context.getWidth(), this.context.getWidth(), this.context.getTitle(), 0, 0);
		
		if(window == 0)
		{
			System.exit(-1);
		}
		
		GLFW.glfwMakeContextCurrent(this.window);
		this.capabilities = GL.createCapabilities();
		
		GLFW.glfwShowWindow(this.window);
		
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
	
	public WindowRoutine getRoutine() { return this.routine; }
	
	public WindowTickRoutine getTickRoutine() { return this.tickRoutine; }

	public void release()
	{
		this.routine.release();
		this.context.release();
		
		GLFW.glfwDestroyWindow(this.window);
		
		this.tickRoutine.end();
	}
}
