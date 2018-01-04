package com.codered.engine.shaders;

import com.codered.engine.shaders.terrain.simple.AmbientLight_TShader;
import com.codered.engine.shaders.terrain.simple.DirectionalLight_N_TShader;
import com.codered.engine.shaders.terrain.simple.DirectionalLight_TShader;
import com.codered.engine.shaders.terrain.simple.No_TShader;
import com.codered.engine.shaders.terrain.simple.PointLight_N_TShader;

public class STShaders
{
	private STShaders() { }
	
	public static final AmbientLight_TShader AmbientLight = new AmbientLight_TShader();
	public static final DirectionalLight_N_TShader DirectionalLight_N = new DirectionalLight_N_TShader();
	public static final DirectionalLight_TShader DirectionalLight = new DirectionalLight_TShader();
	public static final No_TShader No = new No_TShader();
	public static final PointLight_N_TShader PointLight_N = new PointLight_N_TShader();
	
}
