package com.codered.resource.registry;

import com.codered.model.Mesh;
import com.codered.model.Model;
import com.codered.rendering.material.Material;
import com.codered.rendering.shader.ShaderPart;
import com.codered.rendering.texture.Texture;

public class ResourceRegistryImpl implements ResourceRegistry
{
	private ResourceRegistryEntry<Texture> textures = new ResourceRegistryEntry<>();
	private ResourceRegistryEntry<Mesh> staticMeshes = new ResourceRegistryEntry<>();
	private ResourceRegistryEntry<Material> materials = new ResourceRegistryEntry<>();
	private ResourceRegistryEntry<ShaderPart> shaderPartsVertex = new ResourceRegistryEntry<>();
	private ResourceRegistryEntry<ShaderPart> shaderPartsFragment = new ResourceRegistryEntry<>();
	private ResourceRegistryEntry<Model> models = new ResourceRegistryEntry<>();

	
	public ResourceRegistryEntry<Texture> textures() { return this.textures; }
	public ResourceRegistryEntry<Mesh> staticMeshes() { return this.staticMeshes; }
	public ResourceRegistryEntry<Material> materials() { return this.materials; }
	public ResourceRegistryEntry<ShaderPart> vertexShaderParts() { return this.shaderPartsVertex; }
	public ResourceRegistryEntry<ShaderPart> fragmentShaderParts() { return this.shaderPartsFragment; }
	public ResourceRegistryEntry<Model> models() { return this.models; }
	
	public void cleanup()
	{
		this.textures.clear();
		this.staticMeshes.clear();
		this.materials.clear();
		this.shaderPartsVertex.clear();
		this.shaderPartsFragment.clear();
		this.models.clear();
	}
}
