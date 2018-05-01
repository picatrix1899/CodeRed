package com.codered.engine.shaders.terrain.simple;

import java.util.List;

import com.codered.engine.light.PointLight;
import com.codered.engine.managing.Material;
import com.codered.engine.shaders.terrain.SimpleTerrainShader;
import com.codered.engine.window.IWindowContext;

import cmn.utilslib.dmap.dmaps.DMap2;

public class PointLight_N_TShader extends SimpleTerrainShader
{

	public PointLight_N_TShader(IWindowContext context)
	{
		super(context);
	}

	protected void getAllUniformLocations()
	{
		super.getAllUniformLocations();
		
		addUniform("textureMap");		
		addUniform("normalMap");
		
		for(int i = 0; i < 4; i++)
		{
			addUniform("lights[" + i + "].base.color");
			addUniform("lights[" + i + "].base.intensity");
			addUniform("lights[" + i + "].position");
			addUniform("lights[" + i + "].attenuation.constant");
			addUniform("lights[" + i + "].attenuation.linear");
			addUniform("lights[" + i + "].attenuation.exponent");
		}

		addUniform("specularPower");
		addUniform("specularIntensity");
	}

	public void loadMaterial(Material m) { setInput("material", m); }

	public void loadPointLight(PointLight light) { setInput("pointLight", light); }
	
	private void loadMaterial0(Material mat)
	{
		loadTexture("textureMap", 0, this.context.getResourceManager().getTexture(mat.getColorMap()));
		loadTexture("normalMap", 1, this.context.getResourceManager().getTexture(mat.getNormalMap()));
	
		loadFloat("specularPower", mat.getSpecularPower());
		loadFloat("specularIntensity", mat.getSpecularIntensity());
	}
	
	private void loadPointLight0(PointLight light)
	{
		loadColor3("lights[0].base.color", light.base.color);
		loadFloat("lights[0].base.intensity", light.base.intensity);
		loadVector3("lights[0].position", light.position);
		loadFloat("lights[0].attenuation.constant",light.attenuation.constant);
		loadFloat("lights[0].attenuation.linear",light.attenuation.linear);
		loadFloat("lights[0].attenuation.exponent",light.attenuation.exponent);
	}
	

	
	public void use()
	{
		start();
		
		super.use();
		
		loadMaterial0(getInput("material"));
		
		loadPointLight0(getInput("pointLight"));
	}
	
	public void attachShaderParts()
	{
		attachVertexShader(this.context.getShaderParts().builtIn().getVertexShader("t_pointLight_N"));
		attachFragmentShader(this.context.getShaderParts().builtIn().getFragmentShader("t_pointLight_N"));
	}

	public void getAttribs(List<DMap2<Integer,String>> attribs)
	{
		attribs.add(new DMap2<Integer,String>(0, "vertexPos"));
		attribs.add(new DMap2<Integer,String>(1, "texCoords"));
		attribs.add(new DMap2<Integer,String>(2, "normal"));
		attribs.add(new DMap2<Integer,String>(3, "tangent"));
	}
}
