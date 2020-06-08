package com.codered.rendering.material;

import java.util.Optional;

import com.codered.ResourceHolder;
import com.codered.rendering.texture.Texture;

public class Material implements ResourceHolder
{
	private Texture diffuse;
	private Texture normal;
	
	public Material(Texture diffuse, Texture normal)
	{
		this.diffuse = diffuse;
		this.normal = normal;
	}
	
	public Optional<Texture> getDiffuse()
	{
		return Optional.ofNullable(this.diffuse);
	}
	
	public Optional<Texture> getNormal()
	{
		return Optional.ofNullable(this.normal);
	}

	public void release(boolean forced)
	{
		if(this.diffuse != null) this.diffuse.release(forced);
		if(this.normal != null) this.normal.release(forced);
	}
}
