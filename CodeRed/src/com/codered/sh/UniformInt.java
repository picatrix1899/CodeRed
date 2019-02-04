package com.codered.sh;

public class UniformInt extends Uniform
{
	private int value = 0;
	
	public UniformInt(String name, Object... data)
	{
		super(name);
		addUniform("");
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
		loadInt("", this.value);
	}

}
