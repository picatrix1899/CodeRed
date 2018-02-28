package com.codered.engine.shaders.postprocess.filter;

import com.codered.engine.shader.Shader.Attrib;
import com.codered.engine.shader.Shader.FragmentShader;
import com.codered.engine.shader.Shader.VertexShader;

@VertexShader("ppf_blurVertical")
@FragmentShader("ppf_blur")
@Attrib(pos=0, var="pos")
public class BlurV_PPFilter extends PPFShader
{
	
	



	protected void getAllUniformLocations()
	{
		addUniform("frame");
		addUniform("targetHeight");
	}
	
	public void use()
	{
		start();
		loadTexture("frame", 0, getInput("frame"));
		loadFloat("targetHeight", getInput("targetHeight"));
	}
}
