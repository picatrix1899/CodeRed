#version 400 core

struct BaseLight
{
	vec3 color;
	float intensity;
};

struct DirectionalLight
{
	BaseLight base;
	vec3 direction;
};

struct Camera
{
	vec3 position;
	mat4 T_view;
};


struct Material
{
	sampler2D albedoMap;
	sampler2D normalMap;
	float specularIntensity;
	float specularPower;
};

in vec2 pass_texCoords;
in vec3 pass_normal;
in vec3 pass_worldPos;
in mat3 pass_tbn;
in Camera pass_camera;

out vec4 out_Color;

uniform DirectionalLight directionalLight;
uniform Material material;

float calcBrightness(vec4 color)
{
	return dot(color.rgb, vec3(0.2126,0.7152, 0.0722));
}

vec4 calcDirectionalLight(BaseLight light, vec3 dir,  vec3 normal)
{
	float diffuseFactor = dot(-dir, normal);
	
	vec4 diffuseColor = vec4(0,0,0,0);
	vec4 specularColor = vec4(0,0,0,0);
	
	if (diffuseFactor > 0)
	{
		diffuseColor = vec4(light.color, 1.0f) * light.intensity * diffuseFactor;
	}
	
	return diffuseColor;
}

vec4 calcSpecularReflection(BaseLight light, vec3 dir, vec3 camPos, vec3 fragPos, vec3 normal, float intensity, float power, float divident)
{
	float diffuseFactor = dot(-dir, normal);
	
	vec4 specularColor = vec4(0,0,0,0);
	
	if (diffuseFactor > 0)
	{
		vec3 directionToCamera = normalize(camPos - fragPos);
		vec3 reflectDirection = normalize(reflect(directionToCamera, normal));
		
		if(intensity > 0.0f)
		{
			float specularFactor = dot(directionToCamera, reflectDirection);
			specularFactor = pow(specularFactor, power);
			
			if(specularFactor > 0.0f)
			{
				specularColor = vec4(light.color, 1.0f) * intensity * specularFactor;
			}	
		}

	}
	
	return specularColor / divident;
}

void main(void)
{
	vec4 textureColor = texture(material.albedoMap, pass_texCoords);
	
	vec3 nrm = normalize(pass_tbn * ((2 * texture(material.normalMap, pass_texCoords).rgb) - 1));
	
	vec4 dLight = calcDirectionalLight(directionalLight.base, directionalLight.direction, nrm);
	
	float atten = 1 / (calcBrightness(dLight) + (directionalLight.base.intensity / 10));
	
	dLight += calcSpecularReflection(directionalLight.base, directionalLight.direction, pass_camera.position, pass_worldPos, nrm, material.specularIntensity, material.specularPower, atten);

	out_Color = textureColor * dLight;

}