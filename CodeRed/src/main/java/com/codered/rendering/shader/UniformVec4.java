package com.codered.rendering.shader;

import org.barghos.core.tuple4.api.Tup4fR;
import org.barghos.math.vec4.Vec4f;

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
		if(!(obj[0] instanceof Tup4fR)) throw new IllegalArgumentException();
		Tup4fR v = (Tup4fR)obj[0];
		this.value.set(v);
		loadVec4f(this.location, this.value.getX(), this.value.getY(), this.value.getZ(), this.value.getW());
	}

	@Override
	public void loadUniformLocations(int shaderProgrammId)
	{
		this.location = getLocationFor(this.name, shaderProgrammId);
	}
}
