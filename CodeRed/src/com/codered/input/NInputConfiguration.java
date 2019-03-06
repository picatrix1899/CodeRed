package com.codered.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class NInputConfiguration
{
	private List<Integer> registeredKeys = new ArrayList<>();
	private Map<Integer,Boolean> lastKeyStates = new HashMap<>();
	private Map<Integer,Boolean> currentKeyStates = new HashMap<>();

	
	public void registerKey(int key)
	{
		this.registeredKeys.add(key);
		this.lastKeyStates.put(key, false);
		this.currentKeyStates.put(key, false);
	}
	
	public void unregisterKey(int key)
	{
		this.registeredKeys.remove(key);
		this.lastKeyStates.remove(key);
		this.currentKeyStates.remove(key);
	}
	
	public List<Integer> getRegisteredKeys()
	{
		return this.registeredKeys;
	}
	
	public void update()
	{
		for(Iterator<Integer> it = this.registeredKeys.iterator(); it.hasNext();)
		{
			int key = it.next();
			this.lastKeyStates.put(key, this.currentKeyStates.get(key));
		}
	}
	
	public boolean hasActiveKeys()
	{
		return !this.lastKeyStates.containsValue(true) && !this.currentKeyStates.containsValue(true);
	}
	
	public void setKey(int key, boolean value)
	{
		this.currentKeyStates.put(key, value);
	}
	
	public boolean isPressed(int key)
	{
		return !this.lastKeyStates.get(key) && this.currentKeyStates.get(key);
	}
	
	public boolean isHold(int key)
	{
		return this.lastKeyStates.get(key) || this.currentKeyStates.get(key);
	}
	
	public boolean isReleased(int key)
	{
		return this.lastKeyStates.get(key) && !this.currentKeyStates.get(key);
	}
}
