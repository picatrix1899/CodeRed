package com.codered.rendering.shader;

import org.barghos.core.tuple.tuple3.Tup3fR;
import org.barghos.math.vector.vec3.Vec3;

public class UniformVec3 extends Uniform
{
	private Vec3 value = new Vec3();
	
	private int location = -1;
	
	public UniformVec3(String name, Object... data)
	{
		super(name);
	}

	@Override
	public void set(Object... obj)
	{
		if(obj[0] instanceof Tup3fR)
		{
			Tup3fR v = (Tup3fR)obj[0];
			this.value.set(v);
			loadVec3f(this.location, this.value.getX(), this.value.getY(), this.value.getZ());
		}
		else
		{
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void load()
	{
//		loadVec3f(this.location, this.value.getX(), this.value.getY(), this.value.getZ());
	}

	@Override
	public void loadUniformLocations(int shaderProgrammId)
	{
		this.location = getLocationFor(this.name, shaderProgrammId);
	}
}
