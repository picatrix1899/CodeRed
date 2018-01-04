package com.codered.engine.shaders.postprocess.filter;

import com.codered.engine.shaders.shader.Shader.Attrib;
import com.codered.engine.shaders.shader.Shader.FragmentShader;
import com.codered.engine.shaders.shader.Shader.VertexShader;

@VertexShader("ppf/ppf_blurVertical")
@FragmentShader("ppf/ppf_blur")
@Attrib(pos=0, var="pos")
public class BlurV_PPFilter extends PPFShader
{
	
	
	public static final BlurV_PPFilter instance = new BlurV_PPFilter();


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
