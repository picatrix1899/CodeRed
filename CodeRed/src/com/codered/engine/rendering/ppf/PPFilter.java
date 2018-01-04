package com.codered.engine.rendering.ppf;

import com.codered.engine.managing.PPF;

public abstract class PPFilter extends PPF
{
	
	public static PPF_Contrast Contrast()
	{
		return PPF_Contrast.instance;
	}
	
	public static PPF_No No()
	{
		return PPF_No.instance;
	}
	
	public static PPF_BlurH BlurH()
	{
		return PPF_BlurH.instance;
	}
	
	public static PPF_BlurV BlurV()
	{
		return PPF_BlurV.instance;
	}
	
	public static PPF_SimpleBlend SimpleBlend()
	{
		return PPF_SimpleBlend.instance;
	}

	
	public static PPF_Brightness Brightness()
	{
		return PPF_Brightness.instance;
	}
	
	
	public static PPF_RadialBlur RadialBlur()
	{
		return PPF_RadialBlur.instance;
	}
	
	public static PPF_HDR HDR()
	{
		return PPF_HDR.instance;
	}
	
	public static PPF_DepthMap DepthMap()
	{
		return PPF_DepthMap.instance;
	}
	
	public static PPF_DepthTest DepthTest()
	{
		return PPF_DepthTest.instance;
	}
}
