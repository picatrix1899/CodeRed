package com.codered.resourcemanager.materials;

public class MaterialData
{
	private String albedoMap;
	private String normalMap;
	private String dispMap;
	
	private float specularIntensity;
	private float specularPower;
	
	public MaterialData(String albedoMap, String normalMap, String dispMap, float power, float intensity)
	{
		this.albedoMap = albedoMap;
		this.normalMap = normalMap;
		this.dispMap = dispMap;
		
		this.specularPower = power;
		this.specularIntensity = intensity;
	}
	
	public String getAlbedoMap()
	{
		return this.albedoMap;
	}
	
	public String getNormalMap()
	{
		return this.normalMap;
	}

	public String getDisplacementMap()
	{
		return this.dispMap;
	}
	
	public boolean hasNormalMap()
	{
		return this.normalMap != "";
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
