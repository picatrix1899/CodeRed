package com.codered.rendering.light;

import org.barghos.core.color.api.Color3R;
import org.barghos.core.tuple3.api.Tup3fR;
import org.barghos.math.point.Point3f;

public class PointLight
{
	public BaseLight base;
	public Attenuation attenuation;
	public Point3f position = new Point3f();
	public long id;
	
	public PointLight(Tup3fR pos, Color3R color, float intensity, float constant, float linear, float exponent)
	{
		this.position.set(pos);
		this.base = new BaseLight(color, intensity);
		this.attenuation = new Attenuation(constant, linear, exponent);
	}
}
