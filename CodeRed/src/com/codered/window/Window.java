package com.codered.window;

import org.barghos.core.event.Event;
import org.barghos.core.event.EventArgs;
import org.barghos.core.event.NoArgs;
import org.barghos.math.experimental.vector.vec2.Vec2;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;

import com.codered.CodeRed;
import com.codered.engine.EngineRegistry;
import com.codered.utils.BindingUtils;
import com.codered.utils.GLUtils;

public class Window
{
	private long window;
	private long parentWindow;
	
	private GLCapabilities capabilities;

	private Vec2 size = new Vec2();
	private String title = "";
	
	private Runnable initWindowHints;
	
	private boolean isReleased;
	

	
	public Event<ResizeEventArgs> Resize = new Event<ResizeEventArgs>();
	public Event<NoArgs> WindowClose = new Event<NoArgs>();
	
	public Window(int width, int height, String title, long parentWindow)
	{
		this.title = title;
		this.size.set(width, height);
		this.parentWindow = parentWindow;
	}
	
	public void makeContextCurrent()
	{
		if(glfwGetCurrentContext() != this.window)
			glfwMakeContextCurrent(this.window);
	}
	
	public void setWindowHintCallback(Runnable callback)
	{
		this.initWindowHints = callback;
	}
	
	public void init()
	{
		this.initWindowHints.run();

		this.window = glfwCreateWindow((int)this.size.getX(), (int)this.size.getY(), this.title, 0, this.parentWindow);
		
		if(window == 0)
		{
			System.exit(-1);
		}

		makeContextCurrent();
		this.capabilities = GL.createCapabilities();
		
		glfwShowWindow(this.window);
		
		glfwSetWindowSizeCallback(this.window, (id, w, h) -> { onResize(id, w, h); });
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
	public int getWidth() { return (int)this.size.getX(); }
	public int getHeight() { return (int)this.size.getY(); }
	public Vec2 getSize() { return new Vec2(this.size); }

	public void onResize(long id, int width, int height)
	{
		if(id == this.window)
		{
			WindowContext oldContext = EngineRegistry.getWindowContext(glfwGetCurrentContext());
			WindowContext newContext = EngineRegistry.getWindowContext(this.window);
			
			newContext.makeContextCurrent();
			this.size.set(width, height);
			
			GL11.glViewport(0, 0, width, height);
			
			this.Resize.fire(new ResizeEventArgs(width, height));
			oldContext.makeContextCurrent();
		}
	}
	
	public void update(double delta)
	{
		glfwPollEvents();

		if(glfwWindowShouldClose(this.window))
			WindowClose.fire(NoArgs.getInstance());
	}
	
	public void render(double delta)
	{
		glfwSwapBuffers(this.window);
		
		if(CodeRed.AUTORESET_DEFAULT_FBO)
		{
			BindingUtils.bindDefaultFramebuffer();
			GLUtils.clearAll();
		}
	}

	public void release()
	{
		glfwDestroyWindow(this.window);
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
