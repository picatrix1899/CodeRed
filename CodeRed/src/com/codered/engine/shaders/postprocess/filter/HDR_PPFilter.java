package com.codered.engine.shaders.postprocess.filter;

import com.codered.engine.shaders.shader.Shader.Attrib;
import com.codered.engine.shaders.shader.Shader.FragmentShader;
import com.codered.engine.shaders.shader.Shader.VertexShader;

@VertexShader("ppf/ppf_hdr")
@FragmentShader("ppf/ppf_hdr")
@Attrib(pos=0, var="pos")
public class HDR_PPFilter extends PPFShader
{
	
	public static final HDR_PPFilter instance = new HDR_PPFilter();
	
	


	protected void getAllUniformLocations()
	{
		addUniform("frame");
		addUniform("exposure");
	}
	
	public void use()
	{
		start();
		loadTexture("frame", 0, getInput("frame"));
		loadFloat("exposure", getInput("exposure"));
	}

}
