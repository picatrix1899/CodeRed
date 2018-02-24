package com.codered.engine.managing;

import java.util.HashMap;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;

import com.codered.engine.input.Input;
import com.codered.engine.input.Input2;

import cmn.utilslib.events.EmptyArgs;
import cmn.utilslib.events.Event;

import cmn.utilslib.math.Maths;
import cmn.utilslib.math.matrix.Matrix4f;
import cmn.utilslib.math.vector.Vector2f;
import cmn.utilslib.math.vector.api.Vec2f;

public class Window
{
	
	public static HashMap<String, Window> named_windows = new HashMap<String, Window>();
	public static HashMap<Long, Window> id_windows = new HashMap<Long, Window>();
	
	public static Window active;
	
	public int WIDTH = 1600;
	public int HEIGHT = 1000;
	public int FPS_CAP = 100;
	
	public float FOVX = 60;
	public float FOVY = 45;
	public float NEAR = 0.1f;
	public float FAR = 1000;
	
	public Matrix4f projectionMatrix;
	
	public long window;
	
	public GLCapabilities capabilities;
	
	private boolean isCreated;
	
	private Input input;
	private Input2 input2;
	
	private String id;
	
	public Event<EmptyArgs> CloseRequested = new Event<EmptyArgs>();
	
	public Window(String id, int width, int height, String title)
	{
		
		WIDTH = width;
		HEIGHT = height;
	
		window = GLFW.glfwCreateWindow(WIDTH, HEIGHT, title, 0, 0);
		
		if(window == 0)
		{
			System.exit(-1);
		}
		
		GLFW.glfwMakeContextCurrent(window);
		capabilities = GL.createCapabilities();
		
		GLFW.glfwSetWindowFocusCallback(window, (a, b) -> {focusChanged(a,b);});
		
		GLFW.glfwShowWindow(window);
		
		isCreated = true;
		
		createProjectionMatrix();
		
		this.id = id;
		named_windows.put(id, this);
		id_windows.put(window, this);
		this.input = new Input(this);
		this.input2 = new Input2(this);
	}
	
	public String getId()
	{
		return this.id;
	}
	
	public void focusChanged(long id, boolean gain)
	{
		if(gain)
		{
			id_windows.get(id).bind();
		}
	}
	
	public void bind()
	{
		GLFW.glfwMakeContextCurrent(window);
		GLFW.glfwShowWindow(window);
		active = this;
	}
	
	public void updateDisplay()
	{
		GLFW.glfwPollEvents();
		GLFW.glfwSwapBuffers(window);
		
		if(GLFW.glfwWindowShouldClose(this.window)) this.CloseRequested.raise(EmptyArgs.getInstance());
		
		
		input.update();
		input2.update();
	}
	
	public void closeDisplay()
	{
		GLFW.glfwDestroyWindow(window);
		isCreated = false;
	}
	
	public Vec2f getSize()
	{
		return new Vector2f(WIDTH, HEIGHT).mul(1.0f);
	}
	
	public Input getInputManager()
	{
		return this.input;
	}
	
	public Input2 getInputManager2()
	{
		return this.input2;
	}
	
	public boolean isCreated()
	{
		return this.isCreated;
	}
	
	private void createProjectionMatrix()
	{
		float tanHalfFOVX = (float)Math.tan(Maths.DEG_TO_RAD * (FOVX / 2f));
		float tanHalfFOVY = (float)Math.tan(Maths.DEG_TO_RAD * (FOVY / 2f));
		float aspectRatio = (float) WIDTH / (float) HEIGHT;
		float y_scale = 1.0f / (tanHalfFOVY * aspectRatio);
		float x_scale = 1.0f / tanHalfFOVX;//y_scale / aspectRatio;
		float range = FAR - NEAR;
		
		projectionMatrix = new Matrix4f();
		
		projectionMatrix.m0.x = x_scale;
		projectionMatrix.m1.y = y_scale;
		projectionMatrix.m2.z = -((FAR + NEAR) / range); //-((FAR + NEAR) / frustrum_length);
		
		projectionMatrix.m2.a = -((2 * NEAR * FAR) / range);
		projectionMatrix.m3.a = 0;
		projectionMatrix.m3.z = -1;
	}
	
	public static void closeDisplays()
	{
		for (Window w : named_windows.values())
		{
			w.closeDisplay();
		}
	}
}
