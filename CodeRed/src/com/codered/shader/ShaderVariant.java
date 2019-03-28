package com.codered.shader;

import java.util.Set;

import com.google.common.collect.Sets;

public class ShaderVariant
{
	private Set<String> vertexShaderParts = Sets.newHashSet();
	private Set<String> fragmentShaderParts = Sets.newHashSet();
	private Set<String> geometryShaderParts = Sets.newHashSet();
	private Set<String> tessellationControlShaderParts = Sets.newHashSet();
	private Set<String> tessellationEvaluationShaderParts = Sets.newHashSet();
	
	public void addVertexShaderPart(String part)
	{
		this.vertexShaderParts.add(part);
	}
	
	public void addFragmentShaderPart(String part)
	{
		this.fragmentShaderParts.add(part);
	}
	
	public void addGeometryShaderPart(String part)
	{
		this.geometryShaderParts.add(part);
	}
	
	public void addTessellationControlShaderPart(String part)
	{
		this.tessellationControlShaderParts.add(part);
	}
	
	public void addTessellationEvaluationShaderPart(String part)
	{
		this.tessellationEvaluationShaderParts.add(part);
	}
	
	public Set<String> getVertexShaderParts()
	{
		return this.vertexShaderParts;
	}
	
	public Set<String> getFragmentShaderParts()
	{
		return this.fragmentShaderParts;
	}
	
	public Set<String> getGeometryShaderParts()
	{
		return this.geometryShaderParts;
	}
	
	public Set<String> getTessellationControlShaderParts()
	{
		return this.tessellationControlShaderParts;
	}
	
	public Set<String> getTessellationEvaluationShaderParts()
	{
		return this.tessellationEvaluationShaderParts;
	}
}
