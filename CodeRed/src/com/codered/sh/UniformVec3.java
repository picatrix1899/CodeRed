package com.codered.sh;

import org.barghos.core.api.tuple.ITup3R;
import org.barghos.math.vector.Vec3f;

public class UniformVec3 extends Uniform
{
	private Vec3f value = new Vec3f();
	
	public UniformVec3(String name, Object... data)
	{
		super(name);
		addUniform("");
	}

	@Override
	public void set(Object obj)
	{
		if(!(obj instanceof ITup3R)) throw new IllegalArgumentException();
		ITup3R v = (ITup3R)obj;
		this.value.set(v);
	}

	@Override
	public void load()
	{
		loadVec3f("", this.value.getX(), this.value.getY(), this.value.getZ());
	}
}
