package com.codered.engine.shaders.object.simple;

import com.codered.engine.managing.Material;
import com.codered.engine.shaders.object.SimpleObjectShader;
import com.codered.engine.shaders.shader.Shader.Attrib;
import com.codered.engine.shaders.shader.Shader.FragmentShader;
import com.codered.engine.shaders.shader.Shader.VertexShader;

@VertexShader("o_no")
@FragmentShader("o_no")
@Attrib(pos=0, var="vertexPos")
@Attrib(pos=1, var="texCoords")
public class No_OShader extends SimpleObjectShader
{

	protected void getAllUniformLocations()
	{
		super.getAllUniformLocations();
		
		addUniform("textureMap");
	}

	private void loadMaterial0(Material mat)
	{
		loadTexture("textureMap", 0, mat.getColorMap().getId());
	}
	
	public void use()
	{
		start();
		
		super.use();
		
		loadMaterial0(getInput("material"));
	}
	
}
