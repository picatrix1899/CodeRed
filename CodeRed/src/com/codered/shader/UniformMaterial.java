package com.codered.shader;

import com.codered.material.Material;
import com.codered.window.WindowContext;

public class UniformMaterial extends Uniform
{

	private int startIndex;
	
	private Material material;

	public UniformMaterial(String name, int startIndex, WindowContext context, ShaderProgram shader)
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
		loadTexture(this.name + ".albedoMap", this.startIndex + 0, this.material.getAlbedoMap());
	
		if(this.material.hasNormalMap()) loadTexture(this.name + ".normalMap", this.startIndex + 1, this.material.getNormalMap());
	
		loadFloat(this.name + ".specularPower", this.material.getSpecularPower());
		loadFloat(this.name + ".specularIntensity", this.material.getSpecularIntensity());
	}
	
	public void set(Material material)
	{
		this.material = material;
	}
}
