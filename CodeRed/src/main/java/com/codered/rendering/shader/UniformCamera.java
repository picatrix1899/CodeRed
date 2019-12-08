package com.codered.rendering.shader;

import org.barghos.math.vector.vec3.Vec3;

import com.codered.entities.Camera;

public class UniformCamera extends Uniform
{

	private int location_T_view = -1;
	private int location_position = -1;
	
	private Camera value;
	private float alpha;
	
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
	public void set(Object... obj)
	{
		if(!(obj[0] instanceof Camera)) throw new IllegalArgumentException();
		if(!(obj[1] instanceof Double)) throw new IllegalArgumentException();
		Camera v = (Camera)obj[0];
		double a = (double)obj[1];
		this.value = v;
		this.alpha = (float)a;
		
	}

	@Override
	public void load()
	{
		loadMat4(this.location_T_view, this.value.getLerpedViewMatrix(alpha));
		Vec3 v = this.value.getTotalPos();
		loadVec3f(this.location_position, v.getX(), v.getY(), v.getZ());
	}

}
