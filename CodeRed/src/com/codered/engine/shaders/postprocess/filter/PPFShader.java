package com.codered.engine.shaders.postprocess.filter;

import java.util.ArrayList;

import com.codered.engine.shaders.shader.ShaderProgram;
import com.google.common.collect.Lists;

public abstract class PPFShader extends ShaderProgram
{
	
	private static ArrayList<PPFShader> shaders = Lists.newArrayList();
	
	public PPFShader()
	{
		shaders.add(this);
	}
	
	public static void clean()
	{
		for(PPFShader s : shaders)
		{
			s.cleanup();
		}
		
		shaders.clear();
	}
	
	
	public static Contrast_PPFilter Contrast()
	{
		return Contrast_PPFilter.instance;
	}
	
	public static Invert_PPFilter Invert()
	{
		return Invert_PPFilter.instance;
	}
	
	public static No_PPFilter No()
	{
		return No_PPFilter.instance;
	}
	
	public static BlurH_PPFilter BlurH()
	{
		return BlurH_PPFilter.instance;
	}
	
	public static BlurV_PPFilter BlurV()
	{
		return BlurV_PPFilter.instance;
	}
	
	public static RadialBlur_PPFilter RadialBlur()
	{
		return RadialBlur_PPFilter.instance;
	}
	
	public static Blend_PPFilter Blend()
	{
		return Blend_PPFilter.instance;
	}
	
	public static Brightness_PPFilter Brightness()
	{
		return Brightness_PPFilter.instance;
	}
	
	public static HDR_PPFilter HDR()
	{
		return HDR_PPFilter.instance;
	}
	
	public static DepthMap_PPFilter DepthMap()
	{
		return DepthMap_PPFilter.instance;
	}
	
	public static DepthTest_PPFilter DepthTest()
	{
		return DepthTest_PPFilter.instance;
	}
	
	public static CameraStripsHorizontal_PPFilter CameraStripsHorizontal()
	{
		return CameraStripsHorizontal_PPFilter.instance;
	}
	
	public static CameraStripsVertical_PPFilter CameraStripsVertical()
	{
		return CameraStripsVertical_PPFilter.instance;
	}
}
