package com.codered.input;

import java.util.Stack;

import static org.lwjgl.glfw.GLFW.*;

import com.codered.window.WindowContext;

public class NInput
{
	private long windowId;
	
	private Stack<NInputConfiguration> configuration = new Stack<>();
	private boolean isConfigPushPending = false;
	private boolean isConfigPopPending = false;
	private NInputConfiguration pending; 
	
	public NInput(WindowContext context)
	{
		this.windowId = context.getWindowId();
		
		glfwSetKeyCallback(windowId, (w, k, s, a, m) -> keyCallback(w, k, s, a, m));
	}
	
	public void pushInputConfiguration(NInputConfiguration config)
	{
		pending = config;
		this.isConfigPushPending = true;
	}
	
	public void popInputConfiguration()
	{
		this.isConfigPopPending = true;
	}
	
	public void keyCallback(long window, int key, int scancode, int action, int mods)
	{
		if(window != windowId) return;
		if(this.configuration.isEmpty()) return;
		
		if(action == GLFW_RELEASE)
		{
			this.configuration.peek().setKey(key, false);
		}
		else
		{
			this.configuration.peek().setKey(key, true);
		}
	}
	
	public void update()
	{
		if(!this.configuration.isEmpty())
			this.configuration.peek().update();
		
		if(this.isConfigPushPending)
		{
			if(!this.configuration.isEmpty())
			{
				if(!this.configuration.peek().hasActiveKeys())
				{
					this.configuration.push(this.pending);
					this.pending = null;
					this.isConfigPushPending = false;
				}
			}
			else
			{
				this.configuration.push(this.pending);
				this.pending = null;
				this.isConfigPushPending = false;
			}
		}
		
		if(this.isConfigPopPending && !this.configuration.isEmpty())
		{
			if(!this.configuration.peek().hasActiveKeys())
			{
				this.configuration.pop();
				this.isConfigPopPending = false;
			}
		}
	}
	
	public boolean isPressed(int key)
	{
		if(this.configuration.isEmpty()) return false;
		if(this.isConfigPopPending || this.isConfigPushPending) return false;
		
		return this.configuration.peek().isPressed(key);
	}
	
	public boolean isHold(int key)
	{
		if(this.configuration.isEmpty()) return false;
		if(this.isConfigPopPending || this.isConfigPushPending) return false;
		
		return this.configuration.peek().isHold(key);
	}
	
	public boolean isReleased(int key)
	{
		if(this.configuration.isEmpty()) return false;
		if(this.isConfigPopPending || this.isConfigPushPending) return false;
		
		return this.configuration.peek().isReleased(key);
	}
}
