package com.codered.rendering.light;

import org.barghos.core.color.Color3R;

public class AmbientLight
{
	public BaseLight base;
	
	public AmbientLight(Color3R color, float intensity)
	{
		this.base = new BaseLight(color, intensity);
	}
}
