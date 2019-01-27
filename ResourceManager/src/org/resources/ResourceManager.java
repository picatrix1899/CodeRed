package org.resources;

import org.resources.errors.MissingResourceLookupException;
import org.resources.materials.MaterialData;
import org.resources.materials.MaterialLoader;
import org.resources.objects.ObjectData;
import org.resources.objects.ObjectLoader;
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
	
	private static ResourceManager instance;
	
	public boolean isRunning = false;
	
	public static ResourceManager getInstance()
	{
		if(instance == null) instance = new ResourceManager();
		
		return instance;
	}
	
	private ResourceManager()
	{
		this.textureLookup = new ResourceLookupTable();
		this.textureLoader = new TextureLoader();
		this.textureStorage = new ResourceStorage<TextureData>();
		
		this.staticMeshLookup = new ResourceLookupTable();
		this.staticMeshLoader = new ObjectLoader();
		this.staticMeshStorage = new ResourceStorage<ObjectData>();
		
		this.materialLookup = new ResourceLookupTable();
		this.materialLoader = new MaterialLoader();
		this.materialStorage = new ResourceStorage<MaterialData>();
	}
	
	public void start()
	{
		isRunning = true;
		this.textureLoader.startPool();
	}
	
	public void stop()
	{
		isRunning = false;
		this.textureLoader.stopPool();
	}
	
	public void cancel()
	{
		this.textureLoader.cancelPool();
	}
	
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
}
