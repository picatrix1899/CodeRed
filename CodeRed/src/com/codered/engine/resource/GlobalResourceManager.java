package com.codered.engine.resource;

import java.util.HashMap;

import com.codered.engine.managing.Material;
import com.codered.engine.managing.loader.data.ShaderPartData;
import com.codered.engine.managing.loader.data.TextureData;

public class GlobalResourceManager
{
	
	public static final GlobalResourceManager INSTANCE = new GlobalResourceManager();
	
	private GlobalResourceManager() {}
	
	private HashMap<String,TextureData> textures = new HashMap<String, TextureData>();
	private HashMap<String,Material> materials = new HashMap<String,Material>();
	private HashMap<String,ShaderPartData> shaderparts = new HashMap<String,ShaderPartData>();
	
	public void regTexture(String name, TextureData data) { this.textures.put(name, data); }
	public void regMaterial(String name, Material data) { this.materials.put(name, data); }
	public void regShaderPart(String name, ShaderPartData data) { this.shaderparts.put(name, data); }
	
	public boolean existsTexture(String name) { return this.textures.containsKey(name); }
	public boolean existsMaterial(String name) { return this.materials.containsKey(name); }
	public boolean existsShaderPart(String name) { return this.shaderparts.containsKey(name); }
	
	public TextureData getTexture(String name) { return this.textures.get(name); }
	public Material getMaterial(String name) { return this.materials.get(name); }
	public ShaderPartData getShaderPart(String name) { return this.shaderparts.get(name); }
}
