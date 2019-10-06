package com.codered.light;

import org.barghos.core.api.color.IColor3R;

public class AmbientLight
{
	public BaseLight base;
	
	public AmbientLight(IColor3R color, float intensity)
	{
		this.base = new BaseLight(color, intensity);
	}
}
