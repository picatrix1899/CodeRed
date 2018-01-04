package com.codered.demo;

import org.lwjgl.glfw.GLFW;

public class GlobalSettings
{

	public static float moveSpeed_forward	= 0.1f;
	public static float moveSpeed_back		= 0.1f;
	public static float moveSpeed_left		= 0.1f;
	public static float moveSpeed_right		= 0.1f;
	public static float moveSpeed_jump		= 0.7f;

	public static float camSpeed_yaw		= 0.06f;
	public static float camSpeed_pitch		= 0.06f;
	
	public static final float ground_accelerate		= 0.10f;
	public static final float air_accelerate		= 0.10f;
	public static final float max_ground_velocity	= 0.10f;
	public static final float max_air_velocity		= 0.10f;
	public static final float friction				= 4.0f;
	public static final float max_jumphight			= 11.0f;
	
	public static class Keys
	{
		public static int k_forward		= GLFW.GLFW_KEY_W;
		public static int k_back		= GLFW.GLFW_KEY_S;
		public static int k_left		= GLFW.GLFW_KEY_A;
		public static int k_right		= GLFW.GLFW_KEY_D;
		public static int k_up			= GLFW.GLFW_KEY_SPACE;
		public static int k_exit		= GLFW.GLFW_KEY_ESCAPE;
		public static int k_turnLeft	= GLFW.GLFW_KEY_Q;
		public static int k_turnRight	= GLFW.GLFW_KEY_E;
		
		public static int b_moveCam		= 2;
	}
	
}
