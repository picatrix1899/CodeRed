package com.codered.rendering.shader;

public class UniformFloat extends Uniform
{
	private float value = 0;
	
	private int location = -1;
	
	public UniformFloat(String name, Object... data)
	{
		super(name);
	}

	@Override
	public void set(Object... obj)
	{
		if(!(obj[0] instanceof Float)) throw new IllegalArgumentException();
		float v = (float)obj[0];
		this.value = v;
	}

	@Override
	public void load()
	{
		loadFloat(this.location, this.value);
	}

	@Override
	public void loadUniformLocations(int shaderProgrammId)
	{
		this.location = getLocationFor(this.name, shaderProgrammId);
	}

}
