package com.codered.engine.managing;

public class Material
{
	
	private String name;
	
	private String colorMap;
	private String normalMap;
	private String glowMap;
	private String dispMap;
	
	private float specularIntensity;
	private float specularPower;
	
	public Material(String name, String colorMap, String normalMap, String glowMap, String dispMap, float power, float intensity)
	{
		this.name = name;
		this.colorMap = colorMap;
		this.normalMap = normalMap;
		this.glowMap = glowMap;
		this.dispMap = dispMap;
		
		this.specularPower = power;
		this.specularIntensity = intensity;
	}

	public String getName()
	{
		return this.name;
	}
	
	public String getColorMap()
	{
		return this.colorMap;
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
