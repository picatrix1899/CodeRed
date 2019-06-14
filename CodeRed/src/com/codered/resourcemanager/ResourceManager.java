package com.codered.resourcemanager;

import com.codered.resourcemanager.materials.MaterialData;
import com.codered.resourcemanager.materials.MaterialLoader;
import com.codered.resourcemanager.objects.ObjectData;
import com.codered.resourcemanager.objects.ObjectLoader;
import com.codered.resourcemanager.shaderparts.ShaderPartData;
import com.codered.resourcemanager.shaderparts.ShaderPartLoader;
import com.codered.resourcemanager.textures.TextureData;
import com.codered.resourcemanager.textures.TextureLoader;

public class ResourceManager
{
	private ResourceManagerPart<TextureData> textures;
	private ResourceManagerPart<ObjectData> staticMeshs;
	private ResourceManagerPart<MaterialData> materials;
	private ResourceManagerPart<ShaderPartData> vertexShaderParts;
	private ResourceManagerPart<ShaderPartData> fragmentShaderParts;
	private ResourceManagerPart<ShaderPartData> geometryShaderParts;
	private ResourceManagerPart<ShaderPartData> tessellationControlShaderParts;
	private ResourceManagerPart<ShaderPartData> tessellationEvaluationShaderParts;
	
	private static ResourceManager instance;
	
	public boolean isRunning = false;
	
	public static ResourceManager getInstance()
	{
		if(instance == null) instance = new ResourceManager();
		
		return instance;
	}
	
	private ResourceManager()
	{
		this.textures = new ResourceManagerPart<TextureData>("Texture", new TextureLoader());
		this.staticMeshs = new ResourceManagerPart<ObjectData>("Static Mesh", new ObjectLoader());
		this.materials = new ResourceManagerPart<MaterialData>("Material", new MaterialLoader());
		this.vertexShaderParts = new ResourceManagerPart<ShaderPartData>("Vertex Shader Part", new ShaderPartLoader());
		this.fragmentShaderParts = new ResourceManagerPart<ShaderPartData>("Fragment Shader Part", new ShaderPartLoader());
		this.geometryShaderParts = new ResourceManagerPart<ShaderPartData>("Geometry Shader Part", new ShaderPartLoader());
		this.tessellationControlShaderParts = new ResourceManagerPart<ShaderPartData>("Tessellation Control Shader Part", new ShaderPartLoader());
		this.tessellationEvaluationShaderParts = new ResourceManagerPart<ShaderPartData>("Tessellation Evaluation Shader Part", new ShaderPartLoader());
	}
	
	public void start()
	{
		isRunning = true;
		this.textures.start();
		this.staticMeshs.start();
		this.materials.start();
		this.vertexShaderParts.start();
		this.fragmentShaderParts.start();
		this.geometryShaderParts.start();
		this.tessellationControlShaderParts.start();
		this.tessellationEvaluationShaderParts.start();
	}
	
	public void stop()
	{
		isRunning = false;
		this.textures.stop();
		this.staticMeshs.stop();
		this.materials.stop();
		this.vertexShaderParts.stop();
		this.fragmentShaderParts.stop();
		this.geometryShaderParts.stop();
		this.tessellationControlShaderParts.stop();
		this.tessellationEvaluationShaderParts.stop();
	}
	
	public void cancel()
	{
		this.textures.cancel();
		this.staticMeshs.cancel();
		this.materials.cancel();
		this.vertexShaderParts.cancel();
		this.fragmentShaderParts.cancel();
		this.geometryShaderParts.cancel();
		this.tessellationControlShaderParts.cancel();
		this.tessellationEvaluationShaderParts.cancel();
	}
	
	public ResourceManagerPart<TextureData> textures() { return this.textures; }
	public ResourceManagerPart<ObjectData> staticMeshs() { return this.staticMeshs; }
	public ResourceManagerPart<MaterialData> materials() { return this.materials; }
	public ResourceManagerPart<ShaderPartData> vertexShaderParts() { return this.vertexShaderParts; }
	public ResourceManagerPart<ShaderPartData> fragmentShaderParts() { return this.fragmentShaderParts; }
	public ResourceManagerPart<ShaderPartData> geometryShaderParts() { return this.geometryShaderParts; }
	public ResourceManagerPart<ShaderPartData> tessellationControlShaderParts() { return this.tessellationControlShaderParts; }
	public ResourceManagerPart<ShaderPartData> tessellationEvaluationShaderParts() { return this.tessellationEvaluationShaderParts; }
}
