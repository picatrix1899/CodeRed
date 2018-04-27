package com.codered.engine.shaders.object.simple;

import java.util.ArrayList;
import java.util.List;

import com.codered.engine.light.AmbientLight;
import com.codered.engine.managing.Material;
import com.codered.engine.shaders.object.SimpleObjectShader;
import com.codered.engine.window.IWindowContext;

import cmn.utilslib.dmap.dmaps.DMap2;
import cmn.utilslib.math.vector.Vector3f;


public class AmbientLight_OShader extends SimpleObjectShader
{
	public AmbientLight_OShader(IWindowContext context)
	{
		super(context);
		
		compile();
	}
	
	protected void getAllUniformLocations()
	{
		super.getAllUniformLocations();
		
		addUniform("textureMap");
		
		addUniform("ambientLight.base.color");
		addUniform("ambientLight.base.intensity");
		
		addUniform("skyColor");
	}
	

	
	public void loadAmbientLight(AmbientLight light)
	{
		setInput("ambientLight", light);
	}
	
	
	private void loadMaterial0(Material mat)
	{
		loadTexture("textureMap", 0, this.context.getResourceManager().getTexture(mat.getColorMap()).getId());
	}	
	
	private void loadAmbientLight0(AmbientLight light)
	{
		loadColor3("ambientLight.base.color", light.base.color);
		loadFloat("ambientLight.base.intensity", light.base.intensity);
	}
	
	public void use()
	{
		start();
		
		super.use();
	
		loadMaterial0(getInput("material"));
		loadAmbientLight0(getInput("ambientLight"));
		loadVector3("skyColor", new Vector3f(0.0f));
	}



	public void attachShaderParts()
	{
		attachVertexShader(this.context.getShaderParts().builtIn().getVertexShader("o_ambientLight"));
		attachFragmentShader(this.context.getShaderParts().builtIn().getFragmentShader("o_ambientLight"));
	}



	public List<DMap2<Integer,String>> getAttribs()
	{
		ArrayList<DMap2<Integer,String>> attribs = new ArrayList<DMap2<Integer,String>>();
		
		attribs.add(new DMap2<Integer,String>(0, "vertexPos"));
		attribs.add(new DMap2<Integer,String>(1, "texCoords"));
		attribs.add(new DMap2<Integer,String>(2, "normal"));
		attribs.add(new DMap2<Integer,String>(3, "tangent"));
		
		return attribs;
	}
	
}
