package com.codered.managing.loader.data;

public class MaterialData
{
	private String albedoMap;
	private String normalMap;
	private String glowMap;
	private String dispMap;
	
	private float specularIntensity;
	private float specularPower;
	
	public MaterialData(String albedoMap, String normalMap, String glowMap, String dispMap, float power, float intensity)
	{
		this.albedoMap = albedoMap;
		this.normalMap = normalMap;
		this.glowMap = glowMap;
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
	
	public String getGlowMap()
	{
		return this.glowMap;
	}
	
	public String getDisplacementMap()
	{
		return this.dispMap;
	}
	
	public boolean hasNormalMap()
	{
		return this.normalMap != "";
	}
	
	public boolean hasGlowMap()
	{
		return this.glowMap != "";
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
