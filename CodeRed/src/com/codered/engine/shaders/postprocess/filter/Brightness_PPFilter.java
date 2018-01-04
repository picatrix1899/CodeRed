package com.codered.engine.shaders.postprocess.filter;

import com.codered.engine.shaders.shader.Shader.Attrib;
import com.codered.engine.shaders.shader.Shader.FragmentShader;
import com.codered.engine.shaders.shader.Shader.VertexShader;

@VertexShader("ppf/ppf_brightness")
@FragmentShader("ppf/ppf_brightness")
@Attrib(pos=0, var="pos")
public class Brightness_PPFilter extends PPFShader
{
	
	
	public static final Brightness_PPFilter instance = new Brightness_PPFilter();
	


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
