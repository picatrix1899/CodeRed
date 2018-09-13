package com.codered.utils;

import com.codered.window.WindowContext;
import com.codered.window.WindowContextImpl;
import com.codered.window.WindowTickRoutineImpl;

public class WindowContextHelper
{
	public static WindowContext getCurrentContext()
	{
		Thread t = Thread.currentThread();
		
		if(t instanceof WindowTickRoutineImpl)
		{
			return ((WindowTickRoutineImpl) t).getWindowContext();
		}
		
		return null;
	}
	
	public static WindowContextImpl getCurrentContextImpl()
	{
		Thread t = Thread.currentThread();
		
		if(t instanceof WindowTickRoutineImpl)
		{
			return (WindowContextImpl)((WindowTickRoutineImpl) t).getWindowContext();
		}
		
		return null;
	}
}
