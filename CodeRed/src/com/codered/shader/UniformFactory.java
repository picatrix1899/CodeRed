package com.codered.shader;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import com.google.common.collect.Maps;

public class UniformFactory
{
	
	private static UniformFactory instance;
	
	private Map<String, Class<? extends Uniform>> extendedUniformTypes = Maps.newHashMap();
	
	public static UniformFactory  getInstance()
	{
		if(instance == null) instance = new UniformFactory();
		
		return instance;
	}
	
	private UniformFactory()
	{
		
	}
	
	public void setExtendedUniformTypes(Map<String, Class<? extends Uniform>> types)
	{
		extendedUniformTypes.clear();
		extendedUniformTypes.putAll(types);
	}
	
	public Uniform createUniform(String name, String type, Object... data) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		switch (type)
		{
			case "vec2":
			{
				return new UniformVec2(name, data);
			}
			case "vec3":
			{
				return new UniformVec3(name, data);
			}
			case "vec4":
			{
				return new UniformVec4(name, data);
			}
			case "mat4":
			{
				return new UniformMat4(name, data);
			}
			case "sampler2D":
			case"sampler2d":
			{
				return new UniformTexture2D(name, data);
			}
			case "int":
			{
				return new UniformInt(name, data);
			}
			case "float":
			{
				return new UniformFloat(name, data);
			}
			case "camera":
			{
				return new UniformCamera(name, data);
			}
			case "material":
			{
				return new UniformMaterial(name, data);
			}
			default:
			{
				if(this.extendedUniformTypes.containsKey(type))
				{
					Class<? extends Uniform> c = this.extendedUniformTypes.get(type);
					Constructor<? extends Uniform> ctor = c.getConstructor(String.class, Object[].class);
					try
					{
						return ctor.newInstance(name);
					}
					catch(IllegalArgumentException e) { }
				}
				return null;
			}
		}
	}
}
