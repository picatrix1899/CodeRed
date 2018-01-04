package com.codered.engine.light;

import cmn.utilslib.color.colors.api.IColor3Base;
import cmn.utilslib.color.colors.LDRColor3;

public class Glow
{
	public static final Glow DEFAULT;
	
	public BaseLight base;
	
	public float intesity;
	public float affect;
	
	static
	{
		DEFAULT = new Glow(LDRColor3.BLACK, 1.0f, 1.0f, 0.0f);
	}
	
	public Glow(IColor3Base color, float intensity, float finalIntensity, float affect)
	{
		this.base = new BaseLight(color, intensity);
		this.intesity = finalIntensity;
		this.affect = affect;
	}
}
