package com.codered;

import java.io.Closeable;

public class MemoryWatchdog
{
	private static MemorySession last;
	
	public static MemorySession start()
	{
		MemorySession s = new MemorySession();
		last = s;
		s.start();
		return s; 
	}
	
	public static long getLastDelta()
	{
		if(last == null) return 0l;
		
		return last.getDelta();
	}
	
	public static class MemorySession implements Closeable
	{
		private long oldMemory;
		private long newMemory;
		
		public void start()
		{
			oldMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		}
		
		public void close()
		{
			newMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		}
		
		public long getDelta()
		{
			return newMemory - oldMemory;
		}
	}
			
	
}
