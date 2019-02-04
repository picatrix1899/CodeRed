package com.codered.sh;

public class UniformFloat extends Uniform
{
	private float value = 0;
	
	public UniformFloat(String name, Object... data)
	{
		super(name);
		addUniform("");
	}

	@Override
	public void set(Object obj)
	{
		if(!(obj instanceof Float)) throw new IllegalArgumentException();
		float v = (float)obj;
		this.value = v;
	}

	@Override
	public void load()
	{
		loadFloat("", this.value);
	}

}
