package com.codered.shaders.object.simple;

import java.util.List;

import com.codered.shader.UniformDirectionalLight;
import com.codered.shader.UniformMaterial;
import com.codered.shaders.object.SimpleObjectShader;
import com.codered.window.WindowContext;

import cmn.utilslib.dmap.dmaps.DMap2;

public class DirectionalLight_N_OShader extends SimpleObjectShader
{

	public UniformMaterial u_material = new UniformMaterial("material", 0);
	public UniformDirectionalLight u_directionalLight  = new UniformDirectionalLight("directionalLight");
	
	public DirectionalLight_N_OShader(WindowContext context)
	{
		super(context);
		
		addUniform(u_material);
		addUniform(u_directionalLight);
		
		compile();
		
		getAllUniformLocations();
	}
	
	public void use()
	{
		start();
		
		super.use();
		
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
