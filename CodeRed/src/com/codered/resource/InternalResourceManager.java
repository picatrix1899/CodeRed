package com.codered.resource;

import java.io.File;
import java.util.HashMap;

import com.codered.engine.EngineRegistry;
import com.codered.font.FontType;
import com.codered.managing.models.Mesh;
import com.codered.managing.models.RawModel;
import com.codered.managing.models.TexturedModel;
import com.codered.material.Material;
import com.codered.resourcemanager.materials.MaterialData;
import com.codered.resourcemanager.materials.MaterialLoader;
import com.codered.texture.Texture;
import com.codered.texture.TextureData;
import com.codered.texture.TextureLoader;
import com.codered.utils.TextureUtils;
import com.codered.window.WindowContext;

public class InternalResourceManager
{
	private HashMap<String,Texture> textures = new HashMap<>();
	private HashMap<String,Material> materials = new HashMap<>();
	private HashMap<String,Mesh> staticMeshes = new HashMap<>();
	private HashMap<String,RawModel> rawModels = new HashMap<>();
	private HashMap<String,TexturedModel> texturedModels = new HashMap<>();
	private HashMap<String,FontType> fonts = new HashMap<>();
	
	private WindowContext context;

	org.resources.ResourceManager resourceManager;
	
	public InternalResourceManager()
	{
		this.context = EngineRegistry.getCurrentWindowContext();
		resourceManager = org.resources.ResourceManager.getInstance();
	}
	
	
	public Material getMaterial(String name)
	{
		if (!this.materials.containsKey(name)) throw new ResourceNotLoadedError("Material", name, 2);
		
		return this.materials.get(name);
	}
	
	public Mesh getStaticMesh(String name) { return this.staticMeshes.get(name); }
	
	public RawModel getRawModel(String name) { return this.rawModels.get(name); }
	
	public TexturedModel getTexturedModel(String name) { return this.texturedModels.get(name); }
	
	public Texture getTexture(String name)
	{
		if (!this.textures.containsKey(name)) throw new ResourceNotLoadedError("Texture", name, 2);
		
		return this.textures.get(name);
	}
	
	public FontType getFont(String name)
	{
		if(!this.fonts.containsKey(name)) throw new ResourceNotLoadedError("Font", name, 2);
		
		return this.fonts.get(name);
	}
	
	public boolean containsMaterial(String name) { return this.materials.containsKey(name); }
	public boolean containsStaticMesh(String name) { return this.staticMeshes.containsKey(name); }
	public boolean containsRawModel(String name) { return this.rawModels.containsKey(name); }
	public boolean containsTexturedModel(String name) { return this.texturedModels.containsKey(name); }
	public boolean containsTexture(String name) { return this.textures.containsKey(name); }
	public boolean containsFont(String name) { return this.fonts.containsKey(name); }
	
	public void regMaterial(String fileName)
	{
		if(!this.materials.containsKey(fileName))
		{
			MaterialData data = MaterialLoader.load(fileName);
			
			loadTextureForced(data.getAlbedoMap());
			loadTextureForced(data.getNormalMap());
			loadTextureForced(data.getGlowMap());

			Texture albedo = this.textures.get(data.getAlbedoMap());
			Texture normal = this.textures.get(data.getNormalMap());
			Texture glow = this.textures.get(data.getGlowMap());
			
			Material material = new Material(albedo, normal, glow, null, data.getSpecularPower(), data.getSpecularIntensity());
			
			this.materials.put(fileName, material);	
		}
	}
	
	public void regFont(String filename)
	{
		if(filename.isEmpty()) return;
		
		if(!this.fonts.containsKey(filename))
		{
			TextureData data = TextureLoader.loadTexture(filename + ".png");
			
			Texture texture = TextureUtils.genTexture(data);
			
			FontType font = new FontType(texture, new File(filename + ".fnt"));
			
			this.fonts.put(filename, font);
		}
	}

	
	public void regRawModel(String name, RawModel model)
	{
		if(!this.rawModels.containsKey(name))
			this.rawModels.put(name, model);
	}
	
	public void regTexturedModel(String name, String fileNameModel, String fileNameMaterial)
	{
		if(!this.texturedModels.containsKey(name))
		{
			TexturedModel model = new TexturedModel(fileNameModel, fileNameMaterial);
			
			this.texturedModels.put(name, model);
		}

	}
	
	public void update(double delta)
	{
		
	}
	
	public void renew()
	{
		clear();
	}
	
	public void clear()
	{
		for(Texture t : this.textures.values())
			t.cleanup();
	}

}
