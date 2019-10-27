package com.codered.light;

import org.barghos.core.testcolor.Color3R;
import org.barghos.math.vector.Vec3f;

public class PointLight
{
	public BaseLight base;
	public Attenuation attenuation;
	public Vec3f position = new Vec3f();
	public long id;
	
	public PointLight(Vec3f pos, Color3R color, float intensity, float constant, float linear, float exponent)
	{
		this.position.set(pos);
		this.base = new BaseLight(color, intensity);
		this.attenuation = new Attenuation(constant, linear, exponent);
	}
}
