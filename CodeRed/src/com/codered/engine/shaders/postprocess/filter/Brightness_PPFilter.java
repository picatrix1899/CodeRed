package com.codered.engine.shaders.postprocess.filter;

import com.codered.engine.shader.Shader.Attrib;
import com.codered.engine.shader.Shader.FragmentShader;
import com.codered.engine.shader.Shader.VertexShader;

@VertexShader("ppf_brightness")
@FragmentShader("ppf_brightness")
@Attrib(pos=0, var="pos")
public class Brightness_PPFilter extends PPFShader
{
	
	

	


	protected void getAllUniformLocations()
	{
		addUniform("frame");
	}
	
	public void use()
	{
		start();
		loadTexture("frame", 0, getInput("frame"));
	}

}
