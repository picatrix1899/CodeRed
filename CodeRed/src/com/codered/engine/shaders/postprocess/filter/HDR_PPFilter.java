package com.codered.engine.shaders.postprocess.filter;

import com.codered.engine.shader.Shader.Attrib;
import com.codered.engine.shader.Shader.FragmentShader;
import com.codered.engine.shader.Shader.VertexShader;

@VertexShader("ppf_hdr")
@FragmentShader("ppf_hdr")
@Attrib(pos=0, var="pos")
public class HDR_PPFilter extends PPFShader
{
	

	
	


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
