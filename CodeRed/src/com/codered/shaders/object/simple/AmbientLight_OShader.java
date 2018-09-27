package com.codered.shaders.object.simple;

import java.util.List;

import com.codered.shader.UniformAmbientLight;
import com.codered.shader.UniformVector3;
import com.codered.window.WindowContext;

import cmn.utilslib.dmap.dmaps.DMap2;
import cmn.utilslib.math.vector.Vector3f;


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
	
	public void use()
	{
		start();
		
		super.use();
		
		this.u_skyColor.set(Vector3f.ZERO);
		
		this.u_material.load();
		this.u_ambientLight.load();
		this.u_skyColor.load();
	}



	public void attachShaderParts()
	{
		attachVertexShader(this.context.getShaderParts().builtIn().getVertexShader("o_ambientLight"));
		attachFragmentShader(this.context.getShaderParts().builtIn().getFragmentShader("o_ambientLight"));
	}


	public void getAttribs(List<DMap2<Integer,String>> attribs)
	{
		attribs.add(new DMap2<Integer,String>(0, "vertexPos"));
		attribs.add(new DMap2<Integer,String>(1, "texCoords"));
		attribs.add(new DMap2<Integer,String>(2, "normal"));
		attribs.add(new DMap2<Integer,String>(3, "tangent"));
	}
	
}
