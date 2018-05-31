package com.codered.engine.utils;

import com.codered.engine.window.WindowContext;
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
}
