package com.codered.shaders.object.simple;

import java.util.List;

import com.codered.shader.UniformPointLight;
import com.codered.window.WindowContext;

import cmn.utilslib.dmap.dmaps.DMap2;

public class PointLight_N_OShader extends TexturedObjectShader
{

	public UniformPointLight u_pointLight  = new UniformPointLight("light");
	
	public PointLight_N_OShader(WindowContext context)
	{
		super(context);
		
		addUniform(u_pointLight);
		
		compile();
	
		getAllUniformLocations();
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
