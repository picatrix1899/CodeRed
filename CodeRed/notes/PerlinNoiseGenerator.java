package com.codered.engine;

public class PerlinNoiseGenerator
{
	public static float noise(int x, int y)
	{
		int n = x + y * 57;
		n = (n<<13) ^ n;
		
		return (1.0f - (n * (n * n * 15731 + 789221) & 0x7fffffff) / 1073741824.0f);
	}
	
	public static float smoothNoise(int x, int y)
	{		
		double corners = (noise(x-1, y-1) + noise(x+1, y-1) + noise(x-1, y+1) + noise(x+1, y+1)) / 16;
		double sides = (noise(x-1, y) + noise(x+1, y) + noise(x, y-1) + noise(x, y+1)) / 8;
		double center = noise(x, y) / 4;
		
		return (float)(corners + sides + center);
	}
	
	public static float interpolatedNoise(float x, float y)
	{
		int intX = (int)x;
		int intY = (int)y;
		
		float fracX = x - intX;
		float fracY = y - intY;
		
		float v1 = smoothNoise(intX, intY);
		float v2 = smoothNoise(intX + 1, intY);
		float v3 = smoothNoise(intX, intY + 1);
		float v4 = smoothNoise(intX + 1, intY + 1);
		
		float i1 = interpolate(v1, v2, fracX);
		float i2 = interpolate(v3, v4, fracX);
		
		return interpolate(i1, i2, fracY);
	}
	
	public static float interpolate(float a, float b, float x)
	{
		double ft = x * Math.PI;
		double f = (1 - Math.cos(ft)) * 0.5;
		
		return (float)((1 - f) * a + b * f);
	}
	
	public static float perlinNoise(float x, float y, float pers, int oct)
	{
		double total = 0;
		
		float freq;
		float amp;
		
		for(int i = 0; i < oct; i++)
		{
			freq = (float)Math.pow(2, i);
			amp = (float)Math.pow(pers, i);
			
			total += interpolatedNoise(x * freq, y * freq) * amp;
		}
		
		return (float)total;
	}
	
}
