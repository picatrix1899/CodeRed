package com.codered.material;

import com.codered.texture.Texture;

public class Material
{
	private Texture albedoMap;
	private Texture normalMap;
	private Texture glowMap;
	private Texture dispMap;
	
	private float specularIntensity;
	private float specularPower;
	
	public Material(Texture albedoMap, Texture normalMap, Texture glowMap, Texture dispMap, float power, float intensity)
	{
		this.albedoMap = albedoMap;
		this.normalMap = normalMap;
		this.glowMap = glowMap;
		this.dispMap = dispMap;
		
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
	
	public Texture getGlowMap()
	{
		return this.glowMap;
	}
	
	public Texture getDisplacementMap()
	{
		return this.dispMap;
	}
	
	public boolean hasNormalMap()
	{
		return this.normalMap != null;
	}
	
	public boolean hasGlowMap()
	{
		return this.glowMap != null;
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
