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
}
