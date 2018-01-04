
 package com.codered.engine;

import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.glfw.GLFW;

import com.codered.engine.managing.Window;

import cmn.utilslib.essentials.BufferUtils;
import cmn.utilslib.math.vector.Vector2f;

public class Input
{
	
	private ArrayList<Integer> registeredKeys = new ArrayList<Integer>();
	private HashMap<Integer,Boolean> lastKeyDown = new HashMap<Integer,Boolean>();
	private HashMap<Integer,Boolean> isKeyDown = new HashMap<Integer,Boolean>();
	
	private ArrayList<Integer> registeredButtons = new ArrayList<Integer>();
	private HashMap<Integer,Boolean> lastButtonDown = new HashMap<Integer,Boolean>();
	private HashMap<Integer,Boolean> isButtonDown = new HashMap<Integer,Boolean>();
	
	private float DX = 0;
	private float DY = 0;
	
	private int X = 0;
	private int Y = 0;
	
	private boolean lock;
	
	private Window w;
	private Vector2f center;
	
	
	public Input(Window w)
	{
		this.w = w;
		
		this.center = new Vector2f(this.w.WIDTH / 2.0, this.w.HEIGHT / 2.0);
		
	}
	
	public void registerKey(int key)
	{
		registeredKeys.add(key);
		lastKeyDown.put(key, false);
		isKeyDown.put(key, false);
	}
	
	public void registerButton(int button)
	{
		registeredButtons.add(button);
		lastButtonDown.put(button, false);
		isButtonDown.put(button, false);
	}
	
	public void unregisterKey(int key)
	{
		registeredKeys.remove(key);
		lastKeyDown.remove(key);
		isKeyDown.remove(key);
	}
	
	public void unregisterButton(int button)
	{
		registeredButtons.remove(button);
		lastButtonDown.remove(button);
		isButtonDown.remove(button);
	}
	
	public boolean isKeyPressed(int key)
	{
		
		if(lastKeyDown.get(key) == false && isKeyDown.get(key) == true)
		{
			return true;
		}
		
		return false;
	}
	
	public boolean isButtonPressed(int button)
	{
		
		if(lastButtonDown.get(button) == false && isButtonDown.get(button) == true)
		{
			return true;
		}
		
		return false;
	}
	
	public  boolean isKeyReleased(int key)
	{
		if(lastKeyDown.get(key) == true && isKeyDown.get(key) == false)
		{
			return true;
		}
		
		return false;
	}
	
	public  boolean isButtonReleased(int button)
	{
		if(lastButtonDown.get(button) == true && isButtonDown.get(button) == false)
		{
			return true;
		}
		
		return false;
	}
	
	public boolean isKeyHelt(int key)
	{
		if(lastKeyDown.get(key) == true && isKeyDown.get(key) == true)
		{
			return true;
		}
		else
		{
			return isKeyPressed(key);
		}	
	}
	
	public  boolean isButtonHelt(int button)
	{
		if(lastButtonDown.get(button) == true && isButtonDown.get(button) == true)
		{
			return true;
		}
		else
		{
			return isButtonPressed(button);
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
	
	public void update()
	{
		
		for (int key : registeredKeys)
		{
			lastKeyDown.put(key, isKeyDown.get(key));
			
			if(GLFW.glfwGetKey(this.w.window, key) == GLFW.GLFW_PRESS)
			{
				isKeyDown.put(key, true);		
			}
			else if(GLFW.glfwGetKey(this.w.window, key) == GLFW.GLFW_RELEASE)
			{
				isKeyDown.put(key, false);	
			}
		}
		
		
		for(int button : registeredButtons)
		{
			lastButtonDown.put(button, isButtonDown.get(button));
			if(GLFW.glfwGetMouseButton(this.w.window, button) == GLFW.GLFW_PRESS)
			{
				isButtonDown.put(button, true);		
			}
			else if(GLFW.glfwGetMouseButton(this.w.window, button) == GLFW.GLFW_RELEASE)
			{
				isButtonDown.put(button, false);	
			}
		}
		
		DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer y = BufferUtils.createDoubleBuffer(1);
		
		GLFW.glfwGetCursorPos(this.w.window, x, y);
		
		this.X = (int)x.get();
		this.Y = (int)y.get();	
			
		if(lock)
		{
			this.DX = this.X - this.center.x;
			this.DY = this.Y - this.center.y;
			
			GLFW.glfwSetCursorPos(this.w.window, center.x, center.y);
		}

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
		GLFW.glfwSetCursorPos(this.w.window, x, y);
	}
	
	public void setMouseGrabbed(boolean lock)
	{
		this.lock = lock;
		
		if(lock)
		{
			
			GLFW.glfwSetCursorPos(this.w.window, this.w.WIDTH / 2.0, this.w.HEIGHT / 2.0);

			GLFW.glfwSetInputMode(this.w.window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_HIDDEN);
		}
		else
		{
			GLFW.glfwSetInputMode(this.w.window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
		}
	}
}
