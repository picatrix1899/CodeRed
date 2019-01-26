package com.codered;

import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Consumer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

import com.google.common.collect.Maps;

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
	
	public final class ArgumentInterpreter
	{
		
		private HashMap<String, Argument> interpreter = Maps.newHashMap();
		
		public void addArgument(String args, Argument destination)
		{
			this.interpreter.put(args.toLowerCase(), destination);
		}
		
		protected void interpret(String[] args)
		{
			int index = 0;
			
			String currentArg = "";
			String[] values = new String[0];
			
			while(index < args.length)
			{
				if(args[index].startsWith("-"))
				{	
					currentArg = args[index].substring(1, args[index].length()).toLowerCase();
					
					if(this.interpreter.containsKey(currentArg))
					{
						for(int i = index + 1; i < args.length; i++)
						{
							if(args[i].startsWith("-"))
							{
								if(i > index + 1)
								{
									values = Arrays.copyOfRange(args, index + 1, i);
									index = i - 1;
								}
								else
								{
									values = new String[0];
								}
								break;
							}
							else if(i == args.length - 1)
							{
								values = Arrays.copyOfRange(args, index + 1, i + 1);
								index = i;
							}
						}
						
						this.interpreter.get(currentArg).accept(values);
						values = new String[0];
					}
				}
				
				index++;
			}
		}
		

	}
	public interface Argument extends Consumer<String[]> { }
}
