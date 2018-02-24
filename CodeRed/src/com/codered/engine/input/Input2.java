
 package com.codered.engine.input;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.glfw.GLFW;

import com.codered.engine.managing.Window;

import cmn.utilslib.events.Event;
import cmn.utilslib.events.EventArgs;

public class Input2
{
	private Window w;

	public Event<KeyEventArgs> keyStroke = new Event<KeyEventArgs>();
	public Event<KeyEventArgs> keyPress = new Event<KeyEventArgs>();
	public Event<KeyEventArgs> keyRelease = new Event<KeyEventArgs>();
	
	private ArrayList<Integer> registeredKeys = new ArrayList<Integer>();
	private HashMap<Integer,Boolean> lastKeyDown = new HashMap<Integer,Boolean>();
	private HashMap<Integer,Boolean> isKeyDown = new HashMap<Integer,Boolean>();
	
	private ArrayList<Integer> registeredButtons = new ArrayList<Integer>();
	private HashMap<Integer,Boolean> lastButtonDown = new HashMap<Integer,Boolean>();
	private HashMap<Integer,Boolean> isButtonDown = new HashMap<Integer,Boolean>();

	public void registerKey(int key)
	{
		registeredKeys.add(key);
		lastKeyDown.put(key, false);
		isKeyDown.put(key, false);
	}
	
	public void registerButton(int button)
	{
		registeredButtons.add(button);
	}
	
	public void unregisterKey(int key)
	{
		registeredKeys.remove(key);
	}
	
	public void unregisterButton(int button)
	{
		registeredButtons.remove(button);
	}
	
	public Input2(Window w)
	{
		this.w = w;
		
	}
	
	public void setConfiguration()
	{
		
	}

	public void update()
	{
		ArrayList<Integer> keyStrokes = new ArrayList<Integer>();
		ArrayList<Integer> keyHolds = new ArrayList<Integer>();
		ArrayList<Integer> keyReleases = new ArrayList<Integer>();
		
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
			
			if(lastKeyDown.get(key) == false && isKeyDown.get(key) == true)
			{
				keyStrokes.add(key);
				keyHolds.add(key);
			}
			
			if(lastKeyDown.get(key) == true && isKeyDown.get(key) == false)
			{
				keyHolds.add(key);
			}
			
			if(lastKeyDown.get(key) == true && isKeyDown.get(key) == true)
			{
				keyReleases.add(key);
				keyHolds.add(key);
			}
		}
		
		if(!keyStrokes.isEmpty()) this.keyStroke.raise(new KeyEventArgs(new KeyResponse(keyStrokes)));
		if(!keyHolds.isEmpty()) this.keyPress.raise(new KeyEventArgs(new KeyResponse(keyHolds)));
		if(!keyReleases.isEmpty()) this.keyRelease.raise(new KeyEventArgs(new KeyResponse(keyReleases)));
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
	
}
