package com.codered.engine.light;

import cmn.utilslib.color.colors.api.IColor3Base;
import cmn.utilslib.math.vector.Vector3f;
import cmn.utilslib.math.vector.api.Vec3f;

public class DirectionalLight
{
	public BaseLight base;
	public Vec3f direction = new Vector3f();
	
	public DirectionalLight(IColor3Base color, float intensity, Vec3f direction)
	{
		this.base = new BaseLight(color, intensity);
		this.direction.set(direction);
	}
}
