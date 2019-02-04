package com.codered.sh;

public class UniformTexture2D extends Uniform
{

	private int texture;
	private int index;
	
	public UniformTexture2D(String name, Object... data)
	{
		super(name);
		this.index = (int)data[0];
		addUniform("");
	}
	
	@Override
	public void set(Object obj)
	{
		if(!(obj instanceof Integer)) throw new IllegalArgumentException();
		int i = (int)obj;
		this.texture = i;
	}

	@Override
	public void load()
	{
		loadTexture2D("", this.index, this.texture);
	}

}
