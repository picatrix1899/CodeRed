package com.codered.window;

import org.lwjgl.glfw.GLFW;

public class WindowHint
{
	public static void resizable(boolean resizable)
	{
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, resizable ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
	}
	
	public static void glVersion(int major, int minor)
	{
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, major);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, minor);
	}
	
	public static void glVersion(String version)
	{
		String[] parts = version.split("\\.");
		
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, Integer.parseInt(parts[0]));
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, Integer.parseInt(parts[1]));
	}
	
	public static void depthBits(int bits)
	{
		GLFW.glfwWindowHint(GLFW.GLFW_DEPTH_BITS, bits);
	}
	
	public static void glProfile(GLProfile profile)
	{
		int p = 0;
		
		switch(profile)
		{
		case ANY:
			p = GLFW.GLFW_OPENGL_ANY_PROFILE;
			break;
		case COMPAT:
			p = GLFW.GLFW_OPENGL_COMPAT_PROFILE;
			break;
		case CORE:
			p = GLFW.GLFW_OPENGL_CORE_PROFILE;
			break;
		}
		
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, p);
	}
	
	public static void samples(int samples)
	{
		GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, samples);
	}
	
	public static void doubleBuffering(boolean doublebuffering)
	{
		GLFW.glfwWindowHint(GLFW.GLFW_DOUBLEBUFFER, doublebuffering ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
	}
	
	public static void autoShowWindow(boolean auto)
	{
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, auto ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
	}
	
	public static enum GLProfile
	{
		ANY,
		COMPAT,
		CORE
	}
}
