package com.codered.shader;

import com.codered.material.Material;

public class UniformMaterial extends Uniform
{

	private int startIndex;

	public UniformMaterial(String name, int startIndex)
	{
		super(name);
		this.startIndex = startIndex;
		
		addUniform("albedoMap");
		addUniform("normalMap");
		addUniform("specularPower");
		addUniform("specularIntensity");
	}

	public void set(Material material)
	{
		loadTexture("albedoMap", this.startIndex + 0, material.getAlbedoMap());
		
		if(material.hasNormalMap()) loadTexture("normalMap", this.startIndex + 1, material.getNormalMap());
	
		loadFloat("specularPower", material.getSpecularPower());
		loadFloat("specularIntensity", material.getSpecularIntensity());
	}
}
