package com.codered.engine.shaders.gui;

import com.codered.engine.Time;
import com.codered.engine.shaders.shader.Shader.Attrib;
import com.codered.engine.shaders.shader.Shader.FragmentShader;
import com.codered.engine.shaders.shader.Shader.VertexShader;

@VertexShader("gui/gui_noShading")
@FragmentShader("gui/gui_noShading")
@Attrib(pos=0, var="vertexPos")
@Attrib(pos=1, var="texCoords")
public class No_GUIShader extends Base_GUIShader
{
	
	public static final No_GUIShader instance = new No_GUIShader();
	
	
	protected void getAllUniformLocations()
	{
		super.getAllUniformLocations();
		addUniform("time");
	}

	public void use()
	{
		super.use();
		loadInt("time", (int) Time.getTime());
	}
}
