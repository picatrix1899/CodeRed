package com.codered.engine.shaders.shader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;

import com.codered.engine.managing.ResourcePath;

import cmn.utilslib.essentials.Auto;

public class ShaderPartList
{
	private HashMap<String, ShaderPart> vertexShaders = Auto.HashMap();
	private HashMap<String, ShaderPart> geometryShaders = Auto.HashMap();
	private HashMap<String, ShaderPart> fragmentShaders = Auto.HashMap();
	
	
	public void loadVertexShader(String name, ResourcePath res) throws ShaderNotFoundException, MalformedShaderException
	{
		vertexShaders.put(name, new ShaderPart().loadShader(toUrl(res), res.src(), GL20.GL_VERTEX_SHADER));
	}
	
	public void loadGeometryShader(String name, ResourcePath res) throws ShaderNotFoundException, MalformedShaderException
	{
		geometryShaders.put(name, new ShaderPart().loadShader(toUrl(res), res.src(), GL32.GL_GEOMETRY_SHADER));
	}
	
	public void loadFragmentShader(String name, ResourcePath res) throws ShaderNotFoundException, MalformedShaderException
	{
		fragmentShaders.put(name, new ShaderPart().loadShader(toUrl(res), res.src(), GL20.GL_FRAGMENT_SHADER));
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
	
	private URL toUrl(ResourcePath res) throws ShaderNotFoundException
	{
		URL url = null;
		
		try
		{
			switch(res.dest())
			{
				case EMBEDED:
				{
					url = res.src().getResource(res.toString());
					break;
				}
				case LOCAL:
				{
					url = new File(res.toString()).toURI().toURL();
					break;
				}
				case URL:
				{
					url = new URL(res.toString());
					break;
				}
			}
			
			if(url == null) throw new ShaderNotFoundException(res.toString());
			
			return url;
		}
		catch(IOException e)
		{
			throw new ShaderNotFoundException(res.toString());
		}
	}
}
