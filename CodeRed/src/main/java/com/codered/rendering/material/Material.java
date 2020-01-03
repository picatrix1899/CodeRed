package com.codered.rendering.material;

import com.codered.rendering.texture.Texture;

public class Material
{
	private Texture diffuseMap;
	private Texture normalMap;

	private float specularIntensity;
	private float specularPower;
	
	public Material(Texture diffuseMap, Texture normalMap, float power, float intensity)
	{
		this.diffuseMap = diffuseMap;
		this.normalMap = normalMap;
		
		this.specularPower = power;
		this.specularIntensity = intensity;
	}
	
	public Texture getAlbedoMap()
	{
		return this.diffuseMap;
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
	
	public void release()
	{
		if(this.diffuseMap != null) this.diffuseMap.cleanup();
		if(this.normalMap != null) this.normalMap.cleanup();
	}
}
