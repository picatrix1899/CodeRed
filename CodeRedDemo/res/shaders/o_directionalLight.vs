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

out mat3 pass_tbn;
out vec2 pass_texCoords;
out vec3 pass_normal;
out vec3 pass_worldPos;
out Camera pass_camera;

void main(void)
{
	vec4 posRelativeToCam = camera.T_view * T_model * vec4(vertexPos, 1.0);
	gl_Position = T_projection * posRelativeToCam;
	
	pass_texCoords = texCoords;
	pass_normal = normal;
	pass_worldPos = (T_model * vec4(vertexPos, 1.0)).xyz;
	
	vec3 n = normalize(T_model * vec4(normal, 0.0f)).xyz;
	vec3 t = normalize(T_model * vec4(tangent, 0.0f)).xyz;
	
	t = normalize(t - dot(t,n) * n);
	
	vec3 b = cross(t,n);
	
	pass_tbn = mat3(t,b,n);
	pass_camera = camera;
}