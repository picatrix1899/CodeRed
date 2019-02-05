package com.codered.resource;

import java.io.File;
import java.util.Map;
import java.util.Set;

import org.resources.materials.MaterialData;

import com.codered.managing.models.Mesh;
import com.codered.material.Material;
import com.codered.sh.ShaderPart;
import com.codered.texture.Texture;
import com.codered.utils.TextureUtils;
import com.codered.window.WindowContext;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class DRM
{
	private IRM irm;
	private ResourceBlock currentBlock;
	private WindowContext context;
	private org.resources.ResourceManager rm;
	
	private Map<String, Texture> textures = Maps.newHashMap();
	private Map<String, Mesh> staticMeshes = Maps.newHashMap();
	private Map<String, Material> materials = Maps.newHashMap();
	private Map<String, ShaderPart> shaderParts = Maps.newHashMap();
	
	public DRM(WindowContext context)
	{
		this.irm = IRM.getInstance();
		this.rm = org.resources.ResourceManager.getInstance();
		this.context = context;
	}
	
	public void loadResourceBlockForced(ResourceBlock block)
	{
		try
		{		
			for(String id : block.getPendingTextures())
			{
				this.rm.textures().registerLookup(id, new File(id).toURI().toURL());
				this.rm.textures().loadForced(id);
				generateTexture(id);
			}
			
			for(String id : block.getPendingStaticMeshes())
			{
				this.rm.staticMeshs().registerLookup(id, new File(id).toURI().toURL());
				this.rm.staticMeshs().loadForced(id);
				generateStaticMesh(id);
			}
			
			for(String id : block.getPendingMaterials())
			{
				this.rm.materials().registerLookup(id, new File(id).toURI().toURL());
				this.rm.materials().loadForced(id);
				generateMaterial(id);
			}
			
			block.getPendingTextures().clear();
			block.getPendingStaticMeshes().clear();
			block.getPendingMaterials().clear();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void loadResourceBlock(ResourceBlock block)
	{
		this.currentBlock = block;
		
		try
		{		
			for(String id : this.currentBlock.getPendingTextures())
			{
				org.resources.utils.ResourcePath path = new org.resources.utils.ResourcePath();
				path.path = id;
				this.irm.loadTexture(id, path);
			}
			
			for(String id : this.currentBlock.getPendingStaticMeshes())
			{
				org.resources.utils.ResourcePath path = new org.resources.utils.ResourcePath();
				path.path = id;
				this.irm.loadStaticMesh(id, path);
			}
			
			for(String id : this.currentBlock.getPendingMaterials())
			{
				org.resources.utils.ResourcePath path = new org.resources.utils.ResourcePath();
				path.path = id;
				this.irm.loadMaterial(id, path);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			this.currentBlock = null;
		}
	}
	
	private void generateTexture(String id)
	{
		try
		{
			this.textures.put(id, TextureUtils.genTexture(this.rm.textures().get(id), context));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public Texture getTexture(String id)
	{
		return this.textures.get(id);
	}
	
	private void generateStaticMesh(String id)
	{
		try
		{
			this.staticMeshes.put(id, new Mesh().loadFromObj(this.rm.staticMeshs().get(id)));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public Mesh getStaticMesh(String id)
	{
		return this.staticMeshes.get(id);
	}
	
	
	private void generateMaterial(String id)
	{
		try
		{
			MaterialData data = this.irm.getMaterial(id);
			
			if(this.currentBlock != null)
			{
				if(data.getAlbedoMap() != "")
				{
					this.currentBlock.addTexture(data.getAlbedoMap());
					this.rm.textures().registerLookup(data.getAlbedoMap(), new File(data.getAlbedoMap()).toURI().toURL());
					this.rm.textures().load(data.getAlbedoMap(), (d) -> {});
				}
				if(data.getNormalMap() != "")
				{
					this.currentBlock.addTexture(data.getNormalMap());
					this.rm.textures().registerLookup(data.getAlbedoMap(), new File(data.getAlbedoMap()).toURI().toURL());
					this.rm.textures().load(data.getAlbedoMap(), (d) -> {});
				}
				if(data.getDisplacementMap() != "")
				{
					this.currentBlock.addTexture(data.getDisplacementMap());
					this.rm.textures().registerLookup(data.getAlbedoMap(), new File(data.getAlbedoMap()).toURI().toURL());
					this.rm.textures().load(data.getAlbedoMap(), (d) -> {});
				}
			}
			else
			{
				ResourceBlock block = new ResourceBlock(true);
				if(data.getAlbedoMap() != "") block.addTexture(data.getAlbedoMap());
				if(data.getNormalMap() != "") block.addTexture(data.getNormalMap());
				if(data.getDisplacementMap() != "") block.addTexture(data.getDisplacementMap());
				loadResourceBlockForced(block);
			}

			this.materials.put(id, new Material(data.getAlbedoMap(), data.getNormalMap(), data.getDisplacementMap(), data.getSpecularPower(), data.getSpecularIntensity()));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public Material getMaterial(String id)
	{
		return this.materials.get(id);
	}
	
	private void generateShaderPart(String id)
	{
		try
		{
			this.shaderParts.put(id, new ShaderPart(name, type, data);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void addShaderPart(String id, ShaderPart part)
	{
		this.shaderParts.put(id, part);
	}
	
	
	public ShaderPart getShaderPart(String id)
	{
		return this.shaderParts.get(id);
	}
	
	public boolean isCritical()
	{
		return this.currentBlock != null ? this.currentBlock.isCritical() : false;
	}
	
	public boolean isOccupied()
	{
		return this.currentBlock != null;
	}
	
	public void update(double delta)
	{
		if(this.currentBlock != null)
		{
			Set<String> removedPendingTextures = Sets.newHashSet();
			for(String id : this.currentBlock.getPendingTextures())
			{
				if(this.rm.textures().isAvailable(id))
				{
					generateTexture(id);
					removedPendingTextures.add(id);
				}
			}
			for(String id : removedPendingTextures)
				this.currentBlock.getPendingTextures().remove(id);
			
			Set<String> removedPendingStaticMeshes = Sets.newHashSet();
			for(String id : this.currentBlock.getPendingStaticMeshes())
			{
				if(this.rm.staticMeshs().isAvailable(id))
				{
					generateStaticMesh(id);
					removedPendingStaticMeshes.add(id);
				}
			}
			for(String id : removedPendingStaticMeshes)
				this.currentBlock.getPendingStaticMeshes().remove(id);
			
			Set<String> removedPendingMaterials = Sets.newHashSet();
			for(String id : this.currentBlock.getPendingMaterials())
			{
				if(this.rm.materials().isAvailable(id))
				{
					generateMaterial(id);
					removedPendingMaterials.add(id);
				}
			}
			for(String id : removedPendingMaterials)
				this.currentBlock.getPendingMaterials().remove(id);
			
			if(this.currentBlock.isFinished()) this.currentBlock = null;
		}
	}
}
