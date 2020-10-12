package com.codered.input;

import org.barghos.math.vector.vec2.Vec2f;
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
		GLFW.glfwSetCursorPos(this.windowId, pos.getX(), pos.getY());
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
			this.deltaPos.set(0, 0);
		}
		else
		{
			GLFW.glfwSetInputMode(this.windowId, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
			GLFW.glfwSetCursorPos(this.windowId, this.returnPos.getX(), this.returnPos.getY());
		}
	}
	
	public void cursorPosCallback(long window, double x, double y)
	{
		if((this.grabbed && (this.returnPos.getX() != x || this.returnPos.getY() != y)) || !this.grabbed)
		{
			currentPos.set((float)x, (float)y);
			currentPos.sub(this.returnPos, deltaPos);
		}

	}
	
	public void update()
	{
		if(this.grabbed)
		{
			this.currentPos.set(this.returnPos);
			this.deltaPos.set(0, 0);
			GLFW.glfwSetCursorPos(this.windowId, this.returnPos.getX(), this.returnPos.getY());
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
