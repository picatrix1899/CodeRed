package com.codered.engine.shaders.postprocess.filter;

import com.codered.engine.shader.Shader.Attrib;
import com.codered.engine.shader.Shader.FragmentShader;
import com.codered.engine.shader.Shader.VertexShader;

@VertexShader("ppf_depthMap")
@FragmentShader("ppf_depthMap")
@Attrib(pos=0, var="pos")
public class DepthMap_PPFilter extends PPFShader
{
	

	


	protected void getAllUniformLocations()
	{
		addUniform("frame");
		addUniform("near");
		addUniform("far");
	}
	
	public void use()
	{
		start();
		loadTexture("frame", 0, (int) getInput("frame"));
		loadFloat("near", (float) getInput("near"));
		loadFloat("far", (float) getInput("far"));
	}

}
