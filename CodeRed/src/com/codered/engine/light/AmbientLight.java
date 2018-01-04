package com.codered.engine.light;

import cmn.utilslib.color.colors.api.IColor3Base;

public class AmbientLight
{
	public BaseLight base;
	
	public AmbientLight(IColor3Base color, float intensity)
	{
		this.base = new BaseLight(color, intensity);
	}
}
