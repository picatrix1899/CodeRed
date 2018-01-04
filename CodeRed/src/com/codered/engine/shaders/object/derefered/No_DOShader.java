package com.codered.engine.shaders.object.derefered;

import com.codered.engine.shaders.object.DerefObjectShader;
import com.codered.engine.shaders.shader.Shader.Attrib;
import com.codered.engine.shaders.shader.Shader.FragmentShader;
import com.codered.engine.shaders.shader.Shader.VertexShader;

@VertexShader("object/derefered/do_noShading")
@FragmentShader("object/derefered/do_noShading")
@Attrib(pos=0, var="pos")
public class No_DOShader extends DerefObjectShader
{
	protected void getAllUniformLocations()
	{
		addUniform("albedo");
	}
	
	public void loadAlbedo(int i) { setInput("albedo", i); }
	
	
	public void use()
	{
		start();

		loadTexture("albedo", 2, getInput("albedo"));
	}

}
