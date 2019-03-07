package com.codered.input;

import org.barghos.math.vector.Vec2f;
import org.lwjgl.glfw.GLFW;

import com.codered.window.WindowContext;

public class Mouse
{
	private long windowId;
	
	private boolean grabbed;
	
	private Vec2f currentPos = new Vec2f();
	private Vec2f deltaPos = new Vec2f();
	
	private Vec2f returnPos = new Vec2f();
	
	public Mouse(WindowContext context)
	{
		this.windowId = context.getWindowId();
		GLFW.glfwSetCursorPosCallback(this.windowId, (w, x, y) -> cursorPosCallback(w, x, y));
	}
	
	public void setMousePos(Vec2f pos)
	{
		GLFW.glfwSetCursorPos(this.windowId, pos.x, pos.y);
	}
	
	public void setMousePos(double x, double y)
	{
		GLFW.glfwSetCursorPos(this.windowId, x, y);
	}
	
	public void grab(boolean state)
	{
		this.grabbed = state;
		
		if(this.grabbed)
		{
			GLFW.glfwSetInputMode(this.windowId, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_HIDDEN);
			this.returnPos.set(currentPos);
			this.deltaPos.set(0);
		}
		else
		{
			GLFW.glfwSetInputMode(this.windowId, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
			GLFW.glfwSetCursorPos(this.windowId, this.returnPos.x, this.returnPos.y);
		}
	}
	
	public void cursorPosCallback(long window, double x, double y)
	{
		if((this.grabbed && (this.returnPos.x != x || this.returnPos.y != y)) || !this.grabbed)
		{
			currentPos.set(x, y);
			currentPos.sub(this.returnPos, deltaPos);
		}

	}
	
	public void update()
	{
		if(this.grabbed)
		{
			this.currentPos.set(this.returnPos);
			this.deltaPos.set(0);
			GLFW.glfwSetCursorPos(this.windowId, this.returnPos.x, this.returnPos.y);
		}
	}
	
	public Vec2f getCurrentPos()
	{
		return this.currentPos;
	}
	
	public Vec2f getDeltaPos()
	{
		return this.deltaPos;
	}
}
