package com.codered.engine.window;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;

import cmn.utilslib.events.Event;
import cmn.utilslib.events.EventArgs;

public class WindowImpl implements Window
{
	private long window;
	
	private GLCapabilities capabilities;

	private WindowContextImpl context;
	
	private WindowTickRoutine tickRoutine;
	
	private WindowRoutine routine;
	
	public Event<ResizeEventArgs> Resize = new Event<ResizeEventArgs>();
	
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
		
		this.window = GLFW.glfwCreateWindow(this.context.getWidth(), this.context.getHeight(), this.context.getTitle(), 0, 0);
		
		if(window == 0)
		{
			System.exit(-1);
		}
		
		GLFW.glfwMakeContextCurrent(this.window);
		this.capabilities = GL.createCapabilities();
		
		GLFW.glfwShowWindow(this.window);
		
		GLFW.glfwSetWindowSizeCallback(this.window, (id, w, h) -> { onResize(w, h); });
		
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
	
	public void onResize(int width, int height)
	{
		this.context.setWidth(width);
		this.context.setHeight(height);
		
		this.Resize.raise(new ResizeEventArgs(width, height));
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
	
	public class ResizeEventArgs implements EventArgs
	{

		public int width;
		public int height;
		
		public ResizeEventArgs(int width, int height)
		{
			this.width = width;
			this.height = height;
		}
		

		@Override
		public EventArgs cloneArgs()
		{
			return new ResizeEventArgs(this.width, this.height);
		}
		
	}
}
