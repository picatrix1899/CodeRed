package com.codered.utils;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.codered.engine.EngineRegistry;

public class GLCommon
{
	private static boolean detectLeaks;
	
	public static void isLeakDetectionEnabled(boolean b)
	{
		detectLeaks = b;
	}
	
	private static int ACTIVE_WINDOWS = 0;
	
	private static long currentWindowId = 0;
	
	private static Map<Long, LeakDataMetrics> metrics = new HashMap<>();
	
	public static void updateWindowId()
	{
		if(detectLeaks) currentWindowId = EngineRegistry.getCurrentWindowContext().getWindowId();
	}
	
	public static void registerContext(long id)
	{
		if(detectLeaks)
		{
			metrics.put(id, new LeakDataMetrics());
		}
	}
	
	private static LeakDataMetrics getCurrentMetrics()
	{
		return metrics.get(currentWindowId);
	}
	
	public static long createWindow(int width, int height, CharSequence title, long monitor, long share)
	{
		if(detectLeaks) ACTIVE_WINDOWS++;
		return GLFW.glfwCreateWindow(width, height, title, monitor, share);
	}
	
	public static void destroyWindow(long window)
	{
		if(detectLeaks) ACTIVE_WINDOWS--;
		GLFW.glfwDestroyWindow(window);
	}
	
	public static int genTexture()
	{
		if(detectLeaks) getCurrentMetrics().activeTextures++;
		return GL11.glGenTextures();
	}
	
	public static void genTextures(int[] textures)
	{
		if(detectLeaks) getCurrentMetrics().activeTextures += textures.length;
		GL11.glGenTextures(textures);
	}
	
	public static void deleteTexture(int texture)
	{
		if(detectLeaks) getCurrentMetrics().activeTextures--;
		GL11.glDeleteTextures(texture);
	}
	
	public static void deleteTextures(int[] textures)
	{
		if(detectLeaks) getCurrentMetrics().activeTextures -= textures.length;
		GL11.glDeleteTextures(textures);
	}
	
	public static int genBuffers()
	{
		if(detectLeaks) getCurrentMetrics().activeBuffers++;
		return GL15.glGenBuffers();
	}
	
	public static void deleteBuffers(int buffer)
	{
		if(detectLeaks) getCurrentMetrics().activeBuffers--;
		GL15.glDeleteBuffers(buffer);
	}
	
	public static int genVertexArrays()
	{
		if(detectLeaks) getCurrentMetrics().activeVaos++;
		return GL30.glGenVertexArrays();
	}
	
	public static void deleteVertexArrays(int array)
	{
		if(detectLeaks) getCurrentMetrics().activeVaos--;
		GL30.glDeleteVertexArrays(array);
	}
	
	public static int createProgram()
	{
		if(detectLeaks) getCurrentMetrics().activePrograms++;
		return GL20.glCreateProgram();
	}
	
	public static void deleteProgram(int program)
	{
		if(detectLeaks) getCurrentMetrics().activePrograms--;
		GL20.glDeleteProgram(program);
	}
	
	public static int createShader(int type)
	{
		if(detectLeaks) getCurrentMetrics().activeShaders++;
		return GL20.glCreateShader(type);
	}
	
	public static void deleteShader(int shader)
	{
		if(detectLeaks) getCurrentMetrics().activeShaders--;
		GL20.glDeleteShader(shader);
	}
	
	public static int genFramebuffer()
	{
		if(detectLeaks) getCurrentMetrics().activeFramebuffers++;
		return GL30.glGenFramebuffers();
	}
	
	public static void deleteFramebuffer(int framebuffer)
	{
		if(detectLeaks) getCurrentMetrics().activeFramebuffers--;
		GL30.glDeleteFramebuffers(framebuffer);
	}
	
	public static int genRenderbuffer()
	{
		if(detectLeaks) getCurrentMetrics().activeRenderbuffers++;
		return GL30.glGenRenderbuffers();
	}
	
	public static void deleteRenderbuffer(int renderbuffer)
	{
		if(detectLeaks) getCurrentMetrics().activeRenderbuffers--;
		GL30.glDeleteRenderbuffers(renderbuffer);
	}
	
	public static void report(PrintStream stream)
	{
		stream.println("ACTIVE_WINDOWS: " + ACTIVE_WINDOWS);
		for(long id : metrics.keySet())
		{
			stream.println("Window(" + id + ")");
			metrics.get(id).report(stream);
			stream.println();
		}
	}
}
