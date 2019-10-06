package com.codered.shader;

import org.barghos.core.api.tuple.ITup3R;
import org.barghos.math.vector.Vec3f;

import com.codered.ConvUtils;

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
		if(obj[0] instanceof ITup3R)
		{
			ITup3R v = (ITup3R)obj[0];
			this.value.set(v);

		}
		else if(obj[0] instanceof org.barghos.core.tuple.tuple3.api.ITup3R)
		{
			ITup3R v = ConvUtils.ITup3R_new_old((org.barghos.core.tuple.tuple3.api.ITup3R)obj[0]);
			this.value.set(v);
		}
		else
		{
			throw new IllegalArgumentException();
		}
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
