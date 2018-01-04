package com.codered.engine.shaders.postprocess.filter;

import com.codered.engine.shaders.shader.Shader.Attrib;
import com.codered.engine.shaders.shader.Shader.FragmentShader;
import com.codered.engine.shaders.shader.Shader.VertexShader;

@VertexShader("ppf/ppf_camerastrips")
@FragmentShader("ppf/ppf_camerastripsVertical")
@Attrib(pos=0, var="pos")
public class CameraStripsVertical_PPFilter extends PPFShader
{
	

	


	protected void getAllUniformLocations()
	{
		addUniform("frame");
		addUniform("width");
		addUniform("stripWidth");
		addUniform("intensity");
	}
	
	public void use()
	{
		start();
		loadTexture("frame", 0, getInput("frame"));
		loadInt("width", getInput("width"));
		loadFloat("stripWidth", getInput("stripWidth"));
		loadFloat("intensity", getInput("intensity"));
	}

}
