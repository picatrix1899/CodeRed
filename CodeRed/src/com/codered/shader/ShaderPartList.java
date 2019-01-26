package com.codered.shader;

import java.util.HashMap;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;

import com.codered.managing.loader.data.ShaderPartData;
import com.google.common.collect.Maps;

public class ShaderPartList
{
	private HashMap<String, ShaderPart> vertexShaders = Maps.newHashMap();
	private HashMap<String, ShaderPart> geometryShaders = Maps.newHashMap();
	private HashMap<String, ShaderPart> fragmentShaders = Maps.newHashMap();
	
	
	public void loadVertexShader(String name, ShaderPartData data) throws MalformedShaderException
	{
		vertexShaders.put(name, new ShaderPart().loadShader(name, data, GL20.GL_VERTEX_SHADER));
	}
	
	public void loadGeometryShader(String name, ShaderPartData data) throws MalformedShaderException
	{
		geometryShaders.put(name, new ShaderPart().loadShader(name, data, GL32.GL_GEOMETRY_SHADER));
	}
	
	public void loadFragmentShader(String name, ShaderPartData data) throws MalformedShaderException
	{
		fragmentShaders.put(name, new ShaderPart().loadShader(name, data, GL20.GL_FRAGMENT_SHADER));
	}
	
	public void dumpVertexShader()
	{
		for(String s : this.vertexShaders.keySet())
		{
			System.out.println(s);
		}
	}
	
	public ShaderPart getVertexShader(String name) { return vertexShaders.get(name); }

	public ShaderPart getGeometryShader(String name) { return geometryShaders.get(name); }
	
	public ShaderPart getFragmentShader(String name) { return fragmentShaders.get(name); }
	
	public void clear()
	{
		for(ShaderPart part : vertexShaders.values())
		{
			part.clear();
		}
		
		vertexShaders.clear();
		
		for(ShaderPart part : geometryShaders.values())
		{
			part.clear();
		}
		
		geometryShaders.clear();
		
		for(ShaderPart part : fragmentShaders.values())
		{
			part.clear();
		}
		
		fragmentShaders.clear();
	}
	

}
