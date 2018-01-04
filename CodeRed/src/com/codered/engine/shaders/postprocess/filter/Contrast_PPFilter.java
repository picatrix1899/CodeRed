package com.codered.engine.shaders.postprocess.filter;

import com.codered.engine.shaders.shader.Shader.Attrib;
import com.codered.engine.shaders.shader.Shader.FragmentShader;
import com.codered.engine.shaders.shader.Shader.VertexShader;

@VertexShader("ppf/ppf_contrast")
@FragmentShader("ppf/ppf_contrast")
@Attrib(pos=0, var="pos")
public class Contrast_PPFilter extends PPFShader
{

	protected void getAllUniformLocations()
	{
		addUniform("frame");
		addUniform("contrast");
	}
	
	public void use()
	{
		start();
		loadTexture("frame", 0, getInput("frame"));
		loadFloat("contrast", getInput("contrast"));
	}

}
