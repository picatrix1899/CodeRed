package com.codered.rendering.shader;

import com.codered.rendering.material.Material;

public class UniformMaterial extends Uniform
{

	private int location_albedoMap = -1;
	private int location_normalMap = -1;
	private int location_specularIntensity = -1;
	private int location_specularPower = -1;
	
	private Material value;
	
	private int index_albedoMap;
	private int index_normalMap;
	
	public UniformMaterial(String name, Object... data)
	{
		super(name, data);
		this.index_albedoMap = (int)data[0];
		this.index_normalMap = (int)data[1];
	}

	@Override
	public void loadUniformLocations(int shaderProgrammId)
	{
		this.location_albedoMap = getLocationFor(this.name + ".albedoMap", shaderProgrammId);
		this.location_normalMap = getLocationFor(this.name + ".normalMap", shaderProgrammId);
		this.location_specularIntensity = getLocationFor(this.name + ".specularIntensity", shaderProgrammId);
		this.location_specularPower = getLocationFor(this.name + ".specularPower", shaderProgrammId);
	}

	@Override
	public void set(Object... obj)
	{
		if(!(obj[0] instanceof Material)) throw new IllegalArgumentException();
		Material v = (Material)obj[0];
		this.value = v;
	}

	@Override
	public void load()
	{
		loadTexture2D(this.location_albedoMap, this.index_albedoMap, this.value.getAlbedoMap().getId());
		loadTexture2D(this.location_normalMap, this.index_normalMap, this.value.getNormalMap().getId());
		loadFloat(this.location_specularIntensity, this.value.getSpecularIntensity());
		loadFloat(this.location_specularPower, this.value.getSpecularPower());
	}

}
