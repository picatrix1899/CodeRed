package com.codered.sh;

public class UniformInt extends Uniform
{
	private int value = 0;
	
	private int location = -1;
	
	public UniformInt(String name, Object... data)
	{
		super(name);
	}

	@Override
	public void set(Object obj)
	{
		if(!(obj instanceof Integer)) throw new IllegalArgumentException();
		int v = (int)obj;
		this.value = v;
	}

	@Override
	public void load()
	{
		loadInt(this.location, this.value);
	}

	@Override
	public void loadUniformLocations(int shaderProgrammId)
	{
		this.location = getLocationFor(this.name, shaderProgrammId);
	}

}
