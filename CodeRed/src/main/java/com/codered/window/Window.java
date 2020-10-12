package com.codered.window;

import org.barghos.core.event.Event;

import org.barghos.math.vector.vec2.Vec2f;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;

import com.codered.CodeRed;
import com.codered.engine.CriticalEngineStateException;
import com.codered.engine.EngineRegistry;
import com.codered.utils.BindingUtils;
import com.codered.utils.GLCommon;
import com.codered.utils.GLUtils;

public class Window
{
	private long windowId;
	
	private Window parentWindow;
	
	private GLCapabilities capabilities;

	private Vec2f size = new Vec2f();
	private String title = "";
	
	private boolean isReleased;
	
		private WindowContext context;
	
	private WindowHints hints;
	
	public Event<Vec2f> Resize = new Event<>();
	public Event<Void> WindowClose = new Event<>();
	
	public Window(int width, int height, String title, WindowHints hints)
	{
		this.title = title;
		this.size.set(width, height);
		this.hints = hints;
	}
	
	public Window(int width, int height, String title, WindowHints hints, Window parent)
	{
		this(width, height, title, hints);
		this.parentWindow = parent;
	}
	
	public void setContext(WindowContext context)
	{
		this.context = context;
	}
	
	public WindowContext getContext()
	{
		return this.context;
	}
	
	public void makeContextCurrent()
	{
		if(glfwGetCurrentContext() != this.windowId)
			glfwMakeContextCurrent(this.windowId);
	}
	
	public void makeCurrent()
	{
		makeContextCurrent();
		EngineRegistry.setCurrentWindow(this);
		GLCommon.updateWindowId();
	}
	
	private void initWindowHints()
	{
		if(this.hints.hasGlVersionChanged())
		{
			WindowHint.glVersion(this.hints.glVersionMajor(), this.hints.glVersionMinor());
		}
		
		if(this.hints.hasGlProfileChanged())
		{
			WindowHint.glProfile(this.hints.glProfile());
		}
		
		if(this.hints.hasSamplesChanged())
		{
			WindowHint.samples(this.hints.samples());
		}
		
		if(this.hints.hasAutoShowWindowChanged())
		{
			WindowHint.autoShowWindow(this.hints.autoShowWindow());
		}
		
		if(this.hints.hasDoubleBufferingChanged())
		{
			WindowHint.doubleBuffering(this.hints.doubleBuffering());
		}
		
		if(this.hints.hasResizableChanged())
		{
			WindowHint.resizable(this.hints.resizable());
		}
	}
	
	public void show()
	{
		glfwShowWindow(this.windowId);
	}
	
	public void init()
	{
		initWindowHints();
		
		this.windowId = glfwCreateWindow((int)this.size.getX(), (int)this.size.getY(), this.title, 0, this.parentWindow != null ? this.parentWindow .getId() : 0);
		
		if(windowId == 0)
		{
			throw new CriticalEngineStateException(new Exception());
		}

		makeContextCurrent();
		this.capabilities = GL.createCapabilities();
		
		glfwSetWindowSizeCallback(this.windowId, (id, w, h) -> { onResize(id, w, h); });
		
		this.context = new WindowContext(this);
		this.context.init();
		
		makeCurrent();
	}
	
	public GLCapabilities getCapabilities()
	{
		return this.capabilities;
	}
	
	public long getId()
	{
		return this.windowId;
	}
	
	public String getTitle() { return this.title; }
	public int getWidth() { return (int)this.size.getX(); }
	public int getHeight() { return (int)this.size.getY(); }
	public Vec2f getSize() { return new Vec2f(this.size); }

	public void onResize(long id, int width, int height)
	{
		if(id == this.windowId)
		{
			Window currentWindow = EngineRegistry.getCurrentWindow();
			
			makeContextCurrent();
			this.size.set(width, height);
			
			this.Resize.fire(new Vec2f(width, height));
			currentWindow.makeContextCurrent();
		}
	}
	
	public void update(double delta)
	{
		if(isReleased()) return;
		
		makeCurrent();

		glfwPollEvents();

		if(glfwWindowShouldClose(this.windowId))
			WindowClose.fire(null);
	}
	
	public void postUpdate(double delta)
	{
		if(isReleased()) return;
		this.context.postUpdate(delta);
	}
	
	public void render(double delta, double alpha)
	{
		if(isReleased()) return;
		makeCurrent();
		
		glfwSwapBuffers(this.windowId);
		
		if(CodeRed.AUTORESET_DEFAULT_FBO)
		{
			BindingUtils.bindDefaultFramebuffer();
			GLUtils.clearAll();
		}
	}

	public void release(boolean forced)
	{
		if(isReleased()) return;
		makeCurrent();
		
		this.context.release(forced);
		
		glfwDestroyWindow(this.windowId);
		this.isReleased = true;
	}
	
	public boolean isReleased()
	{
		return this.isReleased;
	}
}
