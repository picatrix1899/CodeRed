#version 400 core

out vec4 out_Color;

in vec2 texCoords;

uniform sampler2D samp;

void main()
{
	out_Color = texture(samp, texCoords);
}