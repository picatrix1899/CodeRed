package com.codered.shader;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.lwjgl.opengl.GL20;

import com.codered.engine.EngineRegistry;

public abstract class ShaderProgram implements Shader
{
	private int id;

	private List<String> fixedVertexShaderParts = new LinkedList<>();
	private List<String> fixedFragmentShaderParts = new LinkedList<>();
	private List<String> fixedGeometryShaderParts = new LinkedList<>();
	private List<String> fixedTessellationControlShaderParts = new LinkedList<>();
	private List<String> fixedTessellationEvaluationShaderParts = new LinkedList<>();
	private Map<String,ShaderVariant> variants = new HashMap<>();
	private Map<Integer,Uniform> uniforms = new HashMap<>();
	private Map<String,Integer> attribs = new HashMap<>();
	
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
	
	public Uniform getUniform(int id)
	{
		return this.uniforms.get(id);
	}
	
	public void setUniformValue(int id, Object obj)
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
		this.isRunning = true;
		return new ShaderSession(this);
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
	
		for(Iterator<String> it = this.fixedVertexShaderParts.iterator(); it.hasNext();)
		{
			String p = it.next();
			ShaderPart part = EngineRegistry.getCurrentWindowContext().getDRM().getVertexShaderPart(p);
			GL20.glAttachShader(this.id, part.getId());
		}
		
		for(Iterator<String> it = this.fixedFragmentShaderParts.iterator(); it.hasNext();)
		{
			String p = it.next();
			ShaderPart part = EngineRegistry.getCurrentWindowContext().getDRM().getFragmentShaderPart(p);
			GL20.glAttachShader(this.id, part.getId());
		}
		
		for(Iterator<String> it = this.fixedGeometryShaderParts.iterator(); it.hasNext();)
		{
			String p = it.next();
			ShaderPart part = EngineRegistry.getCurrentWindowContext().getDRM().getGeometryShaderPart(p);
			GL20.glAttachShader(this.id, part.getId());
		}
		
		for(Iterator<String> it = this.fixedTessellationControlShaderParts.iterator(); it.hasNext();)
		{
			String p = it.next();
			ShaderPart part = EngineRegistry.getCurrentWindowContext().getDRM().getTessellationControlShaderPart(p);
			GL20.glAttachShader(this.id, part.getId());
		}
		
		for(Iterator<String> it = this.fixedTessellationEvaluationShaderParts.iterator(); it.hasNext();)
		{
			String p = it.next();
			ShaderPart part = EngineRegistry.getCurrentWindowContext().getDRM().getTessellationEvaluationShaderPart(p);
			GL20.glAttachShader(this.id, part.getId());
		}
	}
	
	private void attachVariantShaderParts()
	{
		if(this.activeVariant == null) return;
		
		for(String p : this.activeVariant.getVertexShaderParts())
		{
			ShaderPart part = EngineRegistry.getCurrentWindowContext().getDRM().getVertexShaderPart(p);
			GL20.glAttachShader(this.id, part.getId());
		}
		
		for(String p : this.activeVariant.getFragmentShaderParts())
		{
			ShaderPart part = EngineRegistry.getCurrentWindowContext().getDRM().getFragmentShaderPart(p);
			GL20.glAttachShader(this.id, part.getId());
		}
		
		for(String p : this.activeVariant.getGeometryShaderParts())
		{
			ShaderPart part = EngineRegistry.getCurrentWindowContext().getDRM().getGeometryShaderPart(p);
			GL20.glAttachShader(this.id, part.getId());
		}
		
		for(String p : this.activeVariant.getTessellationControlShaderParts())
		{
			ShaderPart part = EngineRegistry.getCurrentWindowContext().getDRM().getTessellationControlShaderPart(p);
			GL20.glAttachShader(this.id, part.getId());
		}
		
		for(String p : this.activeVariant.getTessellationEvaluationShaderParts())
		{
			ShaderPart part = EngineRegistry.getCurrentWindowContext().getDRM().getTessellationEvaluationShaderPart(p);
			GL20.glAttachShader(this.id, part.getId());
		}
	}
	
	private void detachVariantShaderParts()
	{
		if(this.activeVariant == null) return;
		
			for(String p : this.activeVariant.getVertexShaderParts())
			{
				ShaderPart part = EngineRegistry.getCurrentWindowContext().getDRM().getVertexShaderPart(p);
				GL20.glDetachShader(this.id, part.getId());
			}
			
			for(String p : this.activeVariant.getFragmentShaderParts())
			{
				ShaderPart part = EngineRegistry.getCurrentWindowContext().getDRM().getFragmentShaderPart(p);
				GL20.glDetachShader(this.id, part.getId());
			}
			
			for(String p : this.activeVariant.getGeometryShaderParts())
			{
				ShaderPart part = EngineRegistry.getCurrentWindowContext().getDRM().getGeometryShaderPart(p);
				GL20.glDetachShader(this.id, part.getId());
			}
			
			for(String p : this.activeVariant.getTessellationControlShaderParts())
			{
				ShaderPart part = EngineRegistry.getCurrentWindowContext().getDRM().getTessellationControlShaderPart(p);
				GL20.glDetachShader(this.id, part.getId());
			}
			
			for(String p : this.activeVariant.getTessellationEvaluationShaderParts())
			{
				ShaderPart part = EngineRegistry.getCurrentWindowContext().getDRM().getTessellationEvaluationShaderPart(p);
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
		}
	}
	
}
