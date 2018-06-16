package com.codered.engine.utils;

import com.codered.engine.window.WindowContext;
import com.codered.engine.window.WindowContextImpl;
import com.codered.engine.window.WindowTickRoutineImpl;

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
