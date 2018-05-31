package com.codered.engine.shaders.object.simple;

import java.util.List;

import com.codered.engine.shader.UniformMaterial;
import com.codered.engine.shader.UniformPointLight;
import com.codered.engine.shaders.object.SimpleObjectShader;
import com.codered.engine.window.WindowContext;

import cmn.utilslib.dmap.dmaps.DMap2;

public class PointLight_N_OShader extends SimpleObjectShader
{

	public UniformMaterial u_material;
	public UniformPointLight u_pointLight;
	
	public PointLight_N_OShader(WindowContext context)
	{
		super(context);
		
		this.u_material = new UniformMaterial("material", 0, context, this);
		this.u_pointLight = new UniformPointLight("light", context, this);
		
		compile();
	
		getAllUniformLocations();
	}

	protected void getAllUniformLocations()
	{
		super.getAllUniformLocations();
		
		this.u_pointLight.getUniformLocations();
	}

	public void use()
	{
		start();
		
		super.use();
		
		this.u_material.set(getInput("material"));
		this.u_pointLight.set(getInput("pointLight"));
		
		this.u_material.load();
		this.u_pointLight.load();
	}

	public void attachShaderParts()
	{
		attachVertexShader(this.context.getShaderParts().builtIn().getVertexShader("o_pointLight_N"));
		attachFragmentShader(this.context.getShaderParts().builtIn().getFragmentShader("o_pointLight_N"));
	}

	public void getAttribs(List<DMap2<Integer,String>> attribs)
	{
		attribs.add(new DMap2<Integer,String>(0, "vertexPos"));
		attribs.add(new DMap2<Integer,String>(1, "texCoords"));
		attribs.add(new DMap2<Integer,String>(2, "normal"));
		attribs.add(new DMap2<Integer,String>(3, "tangent"));
	}
}
