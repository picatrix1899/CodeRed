package org.resources;

import java.net.URL;

import org.resources.errors.MissingResourceLookupException;
import org.resources.utils.LoaderCallback;
import org.resources.utils.ResourceLoader;
import org.resources.utils.ResourceLookupTable;
import org.resources.utils.ResourceStorage;

public class ResourceManagerPart<T>
{
	private ResourceLookupTable lookup;
	private ResourceLoader<T> loader;
	private ResourceStorage<T> storage;
	
	private String type;
	
	public ResourceManagerPart(String type, ResourceLoader<T> loader)
	{
		this.lookup = new ResourceLookupTable();
		this.loader = loader;
		this.storage = new ResourceStorage<T>();
		
		this.type = type;
	}
	
	public void start()
	{
		this.loader.startPool();
	}
	
	public void stop()
	{
		this.loader.stopPool();
	}
	
	public void cancel()
	{
		this.loader.cancelPool();
	}
	
	public void registerLookup(String id, URL url)
	{
		if(!this.lookup.contains(id))
			this.lookup.put(id, url);
	}
	
	public T get(String id) throws Exception
	{
		if(!this.lookup.contains(id)) throw new MissingResourceLookupException(this.type, id);

		return this.storage.get(id);
	}
	
	public void loadForced(String id) throws Exception
	{
		if(!this.lookup.contains(id)) throw new MissingResourceLookupException(this.type, id);
		if(this.storage.contains(id)) return;
		
		try
		{
			T data = this.loader.directLoadResource(id, this.lookup.get(id));
			
			this.storage.add(id, data);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void load(String id, LoaderCallback<T> callback) throws Exception
	{
		if(!this.lookup.contains(id)) throw new MissingResourceLookupException(this.type, id);
		if(this.storage.contains(id)) return;

		this.loader.loadResource(id, this.lookup.get(id), (response) ->
		{
			if(response.resourceError == null) this.storage.add(id, response.resourceData);
			callback.callback(response);
		});
	}
	
	public boolean isAvailable(String id)
	{
		return this.storage.contains(id);
	}
	
	public void unload(String id) throws Exception
	{
		if(!this.lookup.contains(id)) throw new MissingResourceLookupException(this.type, id);
			this.storage.unload(id);
	}
}
