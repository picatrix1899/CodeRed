#version 400 core

in vec2 pos;

out vec2 pass_texCoords[11];

uniform float targetHeight;

void main()
{
	gl_Position = vec4(pos, 0.0, 1.0);
	
	vec2 centerTexCoords = pos * 0.5 + 0.5;
	float pixelSize = 1.0 / targetHeight;
	
	for(int i = -5; i <= 5; i++)
	{
		pass_texCoords[i + 5] = centerTexCoords + vec2(0.0, pixelSize * i);
	}
}