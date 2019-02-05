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

vec4 calcAmbientLight(AmbientLight);

in vec2 pass_texCoords;
in float pass_vis;

out vec4 out_Color;

uniform AmbientLight ambientLight;

uniform sampler2D albedoMap;

void main(void)
{

	vec4 totalLight = calcAmbientLight(ambientLight);

	out_Color = texture(albedoMap, pass_texCoords) * totalLight;
}