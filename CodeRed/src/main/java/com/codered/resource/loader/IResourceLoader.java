package com.codered.resource.loader;

import com.codered.resource.model.ModelData;
import com.codered.resource.texture.TextureData;

public interface IResourceLoader
{
	TextureData loadTexture(String path) throws Exception;
	
	ModelData loadModel(String path) throws Exception;
}
