package com.codered.utils;

import com.codered.window.WindowContext;

public class WindowContextHelper
{
	public static WindowContext getCurrentContext()
	{
		return WindowContext.getInstance();
	}
	
	public static WindowContext getCurrentContextImpl()
	{
		return WindowContext.getInstance();
	}
}
