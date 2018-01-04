package com.codered.engine.rendering.deref;


public class Deref
{
	public static DEREF_AmbientLight AmbientLight()
	{
		return DEREF_AmbientLight.instance;
	}
	
	public static DEREF_DirectionalLight DirectionalLight()
	{
		return DEREF_DirectionalLight.instance;
	}
	
	public static DEREF_PointLight PointLight()
	{
		return DEREF_PointLight.instance;
	}
	
	public static DEREF_No No()
	{
		return DEREF_No.instance;
	}
}
