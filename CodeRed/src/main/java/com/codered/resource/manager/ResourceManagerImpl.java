package com.codered.resource.manager;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.function.Consumer;

import org.lwjgl.opengl.GL20;

import com.codered.engine.CriticalEngineStateException;
import com.codered.engine.Engine;
import com.codered.engine.EngineRegistry;
import com.codered.managing.TextureManager;
import com.codered.managing.models.Mesh;
import com.codered.material.Material;
import com.codered.resource.LoadingStage;
import com.codered.resource.ResourceRequest;
import com.codered.resource.ResourceRequestBlock;
import com.codered.resource.loader.ResourceLoader;
import com.codered.resource.material.MaterialData;
import com.codered.resource.object.ObjectData;
import com.codered.resource.registry.ResourceRegistry;
import com.codered.resource.shaderpart.ShaderPartData;
import com.codered.resource.texture.TextureData;
import com.codered.shader.ShaderPart;
import com.codered.texture.Texture;
import com.codered.utils.TextureUtils;

public class ResourceManagerImpl implements ResourceManager
{
	private ResourceRegistry registry;
	private ResourceLoader loader;
	private TextureManager textureManager;
	
	private Deque<ResourceRequestBlock> requests = new ArrayDeque<>();
	
	private ResourceRequestBlock currentBlock;
	
	public ResourceManagerImpl()
	{
		this.registry = EngineRegistry.getResourceRegistry();
		this.loader = Engine.getInstance().getResourceLoader();
		this.textureManager = EngineRegistry.getTextureManager();
	}
	
	public void load(ResourceRequestBlock block)
	{
		if(block.loadAsync())
		{
			if(this.currentBlock == null)
			{
				this.currentBlock = block;
				this.currentBlock.stage(LoadingStage.TEXTURES);
				loadTextures(this.currentBlock, true);
			}
			else
			{
				this.requests.add(block);
				block.stage(LoadingStage.WAITING);
			}
		}
		else
		{
			loadTextures(block, false);
			process(block.textures(), (r) -> createTexture(r.key, r.response.data));
			loadVertexShaderParts(block, false);
			process(block.vertexShaderParts(), (r) -> createVertexShaderPart(r.key, r.response.data));
			loadFragmentShaderParts(block, false);
			process(block.fragmentShaderParts(), (r) -> createFragmentShaderPart(r.key, r.response.data));

		}
	}

	public void update()
	{
		if(this.requests.size() > 0 && this.currentBlock == null) this.currentBlock = this.requests.poll();
		
		if(this.currentBlock != null)
		{
			if(this.currentBlock.stage() == LoadingStage.WAITING)
			{
				this.currentBlock.stage(LoadingStage.TEXTURES);
				loadTextures(this.currentBlock, true);
			}
			
			switch(this.currentBlock.stage())
			{
				case TEXTURES:
				{
					process(this.currentBlock.textures(), (r) -> createTexture(r.key, r.response.data));
					if(this.currentBlock.areTexturesFinished())
					{
						this.currentBlock.stage(LoadingStage.STATIC_MESHES);
						loadStaticMeshes(this.currentBlock, true);
					}
					else
					{
						break;
					}
				}
				case STATIC_MESHES:
				{
					process(this.currentBlock.staticMeshes(), (r) -> createStaticMesh(r.key, r.response.data));
					if(this.currentBlock.areStaticMeshesFinished())
					{
						this.currentBlock.stage(LoadingStage.MATERIALS);
						loadMaterials(this.currentBlock, true);
					}
					else
					{
						break;
					}
				}
				case MATERIALS:
				{
					process(this.currentBlock.materials(), (r) -> createMaterial(r.key, r.response.data));
					if(this.currentBlock.areMaterialsFinished())
					{
						this.currentBlock.stage(LoadingStage.SHADERPARTS_VERTEX);
						loadVertexShaderParts(this.currentBlock, true);
					}
					else
					{
						break;
					}
				}
				case SHADERPARTS_VERTEX:
				{
					process(this.currentBlock.vertexShaderParts(), (r) -> createVertexShaderPart(r.key, r.response.data));
					if(this.currentBlock.areVertexShaderPartsFinished())
					{
						this.currentBlock.stage(LoadingStage.SHADERPARTS_FRAGMENT);
						loadFragmentShaderParts(this.currentBlock, true);
					}
					else
					{
						break;
					}
				}
				case SHADERPARTS_FRAGMENT:
				{
					process(this.currentBlock.fragmentShaderParts(), (r) -> createFragmentShaderPart(r.key, r.response.data));
					if(this.currentBlock.areFragmentShaderPartsFinished())
					{

					}
					else
					{
						break;
					}
				}

			default:
				break;
			}
			
			if(this.currentBlock.stage().ordinal() == LoadingStage.values().length -1)
			{
				if(this.currentBlock.isFinished())
				{
					this.currentBlock = null;
				}
			}
		}
	}
	
