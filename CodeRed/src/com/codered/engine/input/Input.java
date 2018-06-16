
 package com.codered.engine.input;

import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.glfw.GLFW;

import com.codered.engine.utils.WindowContextHelper;
import com.codered.engine.window.WindowContext;

import cmn.utilslib.essentials.BufferUtils;
import cmn.utilslib.events.Event;
import cmn.utilslib.events.EventArgs;
import cmn.utilslib.math.vector.Vector2f;

public class Input
{
	private WindowContext context;

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
		this.pendingConfiguration = config;
		
		if(this.configuration == null) applyPendingConfiguration();
	}

	public void update(double delta)
	{
		ArrayList<Integer> keyStrokes = new ArrayList<Integer>();
		ArrayList<Integer> keyHolds = new ArrayList<Integer>();
		ArrayList<Integer> keyReleases = new ArrayList<Integer>();
		
		ArrayList<Integer> buttonStrokes = new ArrayList<Integer>();
		ArrayList<Integer> buttonHolds = new ArrayList<Integer>();
		ArrayList<Integer> buttonReleases = new ArrayList<Integer>();
		
		if(this.configuration != null)
		{
			for(int key : this.configuration.getRegisteredKeys())
			{
				this.lastKeyDown.put(key, this.isKeyDown.get(key));
				
				if(GLFW.glfwGetKey(this.context.getWindowId(), key) == GLFW.GLFW_PRESS)
				{
					this.isKeyDown.put(key, true);		
				}
				else if(GLFW.glfwGetKey(this.context.getWindowId(), key) == GLFW.GLFW_RELEASE)
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
				if(GLFW.glfwGetMouseButton(this.context.getWindowId(), button) == GLFW.GLFW_PRESS)
				{
					isButtonDown.put(button, true);
				}
				else if(GLFW.glfwGetMouseButton(this.context.getWindowId(), button) == GLFW.GLFW_RELEASE)
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
			if(!keyStrokes.isEmpty()) this.keyStroke.raise(new KeyEventArgs(new KeyResponse(keyStrokes), delta));
			if(!keyHolds.isEmpty()) this.keyPress.raise(new KeyEventArgs(new KeyResponse(keyHolds), delta));
			if(!keyReleases.isEmpty()) this.keyRelease.raise(new KeyEventArgs(new KeyResponse(keyReleases), delta));
			if(!buttonStrokes.isEmpty()) this.buttonStroke.raise(new ButtonEventArgs(new ButtonResponse(buttonStrokes), delta));
			if(!buttonHolds.isEmpty()) this.buttonPress.raise(new ButtonEventArgs(new ButtonResponse(buttonHolds), delta));
			if(!buttonReleases.isEmpty()) this.buttonRelease.raise(new ButtonEventArgs(new ButtonResponse(buttonReleases), delta));
		}
		
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
		public double delta;
		
		public KeyEventArgs(KeyResponse response, double delta)
		{
			this.response = response;
			this.delta = delta;
		}
		
		public EventArgs cloneArgs()
		{
			return new KeyEventArgs(this.response.clone(), this.delta);
		}
	}

	public class ButtonEventArgs implements EventArgs
	{
		public ButtonResponse response;
		public double delta;
		
		public ButtonEventArgs(ButtonResponse response, double delta)
		{
			this.response = response;
			this.delta = delta;
		}
		
		public EventArgs cloneArgs()
		{
			return new ButtonEventArgs(this.response.clone(), this.delta);
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
