package com.codered.engine;

public class Time
{
	public static long SECOND = 1000000000l;
	
	private static double delta;
	private static int fps;
	private static int fps_cap = 30;
	
	public static long getTime() { return System.nanoTime(); }
	
	public static double getDelta() { return delta; }
	
	public static void setDelta(double delta) { Time.delta = delta; }
	
	public static void setFPS(int fps) { Time.fps = fps; }
	
	public static int getFPS() { return fps; }
	
	public static int getFPSCap() { return fps_cap; }
	
	public static void setFPSCap(int cap) { fps_cap = cap; }
}
