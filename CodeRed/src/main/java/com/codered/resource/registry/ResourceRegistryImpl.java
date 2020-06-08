package com.codered.resource.registry;

import java.util.HashMap;
import java.util.Map;

import com.codered.ResourceHolder;
import com.codered.model.Mesh;
import com.codered.model.Model;
import com.codered.rendering.material.Material;
import com.codered.rendering.shader.FragmentShaderPart;
import com.codered.rendering.shader.VertexShaderPart;
import com.codered.rendering.texture.Texture;

public class ResourceRegistryImpl implements ResourceRegistry
{
	private Map<Class<? extends ResourceHolder>, ResourceRegistryEntry<ResourceHolder>> resources = new HashMap<>();
	
	public ResourceRegistryImpl()
	{
		addResourceType(Texture.class);
		addResourceType(Model.class);
		addResourceType(VertexShaderPart.class);
		addResourceType(FragmentShaderPart.class);
		addResourceType(Material.class);
	}
	
	private ResourceRegistryEntry<Mesh> staticMeshes = new ResourceRegistryEntry<>();

	public ResourceRegistryEntry<Mesh> staticMeshes() { return this.staticMeshes; }
	
	public void addResourceType(Class<? extends ResourceHolder> type)
	{
		this.resources.put(type, new ResourceRegistryEntry<>());
	}
	
	public <T extends ResourceHolder> void addResource(String key, T resource, Class<T> type)
	{
		ResourceRegistryEntry<ResourceHolder> entry = this.resources.get(type);
		if(entry == null) return;
		
		entry.add(key, resource);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends ResourceHolder> T get(String key, Class<T> type)
	{
		ResourceRegistryEntry<ResourceHolder> entry = this.resources.get(type);
		if(entry == null) return null;
		
		return (T) entry.get(key);
	}
	
	public void release(boolean forced)
	{
		for(ResourceRegistryEntry<ResourceHolder> entry : this.resources.values())
		{
			entry.release(forced);
		}
		
		this.staticMeshes.clear();
	}
}
