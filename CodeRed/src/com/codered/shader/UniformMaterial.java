package com.codered.shader;

import com.codered.material.Material;

public class UniformMaterial extends Uniform
{

	private int startIndex;
	
	private Material material;

	public UniformMaterial(String name, int startIndex)
	{
		super(name);
		this.startIndex = startIndex;
		
		addUniform("albedoMap");
		addUniform("normalMap");
		addUniform("specularPower");
		addUniform("specularIntensity");
	}

	public void load()
	{
		loadTexture("albedoMap", this.startIndex + 0, this.material.getAlbedoMap());
	
		if(this.material.hasNormalMap()) loadTexture("normalMap", this.startIndex + 1, this.material.getNormalMap());
	
		loadFloat("specularPower", this.material.getSpecularPower());
		loadFloat("specularIntensity", this.material.getSpecularIntensity());
	}
	
	public void set(Material material)
	{
		this.material = material;
	}
}
