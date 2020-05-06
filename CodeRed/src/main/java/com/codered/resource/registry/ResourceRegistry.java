package com.codered.resource.registry;

import com.codered.model.Mesh;
import com.codered.model.Model;
import com.codered.rendering.material.Material;
import com.codered.rendering.shader.ShaderPart;
import com.codered.rendering.texture.Texture;

public interface ResourceRegistry
{
	ResourceRegistryEntry<Texture> textures();
	ResourceRegistryEntry<Mesh> staticMeshes();
	ResourceRegistryEntry<Material> materials();
	ResourceRegistryEntry<ShaderPart> vertexShaderParts();
	ResourceRegistryEntry<ShaderPart> fragmentShaderParts();
	ResourceRegistryEntry<Model> models();
	
	void cleanup();
}
