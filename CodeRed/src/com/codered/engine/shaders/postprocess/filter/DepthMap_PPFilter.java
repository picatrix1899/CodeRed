package com.codered.engine.shaders.postprocess.filter;

import com.codered.engine.shaders.shader.Shader.Attrib;
import com.codered.engine.shaders.shader.Shader.FragmentShader;
import com.codered.engine.shaders.shader.Shader.VertexShader;

@VertexShader("ppf/ppf_depthMap")
@FragmentShader("ppf/ppf_depthMap")
@Attrib(pos=0, var="pos")
public class DepthMap_PPFilter extends PPFShader
{
	
	public static final DepthMap_PPFilter instance = new DepthMap_PPFilter();
	


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
