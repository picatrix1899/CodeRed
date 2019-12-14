package com.codered.resource.registry;

import com.codered.managing.models.Mesh;
import com.codered.model.StaticModel;
import com.codered.rendering.material.Material;
import com.codered.rendering.shader.ShaderPart;
import com.codered.rendering.texture.Texture;

public class ResourceRegistryImpl implements ResourceRegistry
{
	private ResourceRegistryEntry<Texture> textures = new ResourceRegistryEntry<>();
	private ResourceRegistryEntry<Mesh> staticMeshes = new ResourceRegistryEntry<>();
	private ResourceRegistryEntry<StaticModel> staticModels = new ResourceRegistryEntry<>();
	private ResourceRegistryEntry<Material> materials = new ResourceRegistryEntry<>();
	private ResourceRegistryEntry<ShaderPart> shaderPartsVertex = new ResourceRegistryEntry<>();
	private ResourceRegistryEntry<ShaderPart> shaderPartsFragment = new ResourceRegistryEntry<>();


	
	public ResourceRegistryEntry<Texture> textures() { return this.textures; }
	public ResourceRegistryEntry<Mesh> staticMeshes() { return this.staticMeshes; }
	public ResourceRegistryEntry<StaticModel> staticModels() { return this.staticModels; }
	public ResourceRegistryEntry<Material> materials() { return this.materials; }
	public ResourceRegistryEntry<ShaderPart> vertexShaderParts() { return this.shaderPartsVertex; }
	public ResourceRegistryEntry<ShaderPart> fragmentShaderParts() { return this.shaderPartsFragment; }

	
	public void cleanup()
	{
		this.textures.clear();
		this.staticMeshes.clear();
		this.staticModels.clear();
		this.materials.clear();
		this.shaderPartsVertex.clear();
		this.shaderPartsFragment.clear();
	}
}
