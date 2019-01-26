package com.codered.window;

import org.barghos.core.event.Event;
import org.barghos.core.event.EventArgs;
import org.barghos.core.event.NoArgs;
import org.barghos.math.vector.Vec2f;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;

import com.codered.CodeRed;
import com.codered.EngineRegistry;
import com.codered.utils.BindingUtils;
import com.codered.utils.GLUtils;

public class Window
{
	private long window;
	
	private GLCapabilities capabilities;

	private Vec2f size = new Vec2f();
	private String title = "";
	
	private Runnable initWindowHints;
	
	private boolean isReleased;
	
	public Event<ResizeEventArgs> Resize = new Event<ResizeEventArgs>();
	public Event<NoArgs> WindowClose = new Event<NoArgs>();
	
	public Window(int width, int height, String title)
	{
		this.title = title;
		this.size.set((double)width, (double)height);
	}
	
	public void makeContextCurrent()
	{
		GLFW.glfwMakeContextCurrent(this.window);
	}
	
	public void setWindowHintCallback(Runnable callback)
	{
		this.initWindowHints = callback;
	}
	
	public void init()
	{
		this.initWindowHints.run();
		
		this.window = GLFW.glfwCreateWindow((int)this.size.x, (int)this.size.y, this.title, 0, 0);

		if(window == 0)
		{
			System.exit(-1);
		}
		
		makeContextCurrent();
		this.capabilities = GL.createCapabilities();
		
		GLFW.glfwShowWindow(this.window);
		
		GLFW.glfwSetWindowSizeCallback(this.window, (id, w, h) -> { onResize(id, w, h); });
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

	public void onResize(long id, int width, int height)
	{
		if(id == this.window)
		{
			WindowContext oldContext = EngineRegistry.getWindowContext(GLFW.glfwGetCurrentContext());
			WindowContext newContext = EngineRegistry.getWindowContext(this.window);
			
			newContext.makeContextCurrent();
			this.size.set((double)width, (double)height);
			
			GL11.glViewport(0, 0, width, height);
			
			this.Resize.fire(new ResizeEventArgs(width, height));
			oldContext.makeContextCurrent();
		}
	}
	
	public void update(double delta)
	{
		GLFW.glfwPollEvents();

		if(GLFW.glfwWindowShouldClose(this.window))
			WindowClose.fire(NoArgs.getInstance());
	}
	
	public void render(double delta)
	{
		GLFW.glfwSwapBuffers(this.window);
		
		if(CodeRed.AUTORESET_DEFAULT_FBO)
		{
			BindingUtils.bindDefaultFramebuffer();
			GLUtils.clearAll();
		}
	}

	public void release()
	{
		GLFW.glfwDestroyWindow(this.window);
		this.isReleased = true;
	}
	
	public boolean isReleased()
	{
		return this.isReleased;
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
