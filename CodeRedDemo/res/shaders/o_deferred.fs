#version 400 core

struct Material
{
	sampler2D albedoMap;
	sampler2D normalMap;
	float specularIntensity;
	float specularPower;
};

layout(location = 0) out vec3 gPosition;
layout(location = 1) out vec3 gNormal;
layout(location = 2) out vec4 gAlbedo;

in vec2 pass_texCoords;
in vec3 pass_pos;
in mat3 pass_tbn;

uniform Material material;

void main()
{
	gPosition = pass_pos;
	gNormal = normalize(pass_tbn * ((2 * texture(material.normalMap, pass_texCoords).rgb) - 1));
	gAlbedo = texture(material.albedoMap, pass_texCoords);
}