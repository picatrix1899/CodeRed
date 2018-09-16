package com.codered.light;

import cmn.utilslib.color.colors.api.IColor3Base;
import cmn.utilslib.math.vector.Vector3f;
import cmn.utilslib.math.vector.api.Vec3f;

public class PointLight
{
	public BaseLight base;
	public Attenuation attenuation;
	public Vector3f position = new Vector3f();
	public long id;
	
	public PointLight(Vec3f pos, IColor3Base color, float intensity, float constant, float linear, float exponent)
	{
		this.position.set(pos);
		this.base = new BaseLight(color, intensity);
		this.attenuation = new Attenuation(constant, linear, exponent);
	}
}
