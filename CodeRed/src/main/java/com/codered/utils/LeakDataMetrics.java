package com.codered.utils;

import java.io.PrintStream;

public class LeakDataMetrics
{
	public int activeTextures = 0;
	public int activeBuffers = 0;
	public int activeVaos = 0;
	public int activePrograms = 0;
	public int activeShaders = 0;
	public int activeFramebuffers = 0;
	public int activeRenderbuffers = 0;
	
	public void report(PrintStream stream)
	{
		stream.println("ACTIVE_TEXTURES: " + activeTextures);
		stream.println("ACTIVE_BUFFERS: " + activeBuffers);
		stream.println("ACTIVE_VAOS: " + activeVaos);
		stream.println("ACTIVE_PROGRAMS: " + activePrograms);
		stream.println("ACTIVE_SHADERS: " + activeShaders);
		stream.println("ACTIVE_FRAMEBUFFERS: " + activeFramebuffers);
		stream.println("ACTIVE_RENDERBUFFERS: " + activeRenderbuffers);
	}
}
