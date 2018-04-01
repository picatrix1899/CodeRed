package com.codered.engine.resource;

import java.util.HashMap;

import com.codered.engine.managing.Material;
import com.codered.engine.managing.Texture;
import com.codered.engine.managing.loader.LambdaFont;
import com.codered.engine.managing.loader.TextureLoader;
import com.codered.engine.managing.models.Mesh;
import com.codered.engine.managing.models.RawModel;
import com.codered.engine.managing.models.TexturedModel;

import cmn.utilslib.essentials.Auto;

public class InternalResourceManager
{
	private HashMap<String,Texture> colorMaps = Auto.HashMap();
	private HashMap<String,Texture> normalMaps = Auto.HashMap();
	private HashMap<String,Texture> glowMaps = Auto.HashMap();
	private HashMap<String,Texture> dispMaps = Auto.HashMap();
	private HashMap<String,Material> materials = Auto.HashMap();
	
	private HashMap<String,Mesh> staticMeshes = Auto.HashMap();
	private HashMap<String,RawModel> rawModels = Auto.HashMap();
	private HashMap<String,TexturedModel> texturedModels = Auto.HashMap();
	
	private HashMap<String,LambdaFont> fonts = Auto.HashMap();
	
	public Material getMaterial(String name) { return this.materials.get(name); }
	
	public LambdaFont getFont(String name) { return this.fonts.get(name); }
	 
	public Mesh getStaticMesh(String name) { return this.staticMeshes.get(name); }
	
	public RawModel getRawModel(String name) { return this.rawModels.get(name); }
	
	public TexturedModel getTexturedModel(String name) { return this.texturedModels.get(name); }
	
	public Texture getColorMap(String name) { return this.colorMaps.get(name); }
	public Texture getNormalMap(String name) { return this.normalMaps.get(name); }
	public Texture getGlowMap(String name) { return this.glowMaps.get(name); }
	public Texture getDisplacementMap(String name) { return this.dispMaps.get(name); }
	
	
	public boolean containsMaterial(String name) { return this.materials.containsKey(name); }
	public boolean containsFont(String name) { return this.fonts.containsKey(name); }
	public boolean containsStaticMesh(String name) { return this.staticMeshes.containsKey(name); }
	public boolean containsRawModel(String name) { return this.rawModels.containsKey(name); }
	public boolean containsTexturedModel(String name) { return this.texturedModels.containsKey(name); }
	public boolean containsColorMap(String name) { return this.colorMaps.containsKey(name); }
	public boolean containsNormalMap(String name) { return this.normalMaps.containsKey(name); }
	public boolean containsGlowMap(String name) { return this.glowMaps.containsKey(name); }
	public boolean containsDisplacementMap(String name) { return this.dispMaps.containsKey(name); }
	
	public void regMaterial(String name, Material material)
	{
		if(!this.materials.containsKey(name))
			this.materials.put(name, material);	
		
		if(!this.colorMaps.containsKey(material.getColorMap()))
			this.colorMaps.put(material.getColorMap(), TextureLoader.loadTexture(material.getColorMap()));
		
		if(!this.normalMaps.containsKey(material.getNormalMap()))
			this.normalMaps.put(material.getNormalMap(), TextureLoader.loadTexture(material.getNormalMap()));
		
		if(!this.glowMaps.containsKey(material.getGlowMap()))
			this.glowMaps.put(material.getGlowMap(), TextureLoader.loadTexture(material.getGlowMap()));
		
		if(!this.dispMaps.containsKey(material.getDisplacementMap()))
			this.dispMaps.put(material.getDisplacementMap(), TextureLoader.loadTexture(material.getDisplacementMap()));
	}
	
	public void regColorMap(String name, Texture texture)
	{
		if(!this.colorMaps.containsKey(name))
			this.colorMaps.put(name, texture);			
	}
	
	public void regNormalMap(String name, Texture texture)
	{
		if(!this.normalMaps.containsKey(name))
			this.normalMaps.put(name, texture);
	}
	
	public void regGlowMap(String name, Texture texture)
	{
		if(!this.glowMaps.containsKey(name))
			this.glowMaps.put(name, texture);
	}
	
	public void regDisplacementMap(String name, Texture texture)
	{
		if(!this.dispMaps.containsKey(name))
			this.dispMaps.put(name, texture);			
	}
	
	public void regStaticMesh(String name, Mesh mesh)
	{
		if(!this.staticMeshes.containsKey(name))
			this.staticMeshes.put(name, mesh);
	}
	
	public void regRawModel(String name, RawModel model)
	{
		if(!this.rawModels.containsKey(name))
			this.rawModels.put(name, model);
	}
	
	public void regTexturedModel(String name, TexturedModel model)
	{
		if(!this.texturedModels.containsKey(name))
			this.texturedModels.put(name, model);
	}
	
	public void regFont(String name, LambdaFont font)
	{
		if(!this.fonts.containsKey(name))
			this.fonts.put(name, font);
	}
	
	public void renew()
	{
		clear();
		
		regColorMap("black", ResourceManager.DEFAULT_TEXTURE);
		regGlowMap("black", ResourceManager.DEFAULT_TEXTURE);
		regNormalMap("black", ResourceManager.DEFAULT_TEXTURE);
		regDisplacementMap("black", ResourceManager.DEFAULT_TEXTURE);
	}
	
	public void clear()
	{
		for(Texture t : this.colorMaps.values())
			t.cleanup();
		
		for(Texture t : this.dispMaps.values())
			t.cleanup();
		
		for(Texture t : this.glowMaps.values())
			t.cleanup();
		
		for(Texture t : this.normalMaps.values())
			t.cleanup();
		
		for(LambdaFont f : this.fonts.values())
			f.getTexture().cleanup();
	}
}
