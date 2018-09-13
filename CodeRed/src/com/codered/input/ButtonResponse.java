package com.codered.input;

import java.util.ArrayList;
import java.util.Collection;

public class ButtonResponse
{
	private ArrayList<Integer> buttons = new ArrayList<Integer>();
	
	public ButtonResponse() { }
	
	public ButtonResponse(Collection<Integer> buttons)
	{
		this.buttons.addAll(buttons);
	}
	
	public boolean buttonPresent(int button)
	{
		return this.buttons.contains(button);
	}
	
	public boolean buttonPresent(MouseButton button)
	{
		return this.buttons.contains(button.getId());
	}
	
	public ButtonResponse clone()
	{
		ButtonResponse out = new ButtonResponse();
		
		for(int button : buttons)
		{
			out.buttons.add(button);
		}
		
		return out;
	}
}
