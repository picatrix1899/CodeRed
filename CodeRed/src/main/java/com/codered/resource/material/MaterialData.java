package com.codered.resource.material;

import com.codered.resource.texture.TextureData;

public class MaterialData
{
	private TextureData albedoMap;
	private TextureData normalMap;
	
	private float specularIntensity;
	private float specularPower;
	
	public MaterialData(TextureData albedoMap, TextureData normalMap, float power, float intensity)
	{
		this.albedoMap = albedoMap;
		this.normalMap = normalMap;
		
		this.specularPower = power;
		this.specularIntensity = intensity;
	}
	
	public TextureData getAlbedoMap()
	{
		return this.albedoMap;
	}
	
	public TextureData getNormalMap()
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
