package com.codered.engine.shaders.postprocess.filter;

import com.codered.engine.shader.Shader.Attrib;
import com.codered.engine.shader.Shader.FragmentShader;
import com.codered.engine.shader.Shader.VertexShader;

@VertexShader("ppf_contrastMS")
@FragmentShader("ppf_contrastMS")
@Attrib(pos=0, var="pos")
public class ContrastMS_PPFilter extends PPFShader
{

	protected void getAllUniformLocations()
	{
		addUniform("frame");
		addUniform("contrast");
	}
	
	public void use()
	{
		start();
		loadTextureMS("frame", 0, getInput("frame"));
		loadFloat("contrast", getInput("contrast"));
	}

}
