package com.codered.resource.material;

import java.util.Optional;

import com.codered.resource.texture.TextureData;

public class MaterialData
{
	private Optional<TextureData> diffuse;
	private Optional<TextureData> normal;
	
	public MaterialData(Optional<TextureData> diffuse, Optional<TextureData> normal)
	{
		this.diffuse = diffuse;
		this.normal = normal;
	}
	
	public Optional<TextureData> getDiffuse()
	{
		return this.diffuse;
	}
	
	public Optional<TextureData> getNormal()
	{
		return this.normal;
	}
}
