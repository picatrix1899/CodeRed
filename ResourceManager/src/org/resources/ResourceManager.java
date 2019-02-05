package org.resources;

import org.resources.errors.MissingResourceLookupException;
import org.resources.materials.MaterialData;
import org.resources.materials.MaterialLoader;
import org.resources.objects.ObjectData;
import org.resources.objects.ObjectLoader;
import org.resources.shaderparts.ShaderPartData;
import org.resources.shaderparts.ShaderPartLoader;
import org.resources.textures.TextureData;
import org.resources.textures.TextureLoader;
import org.resources.utils.LoaderCallback;
import org.resources.utils.ResourceLookupTable;
import org.resources.utils.ResourcePath;
import org.resources.utils.ResourceStorage;

public class ResourceManager
{
	private ResourceLookupTable textureLookup;
	private TextureLoader textureLoader;
	private ResourceStorage<TextureData> textureStorage;
	
	private ResourceLookupTable staticMeshLookup;
	private ObjectLoader staticMeshLoader;
	private ResourceStorage<ObjectData> staticMeshStorage;
	
	private ResourceLookupTable materialLookup;
	private MaterialLoader materialLoader;
	private ResourceStorage<MaterialData> materialStorage;
	
	private ResourceLookupTable shaderPartLookup;
	private ShaderPartLoader shaderPartLoader;
	private ResourceStorage<ShaderPartData> shaderPartStorage;
	
	private ResourceManagerPart<TextureData> textures;
	private ResourceManagerPart<ObjectData> staticMeshs;
	private ResourceManagerPart<MaterialData> materials;
	private ResourceManagerPart<ShaderPartData> shaderParts;
	
	private static ResourceManager instance;
	
	public boolean isRunning = false;
	
	public static ResourceManager getInstance()
	{
		if(instance == null) instance = new ResourceManager();
		
		return instance;
	}
	
	private ResourceManager()
	{
		this.textures = new ResourceManagerPart<TextureData>("Texture", new TextureLoader());
		this.staticMeshs = new ResourceManagerPart<ObjectData>("Static Mesh", new ObjectLoader());
		this.materials = new ResourceManagerPart<MaterialData>("Material", new MaterialLoader());
		this.shaderParts = new ResourceManagerPart<ShaderPartData>("Shader Part", new ShaderPartLoader());
		
		this.textureLookup = new ResourceLookupTable();
		this.textureLoader = new TextureLoader();
		this.textureStorage = new ResourceStorage<TextureData>();
		
		this.staticMeshLookup = new ResourceLookupTable();
		this.staticMeshLoader = new ObjectLoader();
		this.staticMeshStorage = new ResourceStorage<ObjectData>();
		
		this.materialLookup = new ResourceLookupTable();
		this.materialLoader = new MaterialLoader();
		this.materialStorage = new ResourceStorage<MaterialData>();
		
		this.shaderPartLookup = new ResourceLookupTable();
		this.shaderPartLoader = new ShaderPartLoader();
		this.shaderPartStorage = new ResourceStorage<ShaderPartData>();
	}
	
	public void start()
	{
		isRunning = true;
		this.textures.start();
		this.staticMeshs.start();
		this.materials.start();
		this.shaderParts.start();
		
		this.textureLoader.startPool();
		this.staticMeshLoader.startPool();
		this.materialLoader.startPool();
		this.shaderPartLoader.startPool();
	}
	
	public void stop()
	{
		isRunning = false;
		this.textures.stop();
		this.staticMeshs.stop();
		this.materials.stop();
		this.shaderParts.stop();
		
		this.textureLoader.stopPool();
		this.staticMeshLoader.stopPool();
		this.materialLoader.stopPool();
		this.shaderPartLoader.stopPool();
	}
	
	public void cancel()
	{
		this.textures.cancel();
		this.staticMeshs.cancel();
		this.materials.cancel();
		this.shaderParts.cancel();
		
		this.textureLoader.cancelPool();
		this.staticMeshLoader.cancelPool();
		this.materialLoader.cancelPool();
		this.shaderPartLoader.cancelPool();
	}
	
	public ResourceManagerPart<TextureData> textures() { return this.textures; }
	public ResourceManagerPart<ObjectData> staticMeshs() { return this.staticMeshs; }
	public ResourceManagerPart<MaterialData> materials() { return this.materials; }
	public ResourceManagerPart<ShaderPartData> shaderParts() { return this.shaderParts; }
	
	public void registerTextureLookup(String id, ResourcePath path)
	{
		if(!this.textureLookup.contains(id))
			this.textureLookup.put(id, path);
	}
	
	public void registerStaticMeshLookup(String id, ResourcePath path)
	{
		if(!this.staticMeshLookup.contains(id))
			this.staticMeshLookup.put(id, path);
	}
	
	public void registerMaterialLookup(String id, ResourcePath path)
	{
		if(!this.materialLookup.contains(id))
			this.materialLookup.put(id, path);
	}
	
	public void registerShaderPartLookup(String id, ResourcePath path)
	{
		if(!this.shaderPartLookup.contains(id))
			this.shaderPartLookup.put(id, path);
	}
	
