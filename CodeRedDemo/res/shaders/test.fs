#version 400 core

struct BaseLight
{
	vec3 color;
	float intensity;
};

struct AmbientLight
{
	BaseLight base;
};

vec4 calcBaseLight(BaseLight light)
{
	return (vec4(light.color,1.0) * light.intensity);
}

vec4 calcAmbientLight(AmbientLight light)
{
	return calcBaseLight(light.base);
}