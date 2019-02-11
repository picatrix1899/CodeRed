#version 400 core

struct Camera
{
	vec3 position;
	mat4 T_view;
};

in vec3 vertexPos;
in vec2 texCoords;
in vec3 normal;
in vec3 tangent;

uniform mat4 T_model;
uniform mat4 T_projection;
uniform Camera camera;

out vec2 pass_texCoords;
out vec3 pass_normal;
out vec3 pass_worldPos;
out float pass_vis;
out Camera pass_camera;

const float density = 0.02;
const float gradient = 8;

void main(void)
{
	vec4 posRelativeToCam = camera.T_view * T_model * vec4(vertexPos, 1.0);
	gl_Position = T_projection * posRelativeToCam;
	
	pass_texCoords = texCoords;
	pass_normal = normal;
	pass_worldPos = (T_model * vec4(vertexPos, 1.0)).xyz;
	
	float dist = length(posRelativeToCam.xyz);
	pass_vis = exp(-pow((dist * density), gradient));
	pass_vis = clamp(pass_vis, 0.0, 1.0);
	pass_camera = camera;
}