package com.codered.resource;

import java.io.File;
import java.util.HashMap;

import com.codered.engine.EngineRegistry;
import com.codered.font.FontType;
import com.codered.managing.loader.data.MaterialData;
import com.codered.managing.loader.data.OBJFile;
import com.codered.managing.models.Mesh;
import com.codered.managing.models.RawModel;
import com.codered.managing.models.TexturedModel;
import com.codered.material.Material;
import com.codered.material.MaterialLoader;
import com.codered.texture.Texture;
import com.codered.texture.TextureData;
import com.codered.texture.TextureLoader;
import com.codered.utils.TextureUtils;
import com.codered.window.WindowContext;
import com.google.common.collect.Maps;

public class InternalResourceManager
{
	private HashMap<String,Texture> textures = Maps.newHashMap();
	private HashMap<String,Material> materials = Maps.newHashMap();
	private HashMap<String,Mesh> staticMeshes = Maps.newHashMap();
	private HashMap<String,RawModel> rawModels = Maps.newHashMap();
	private HashMap<String,TexturedModel> texturedModels = Maps.newHashMap();
	private HashMap<String,FontType> fonts = Maps.newHashMap();
	
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
			
			Texture texture = TextureUtils.genTexture(data, this.context);
			
			FontType font = new FontType(texture, new File(filename + ".fnt"));
			
			this.fonts.put(filename, font);
		}
	}
	
	public void loadTextureForced(String fileName)
	{
		if(fileName.isEmpty()) return;
		
		if(!this.textures.containsKey(fileName))
		{
			org.resources.utils.ResourcePath path = new org.resources.utils.ResourcePath();
			path.path = fileName;
			
			resourceManager.registerTextureLookup(fileName, path);
			
			try
			{
				org.resources.textures.TextureData data = resourceManager.getTexture(fileName);
				
				Texture t = TextureUtils.genTexture(data, context);
				
				this.textures.put(fileName, t);
				
			} catch (Exception e)
			{
				e.printStackTrace();
			}

		}
	}
	
	public void regStaticMesh(String fileName)
	{
		if(!this.staticMeshes.containsKey(fileName))
		{
			OBJFile obj = new OBJFile();
			obj.load(new File(fileName));
			Mesh mesh = new Mesh().loadFromObj(obj);
			
			this.staticMeshes.put(fileName, mesh);
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
			regStaticMesh(fileNameModel);
			regMaterial(fileNameMaterial);
			
			TexturedModel model = new TexturedModel(this.staticMeshes.get(fileNameModel), this.materials.get(fileNameMaterial));
			
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
