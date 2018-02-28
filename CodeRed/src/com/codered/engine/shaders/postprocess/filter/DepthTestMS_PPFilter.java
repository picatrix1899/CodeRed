package com.codered.engine.shaders.postprocess.filter;

import com.codered.engine.shader.Shader.Attrib;
import com.codered.engine.shader.Shader.FragmentShader;
import com.codered.engine.shader.Shader.VertexShader;

@VertexShader("ppf_depthTestMS")
@FragmentShader("ppf_depthTestMS")
@Attrib(pos=0, var="pos")
public class DepthTestMS_PPFilter extends PPFShader
{

	protected void getAllUniformLocations()
	{
		addUniform("frameSrc");
		addUniform("frameDst");
		addUniform("depthSrc");
		addUniform("depthDst");
		addUniform("near");
		addUniform("far");
	}
	
	public void use()
	{
		start();
		loadTextureMS("frameSrc", 0, (int) getInput("frameSrc"));
		loadTextureMS("frameDst", 1, (int) getInput("frameDst"));
		loadTextureMS("depthSrc", 2, (int) getInput("depthSrc"));
		loadTextureMS("depthDst", 3, (int) getInput("depthDst"));
		loadFloat("near", (float) getInput("near"));
		loadFloat("far", (float) getInput("far"));
	}

}
