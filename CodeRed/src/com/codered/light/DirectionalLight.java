package com.codered.light;


import org.barghos.core.testcolor.HDRColor3;
import org.barghos.core.testcolor.LDRColor3;
import org.barghos.core.testcolor.api.IColor3R;
import org.barghos.math.vector.Vec3f;

public class DirectionalLight
{
	public BaseLight base;
	public Vec3f direction = new Vec3f();
	
	public DirectionalLight(IColor3R color, float intensity, Vec3f direction)
	{
		this.base = new BaseLight(color, intensity);
		this.direction.set(direction);
	}
	
	public DirectionalLight(int r, int g, int b, float intensity, float x, float y, float z)
	{
		if(r > 255 || g > 255 || b > 255)
			this.base = new BaseLight(new HDRColor3(r, g, b), intensity);
		else
			this.base = new BaseLight(new LDRColor3(r, g, b), intensity);
		
		this.direction.set(x, y, z);
	}
	
	public DirectionalLight(float r, float g, float b, float intensity, float x, float y, float z)
	{
		if(r > 1 || g > 1 || b > 1)
			this.base = new BaseLight(new HDRColor3(r, g, b), intensity);
		else
			this.base = new BaseLight(new LDRColor3(r, g, b), intensity);
		
		this.direction.set(x, y, z);
	}
}
