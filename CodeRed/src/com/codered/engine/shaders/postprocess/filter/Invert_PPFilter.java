package com.codered.engine.shaders.postprocess.filter;

import com.codered.engine.shaders.shader.Shader.Attrib;
import com.codered.engine.shaders.shader.Shader.FragmentShader;
import com.codered.engine.shaders.shader.Shader.VertexShader;

@VertexShader("ppf/ppf_invert")
@FragmentShader("ppf/ppf_invert")
@Attrib(pos=0, var="pos")
public class Invert_PPFilter extends PPFShader
{
	
	public static final Invert_PPFilter instance = new Invert_PPFilter();
	


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
