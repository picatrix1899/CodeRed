package com.codered.engine.shaders.postprocess.filter;

import com.codered.engine.shaders.shader.Shader.Attrib;
import com.codered.engine.shaders.shader.Shader.FragmentShader;
import com.codered.engine.shaders.shader.Shader.VertexShader;

@VertexShader("ppf/ppf_depthTest")
@FragmentShader("ppf/ppf_depthTest")
@Attrib(pos=0, var="pos")
public class DepthTest_PPFilter extends PPFShader
{
	
	public static final DepthTest_PPFilter instance = new DepthTest_PPFilter();
	


	protected void getAllUniformLocations()
	{
		addUniform("frameSrc");
		addUniform("frameDst");
		addUniform("near");
		addUniform("far");
	}
	
	public void use()
	{
		start();
		loadTexture("frameSrc", 0, (int) getInput("frameSrc"));
		loadTexture("frameDst", 1, (int) getInput("frameDst"));
		loadFloat("near", (float) getInput("near"));
		loadFloat("far", (float) getInput("far"));
	}

}
