package com.codered.engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

import com.codered.ArgumentInterpreter;

public class EngineBootstrap
{
	private ArgumentInterpreter argInterpreter = new ArgumentInterpreter();

	private Engine engine;
	
	public EngineBootstrap(Engine engine)
	{
		this.engine = engine;
	}
	
	public void boot()
	{
		init();
		
		start();
	}
	
	protected void init()
	{
		initGLFW();
		setErrorCallback();
	}

	protected void initGLFW()
	{
		GLFW.glfwInit();
	}
	
	protected void setErrorCallback()
	{
		GLFW.glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
	}
	
	protected void start()
	{
		this.engine.start();
	}
	
	public void setArguments(String[] args) { this.argInterpreter.interpret(args); }
	
	public ArgumentInterpreter getArgInterpreter() { return this.argInterpreter; }
}
