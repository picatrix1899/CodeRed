package com.codered.engine.shaders.object.simple;

import com.codered.engine.light.Glow;
import com.codered.engine.managing.Material;
import com.codered.engine.shaders.object.SimpleObjectShader;
import com.codered.engine.shaders.shader.Shader.Attrib;
import com.codered.engine.shaders.shader.Shader.FragmentShader;
import com.codered.engine.shaders.shader.Shader.VertexShader;

@VertexShader("o_glow")
@FragmentShader("o_glow")
@Attrib(pos=0, var="vertexPos")
@Attrib(pos=1, var="texCoords")
public class Glow_OShader extends SimpleObjectShader
{

	protected void getAllUniformLocations()
	{
		super.getAllUniformLocations();
		
		addUniform("textureMap");	
		addUniform("glowMap");
		
		addUniform("glow.base.color");
		addUniform("glow.base.intensity");
		addUniform("glow.intensity");
		addUniform("glow.affect");
	}
	
	private void loadMaterial0(Material m)
	{
		loadTexture("glowMap", 0, m.getGlowMap().getId());
	}	
	
	private void loadGlow0(Glow glow)
	{
		loadColor3("glow.base.color", glow.base.color);
		loadFloat("glow.base.intensity", glow.base.intensity);
		loadFloat("glow.intensity", glow.intesity);
		loadFloat("glow.affect", glow.affect);
	}
	
	public void use()
	{
		start();
		
		super.use();
		
		loadMaterial0(getInput("material"));
		loadGlow0(getInput("glow"));
	}
	
}
