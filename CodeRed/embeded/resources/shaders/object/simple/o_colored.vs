#version 400 core


#include embeded base "base.vsh"

void main()
{
	gl_Position = T_projection * T_view * T_model * vec4(vertexPos, 1.0);
}