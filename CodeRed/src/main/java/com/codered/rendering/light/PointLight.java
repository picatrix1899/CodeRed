package com.codered.rendering.light;

import org.barghos.core.color.Color3R;
import org.barghos.math.vector.vec3.Vec3;

public class PointLight
{
	public BaseLight base;
	public Attenuation attenuation;
	public Vec3 position = new Vec3();
	public long id;
	
	public PointLight(Vec3 pos, Color3R color, float intensity, float constant, float linear, float exponent)
	{
		this.position.set(pos);
		this.base = new BaseLight(color, intensity);
		this.attenuation = new Attenuation(constant, linear, exponent);
	}
}