	private void loadTextures(ResourceRequestBlock block, boolean async)
	{
		for(ResourceRequest request : block.textures())
		{
			if(this.registry.textures().contains(request.key))
			{
				request.created = true;
				continue;
			}

			if(async)
				this.loader.loadTextureAsync(request);
			else
				this.loader.loadTexture(request);
		}
	}
	
	private void loadStaticMeshes(ResourceRequestBlock block, boolean async)
	{
		for(ResourceRequest request : block.staticMeshes())
		{
			if(this.registry.staticMeshes().contains(request.key))
			{
				request.created = true;
				continue;
			}

			if(async)
				this.loader.loadStaticMeshAsync(request);
			else
				this.loader.loadStaticMesh(request);
		}
	}
	
	private void loadMaterials(ResourceRequestBlock block, boolean async)
	{
		for(ResourceRequest request : block.materials())
		{
			if(this.registry.materials().contains(request.key))
			{
				request.created = true;
				continue;
			}

			if(async)
				this.loader.loadMaterialAsync(request);
			else
				this.loader.loadMaterial(request);
		}
	}
	
	private void loadVertexShaderParts(ResourceRequestBlock block, boolean async)
	{
		for(ResourceRequest request : block.vertexShaderParts())
		{
			if(this.registry.vertexShaderParts().contains(request.key))
			{
				request.created = true;
				continue;
			}
			
			if(async)
				this.loader.loadShaderPartAsync(request);
			else
				this.loader.loadShaderPart(request);
		}
	}
	
	private void loadFragmentShaderParts(ResourceRequestBlock block, boolean async)
	{
		for(ResourceRequest request : block.fragmentShaderParts())
		{
			if(this.registry.fragmentShaderParts().contains(request.key))
			{
				request.created = true;
				continue;
			}

			if(async)
				this.loader.loadShaderPartAsync(request);
			else
				this.loader.loadShaderPart(request);
		}
	}
	

	private void process(List<ResourceRequest> requests, Consumer<ResourceRequest> processor)
	{
		for(ResourceRequest request : requests)
		{
			if(!request.created)
			{
				synchronized(request)
				{
					if(request.response != null)
					{
						if(request.response.data != null)
						{
							processor.accept(request);
							request.created = true;
						}
						else
						{
							if(request.response.exception != null)
							{
								throw new CriticalEngineStateException(request.response.exception);
							}
							else
							{
								throw new CriticalEngineStateException(new Exception("loading " + request.key + "from \"" + request.url + "\""));
							}
						}
					}
				}
			}
		}
	}
	
	private void createTexture(String key, Object data)
	{
		Texture t = TextureUtils.genTexture(this.textureManager.create(), (TextureData)data);
		this.registry.textures().add(key, t);
	}
	
	private void createFragmentShaderPart(String key, Object data)
	{
		ShaderPart s = new ShaderPart(key, GL20.GL_FRAGMENT_SHADER, ((ShaderPartData)data).getData());
		this.registry.fragmentShaderParts().add(key, s);
	}
	
	private void createVertexShaderPart(String key, Object data)
	{
		ShaderPart s = new ShaderPart(key, GL20.GL_VERTEX_SHADER, ((ShaderPartData)data).getData());
		this.registry.vertexShaderParts().add(key, s);
	}
	
	private void createStaticMesh(String key, Object data)
	{
		Mesh m = new Mesh().loadFromObj((ObjectData)data);
		this.registry.staticMeshes().add(key, m);
	}
	
	private void createMaterial(String key, Object data)
	{
		MaterialData mdata = (MaterialData)data;
		
		Texture albedo = TextureUtils.genTexture(this.textureManager.create(), mdata.getAlbedoMap());
		Texture normal = null;
		
		if(mdata.hasNormalMap())
			normal = TextureUtils.genTexture(this.textureManager.create(), mdata.getNormalMap());
		
		Material m = new Material(albedo, normal, mdata.getSpecularPower(), mdata.getSpecularIntensity());
		this.registry.materials().add(key, m);
	}

	public void cleanup()
	{
		this.textureManager.cleanup();
	}
}
