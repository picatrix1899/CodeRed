package com.codered.demo;

import com.codered.engine.input.Key;

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
		public static int k_forward		= Key.W.getId();
		public static int k_back		= Key.S.getId();
		public static int k_left		= Key.A.getId();
		public static int k_right		= Key.D.getId();
		public static int k_up			= Key.SPACE.getId();
		public static int k_exit		= Key.ESCAPE.getId();
		public static int k_turnLeft	= Key.Q.getId();
		public static int k_turnRight	= Key.E.getId();
		public static int k_delete		= Key.DELETE.getId();
		
		public static int b_moveCam		= 2;
	}
	
}
