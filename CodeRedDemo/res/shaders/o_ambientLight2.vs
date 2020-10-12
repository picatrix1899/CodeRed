#version 400 core

struct Camera
{
	mat4 T_view;
};

in vec3 vertexPos;
in vec2 texCoords;
in vec3 normal;
in vec3 tangent;

out vec2 pass_texCoords;

uniform mat4 T_model;
uniform mat4 T_projection;
uniform Camera camera;

void main(void)
{
	vec4 posRelativeToCam = camera.T_view * T_model * vec4(vertexPos, 1.0);
	//vec4 t = vec4(posRelativeToCam.x * cos(radians(10 + 0.5 * posRelativeToCam.z)), posRelativeToCam.y, posRelativeToCam.z, posRelativeToCam.w);
	//gl_Position = T_projection * t;
	
	vec4 t = T_projection * posRelativeToCam;
	gl_Position = vec4(t.x * cos(radians(40 + t.z)), t.y, t.z, t.w);
	
	pass_texCoords = texCoords;
}