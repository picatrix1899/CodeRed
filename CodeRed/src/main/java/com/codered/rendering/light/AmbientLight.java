package com.codered.rendering.light;

import org.barghos.core.color.Color3R;
import org.barghos.core.color.HDRColor3;
import org.barghos.core.color.LDRColor3;

public class AmbientLight
{
	public BaseLight base;
	
	public AmbientLight(Color3R color, float intensity)
	{
		this.base = new BaseLight(color, intensity);
	}
	
	public AmbientLight(int r, int g, int b, float intensity)
	{
		if(r > 255 || g > 255 || b > 255)
			this.base = new BaseLight(new HDRColor3(r, g, b), intensity);
		else
			this.base = new BaseLight(new LDRColor3(r, g, b), intensity);
	}
	
	public AmbientLight(float r, float g, float b, float intensity)
	{
		if(r > 1 || g > 1 || b > 1)
			this.base = new BaseLight(new HDRColor3(r, g, b), intensity);
		else
			this.base = new BaseLight(new LDRColor3(r, g, b), intensity);
	}
}
