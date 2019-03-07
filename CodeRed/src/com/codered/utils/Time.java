package com.codered.utils;

public class Time
{
	public final long SECOND = 1000000000l;
	
	private double delta;
	private int fps;
	private int fps_cap = 60;
	
	public long getTime() { return System.nanoTime(); }
	
	public double getDelta() { return delta; }
	
	public void setDelta(double delta) { this.delta = delta; }
	
	public void setFPS(int fps) { this.fps = fps; }
	
	public int getFPS() { return fps; }
	
	public int getFPSCap() { return fps_cap; }
	
	public void setFPSCap(int cap) { fps_cap = cap; }
}
