package com.codered.shader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL20;

import com.codered.engine.EngineRegistry;

public abstract class ShaderProgram implements Shader
{
	private int id;

	private List<String> fixedVertexShaderParts = new ArrayList<>();
	private List<String> fixedFragmentShaderParts = new ArrayList<>();
	private List<String> fixedGeometryShaderParts = new ArrayList<>();
	private List<String> fixedTessellationControlShaderParts = new ArrayList<>();
	private List<String> fixedTessellationEvaluationShaderParts = new ArrayList<>();
	private Map<Integer,Uniform> uniforms = new HashMap<>();
	private Map<String,Integer> attribs = new HashMap<>();

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
		attachFixedShaderParts();

		recompile();
	}
	
	public void recompile()
	{

		for(String name : this.attribs.keySet())
		{
			GL20.glBindAttribLocation(this.id, this.attribs.get(name), name);
		}
		
		GL20.glLinkProgram(this.id);
		
		GL20.glValidateProgram(this.id);
		
		loadUniformLocations();
	}

	public void addAttribute(int attrib, String name)
	{
		this.attribs.put(name, attrib);
	}

	public Uniform getUniform(int id)
	{
		return this.uniforms.get(id);
	}
	
	public void setUniformValue(int id, Object... obj)
	{
		if(this.uniforms.containsKey(id))
			this.uniforms.get(id).set(obj);
	}
	
	public void addUniform(int id, Uniform uniform)
	{
		this.uniforms.put(id, uniform);
	}
	
	public ShaderSession start()
	{
		GL20.glUseProgram(this.id);
		return new ShaderSession(this);
	}
	
	public void stop()
	{
		GL20.glUseProgram(0);
	}

	public void load()
	{
		for(Uniform u : this.uniforms.values())
			u.load();
	}
	
	public void addVertexShaderPart(String part)
	{
		this.fixedVertexShaderParts.add(part);
	}
	
	public void addFragmentShaderPart(String part)
	{
		this.fixedFragmentShaderParts.add(part);
	}
	
	public void addGeometryShaderPart(String part)
	{
		this.fixedGeometryShaderParts.add(part);
	}
	
	public void addTessellationControlShaderPart(String part)
	{
		this.fixedTessellationControlShaderParts.add(part);
	}
	
	public void addTessellationEvaluationShaderPart(String part)
	{
		this.fixedTessellationEvaluationShaderParts.add(part);
	}
	
	private void attachFixedShaderParts()
	{
		int i;
		String p;
		
		int size = this.fixedVertexShaderParts.size();
		for(i = 0; i < size; i++)
		{
			p = this.fixedVertexShaderParts.get(i);
			ShaderPart part = EngineRegistry.getCurrentWindowContext().getDRM().getVertexShaderPart(p);
			GL20.glAttachShader(this.id, part.getId());
		}
		
		size = this.fixedFragmentShaderParts.size();
		for(i = 0; i < size; i++)
		{
			p = this.fixedFragmentShaderParts.get(i);
			ShaderPart part = EngineRegistry.getCurrentWindowContext().getDRM().getFragmentShaderPart(p);
			GL20.glAttachShader(this.id, part.getId());
		}
		
		size = this.fixedGeometryShaderParts.size();
		for(i = 0; i < size; i++)
		{
			p = this.fixedGeometryShaderParts.get(i);
			ShaderPart part = EngineRegistry.getCurrentWindowContext().getDRM().getGeometryShaderPart(p);
			GL20.glAttachShader(this.id, part.getId());
		}
		
		size = this.fixedTessellationControlShaderParts.size();
		for(i = 0; i < size; i++)
		{
			p = this.fixedTessellationControlShaderParts.get(i);
			ShaderPart part = EngineRegistry.getCurrentWindowContext().getDRM().getTessellationControlShaderPart(p);
			GL20.glAttachShader(this.id, part.getId());
		}
		
		size = this.fixedTessellationEvaluationShaderParts.size();
		for(i = 0; i < size; i++)
		{
			p = this.fixedTessellationEvaluationShaderParts.get(i);
			ShaderPart part = EngineRegistry.getCurrentWindowContext().getDRM().getTessellationEvaluationShaderPart(p);
			GL20.glAttachShader(this.id, part.getId());
		}
	}

	public void release()
	{
		GL20.glDeleteProgram(this.id);
	}

	private void loadUniformLocations()
	{
		Collection<Uniform> un = this.uniforms.values();
		
		for(Uniform u : un)
		{
			u.loadUniformLocations(this.id);
		}
	}
	
}
