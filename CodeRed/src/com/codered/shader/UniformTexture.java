package com.codered.shader;

import com.codered.texture.Texture;

public class UniformTexture extends Uniform
{

	private int startIndex;

	public UniformTexture(String name, int startIndex)
	{
		super(name);
		this.startIndex = startIndex;
		
		addUniform("");
	}

	public void set(Texture texture)
	{
		loadTexture("", this.startIndex , texture);
	}
	
	public void set(int texture)
	{
		loadTextureId("", this.startIndex , texture);
	}
}
