package com.codered.window;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;

import com.codered.CodeRed;
import com.codered.utils.BindingUtils;
import com.codered.utils.GLUtils;

import cmn.utilslib.events.Event;
import cmn.utilslib.events.EventHandler;

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
		
		GL11.glViewport(0, 0, width, height);
		
		this.Resize.raise(new ResizeEventArgs(width, height));
	}
	
	public void update(double delta)
	{
		GLFW.glfwPollEvents();
		GLFW.glfwSwapBuffers(this.window);
		
		if(GLFW.glfwWindowShouldClose(this.window))
		{
			preEnd();
			return;
		}
		
		this.context.update(delta);
		
		this.routine.update(delta);
	}
	
	public void render(double delta)
	{
		if(CodeRed.AUTORESET_DEFAULT_FBO)
		{
			BindingUtils.bindDefaultFramebuffer();
			GLUtils.clearAll();
		}
		
		this.routine.render(delta);
	}
	
	public WindowRoutine getRoutine() { return this.routine; }
	
	public WindowTickRoutine getTickRoutine() { return this.tickRoutine; }

	private void preEnd()
	{
		this.tickRoutine.end();
	}

	void end()
	{
		this.routine.release();
		this.context.release();
		
		GLFW.glfwDestroyWindow(this.window);
	}
	
	@Override
	public void addResizeHandler(EventHandler<ResizeEventArgs> handler)
	{
		this.Resize.addHandler(handler);
	}
}
