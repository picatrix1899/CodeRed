#version 400 core

in vec2 pass_texCoords;

layout(location=0) out vec4 out_Color;

uniform sampler2D frameSrc;
uniform sampler2D frameDst;
uniform sampler2D depthSrc;
uniform sampler2D depthDst;

uniform float near;
uniform float far;


float LinearizeDepth(float depth)
{
    float z = depth * 2.0 - 1.0; // Back to NDC 
    return (2.0 * near * far) / (far + near - z * (far - near));
}

void main()
{             
    float dSrc = texture(depthSrc, pass_texCoords).r;
    float dDst = texture(depthDst, pass_texCoords).r;
    
    float linSrc = LinearizeDepth(dSrc) / far;
    float linDst = LinearizeDepth(dDst) / far;
 
    if(dSrc > 1)
    {
    	out_Color = texture(frameDst, pass_texCoords);
    	gl_FragDepth = dDst;
    }
    else
    {
    	if(dSrc < dDst)
    	{
			out_Color = texture(frameSrc, pass_texCoords);
			gl_FragDepth = dSrc;
    	}
    	else
    	{
    		out_Color = texture(frameDst, pass_texCoords);
    		gl_FragDepth = dDst;
    	}
    }
}  