package com.codered.engine.input;

import java.util.ArrayList;
import java.util.Collection;

public class KeyResponse
{
	private ArrayList<Integer> keys = new ArrayList<Integer>();
	
	public KeyResponse() { }
	
	public KeyResponse(Collection<Integer> keys)
	{
		this.keys.addAll(keys);
	}
	
	public boolean keyPresent(int key)
	{
		return this.keys.contains(key);
	}
	
	public boolean keyPresent(Key key)
	{
		return this.keys.contains(key.getId());
	}
	
	public KeyResponse clone()
	{
		KeyResponse out = new KeyResponse();
		
		for(int key : keys)
		{
			out.keys.add(key);
		}
		
		return out;
	}
}
