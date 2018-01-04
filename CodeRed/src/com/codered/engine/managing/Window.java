package com.codered.engine.managing;

import java.util.HashMap;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;

import com.codered.engine.Input;
import com.google.common.collect.Maps;

import cmn.utilslib.math.Maths;
import cmn.utilslib.math.matrix.Matrix4f;
import cmn.utilslib.math.vector.Vector2f;
import cmn.utilslib.math.vector.api.Vec2f;

public class Window
{
	
	public static HashMap<String, Window> windows = Maps.newHashMap();
	
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
	
	public GLFWErrorCallback err;
	
	private boolean isCreated;
	
	private Input input;
	
	public Window(String id, int width, int height, String title)
	{
		
		WIDTH = width;
		HEIGHT = height;
		
		GLFW.glfwInit();
		
		GLFW.glfwSetErrorCallback(err = GLFWErrorCallback.createPrint(System.err));
		
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 4);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
		GLFW.glfwWindowHint(GLFW.GLFW_DEPTH_BITS, 24);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
		
		
		window = GLFW.glfwCreateWindow(WIDTH, HEIGHT, title, 0, 0);
		
		if(window == 0)
		{
			System.exit(-1);
		}
		
		GLFW.glfwMakeContextCurrent(window);
		capabilities = GL.createCapabilities();
		
		GLFW.glfwShowWindow(window);
		
		isCreated = true;
		
		createProjectionMatrix();
		
		windows.put(id, this);
		this.input = new Input(this);
		
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
		
		input.update();
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
		for (Window w : windows.values())
		{
			w.closeDisplay();
		}
	}
}
