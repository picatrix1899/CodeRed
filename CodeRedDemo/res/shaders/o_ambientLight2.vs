#version 400 core

struct Camera
{
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

void main(void)
{
	vec4 posRelativeToCam = camera.T_view * T_model * vec4(vertexPos, 1.0);
	gl_Position = T_projection * posRelativeToCam;
	
	pass_texCoords = texCoords;
}