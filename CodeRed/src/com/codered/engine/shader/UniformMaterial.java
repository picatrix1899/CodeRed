package com.codered.engine.shader;

import com.codered.engine.managing.Material;
import com.codered.engine.window.IWindowContext;

public class UniformMaterial extends Uniform
{

	private int startIndex;
	
	private Material material;

	public UniformMaterial(String name, int startIndex, IWindowContext context, ShaderProgram shader)
	{
		super(name, context, shader);
		this.startIndex = startIndex;
	}

	public void getUniformLocations()
	{
		addUniform(this.name + ".albedoMap");
		addUniform(this.name + ".normalMap");
		addUniform(this.name + ".specularPower");
		addUniform(this.name + ".specularIntensity");
	}

	public void load()
	{
		loadTexture(this.name + ".albedoMap", this.startIndex + 0, this.context.getResourceManager().getTexture(material.getColorMap()));
	
		if(this.material.hasNormalMap()) loadTexture(this.name + ".normalMap", this.startIndex + 1, this.context.getResourceManager().getTexture(material.getNormalMap()));
	
		loadFloat(this.name + ".specularPower", this.material.getSpecularPower());
		loadFloat(this.name + ".specularIntensity", this.material.getSpecularIntensity());
	}
	
	public void set(Material material)
	{
		this.material = material;
	}
}
