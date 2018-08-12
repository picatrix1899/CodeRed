
 package com.codered.engine.input;

import java.nio.DoubleBuffer;

import org.lwjgl.glfw.GLFW;

import com.codered.engine.utils.WindowContextHelper;
import com.codered.engine.window.WindowContext;

import cmn.utilslib.essentials.BufferUtils;
import cmn.utilslib.math.vector.Vector2f;

public class Input
{
	private WindowContext context;

	private InputConfiguration configuration;
	
	private float DX = 0;
	private float DY = 0;
	
	private int X = 0;
	private int Y = 0;
	
	private double grabX = 0;
	private double grabY = 0;
	
	private boolean lock;
	private boolean startLocked;
	
	private Vector2f center;

	public Input()
	{
		this.context = WindowContextHelper.getCurrentContext();
		
		this.center = new Vector2f(this.context.getWidth() / 2.0, this.context.getHeight() / 2.0);
	}
	
	public void setConfiguration(InputConfiguration config)
	{
		this.configuration = config;
		this.configuration.isPending = true;
	}

	public void update(double delta)
	{
		this.configuration.update(delta, this.context);
		
		DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer y = BufferUtils.createDoubleBuffer(1);
		
		GLFW.glfwGetCursorPos(this.context.getWindowId(), x, y);
		
		this.X = (int)x.get();
		this.Y = (int)y.get();	
			
		if(this.lock)
		{
			if(this.startLocked)
			{
				GLFW.glfwSetCursorPos(this.context.getWindowId(), center.x, center.y);
				
				this.startLocked = false;
			}
			else
			{
				this.DX = this.X - this.center.x;
				this.DY = this.Y - this.center.y;
				
				GLFW.glfwSetCursorPos(this.context.getWindowId(), center.x, center.y);
			}
		}
		
		
	}

	public float getDX()
	{
		return DX;
	}
	
	public float getDY()
	{
		return -DY;
	}
	
	public int getMouseX()
	{
		return X;
	}
	
	public int getMouseY()
	{
		return Y;
	}
	
	public boolean isMouseGrabbed()
	{
		return lock;
	}
	
	public void setMousePos(float x, float y)
	{
		GLFW.glfwSetCursorPos(this.context.getWindowId(), x, y);
	}
	
	public void setMouseGrabbed(boolean lock)
	{
		this.lock = lock;
		
		if(lock)
		{
			this.startLocked = true;
			
			DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
			DoubleBuffer y = BufferUtils.createDoubleBuffer(1);
			GLFW.glfwGetCursorPos(this.context.getWindowId(), x, y);
			
			this.grabX = x.get();
			this.grabY = y.get();
			
			GLFW.glfwSetCursorPos(this.context.getWindowId(), this.context.getWidth() / 2.0, this.context.getHeight() / 2.0);

			GLFW.glfwSetInputMode(this.context.getWindowId(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_HIDDEN);
		}
		else
		{
			GLFW.glfwSetInputMode(this.context.getWindowId(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
			GLFW.glfwSetCursorPos(this.context.getWindowId(), this.grabX, this.grabY);
		}
	}
}
