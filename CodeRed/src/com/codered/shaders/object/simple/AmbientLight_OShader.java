package com.codered.shaders.object.simple;

import java.util.List;

import com.codered.engine.EngineRegistry;
import com.codered.shader.UniformAmbientLight;
import com.codered.shader.UniformVector3;
import com.codered.window.WindowContext;

import cmn.utilslib.dmap.dmaps.DMap2;


public class AmbientLight_OShader extends TexturedObjectShader
{
	
	public UniformAmbientLight u_ambientLight = new UniformAmbientLight("ambientLight");
	public UniformVector3 u_skyColor = new UniformVector3("skyColor");
	
	public AmbientLight_OShader(WindowContext context)
	{
		super(context);
		
		addUniform(u_ambientLight);
		addUniform(u_skyColor);
		
		compile();

		getAllUniformLocations();
	}

	public void attachShaderParts()
	{
		attachVertexShader(EngineRegistry.getShaderParts().builtIn().getVertexShader("o_ambientLight"));
		attachFragmentShader(EngineRegistry.getShaderParts().builtIn().getFragmentShader("o_ambientLight"));
	}


	public void getAttribs(List<DMap2<Integer,String>> attribs)
	{
		attribs.add(new DMap2<Integer,String>(0, "vertexPos"));
		attribs.add(new DMap2<Integer,String>(1, "texCoords"));
		attribs.add(new DMap2<Integer,String>(2, "normal"));
		attribs.add(new DMap2<Integer,String>(3, "tangent"));
	}
	
}
