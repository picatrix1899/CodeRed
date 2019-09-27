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
	
	private static Map<Long, Integer> ACTIVE_TEXTURES = new HashMap<>();
	private static Map<Long, Integer> ACTIVE_BUFFERS = new HashMap<>();
	private static Map<Long, Integer> ACTIVE_VAOS = new HashMap<>();
	private static Map<Long, Integer> ACTIVE_PROGRAMS = new HashMap<>();
	private static Map<Long, Integer> ACTIVE_SHADERS = new HashMap<>();
	
	public static void updateWindowId()
	{
		if(detectLeaks) currentWindowId = EngineRegistry.getCurrentWindowContext().getWindowId();
	}
	
	public static void registerContext(long id)
	{
		if(detectLeaks)
		{
			ACTIVE_TEXTURES.put(id, 0);
			ACTIVE_BUFFERS.put(id, 0);
			ACTIVE_VAOS.put(id, 0);
			ACTIVE_PROGRAMS.put(id, 0);
			ACTIVE_SHADERS.put(id, 0);
		}
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
	
	public static int genTextures()
	{
		if(detectLeaks) ACTIVE_TEXTURES.put(currentWindowId, ACTIVE_TEXTURES.get(currentWindowId) + 1);
		return GL11.glGenTextures();
	}
	
	public static void genTextures(int[] textures)
	{
		if(detectLeaks) ACTIVE_TEXTURES.put(currentWindowId, ACTIVE_TEXTURES.get(currentWindowId) + textures.length);
		GL11.glGenTextures(textures);
	}
	
	public static void deleteTextures(int texture)
	{
		if(detectLeaks) ACTIVE_TEXTURES.put(currentWindowId, ACTIVE_TEXTURES.get(currentWindowId) - 1);
		GL11.glDeleteTextures(texture);
	}
	
	public static void deleteTextures(int[] textures)
	{
		if(detectLeaks) ACTIVE_TEXTURES.put(currentWindowId, ACTIVE_TEXTURES.get(currentWindowId) - textures.length);
		GL11.glDeleteTextures(textures);
	}
	
	public static int genBuffers()
	{
		if(detectLeaks) ACTIVE_BUFFERS.put(currentWindowId, ACTIVE_BUFFERS.get(currentWindowId) + 1);
		return GL15.glGenBuffers();
	}
	
	public static void deleteBuffers(int buffer)
	{
		if(detectLeaks) ACTIVE_BUFFERS.put(currentWindowId, ACTIVE_BUFFERS.get(currentWindowId) - 1);
		GL15.glDeleteBuffers(buffer);
	}
	
	public static int genVertexArrays()
	{
		if(detectLeaks) ACTIVE_VAOS.put(currentWindowId, ACTIVE_VAOS.get(currentWindowId) + 1);
		return GL30.glGenVertexArrays();
	}
	
	public static void deleteVertexArrays(int array)
	{
		if(detectLeaks) ACTIVE_VAOS.put(currentWindowId, ACTIVE_VAOS.get(currentWindowId) - 1);
		GL30.glDeleteVertexArrays(array);
	}
	
	public static int createProgram()
	{
		if(detectLeaks) ACTIVE_PROGRAMS.put(currentWindowId, ACTIVE_PROGRAMS.get(currentWindowId) + 1);
		return GL20.glCreateProgram();
	}
	
	public static void deleteProgram(int program)
	{
		if(detectLeaks) ACTIVE_PROGRAMS.put(currentWindowId, ACTIVE_PROGRAMS.get(currentWindowId) - 1);
		GL20.glDeleteProgram(program);
	}
	
	public static int createShader(int type)
	{
		if(detectLeaks) ACTIVE_SHADERS.put(currentWindowId, ACTIVE_SHADERS.get(currentWindowId) + 1);
		return GL20.glCreateShader(type);
	}
	
	public static void deleteShader(int shader)
	{
		if(detectLeaks) ACTIVE_SHADERS.put(currentWindowId, ACTIVE_SHADERS.get(currentWindowId) - 1);
		GL20.glDeleteShader(shader);
	}
	
	
	public static void report(PrintStream stream)
	{
		stream.println("ACTIVE_WINDOWS: " + ACTIVE_WINDOWS);
		stream.println("ACTIVE_TEXTURES: " + ACTIVE_TEXTURES);
		stream.println("ACTIVE_BUFFERS: " + ACTIVE_BUFFERS);
		stream.println("ACTIVE_VAOS: " + ACTIVE_VAOS);
		stream.println("ACTIVE_PROGRAMS: " + ACTIVE_PROGRAMS);
		stream.println("ACTIVE_SHADERS: " + ACTIVE_SHADERS);
	}
}
