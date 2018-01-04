package com.codered.engine.shaders.postprocess.filter;

import com.codered.engine.shaders.shader.Shader.Attrib;
import com.codered.engine.shaders.shader.Shader.FragmentShader;
import com.codered.engine.shaders.shader.Shader.VertexShader;
@VertexShader("ppf/ppf_no")
@FragmentShader("ppf/ppf_no")
@Attrib(pos=0, var="pos")
public class No_PPFilter extends PPFShader
{
	
	public static final No_PPFilter instance = new No_PPFilter();

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
