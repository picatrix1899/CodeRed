package com.codered.material;

import com.codered.texture.Texture;

public class Material
{
	private Texture albedoMap;
	private Texture normalMap;

	private float specularIntensity;
	private float specularPower;
	
	public Material(Texture albedoMap, Texture normalMap, float power, float intensity)
	{
		this.albedoMap = albedoMap;
		this.normalMap = normalMap;
		
		this.specularPower = power;
		this.specularIntensity = intensity;
	}
	
	public Texture getAlbedoMap()
	{
		return this.albedoMap;
	}
	
	public Texture getNormalMap()
	{
		return this.normalMap;
	}

	public boolean hasNormalMap()
	{
		return this.normalMap != null;
	}
	
	public float getSpecularPower()
	{
		return this.specularPower;
	}
	
	public float getSpecularIntensity()
	{
		return this.specularIntensity;
	}
}
