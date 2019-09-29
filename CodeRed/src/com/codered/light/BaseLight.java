package com.codered.light;

import org.barghos.core.color.api.IColor3R;

public class BaseLight
{
	public IColor3R color;
	public float intensity;
	
	public BaseLight(IColor3R color, float intensity)
	{
		this.color = color;
		this.intensity = intensity;
	}
}
