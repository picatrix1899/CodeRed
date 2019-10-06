package com.codered.light;

import org.barghos.core.testcolor.api.IColor3R;

public class AmbientLight
{
	public BaseLight base;
	
	public AmbientLight(IColor3R color, float intensity)
	{
		this.base = new BaseLight(color, intensity);
	}
}
