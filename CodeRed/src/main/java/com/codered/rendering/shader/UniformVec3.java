package com.codered.rendering.shader;

import org.barghos.core.tuple3.api.Tup3fR;
import org.barghos.math.vec3.Vec3f;

public class UniformVec3 extends Uniform
{
	private Vec3f value = new Vec3f();
	
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
	public void loadUniformLocations(int shaderProgrammId)
	{
		this.location = getLocationFor(this.name, shaderProgrammId);
	}
}
