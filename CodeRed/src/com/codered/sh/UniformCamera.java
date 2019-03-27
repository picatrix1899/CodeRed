package com.codered.sh;

import org.barghos.math.vector.Vec3f;

import com.codered.entities.Camera;

public class UniformCamera extends Uniform
{

	private int location_T_view = -1;
	private int location_position = -1;
	
	private Camera value;
	
	public UniformCamera(String name, Object... data)
	{
		super(name, data);
	}

	@Override
	public void loadUniformLocations(int shaderProgrammId)
	{
		this.location_T_view = getLocationFor(this.name + ".T_view", shaderProgrammId);
		this.location_position = getLocationFor(this.name + ".position", shaderProgrammId);
	}

	@Override
	public void set(Object obj)
	{
		if(!(obj instanceof Camera)) throw new IllegalArgumentException();
		Camera v = (Camera)obj;
		this.value = v;
	}

	@Override
	public void load()
	{
		loadMat4(this.location_T_view, this.value.getViewMatrix());
		Vec3f v = this.value.getTotalPos();
		loadVec3f(this.location_position, v.x, v.y, v.z);
	}

}
