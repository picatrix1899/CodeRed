package com.codered.resource.material;

public class MaterialData
{
	private org.haze.png.Image albedoMap;
	private org.haze.png.Image normalMap;
	
	private float specularIntensity;
	private float specularPower;
	
	public MaterialData(org.haze.png.Image albedoMap, org.haze.png.Image normalMap, float power, float intensity)
	{
		this.albedoMap = albedoMap;
		this.normalMap = normalMap;
		
		this.specularPower = power;
		this.specularIntensity = intensity;
	}
	
	public org.haze.png.Image getAlbedoMap()
	{
		return this.albedoMap;
	}
	
	public org.haze.png.Image getNormalMap()
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
