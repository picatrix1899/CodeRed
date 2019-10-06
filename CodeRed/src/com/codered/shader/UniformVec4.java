package com.codered.shader;

import org.barghos.core.api.tuple.ITup4R;
import org.barghos.math.vector.Vec4f;

public class UniformVec4 extends Uniform
{
	private Vec4f value = new Vec4f();
	
	private int location = -1;
	
	public UniformVec4(String name, Object... data)
	{
		super(name);
	}

	@Override
	public void set(Object... obj)
	{
		if(!(obj[0] instanceof ITup4R)) throw new IllegalArgumentException();
		ITup4R v = (ITup4R)obj[0];
		this.value.set(v);
	}

	@Override
	public void load()
	{
		loadVec4f(this.location, this.value.getX(), this.value.getY(), this.value.getZ(), this.value.getW());
	}

	@Override
	public void loadUniformLocations(int shaderProgrammId)
	{
		this.location = getLocationFor(this.name, shaderProgrammId);
	}
}
