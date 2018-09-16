package com.codered.light;

import cmn.utilslib.color.colors.HDRColor3;
import cmn.utilslib.color.colors.LDRColor3;
import cmn.utilslib.color.colors.api.IColor3Base;
import cmn.utilslib.math.vector.PVector3f;
import cmn.utilslib.math.vector.Vector3f;
import cmn.utilslib.math.vector.api.Vec3f;

public class DirectionalLight
{
	public BaseLight base;
	public Vector3f direction = new Vector3f();
	
	public DirectionalLight(IColor3Base color, float intensity, Vec3f direction)
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
		
		this.direction.set(PVector3f.gen(x, y, z));
	}
	
	public DirectionalLight(float r, float g, float b, float intensity, float x, float y, float z)
	{
		if(r > 1 || g > 1 || b > 1)
			this.base = new BaseLight(new HDRColor3(r, g, b), intensity);
		else
			this.base = new BaseLight(new LDRColor3(r, g, b), intensity);
		
		this.direction.set(PVector3f.gen(x, y, z));
	}
}
