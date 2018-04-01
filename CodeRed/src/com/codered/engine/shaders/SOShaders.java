package com.codered.engine.shaders;

import com.codered.engine.shaders.object.simple.AmbientLight_OShader;
import com.codered.engine.shaders.object.simple.Colored_OShader;
import com.codered.engine.shaders.object.simple.DirectionalLight_N_OShader;
import com.codered.engine.shaders.object.simple.DirectionalLight_OShader;
import com.codered.engine.shaders.object.simple.Glow_OShader;
import com.codered.engine.shaders.object.simple.No_OShader;
import com.codered.engine.shaders.object.simple.PointLight_N_OShader;

public class SOShaders
{
	public Colored_OShader Colored = new Colored_OShader();
	public AmbientLight_OShader AmbientLight = new AmbientLight_OShader();
	public DirectionalLight_N_OShader DirectionalLight_N = new DirectionalLight_N_OShader();
	public DirectionalLight_OShader DirectionalLight = new DirectionalLight_OShader();
	public Glow_OShader Glow = new Glow_OShader();
	public No_OShader No = new No_OShader();
	public PointLight_N_OShader PointLight_N = new PointLight_N_OShader();
}
