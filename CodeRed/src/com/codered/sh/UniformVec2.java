package com.codered.sh;

import org.barghos.core.api.tuple.ITup2R;
import org.barghos.math.vector.Vec2f;

public class UniformVec2 extends Uniform
{
	private Vec2f value = new Vec2f();
	
	private int location = -1;
	
	public UniformVec2(String name, Object... data)
	{
		super(name);
	}

	@Override
	public void set(Object obj)
	{
		if(!(obj instanceof ITup2R)) throw new IllegalArgumentException();
		ITup2R v = (ITup2R)obj;
		this.value.set(v);
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
