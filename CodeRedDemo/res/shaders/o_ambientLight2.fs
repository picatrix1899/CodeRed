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

struct Material
{
	sampler2D albedoMap;
};

vec4 calcBaseLight(BaseLight light)
{
	return (vec4(light.color,1.0) * light.intensity);
}

vec4 calcAmbientLight(AmbientLight light)
{
	return calcBaseLight(light.base);
}

in vec2 pass_texCoords;

out vec4 out_Color;

uniform AmbientLight ambientLight;
uniform Material material;

void main(void)
{
	vec4 totalLight = calcAmbientLight(ambientLight);

	out_Color = texture(material.albedoMap, pass_texCoords) * totalLight;
}