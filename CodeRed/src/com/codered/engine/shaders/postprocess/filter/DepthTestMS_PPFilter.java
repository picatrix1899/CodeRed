package com.codered.engine.shaders.postprocess.filter;

import com.codered.engine.shaders.shader.Shader.Attrib;
import com.codered.engine.shaders.shader.Shader.FragmentShader;
import com.codered.engine.shaders.shader.Shader.VertexShader;

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
		loadTexture("frameSrc", 0, (int) getInput("frameSrc"));
		loadTexture("frameDst", 1, (int) getInput("frameDst"));
		loadTexture("depthSrc", 2, (int) getInput("depthSrc"));
		loadTexture("depthDst", 3, (int) getInput("depthDst"));
		loadFloat("near", (float) getInput("near"));
		loadFloat("far", (float) getInput("far"));
	}

}
