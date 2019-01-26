package com.codered.shader;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.lwjgl.opengl.GL20;

import com.codered.window.WindowContext;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import cmn.utilslib.dmap.dmaps.DMap2;

public abstract class ShaderProgram
{
	protected int programID;
	
	private Set<ShaderPart> geometryShaders = Sets.newHashSet();
	private Set<ShaderPart> vertexShaders = Sets.newHashSet();
	private Set<ShaderPart> fragmentShaders = Sets.newHashSet();

	private Set<Uniform> newUniforms = Sets.newHashSet();
	
	protected WindowContext context;
	
	public ShaderProgram(WindowContext context)
	{
		this.context = context;
	}
	
	public void addUniform(Uniform uniform)
	{
		this.newUniforms.add(uniform);
	}

	public void compile()
	{
		this.programID = GL20.glCreateProgram();

		attachShaderParts();
		
		for(ShaderPart p : this.geometryShaders)
			GL20.glAttachShader(programID, p.getId());
		
		for(ShaderPart p  : this.vertexShaders)
			GL20.glAttachShader(programID, p.getId());

		for(ShaderPart p  : this.fragmentShaders)
			GL20.glAttachShader(programID, p.getId());

		ArrayList<DMap2<Integer,String>> attribs = Lists.newArrayList();
		
		getAttribs(attribs);
		
		for(DMap2<Integer,String> attrib : attribs)
		{
			bindAttribute(attrib.getA(), attrib.getB());
		}
		
		GL20.glLinkProgram(this.programID);
		
		GL20.glValidateProgram(this.programID);
	}
	
	protected void attachGeometryShader(ShaderPart part)
	{
		this.geometryShaders.add(part);
	}
	
	protected void attachFragmentShader(ShaderPart part)
	{
		this.fragmentShaders.add(part);
	}
	
	protected void attachVertexShader(ShaderPart part)
	{
		this.vertexShaders.add(part);
	}

	public abstract void attachShaderParts();
	
	public abstract void getAttribs(List<DMap2<Integer,String>> attribs);
	
	public void start()
	{
		GL20.glUseProgram(this.programID);
	}
	
	public void stop()
	{
		GL20.glUseProgram(0);
	}
	
	public void cleanup()
	{
		GL20.glUseProgram(0);
		
		for(ShaderPart p : this.geometryShaders)
			GL20.glDetachShader(this.programID, p.getId());
		
		for(ShaderPart p : this.vertexShaders)
			GL20.glDetachShader(this.programID, p.getId());
		
		for(ShaderPart p : this.fragmentShaders)
			GL20.glDetachShader(this.programID, p.getId());
		
		GL20.glDeleteProgram(this.programID);
		
		this.geometryShaders.clear();
		this.vertexShaders.clear();
		this.fragmentShaders.clear();
	}
	
	protected void bindAttribute(int attrib, String var)
	{
		GL20.glBindAttribLocation(this.programID, attrib, var);
	}
	
	protected int getUniformLocation(String uniform)
	{
		return GL20.glGetUniformLocation(this.programID, uniform);
	}
	
	
	protected void getAllUniformLocations()
	{
		for(Uniform uniform : this.newUniforms)
		{
			uniform.getUniformLocations(this);
		}
	}

}
