package com.codered.input;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Stack;

import org.lwjgl.glfw.GLFW;

import com.codered.window.WindowContext;

import cmn.utilslib.events.Event;
import cmn.utilslib.events.EventArgs;


public class InputConfiguration
{
	private ArrayList<Integer> registeredKeys = new ArrayList<Integer>();
	private ArrayList<Integer> registeredButtons = new ArrayList<Integer>();

	private Stack<InputConfiguration> dubConfigurations = new Stack<InputConfiguration>();
	
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
	
	public boolean isPending = false;
	
	public void pushDubConfiguration(InputConfiguration config)
	{
		this.dubConfigurations.push(config);
		this.dubConfigurations.peek().isPending = true;
	}
	
	public void popDubConfiguration()
	{
		this.dubConfigurations.pop();
		if(this.dubConfigurations.isEmpty())
		{
			this.isPending = true;
		}
		else
		{
			this.dubConfigurations.peek().isPending = true;
		}
	}
	
	public void registerKey(int key)
	{
		this.registeredKeys.add(key);
		this.isKeyDown.put(key, false);
		this.lastKeyDown.put(key, false);
	}
	
	public void registerKey(Key key)
	{
		this.registeredKeys.add(key.getId());
		this.isKeyDown.put(key.getId(), false);
		this.lastKeyDown.put(key.getId(), false);
	}
	
	public void registerButton(int button)
	{
		this.registeredButtons.add(button);
		this.isButtonDown.put(button, false);
		this.lastButtonDown.put(button, false);
	}
	
	public void registerButton(MouseButton button)
	{
		this.registeredButtons.add(button.getId());
		this.isButtonDown.put(button.getId(), false);
		this.lastButtonDown.put(button.getId(), false);
	}
	
	public void unregisterKey(int key)
	{
		this.registeredKeys.remove(key);
		this.isKeyDown.remove(key);
		this.lastKeyDown.remove(key);
	}
	
	public void unregisterKey(Key key)
	{
		this.registeredKeys.remove(key.getId());
		this.isKeyDown.remove(key.getId());
		this.lastKeyDown.remove(key.getId());
	}
	
	public void unregisterButton(int button)
	{
		this.registeredButtons.remove(button);
		this.isButtonDown.remove(button);
		this.lastButtonDown.remove(button);
	}
	
	public void unregisterButton(MouseButton button)
	{
		this.registeredButtons.remove(button.getId());
		this.isButtonDown.remove(button.getId());
		this.lastButtonDown.remove(button.getId());
	}
	
	public ArrayList<Integer> getRegisteredKeys()
	{
		return this.registeredKeys;
	}
	
	public ArrayList<Integer> getRegisteredButtons()
	{
		return this.registeredButtons;
	}
	
	public void update(double delta, WindowContext context)
	{
		if(this.dubConfigurations.isEmpty())
		{
			updateInternal(delta, context);
		}
		else
		{
			this.dubConfigurations.peek().update(delta, context);
		}
	}
	
	private void updateInternal(double delta, WindowContext context)
	{
		ArrayList<Integer> keyStrokes = new ArrayList<Integer>();
		ArrayList<Integer> keyHolds = new ArrayList<Integer>();
		ArrayList<Integer> keyReleases = new ArrayList<Integer>();
		
		ArrayList<Integer> buttonStrokes = new ArrayList<Integer>();
		ArrayList<Integer> buttonHolds = new ArrayList<Integer>();
		ArrayList<Integer> buttonReleases = new ArrayList<Integer>();
		
		for(int key : this.registeredKeys)
		{
			this.lastKeyDown.put(key, this.isKeyDown.get(key));
			
			if(GLFW.glfwGetKey(context.getWindowId(), key) == GLFW.GLFW_PRESS)
			{
				this.isKeyDown.put(key, true);		
			}
			else if(GLFW.glfwGetKey(context.getWindowId(), key) == GLFW.GLFW_RELEASE)
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

		for(int button : this.registeredButtons)
		{
			lastButtonDown.put(button, isButtonDown.get(button));
			if(GLFW.glfwGetMouseButton(context.getWindowId(), button) == GLFW.GLFW_PRESS)
			{
				isButtonDown.put(button, true);
			}
			else if(GLFW.glfwGetMouseButton(context.getWindowId(), button) == GLFW.GLFW_RELEASE)
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

		if(this.isPending)
		{
			if(!this.isKeyDown.containsValue(true) && !this.isButtonDown.containsValue(true))
			{
				this.isPending = false;
			}
		}
		else
		{
			if(!keyStrokes.isEmpty()) this.keyStroke.raise(new KeyEventArgs(keyStrokes, delta));
			if(!keyHolds.isEmpty()) this.keyPress.raise(new KeyEventArgs(keyHolds, delta));
			if(!keyReleases.isEmpty()) this.keyRelease.raise(new KeyEventArgs(keyReleases, delta));
			if(!buttonStrokes.isEmpty()) this.buttonStroke.raise(new ButtonEventArgs(new ButtonResponse(buttonStrokes), delta));
			if(!buttonHolds.isEmpty()) this.buttonPress.raise(new ButtonEventArgs(new ButtonResponse(buttonHolds), delta));
			if(!buttonReleases.isEmpty()) this.buttonRelease.raise(new ButtonEventArgs(new ButtonResponse(buttonReleases), delta));
		}
	}
	
	public class KeyEventArgs implements EventArgs
	{
		private ArrayList<Integer> keys = new ArrayList<Integer>();
		
		public double delta;
		
		public boolean keyPresent(int key)
		{
			return this.keys.contains(key);
		}
		
		public boolean keyPresent(Key key)
		{
			return this.keys.contains(key.getId());
		}
		
		public KeyEventArgs(Collection<Integer> keys, double delta)
		{
			this.keys.addAll(keys);
			this.delta = delta;
		}
		
		public EventArgs cloneArgs()
		{
			return new KeyEventArgs(this.keys, this.delta);
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
}
