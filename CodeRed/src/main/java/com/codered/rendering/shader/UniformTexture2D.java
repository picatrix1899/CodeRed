package com.codered.rendering.shader;

public class UniformTexture2D extends Uniform
{

	private int texture;
	private int index;
	
	private int location = -1;
	
	public UniformTexture2D(String name, Object... data)
	{
		super(name);
		this.index = (int)data[0];
	}
	
	@Override
	public void set(Object... obj)
	{
		if(!(obj[0] instanceof Integer)) throw new IllegalArgumentException();
		int i = (int)obj[0];
		this.texture = i;
	}

	@Override
	public void load()
	{
		loadTexture2D(this.location, this.index, this.texture);
	}

	@Override
	public void loadUniformLocations(int shaderProgrammId)
	{
		this.location = getLocationFor(this.name, shaderProgrammId);
	}

}
