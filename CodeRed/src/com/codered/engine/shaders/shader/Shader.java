package com.codered.engine.shaders.shader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Shader
{
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface VertexShader
	{
		public String value();
	}
	
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface FragmentShader
	{
		public String value();
	}
	
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface GeometryShader
	{
		public String value();
	}
	
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@Repeatable(Shader.Attribs.class)
	public @interface Attrib
	{
		public int pos();
		public String var();
	}
	
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Attribs
	{
		public Shader.Attrib[] value();
	}
}
