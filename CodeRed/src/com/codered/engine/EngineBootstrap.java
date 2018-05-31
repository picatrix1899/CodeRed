package com.codered.engine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Consumer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

import com.codered.engine.window.WindowImpl;

import cmn.utilslib.essentials.Auto;

public abstract class EngineBootstrap
{
	private ArgumentInterpreter argInterpreter = new ArgumentInterpreter();

	private WindowImpl window;
	
	public void boot()
	{
		initGLFW();
		setErrorCallback();
	}
	
	public void initGLFW()
	{
		GLFW.glfwInit();
	}
	
	public void setErrorCallback()
	{
		GLFW.glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
	}
	
	public void start()
	{
		Thread t = (Thread) this.window.getTickRoutine();

		t.start();
	}
	
	protected void setWindow(WindowImpl window)
	{
		this.window = window;
	}
	
	public void setArguments(String[] args) { this.argInterpreter.interpret(args); }
	
	public ArgumentInterpreter getArgInterpreter() { return this.argInterpreter; }
	
	public final class ArgumentInterpreter
	{
		
		private HashMap<String, Argument> interpreter = Auto.HashMap();
		
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
