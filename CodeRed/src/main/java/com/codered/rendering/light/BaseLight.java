package com.codered.rendering.light;

import org.barghos.core.color.api.Color3R;

public class BaseLight
{
	public Color3R color;
	public float intensity;
	
	public BaseLight(Color3R color, float intensity)
	{
		this.color = color;
		this.intensity = intensity;
	}
}
