package com.codered.engine.shaders;

import com.codered.engine.shaders.object.simple.AmbientLight_OShader;
import com.codered.engine.shaders.object.simple.Colored_OShader;
import com.codered.engine.shaders.object.simple.Deref_OShader;
import com.codered.engine.shaders.object.simple.DirectionalLight_N_OShader;
import com.codered.engine.shaders.object.simple.DirectionalLight_OShader;
import com.codered.engine.shaders.object.simple.Glow_OShader;
import com.codered.engine.shaders.object.simple.No_OShader;
import com.codered.engine.shaders.object.simple.PointLight_N_OShader;

public class SOShaders
{
	private SOShaders() { }
	
	public static final Colored_OShader Colored = new Colored_OShader();
	public static final AmbientLight_OShader AmbientLight = new AmbientLight_OShader();
	public static final Deref_OShader Deref = new Deref_OShader();
	public static final DirectionalLight_N_OShader DirectionalLight_N = new DirectionalLight_N_OShader();
	public static final DirectionalLight_OShader DirectionalLight = new DirectionalLight_OShader();
	public static final Glow_OShader Glow = new Glow_OShader();
	public static final No_OShader No = new No_OShader();
	public static final PointLight_N_OShader PointLight_N = new PointLight_N_OShader();
}
