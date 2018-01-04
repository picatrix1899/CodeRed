#version 400 core

in vec2 pos;
in vec2 scale;
in vec4 uvs;

out vec2 pass_scale;
out vec4 pass_uvs;

void main()
{
	pass_scale = scale;
	pass_uvs = uvs;
	gl_Position = vec4(pos, 0.0, 1.0);
}