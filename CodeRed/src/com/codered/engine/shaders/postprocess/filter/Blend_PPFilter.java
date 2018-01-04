package com.codered.engine.shaders.postprocess.filter;

import com.codered.engine.shaders.shader.Shader.Attrib;
import com.codered.engine.shaders.shader.Shader.FragmentShader;
import com.codered.engine.shaders.shader.Shader.VertexShader;

@VertexShader("ppf/ppf_blend")
@FragmentShader("ppf/ppf_blend")
@Attrib(pos=0, var="pos")
public class Blend_PPFilter extends PPFShader
{
	

	

	protected void getAllUniformLocations()
	{
		addUniform("scene1");
		addUniform("scene2");
		addUniform("src");
		addUniform("dst");
		addUniform("op");
	}
	
	public void use()
	{
		start();
		loadTexture("scene1", 0, getInput("scene1"));
		loadTexture("scene2", 1, getInput("scene2"));
		loadInt("src", getInput("src"));
		loadInt("dst", getInput("dst"));
		loadInt("op", getInput("op"));
		
	}

}
