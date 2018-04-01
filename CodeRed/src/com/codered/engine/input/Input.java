
 package com.codered.engine.input;

import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.glfw.GLFW;

import com.codered.engine.managing.Window;

import cmn.utilslib.essentials.BufferUtils;
import cmn.utilslib.events.Event;
import cmn.utilslib.events.EventArgs;
import cmn.utilslib.math.vector.Vector2f;

public class Input
{
	private Window w;

	public Event<KeyEventArgs> keyStroke = new Event<KeyEventArgs>();
	public Event<KeyEventArgs> keyPress = new Event<KeyEventArgs>();
	public Event<KeyEventArgs> keyRelease = new Event<KeyEventArgs>();
	
	public Event<ButtonEventArgs> buttonStroke = new Event<ButtonEventArgs>();
	public Event<ButtonEventArgs> buttonPress = new Event<ButtonEventArgs>();
	public Event<ButtonEventArgs> buttonRelease = new Event<ButtonEventArgs>();
	
	private HashMap<Integer,Boolean> lastKeyDown = new HashMap<Integer,Boolean>();
	private HashMap<Integer,Boolean> isKeyDown = new HashMap<Integer,Boolean>();
	
	private HashMap<Integer,Boolean> lastButtonDown = new HashMap<Integer,Boolean>();
	private HashMap<Integer,Boolean> isButtonDown = new HashMap<Integer,Boolean>();

	private InputConfiguration configuration;
	private InputConfiguration pendingConfiguration;
	
	private float DX = 0;
	private float DY = 0;
	
	private int X = 0;
	private int Y = 0;
	
	private boolean lock;
	
	private Vector2f center;
	
	public Input(Window w)
	{
		this.w = w;
		
		this.center = new Vector2f(this.w.WIDTH / 2.0, this.w.HEIGHT / 2.0);
	}
	
	public boolean isButtonPressed(int button)
	{
		
		if(lastButtonDown.get(button) == false && isButtonDown.get(button) == true)
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
	
	public void setConfiguration(InputConfiguration config)
	{
		this.pendingConfiguration = config;
		
		if(this.configuration == null) applyPendingConfiguration();
	}

	public void update()
	{
		ArrayList<Integer> keyStrokes = new ArrayList<Integer>();
		ArrayList<Integer> keyHolds = new ArrayList<Integer>();
		ArrayList<Integer> keyReleases = new ArrayList<Integer>();
		
		ArrayList<Integer> buttonStrokes = new ArrayList<Integer>();
		ArrayList<Integer> buttonHolds = new ArrayList<Integer>();
		ArrayList<Integer> buttonReleases = new ArrayList<Integer>();
		
		for(int key : this.configuration.getRegisteredKeys())
		{
			this.lastKeyDown.put(key, this.isKeyDown.get(key));
			
			if(GLFW.glfwGetKey(this.w.window, key) == GLFW.GLFW_PRESS)
			{
				this.isKeyDown.put(key, true);		
			}
			else if(GLFW.glfwGetKey(this.w.window, key) == GLFW.GLFW_RELEASE)
			{
				this.isKeyDown.put(key, false);	
			}
			
			if(this.lastKeyDown.get(key) == false && this.isKeyDown.get(key) == true)
			{
				keyStrokes.add(key);
				keyHolds.add(key);
			}
			
			if(this.lastKeyDown.get(key) == true && this.isKeyDown.get(key) == false)
			{
				keyHolds.add(key);
			}
			
			if(this.lastKeyDown.get(key) == true && this.isKeyDown.get(key) == true)
			{
				keyReleases.add(key);
				keyHolds.add(key);
			}
		}

		for(int button : this.configuration.getRegisteredButtons())
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
			
			if(lastButtonDown.get(button) == false && isButtonDown.get(button) == true)
			{
				buttonStrokes.add(button);
				buttonHolds.add(button);
			}
			
			if(lastButtonDown.get(button) == true && isButtonDown.get(button) == true)
			{
				buttonHolds.add(button);
			}
			else
			{
				if(lastButtonDown.get(button) == false && isButtonDown.get(button) == true)
				{
					buttonHolds.add(button);
				}
			}
			
			if(lastButtonDown.get(button) == true && isButtonDown.get(button) == false)
			{
				buttonReleases.add(button);
				buttonHolds.add(button);
			}
		}
		
		if(this.pendingConfiguration != null)
		{
			if(!this.isKeyDown.containsValue(true) && !this.isButtonDown.containsValue(true))
			{
				applyPendingConfiguration();
			}
		}
		else
		{
			if(!keyStrokes.isEmpty()) this.keyStroke.raise(new KeyEventArgs(new KeyResponse(keyStrokes)));
			if(!keyHolds.isEmpty()) this.keyPress.raise(new KeyEventArgs(new KeyResponse(keyHolds)));
			if(!keyReleases.isEmpty()) this.keyRelease.raise(new KeyEventArgs(new KeyResponse(keyReleases)));
			if(!buttonStrokes.isEmpty()) this.buttonStroke.raise(new ButtonEventArgs(new ButtonResponse(buttonStrokes)));
			if(!buttonHolds.isEmpty()) this.buttonPress.raise(new ButtonEventArgs(new ButtonResponse(buttonHolds)));
			if(!buttonReleases.isEmpty()) this.buttonRelease.raise(new ButtonEventArgs(new ButtonResponse(buttonReleases)));
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

	public float getDX()
	{
		return DX;
	}
	
	public float getDY()
	{
		return -DY;
	}
	
	private void applyPendingConfiguration()
	{
		this.configuration = this.pendingConfiguration;
		this.pendingConfiguration = null;
		
		this.isKeyDown.clear();
		this.lastKeyDown.clear();
		this.isButtonDown.clear();
		this.lastButtonDown.clear();
		
		for(int key : this.configuration.getRegisteredKeys())
		{
			this.isKeyDown.put(key, false);
			this.lastKeyDown.put(key, false);
		}

		for(int button : this.configuration.getRegisteredButtons())
		{
			this.isButtonDown.put(button, false);
			this.lastButtonDown.put(button, false);
		}
	}
	
	public class KeyEventArgs implements EventArgs
	{
		public KeyResponse response;
		
		public KeyEventArgs(KeyResponse response)
		{
			this.response = response;
		}
		
		public EventArgs cloneArgs()
		{
			return new KeyEventArgs(this.response.clone());
		}
	}

	public class ButtonEventArgs implements EventArgs
	{
		public ButtonResponse response;
		
		public ButtonEventArgs(ButtonResponse response)
		{
			this.response = response;
		}
		
		public EventArgs cloneArgs()
		{
			return new ButtonEventArgs(this.response.clone());
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
