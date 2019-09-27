package com.codered.resource.loader;

import com.codered.resource.ResourceRequest;

public interface ResourceLoader
{
	void start();
	void stop();
	
	void loadTexture(ResourceRequest request);
	void loadTextureAsync(ResourceRequest request);
	
	void loadStaticMesh(ResourceRequest request);
	void loadStaticMeshAsync(ResourceRequest request);
	
	void loadMaterial(ResourceRequest request);
	void loadMaterialAsync(ResourceRequest request);
	
	void loadShaderPart(ResourceRequest request);
	void loadShaderPartAsync(ResourceRequest request);
}
