package com.codered.demo;

import com.codered.engine.input.InputConfiguration;
import com.codered.engine.input.Key;
import com.codered.engine.input.MouseButton;

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
		public static Key k_forward		= Key.W;
		public static Key k_back		= Key.S;
		public static Key k_left		= Key.A;
		public static Key k_right		= Key.D;
		public static Key k_up			= Key.SPACE;
		public static Key k_exit		= Key.ESCAPE;
		public static Key k_turnLeft	= Key.Q;
		public static Key k_turnRight	= Key.E;
		public static Key k_delete		= Key.DELETE;
		
		public static MouseButton b_moveCam		= MouseButton.MIDDLE;
		public static MouseButton b_fire		= MouseButton.LEFT;
	}
	
	public static InputConfiguration ingameInput;
	
}
