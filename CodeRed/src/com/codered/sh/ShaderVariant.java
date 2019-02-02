package com.codered.sh;

import java.util.Set;

import com.google.common.collect.Sets;

public class ShaderVariant
{
	private Set<String> shaderParts = Sets.newHashSet();
	
	public void addShaderPart(String part)
	{
		this.shaderParts.add(part);
	}
	
	public Set<String> getShaderParts()
	{
		return this.shaderParts;
	}
}
