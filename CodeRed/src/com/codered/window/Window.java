package com.codered.window;

import org.barghos.core.event.Event;
import org.barghos.core.event.EventArgs;
import org.barghos.math.vector.Vec2f;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;

import com.codered.CodeRed;
import com.codered.utils.BindingUtils;
import com.codered.utils.GLUtils;

public class Window
{
	private long window;
	
	private GLCapabilities capabilities;

	private WindowContextImpl context;
	
	private WindowTickRoutine tickRoutine;
	
	private WindowRoutine routine;
	
	private Vec2f size = new Vec2f();
	private String title = "";
	
	public Event<ResizeEventArgs> Resize = new Event<ResizeEventArgs>();
	
	public Window(int width, int height, String title, WindowRoutine routine)
	{
		this.title = title;
		this.size.set((double)width, (double)height);
		this.context = new WindowContextImpl(this);

		this.routine = routine;
		this.routine.setWindowContext(this.context);
		this.tickRoutine = new WindowTickRoutineImpl(this.context);
		this.tickRoutine.setWindow(this);
	}
	
	public void init()
	{
		this.context.init();
		this.routine.initWindowHints();
		
		this.window = GLFW.glfwCreateWindow((int)this.size.x, (int)this.size.y, this.title, 0, 0);
		
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
	
	public String getTitle() { return this.title; }
	public int getWidth() { return (int)this.size.x; }
	public int getHeight() { return (int)this.size.y; }
	public Vec2f getSize() { return new Vec2f(this.size); }
	
	public void setWindowShouldClose()
	{
		GLFW.glfwSetWindowShouldClose(this.window, true);
	}
	
	public void onResize(int width, int height)
	{
		this.size.set((double)width, (double)height);
		
		GL11.glViewport(0, 0, width, height);
		
		this.Resize.fire(new ResizeEventArgs(width, height));
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
	
	public static class ResizeEventArgs implements EventArgs
	{
		public int width;
		public int height;
		
		public ResizeEventArgs(int width, int height)
		{
			this.width = width;
			this.height = height;
		}
	}
}
