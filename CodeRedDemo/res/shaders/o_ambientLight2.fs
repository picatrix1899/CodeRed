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

vec4 calcAmbientLight(AmbientLight);

in vec2 pass_texCoords;

out vec4 out_Color;

uniform AmbientLight ambientLight;

uniform Material material;

void main(void)
{

	vec4 totalLight = calcAmbientLight(ambientLight);

	out_Color = texture(material.albedoMap, pass_texCoords) * totalLight;
}