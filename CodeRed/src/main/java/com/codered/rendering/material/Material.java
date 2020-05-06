package com.codered.rendering.material;

import java.util.Optional;

import com.codered.rendering.texture.Texture;

public class Material
{
	private Optional<Texture> diffuse;
	private Optional<Texture> normal;
	
	public Material(Optional<Texture> diffuse, Optional<Texture> normal)
	{
		this.diffuse = diffuse;
		this.normal = normal;
	}
	
	public Optional<Texture> getDiffuse()
	{
		return this.diffuse;
	}
	
	public Optional<Texture> getNormal()
	{
		return this.normal;
	}
}
