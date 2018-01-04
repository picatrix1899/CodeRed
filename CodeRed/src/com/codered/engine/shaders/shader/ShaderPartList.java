package com.codered.engine.shaders.shader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;

import cmn.utilslib.essentials.Auto;

public class ShaderPartList
{
	private HashMap<String, ShaderPart> vertexShaders = Auto.HashMap();
	private HashMap<String, ShaderPart> geometryShaders = Auto.HashMap();
	private HashMap<String, ShaderPart> fragmentShaders = Auto.HashMap();
	
	
	
	public void loadVertexShader(String name, File f, Class<?> clazz) throws ShaderNotFoundException, MalformedShaderException
	{
		try
		{
			vertexShaders.put(name, new ShaderPart().loadShader(f.toURI().toURL(), clazz, GL20.GL_VERTEX_SHADER));
		}
		catch(MalformedURLException e)
		{
			throw new ShaderNotFoundException(f.getAbsolutePath().toString());
		}

	}
	
	public void loadGeometryShader(String name, File f, Class<?> clazz) throws ShaderNotFoundException, MalformedShaderException
	{
		try
		{
			geometryShaders.put(name, new ShaderPart().loadShader(f.toURI().toURL(), clazz, GL32.GL_GEOMETRY_SHADER));
		}
		catch(MalformedURLException e)
		{
			throw new ShaderNotFoundException(f.getAbsolutePath().toString());
		}

	}
	
	public void loadFragmentShader(String name, File f, Class<?> clazz) throws ShaderNotFoundException, MalformedShaderException
	{
		try
		{
			fragmentShaders.put(name, new ShaderPart().loadShader(f.toURI().toURL(), clazz, GL20.GL_FRAGMENT_SHADER));
		}
		catch(MalformedURLException e)
		{
			throw new ShaderNotFoundException(f.getAbsolutePath().toString());
		}

	}
	
	public void loadVertexShader(String name, String path, boolean embeded, Class<?> clazz) throws ShaderNotFoundException, MalformedShaderException
	{
		vertexShaders.put(name, new ShaderPart().loadShader(toUrl(path, embeded), clazz, GL20.GL_VERTEX_SHADER));
	}
	
	public void loadGeometryShader(String name, String path, boolean embeded, Class<?> clazz) throws ShaderNotFoundException, MalformedShaderException
	{
		geometryShaders.put(name, new ShaderPart().loadShader(toUrl(path, embeded), clazz, GL32.GL_GEOMETRY_SHADER));
	}
	
	public void loadFragmentShader(String name, String path, boolean embeded, Class<?> clazz) throws ShaderNotFoundException, MalformedShaderException
	{
		fragmentShaders.put(name, new ShaderPart().loadShader(toUrl(path, embeded), clazz, GL20.GL_FRAGMENT_SHADER));
	}
	
	
	public void loadVertexShader(String name, URL url, Class<?> clazz) throws ShaderNotFoundException, MalformedShaderException
	{
		vertexShaders.put(name, new ShaderPart().loadShader(url, clazz, GL20.GL_VERTEX_SHADER));
	}
	
	public void loadGeometryShader(String name, URL url, Class<?> clazz) throws ShaderNotFoundException, MalformedShaderException
	{
		geometryShaders.put(name, new ShaderPart().loadShader(url, clazz, GL32.GL_GEOMETRY_SHADER));
	}
	
	public void loadFragmentShader(String name, URL url, Class<?> clazz) throws ShaderNotFoundException, MalformedShaderException
	{
		fragmentShaders.put(name, new ShaderPart().loadShader(url, clazz, GL20.GL_FRAGMENT_SHADER));
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
	
	private URL toUrl(String path, boolean embeded) throws ShaderNotFoundException
	{
		URL url;
		
		try
		{
			if(embeded)
				url = ShaderParts.class.getResource(path);
			else
				url = new File(path).toURI().toURL();
			
			if(url == null) throw new ShaderNotFoundException(path);
			
			return url;
		}
		catch(IOException e)
		{
			throw new ShaderNotFoundException(path);
		}
	}
}
