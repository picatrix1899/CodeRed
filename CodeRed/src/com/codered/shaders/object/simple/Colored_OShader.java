package com.codered.shaders.object.simple;

import java.util.List;

import com.codered.engine.EngineRegistry;
import com.codered.shader.UniformColor3;
import com.codered.shaders.object.SimpleObjectShader;
import com.codered.window.WindowContext;

import cmn.utilslib.dmap.dmaps.DMap2;


public class Colored_OShader extends SimpleObjectShader
{

	public UniformColor3 u_color = new UniformColor3("color");
	
	
	
	public Colored_OShader(WindowContext context)
	{
		super(context);
		
		addUniform(u_color);
		
		compile();

		getAllUniformLocations();
	}

	public void attachShaderParts()
	{
		attachVertexShader(EngineRegistry.getShaderParts().builtIn().getVertexShader("o_colored"));
		attachFragmentShader(EngineRegistry.getShaderParts().builtIn().getFragmentShader("o_colored"));
	}

	public void getAttribs(List<DMap2<Integer,String>> attribs)
	{
		attribs.add(new DMap2<Integer,String>(0, "vertexPos"));
	}
	
}
