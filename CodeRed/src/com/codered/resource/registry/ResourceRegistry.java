package com.codered.resource.registry;

import com.codered.managing.models.Mesh;
import com.codered.material.Material;
import com.codered.shader.ShaderPart;
import com.codered.texture.Texture;

public interface ResourceRegistry
{
	ResourceRegistryEntry<Texture> textures();
	ResourceRegistryEntry<Mesh> staticMeshes();
	ResourceRegistryEntry<Material> materials();
	ResourceRegistryEntry<ShaderPart> vertexShaderParts();
	ResourceRegistryEntry<ShaderPart> fragmentShaderParts();
	
	void cleanup();
}
