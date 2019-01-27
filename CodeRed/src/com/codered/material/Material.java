package com.codered.material;

import com.codered.engine.EngineRegistry;
import com.codered.texture.Texture;

public class Material
{
	private String albedoMap;
	private String normalMap;
	private String dispMap;
	
	private float specularIntensity;
	private float specularPower;
	
	public Material(String albedoMap, String normalMap, String dispMap, float power, float intensity)
	{
		this.albedoMap = albedoMap;
		this.normalMap = normalMap;
		this.dispMap = dispMap;
		
		this.specularPower = power;
		this.specularIntensity = intensity;
	}
	
	public Texture getAlbedoMap()
	{
		return EngineRegistry.getCurrentWindowContext().getDRM().getTexture(this.albedoMap);
	}
	
	public Texture getNormalMap()
	{
		return EngineRegistry.getCurrentWindowContext().getDRM().getTexture(this.normalMap);
	}

	
	public Texture getDisplacementMap()
	{
		return EngineRegistry.getCurrentWindowContext().getDRM().getTexture(this.dispMap);
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
