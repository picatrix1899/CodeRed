package com.codered.sh;

import java.util.Map;
import java.util.Set;

import org.lwjgl.opengl.GL20;

import com.codered.engine.EngineRegistry;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public abstract class ShaderProgram implements Shader
{
	private int id;

	private Set<String> fixedShaderParts = Sets.newHashSet();
	private Map<String,ShaderVariant> variants = Maps.newHashMap();
	private Map<String,Uniform> uniforms = Maps.newHashMap();
	private Map<String,Integer> attribs = Maps.newHashMap();
	
	private ShaderVariant activeVariant;
	
	private boolean isRunning;
	
	public ShaderProgram()
	{
		this.id = GL20.glCreateProgram();
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public void compile()
	{
		System.out.println("compile Shader: " + this.id);
		
		attachFixedShaderParts();

		recompile();
	}
	
	public void recompile()
	{

		for(String name : this.attribs.keySet())
		{
			GL20.glBindAttribLocation(this.id, this.attribs.get(name), name);
			System.out.println("Bind Attrib: " + name + " " + this.attribs.get(name));
		}
		
		GL20.glLinkProgram(this.id);
		
		GL20.glValidateProgram(this.id);
		
		loadUniformLocations();
	}
	
	public void addVariant(String name, ShaderVariant variant)
	{
		this.variants.put(name, variant);
	}
	
	public ShaderVariant getVariant(String name)
	{
		return this.variants.get(name);
	}
	
	public Set<String> getVariants()
	{
		return this.variants.keySet();
	}
	
	public void addAttribute(int attrib, String name)
	{
		this.attribs.put(name, attrib);
	}
	
	public void applyVariant(String name)
	{
		boolean wasRunning = this.isRunning;
		if(wasRunning) stop();
		
		detachVariantShaderParts();
		this.activeVariant = getVariant(name);
		attachVariantShaderParts();
		recompile();
		
		if(wasRunning) start();
	}
	
	public Set<String> getUniforms()
	{
		return this.uniforms.keySet();
	}
	
	public Uniform getUniform(String name)
	{
		return this.uniforms.get(name);
	}
	
	public void setUniformValue(String name, Object obj)
	{
		this.uniforms.get(name).set(obj);
	}
	
	public void addUniform(String name, Uniform uniform)
	{
		this.uniforms.put(name, uniform);
	}
	
	public void start()
	{
		GL20.glUseProgram(this.id);
		this.isRunning = true;
	}
	
	public void stop()
	{
		GL20.glUseProgram(0);
		this.isRunning = false;
	}

	public void load()
	{
		for(Uniform u : this.uniforms.values())
			u.load();
	}
	
	public void addShaderPart(String part)
	{
		this.fixedShaderParts.add(part);
	}
	
	private void attachFixedShaderParts()
	{
		for(String p : this.fixedShaderParts)
		{
			ShaderPart part = EngineRegistry.getCurrentWindowContext().getDRM().getShaderPart(p);
			GL20.glAttachShader(this.id, part.getId());
			System.out.println("attach Part: " + p + " " + part.getId());
		}
	}
	
	private void attachVariantShaderParts()
	{
		for(String p : this.activeVariant.getShaderParts())
		{
			ShaderPart part = EngineRegistry.getCurrentWindowContext().getDRM().getShaderPart(p);
			GL20.glAttachShader(this.id, part.getId());
		}

	}
	
	private void detachVariantShaderParts()
	{
		if(this.activeVariant != null)
			for(String p : this.activeVariant.getShaderParts())
			{
				ShaderPart part = EngineRegistry.getCurrentWindowContext().getDRM().getShaderPart(p);
				GL20.glDetachShader(this.id, part.getId());
			}

	}
	
	public void release()
	{
		GL20.glDeleteProgram(this.id);
	}

	private void loadUniformLocations()
	{
		for(Uniform u : this.uniforms.values())
		{
			u.loadUniformLocations(this.id);
			System.out.println("loaded Uniform: " + u);
		}
	}
	
}
