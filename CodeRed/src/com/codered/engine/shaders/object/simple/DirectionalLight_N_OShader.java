package com.codered.engine.shaders.object.simple;

import java.util.List;

import com.codered.engine.shader.UniformDirectionalLight;
import com.codered.engine.shader.UniformMaterial;
import com.codered.engine.shaders.object.SimpleObjectShader;
import com.codered.engine.window.IWindowContext;

import cmn.utilslib.dmap.dmaps.DMap2;

public class DirectionalLight_N_OShader extends SimpleObjectShader
{

	public UniformMaterial u_material;
	public UniformDirectionalLight u_directionalLight;
	
	public DirectionalLight_N_OShader(IWindowContext context)
	{
		super(context);
		
		this.u_material = new UniformMaterial("material", 0, context, this);
		this.u_directionalLight = new UniformDirectionalLight("directionalLight", context, this);
		
		compile();
		
		getAllUniformLocations();
	}

	protected void getAllUniformLocations()
	{
		super.getAllUniformLocations();
		
		this.u_material.getUniformLocations();
		this.u_directionalLight.getUniformLocations();
	}

	public void use()
	{
		start();
		
		super.use();
		
		this.u_material.set(getInput("material"));
		this.u_directionalLight.set(getInput("directionalLight"));
		
		this.u_material.load();
		this.u_directionalLight.load();
	}

	public void attachShaderParts()
	{
		attachVertexShader(this.context.getShaderParts().builtIn().getVertexShader("o_directionalLight_N"));
		attachFragmentShader(this.context.getShaderParts().builtIn().getFragmentShader("o_directionalLight_N"));
	}

	public void getAttribs(List<DMap2<Integer,String>> attribs)
	{
		attribs.add(new DMap2<Integer,String>(0, "vertexPos"));
		attribs.add(new DMap2<Integer,String>(1, "textCoods"));
		attribs.add(new DMap2<Integer,String>(2, "normal"));
		attribs.add(new DMap2<Integer,String>(3, "tangent"));
	}
}
