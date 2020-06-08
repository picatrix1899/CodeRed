package com.codered.resource.registry;

import com.codered.ResourceHolder;
import com.codered.model.Mesh;

public interface ResourceRegistry extends ResourceHolder
{
	ResourceRegistryEntry<Mesh> staticMeshes();
	
	public void addResourceType(Class<? extends ResourceHolder> type);
	
	public <T extends ResourceHolder> void addResource(String key, T resource, Class<T> type);

	public <T extends ResourceHolder> T get(String key, Class<T> type);
}
