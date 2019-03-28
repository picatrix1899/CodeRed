package com.codered.shader;

import org.barghos.core.api.tuple.ITup3R;
import org.barghos.math.vector.Vec3f;

public class UniformVec3 extends Uniform
{
	private Vec3f value = new Vec3f();
	
	private int location = -1;
	
	public UniformVec3(String name, Object... data)
	{
		super(name);
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
		loadVec3f(this.location, this.value.getX(), this.value.getY(), this.value.getZ());
	}

	@Override
	public void loadUniformLocations(int shaderProgrammId)
	{
		this.location = getLocationFor(this.name, shaderProgrammId);
	}
}
