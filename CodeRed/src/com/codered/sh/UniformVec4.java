package com.codered.sh;

import org.barghos.core.api.tuple.ITup4R;
import org.barghos.math.vector.Vec4f;

public class UniformVec4 extends Uniform
{
	private Vec4f value = new Vec4f();
	
	public UniformVec4(String name, Object... data)
	{
		super(name);
		addUniform("");
	}

	@Override
	public void set(Object obj)
	{
		if(!(obj instanceof ITup4R)) throw new IllegalArgumentException();
		ITup4R v = (ITup4R)obj;
		this.value.set(v);
	}

	@Override
	public void load()
	{
		loadVec4f("", this.value.getX(), this.value.getY(), this.value.getZ(), this.value.getW());
	}
}
