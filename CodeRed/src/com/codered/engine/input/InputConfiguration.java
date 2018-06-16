package com.codered.engine.input;

import java.util.ArrayList;


public class InputConfiguration
{
	private ArrayList<Integer> registeredKeys = new ArrayList<Integer>();
	private ArrayList<Integer> registeredButtons = new ArrayList<Integer>();


	
	public void registerKey(int key)
	{
		registeredKeys.add(key);
	}
	
	public void registerKey(Key key)
	{
		registeredKeys.add(key.getId());
	}
	
	public void registerButton(int button)
	{
		registeredButtons.add(button);
	}
	
	public void registerButton(MouseButton button)
	{
		registeredButtons.add(button.getId());
	}
	
	public void unregisterKey(int key)
	{
		registeredKeys.remove(key);
	}
	
	public void unregisterKey(Key key)
	{
		registeredKeys.remove(key.getId());
	}
	
	public void unregisterButton(int button)
	{
		registeredButtons.remove(button);
	}
	
	public void unregisterButton(MouseButton button)
	{
		registeredButtons.remove(button.getId());
	}
	
	public ArrayList<Integer> getRegisteredKeys()
	{
		return this.registeredKeys;
	}
	
	public ArrayList<Integer> getRegisteredButtons()
	{
		return this.registeredButtons;
	}
}
