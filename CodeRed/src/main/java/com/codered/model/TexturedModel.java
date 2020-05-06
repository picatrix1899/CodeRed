package com.codered.model;

import com.codered.rendering.material.Material;

public class TexturedModel
{
	private Material material;
	private Mesh model;
	
	public TexturedModel(Mesh model, Material material)
	{
		this.model = model;
		this.material = material;
	}
	
	public Mesh getModel() { return this.model; }

	public Material getMaterial() { return this.material; }
	
}
