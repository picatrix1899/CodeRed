package com.codered.engine.shaders.object.simple;

import java.util.List;

import com.codered.engine.shader.UniformAmbientLight;
import com.codered.engine.shader.UniformMaterial;
import com.codered.engine.shader.UniformVector3;
import com.codered.engine.shaders.object.SimpleObjectShader;
import com.codered.engine.window.WindowContext;

import cmn.utilslib.dmap.dmaps.DMap2;
import cmn.utilslib.math.vector.Vector3f;


public class AmbientLight_OShader extends SimpleObjectShader
{
	
	public UniformMaterial u_material;
	public UniformAmbientLight u_ambientLight;
	public UniformVector3 u_skyColor;
	
	public AmbientLight_OShader(WindowContext context)
	{
		super(context);
		
		this.u_material = new UniformMaterial("material", 0, context, this);
		this.u_ambientLight = new UniformAmbientLight("ambientLight", context, this);
		this.u_skyColor = new UniformVector3("skyColor", context, this);
		
		compile();

		getAllUniformLocations();
	}
	
	protected void getAllUniformLocations()
	{
		super.getAllUniformLocations();

		this.u_material.getUniformLocations();
		this.u_ambientLight.getUniformLocations();
		this.u_skyColor.getUniformLocations();
	}
	
	public void use()
	{
		start();
		
		super.use();
	
		this.u_material.set(getInput("material"));
		this.u_ambientLight.set(getInput("ambientLight"));
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
