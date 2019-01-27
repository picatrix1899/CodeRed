package com.codered.resource;

import java.util.Set;

import org.resources.materials.MaterialData;
import org.resources.objects.ObjectData;
import org.resources.textures.TextureData;

import com.google.common.collect.Sets;

public class IRM
{
	private static IRM instance;
	
	private org.resources.ResourceManager resourceManager;
	
	private Set<String> availableTextures = Sets.newConcurrentHashSet();
	private Set<String> pendingTextures = Sets.newConcurrentHashSet();
	
	private Set<String> availableStaticMeshes = Sets.newConcurrentHashSet();
	private Set<String> pendingStaticMeshes = Sets.newConcurrentHashSet();
	
	private Set<String> availableMaterial = Sets.newConcurrentHashSet();
	private Set<String> pendingMaterial = Sets.newConcurrentHashSet();
	
	public static IRM getInstance()
	{
		if(instance == null) instance = new IRM();
		return instance;
	}
	
	private IRM()
	{
		this.resourceManager = org.resources.ResourceManager.getInstance();
	}
	
	public void loadTextureForced(String id, org.resources.utils.ResourcePath path) throws Exception
	{
		if(this.availableTextures.contains(id)) return;
		if(this.pendingTextures.contains(id)) return;
		
		this.resourceManager.registerTextureLookup(id, path);
		
		this.pendingTextures.add(id);
		
		this.resourceManager.loadTextureForced(id);
		this.pendingTextures.remove(id);
		this.availableTextures.add(id);
	}
	
	public void loadTexture(String id, org.resources.utils.ResourcePath path) throws Exception
	{
		if(this.availableTextures.contains(id)) return;
		if(this.pendingTextures.contains(id)) return;
		
		this.resourceManager.registerTextureLookup(id, path);
		
		this.pendingTextures.add(id);
		
		this.resourceManager.loadTexture(id, (p) -> {
			if(p.resourceError == null)
			{
				this.pendingTextures.remove(id);
				this.availableTextures.add(id);
			}
		});
	}
	
	public boolean isTextureAvailable(String id)
	{
		return this.availableTextures.contains(id);
	}
	
	public TextureData getTexture(String id) throws Exception
	{
		return this.resourceManager.getTexture(id);
	}
	
	
	public void loadStaticMeshForced(String id, org.resources.utils.ResourcePath path) throws Exception
	{
		if(this.availableStaticMeshes.contains(id)) return;
		if(this.pendingStaticMeshes.contains(id)) return;
		
		this.resourceManager.registerStaticMeshLookup(id, path);
		
		this.pendingStaticMeshes.add(id);
		
		this.resourceManager.loadStaticMeshForced(id);
		this.pendingStaticMeshes.remove(id);
		this.availableStaticMeshes.add(id);
	}
	
	public void loadStaticMesh(String id, org.resources.utils.ResourcePath path) throws Exception
	{
		if(this.availableStaticMeshes.contains(id)) return;
		if(this.pendingStaticMeshes.contains(id)) return;
		
		this.resourceManager.registerStaticMeshLookup(id, path);
		
		this.pendingStaticMeshes.add(id);
		
		this.resourceManager.loadStaticMesh(id, (p) -> {
			if(p.resourceError == null)
			{
				this.pendingStaticMeshes.remove(id);
				this.availableStaticMeshes.add(id);
			}
		});
	}
	
	public boolean isStaticMeshAvailable(String id)
	{
		return this.availableStaticMeshes.contains(id);
	}
	
	public ObjectData getStaticMesh(String id) throws Exception
	{
		return this.resourceManager.getStaticMesh(id);
	}
	
	
	public void loadMaterialForced(String id, org.resources.utils.ResourcePath path) throws Exception
	{
		if(this.availableMaterial.contains(id)) return;
		if(this.pendingMaterial.contains(id)) return;
		
		this.resourceManager.registerStaticMeshLookup(id, path);
		
		this.pendingMaterial.add(id);
		
		this.resourceManager.loadStaticMeshForced(id);
		this.pendingMaterial.remove(id);
		this.availableMaterial.add(id);
	}
	
	public void loadMaterial(String id, org.resources.utils.ResourcePath path) throws Exception
	{
		if(this.availableMaterial.contains(id)) return;
		if(this.pendingMaterial.contains(id)) return;
		
		this.resourceManager.registerMaterialLookup(id, path);
		
		this.pendingMaterial.add(id);
		
		this.resourceManager.loadMaterial(id, (p) -> {
			if(p.resourceError == null)
			{
				this.pendingMaterial.remove(id);
				this.availableMaterial.add(id);
			}
		});
	}
	
	public boolean isMaterialAvailable(String id)
	{
		return this.availableMaterial.contains(id);
	}
	
	public MaterialData getMaterial(String id) throws Exception
	{
		return this.resourceManager.getMaterial(id);
	}
}
