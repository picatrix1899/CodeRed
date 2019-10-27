package com.codered.shader;

import org.barghos.math.experimental.vector.vec2.Vec2;
import org.barghos.math.experimental.vector.vec2.Vec2R;

public class UniformVec2 extends Uniform
{
	private Vec2 value = new Vec2();
	
	private int location = -1;
	
	public UniformVec2(String name, Object... data)
	{
		super(name);
	}

	@Override
	public void set(Object... obj)
	{
		if(obj[0] instanceof Vec2R)
		{
			Vec2R v = (Vec2R)obj[0];
			this.value.set(v);
		}
	}

	@Override
	public void load()
	{
		loadVec2f(this.location, this.value.getX(), this.value.getY());
	}

	@Override
	public void loadUniformLocations(int shaderProgrammId)
	{
		this.location = getLocationFor(this.name, shaderProgrammId);
	}

}
