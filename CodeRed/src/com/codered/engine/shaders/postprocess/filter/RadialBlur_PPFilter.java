package com.codered.engine.shaders.postprocess.filter;

import com.codered.engine.shaders.shader.Shader.Attrib;
import com.codered.engine.shaders.shader.Shader.FragmentShader;
import com.codered.engine.shaders.shader.Shader.VertexShader;

@VertexShader("ppf/ppf_radialBlur")
@FragmentShader("ppf/ppf_radialBlur")
@Attrib(pos=0, var="pos")
public class RadialBlur_PPFilter extends PPFShader
{
	



	protected void getAllUniformLocations()
	{
		addUniform("frame");
		addUniform("delta");
		addUniform("amplitude");
		addUniform("cycles");
	}
	
	public void use()
	{
		start();
		loadTexture("frame", 0, getInput("frame"));
		loadInt("cycles", getInput("cycles"));
		loadFloat("delta", getInput("delta"));
		loadFloat("amplitude", getInput("amplitude"));
	}

}
