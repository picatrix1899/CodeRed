package com.codered.rendering.shader;

import org.barghos.core.tuple.tuple4.Tup4fR;
import org.barghos.math.vector.vec4.Vec4;

public class UniformVec4 extends Uniform
{
	private Vec4 value = new Vec4();
	
	private int location = -1;
	
	public UniformVec4(String name, Object... data)
	{
		super(name);
	}

	@Override
	public void set(Object... obj)
	{
		if(!(obj[0] instanceof Tup4fR)) throw new IllegalArgumentException();
		Tup4fR v = (Tup4fR)obj[0];
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
