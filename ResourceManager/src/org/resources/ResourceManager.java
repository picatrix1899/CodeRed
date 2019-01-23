package org.resources;

import org.resources.errors.MissingResourceLookupException;
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
	
	public TextureData getTexture(String id) throws Exception
	{
		if(!this.textureLookup.contains(id)) throw new MissingResourceLookupException("Texture", id);
		if(this.textureStorage.contains(id)) return this.textureStorage.get(id);
		
		try
		{
			TextureData data = this.textureLoader.directLoadResource(id, this.textureLookup.get(id));
			
			this.textureStorage.add(id, data);
			return data;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
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
	
	public void unloadTexture(String id) throws Exception
	{
		if(!this.textureLookup.contains(id)) throw new MissingResourceLookupException("Texture", id);
		this.textureStorage.unload(id);
	}
}
