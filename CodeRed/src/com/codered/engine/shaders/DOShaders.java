package com.codered.engine.shaders;

import com.codered.engine.shaders.object.derefered.AmbientLight_DOShader;
import com.codered.engine.shaders.object.derefered.DirectionalLight_DOShader;
import com.codered.engine.shaders.object.derefered.No_DOShader;
import com.codered.engine.shaders.object.derefered.PointLight_DOShader;

public class DOShaders
{
	private DOShaders() { }
	
	public static final AmbientLight_DOShader AmbientLight = new AmbientLight_DOShader();
	public static final DirectionalLight_DOShader DirectionalLight = new DirectionalLight_DOShader();
	public static final No_DOShader No = new No_DOShader();
	public static final PointLight_DOShader PointLight = new PointLight_DOShader();
}
