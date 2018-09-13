package com.codered.light;

import cmn.utilslib.color.colors.api.IColor3Base;

public class BaseLight
{
	public IColor3Base color;
	public float intensity;
	
	public BaseLight(IColor3Base color, float intensity)
	{
		this.color = color;
		this.intensity = intensity;
	}
}
