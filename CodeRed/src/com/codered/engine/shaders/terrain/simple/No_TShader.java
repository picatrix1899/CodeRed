package com.codered.engine.shaders.terrain.simple;

import java.util.List;

import com.codered.engine.managing.Material;
import com.codered.engine.shaders.terrain.SimpleTerrainShader;
import com.codered.engine.window.WindowContext;

import cmn.utilslib.dmap.dmaps.DMap2;

public class No_TShader extends SimpleTerrainShader
{

	public No_TShader(WindowContext context)
	{
		super(context);
	}

	protected void getAllUniformLocations()
	{
		super.getAllUniformLocations();

		addUniform("textureMap");
	}
	
	public void loadMaterial(Material m) { setInput("material", m); }
	
	private void loadMaterial0(Material mat)
	{
		loadTexture("textureMap", 0, mat.getAlbedoMap());
	}

	public void use()
	{
		start();
		
		super.use();
		
		loadMaterial0(getInput("material"));
	}
	
	public void attachShaderParts()
	{
		attachVertexShader(this.context.getShaderParts().builtIn().getVertexShader("t_no"));
		attachFragmentShader(this.context.getShaderParts().builtIn().getFragmentShader("t_no"));
	}

	public void getAttribs(List<DMap2<Integer,String>> attribs)
	{
		attribs.add(new DMap2<Integer,String>(0, "vertexPos"));
		attribs.add(new DMap2<Integer,String>(1, "texCoords"));
	}
}
