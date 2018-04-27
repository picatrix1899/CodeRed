package com.codered.engine.resource;

import java.util.HashMap;

import com.codered.engine.managing.Material;
import com.codered.engine.managing.Texture;
import com.codered.engine.managing.loader.LambdaFont;
import com.codered.engine.managing.models.Mesh;
import com.codered.engine.managing.models.RawModel;
import com.codered.engine.managing.models.TexturedModel;
import com.codered.engine.utils.TextureUtils;

public class InternalResourceManager
{
	private HashMap<String,Texture> textures = new HashMap<String,Texture>();
	private HashMap<String,Material> materials = new HashMap<String,Material>();
	private HashMap<String,Mesh> staticMeshes = new HashMap<String,Mesh>();
	private HashMap<String,RawModel> rawModels = new HashMap<String,RawModel>();
	private HashMap<String,TexturedModel> texturedModels = new HashMap<String,TexturedModel>();
	
	private HashMap<String,LambdaFont> fonts = new HashMap<String,LambdaFont>();
	
	private ResourceManager master;
	
	public InternalResourceManager(ResourceManager m)
	{
		this.master = m;
	}
	
	
	public Material getMaterial(String name) { return this.materials.get(name); }
	
	public LambdaFont getFont(String name) { return this.fonts.get(name); }
	 
	public Mesh getStaticMesh(String name) { return this.staticMeshes.get(name); }
	
	public RawModel getRawModel(String name) { return this.rawModels.get(name); }
	
	public TexturedModel getTexturedModel(String name) { return this.texturedModels.get(name); }
	
	public Texture getTexture(String name) { return this.textures.get(name); }
	
	
	public boolean containsMaterial(String name) { return this.materials.containsKey(name); }
	public boolean containsFont(String name) { return this.fonts.containsKey(name); }
	public boolean containsStaticMesh(String name) { return this.staticMeshes.containsKey(name); }
	public boolean containsRawModel(String name) { return this.rawModels.containsKey(name); }
	public boolean containsTexturedModel(String name) { return this.texturedModels.containsKey(name); }
	public boolean containsTexture(String name) { return this.textures.containsKey(name); }
	
	public void regMaterial(String name, Material material)
	{
		if(!this.materials.containsKey(name))
			this.materials.put(name, material);	
		
		if(!this.textures.containsKey(material.getColorMap()))
			this.textures.put(material.getColorMap(), TextureUtils.genTexture(GlobalResourceManager.INSTANCE.getTexture(material.getColorMap()), this.master.getWindowContext()));
		
		if(!this.textures.containsKey(material.getNormalMap()))
			this.textures.put(material.getNormalMap(), TextureUtils.genTexture(GlobalResourceManager.INSTANCE.getTexture(material.getNormalMap()), this.master.getWindowContext()));
		
		if(!this.textures.containsKey(material.getGlowMap()))
			this.textures.put(material.getGlowMap(), TextureUtils.genTexture(GlobalResourceManager.INSTANCE.getTexture(material.getGlowMap()), this.master.getWindowContext()));
		
		if(!this.textures.containsKey(material.getDisplacementMap()))
			this.textures.put(material.getDisplacementMap(), TextureUtils.genTexture(GlobalResourceManager.INSTANCE.getTexture(material.getDisplacementMap()), this.master.getWindowContext()));
	}
	
	public void regTexture(String name, Texture texture)
	{
		if(!this.textures.containsKey(name))
			this.textures.put(name, texture);			
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
	}
	
	public void clear()
	{
		for(Texture t : this.textures.values())
			t.cleanup();
		
		for(LambdaFont f : this.fonts.values())
			f.getTexture().cleanup();
	}

}
