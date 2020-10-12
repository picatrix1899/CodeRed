package com.codered.rendering.shader;

import org.barghos.math.vector.vec2.Vec2f;
import org.barghos.math.vector.vec2.api.Vec2fR;

public class UniformVec2 extends Uniform
{
	private Vec2f value = new Vec2f();
	
	private int location = -1;
	
	public UniformVec2(String name, Object... data)
	{
		super(name);
	}

	@Override
	public void set(Object... obj)
	{
		if(obj[0] instanceof Vec2fR)
		{
			Vec2fR v = (Vec2fR)obj[0];
			this.value.set(v);
			loadVec2f(this.location, this.value.getX(), this.value.getY());
		}
	}

	@Override
	public void loadUniformLocations(int shaderProgrammId)
	{
		this.location = getLocationFor(this.name, shaderProgrammId);
	}

}