	public TextureData getTexture(String id) throws Exception
	{
		if(!this.textureLookup.contains(id)) throw new MissingResourceLookupException("Texture", id);
		return this.textureStorage.get(id);
	}
	

	public void loadTextureForced(String id) throws Exception
	{
		if(!this.textureLookup.contains(id)) throw new MissingResourceLookupException("Texture", id);
		if(this.textureStorage.contains(id)) return;
		
		try
		{
			TextureData data = this.textureLoader.directLoadResource(id, this.textureLookup.get(id));
			
			this.textureStorage.add(id, data);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void loadTexture(String id, LoaderCallback<TextureData> callback) throws Exception
	{
		if(!this.textureLookup.contains(id)) throw new MissingResourceLookupException("Texture", id);
		if(this.textureStorage.contains(id)) return;

		this.textureLoader.loadResource(id, this.textureLookup.get(id), (response) ->
		{
			if(response.resourceError == null) this.textureStorage.add(id, response.resourceData);
			callback.callback(response);
		});
	}

	
	public ObjectData getStaticMesh(String id) throws Exception
	{
		if(!this.staticMeshLookup.contains(id)) throw new MissingResourceLookupException("Static Mesh", id);
		return this.staticMeshStorage.get(id);
	}
	
	public void loadStaticMeshForced(String id) throws Exception
	{
		if(!this.staticMeshLookup.contains(id)) throw new MissingResourceLookupException("Static Mesh", id);
		if(this.staticMeshStorage.contains(id)) return;
		
		try
		{
			ObjectData data = this.staticMeshLoader.directLoadResource(id, this.staticMeshLookup.get(id));
			
			this.staticMeshStorage.add(id, data);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void loadStaticMesh(String id, LoaderCallback<ObjectData> callback) throws Exception
	{
		if(!this.staticMeshLookup.contains(id)) throw new MissingResourceLookupException("Static Mesh", id);
		if(this.staticMeshStorage.contains(id)) return;

		this.staticMeshLoader.loadResource(id, this.staticMeshLookup.get(id), (response) ->
		{
			if(response.resourceError == null) this.staticMeshStorage.add(id, response.resourceData);
			callback.callback(response);
		});
	}
	
	
	public MaterialData getMaterial(String id) throws Exception
	{
		if(!this.materialLookup.contains(id)) throw new MissingResourceLookupException("Material", id);
		return this.materialStorage.get(id);
	}
	
	public void loadMaterialForced(String id) throws Exception
	{
		if(!this.materialLookup.contains(id)) throw new MissingResourceLookupException("Material", id);
		if(this.materialStorage.contains(id)) return;
		
		try
		{
			MaterialData data = this.materialLoader.directLoadResource(id, this.materialLookup.get(id));
			
			this.materialStorage.add(id, data);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void loadMaterial(String id, LoaderCallback<MaterialData> callback) throws Exception
	{
		if(!this.materialLookup.contains(id)) throw new MissingResourceLookupException("Material", id);
		if(this.materialStorage.contains(id)) return;

		this.materialLoader.loadResource(id, this.materialLookup.get(id), (response) ->
		{
			if(response.resourceError == null) this.materialStorage.add(id, response.resourceData);
			callback.callback(response);
		});
	}
	
	
	public ShaderPartData getShaderPart(String id) throws Exception
	{
		if(!this.shaderPartLookup.contains(id)) throw new MissingResourceLookupException("Shader Part", id);
		return this.shaderPartStorage.get(id);
	}
	
	public void loadShaderPartForced(String id) throws Exception
	{
		if(!this.shaderPartLookup.contains(id)) throw new MissingResourceLookupException("Shader Part", id);
		if(this.shaderPartStorage.contains(id)) return;
		
		try
		{
			ShaderPartData data = this.shaderPartLoader.directLoadResource(id, this.shaderPartLookup.get(id));
			
			this.shaderPartStorage.add(id, data);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void loadShaderPart(String id, LoaderCallback<ShaderPartData> callback) throws Exception
	{
		if(!this.shaderPartLookup.contains(id)) throw new MissingResourceLookupException("Shader Part", id);
		if(this.shaderPartStorage.contains(id)) return;

		this.shaderPartLoader.loadResource(id, this.shaderPartLookup.get(id), (response) ->
		{
			if(response.resourceError == null) this.shaderPartStorage.add(id, response.resourceData);
			callback.callback(response);
		});
	}
	
	
	public void unloadTexture(String id) throws Exception
	{
		if(!this.textureLookup.contains(id)) throw new MissingResourceLookupException("Texture", id);
		this.textureStorage.unload(id);
	}
	
	public void unloadStaticMesh(String id) throws Exception
	{
		if(!this.staticMeshLookup.contains(id)) throw new MissingResourceLookupException("Static Mesh", id);
		this.staticMeshStorage.unload(id);
	}
	
	public void unloadMaterial(String id) throws Exception
	{
		if(!this.materialLookup.contains(id)) throw new MissingResourceLookupException("Static Mesh", id);
		this.materialStorage.unload(id);
	}
	
	public void unloadShaderPart(String id) throws Exception
	{
		if(!this.shaderPartLookup.contains(id)) throw new MissingResourceLookupException("Shader Part", id);
		this.shaderPartStorage.unload(id);
	}
}
