package com.codered.resource;

import java.io.File;
import java.util.Map;
import java.util.Set;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL40;

import org.resources.materials.MaterialData;

import com.codered.managing.models.Mesh;
import com.codered.material.Material;
import com.codered.sh.Shader;
import com.codered.sh.ShaderPart;
import com.codered.texture.Texture;
import com.codered.utils.TextureUtils;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class DRM
{
	private ResourceBlock currentBlock;
	private org.resources.ResourceManager rm;
	
	private Map<String, Texture> textures = Maps.newHashMap();
	private Map<String, Mesh> staticMeshes = Maps.newHashMap();
	private Map<String, Material> materials = Maps.newHashMap();
	private Map<String, ShaderPart> vertexShaderParts = Maps.newHashMap();
	private Map<String, ShaderPart> fragmentShaderParts = Maps.newHashMap();
	private Map<String, ShaderPart> geometryShaderParts = Maps.newHashMap();
	private Map<String, ShaderPart> tessellationControlShaderParts = Maps.newHashMap();
	private Map<String, ShaderPart> tessellationEvaluationShaderParts = Maps.newHashMap();
	private Map<String, Shader> shaders = Maps.newHashMap();

	
	public DRM()
	{
		this.rm = org.resources.ResourceManager.getInstance();
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
			
			for(String id : block.getPendingVertexShaderParts())
			{
				this.rm.vertexShaderParts().registerLookup(id, new File(id).toURI().toURL());
				this.rm.vertexShaderParts().loadForced(id);
				generateVertexShaderPart(id);
			}
			
			for(String id : block.getPendingFragmentShaderParts())
			{
				this.rm.fragmentShaderParts().registerLookup(id, new File(id).toURI().toURL());
				this.rm.fragmentShaderParts().loadForced(id);
				generateFragmentShaderPart(id);
			}
			
			for(String id : block.getPendingGeometryShaderParts())
			{
				this.rm.geometryShaderParts().registerLookup(id, new File(id).toURI().toURL());
				this.rm.geometryShaderParts().loadForced(id);
				generateGeometryShaderPart(id);
			}
			
			for(String id : block.getPendingTessellationControlShaderParts())
			{
				this.rm.tessellationControlShaderParts().registerLookup(id, new File(id).toURI().toURL());
				this.rm.tessellationControlShaderParts().loadForced(id);
				generateTessellationControlShaderPart(id);
			}
			
			for(String id : block.getPendingTessellationEvaluationShaderParts())
			{
				this.rm.tessellationEvaluationShaderParts().registerLookup(id, new File(id).toURI().toURL());
				this.rm.tessellationEvaluationShaderParts().loadForced(id);
				generateTessellationEvaluationShaderPart(id);
			}
			
			block.getPendingTextures().clear();
			block.getPendingStaticMeshes().clear();
			block.getPendingMaterials().clear();
			block.getPendingVertexShaderParts().clear();
			block.getPendingFragmentShaderParts().clear();
			block.getPendingGeometryShaderParts().clear();
			block.getPendingTessellationControlShaderParts().clear();
			block.getPendingTessellationEvaluationShaderParts().clear();
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
				this.rm.textures().registerLookup(id, new File(id).toURI().toURL());
				this.rm.textures().load(id, (v) -> {});
			}
			
			for(String id : this.currentBlock.getPendingStaticMeshes())
			{
				this.rm.staticMeshs().registerLookup(id, new File(id).toURI().toURL());
				this.rm.staticMeshs().load(id, (v) -> {});
			}
			
			for(String id : this.currentBlock.getPendingMaterials())
			{
				this.rm.materials().registerLookup(id, new File(id).toURI().toURL());
				this.rm.materials().load(id, (v) -> {});
			}
			
			for(String id : this.currentBlock.getPendingVertexShaderParts())
			{
				this.rm.vertexShaderParts().registerLookup(id, new File(id).toURI().toURL());
				this.rm.vertexShaderParts().load(id, (v) -> {});
			}
			
			for(String id : this.currentBlock.getPendingFragmentShaderParts())
			{
				this.rm.fragmentShaderParts().registerLookup(id, new File(id).toURI().toURL());
				this.rm.fragmentShaderParts().load(id, (v) -> {});
			}
			
			for(String id : this.currentBlock.getPendingGeometryShaderParts())
			{
				this.rm.geometryShaderParts().registerLookup(id, new File(id).toURI().toURL());
				this.rm.geometryShaderParts().load(id, (v) -> {});
			}
			
			for(String id : this.currentBlock.getPendingTessellationControlShaderParts())
			{
				this.rm.tessellationControlShaderParts().registerLookup(id, new File(id).toURI().toURL());
				this.rm.tessellationControlShaderParts().load(id, (v) -> {});
			}
			
			for(String id : this.currentBlock.getPendingTessellationEvaluationShaderParts())
			{
				this.rm.tessellationEvaluationShaderParts().registerLookup(id, new File(id).toURI().toURL());
				this.rm.tessellationEvaluationShaderParts().load(id, (v) -> {});
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
			this.textures.put(id, TextureUtils.genTexture(this.rm.textures().get(id)));
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
			MaterialData data = this.rm.materials().get(id);
			
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
					this.rm.textures().registerLookup(data.getNormalMap(), new File(data.getNormalMap()).toURI().toURL());
					this.rm.textures().load(data.getNormalMap(), (d) -> {});
				}
				if(data.getDisplacementMap() != "")
				{
					this.currentBlock.addTexture(data.getDisplacementMap());
					this.rm.textures().registerLookup(data.getDisplacementMap(), new File(data.getDisplacementMap()).toURI().toURL());
					this.rm.textures().load(data.getDisplacementMap(), (d) -> {});
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
	
	private void generateVertexShaderPart(String id)
	{
		try
		{
			this.vertexShaderParts.put(id, new ShaderPart(id, GL20.GL_VERTEX_SHADER, this.rm.vertexShaderParts().get(id).getData()));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public ShaderPart getVertexShaderPart(String id)
	{
		return this.vertexShaderParts.get(id);
	}
	
	private void generateFragmentShaderPart(String id)
	{
		try
		{
			this.fragmentShaderParts.put(id, new ShaderPart(id, GL20.GL_FRAGMENT_SHADER, this.rm.fragmentShaderParts().get(id).getData()));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public ShaderPart getFragmentShaderPart(String id)
	{
		return this.fragmentShaderParts.get(id);
	}
	
	private void generateGeometryShaderPart(String id)
	{
		try
		{
			this.geometryShaderParts.put(id, new ShaderPart(id, GL32.GL_GEOMETRY_SHADER, this.rm.geometryShaderParts().get(id).getData()));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public ShaderPart getGeometryShaderPart(String id)
	{
		return this.geometryShaderParts.get(id);
	}
	
	private void generateTessellationControlShaderPart(String id)
	{
		try
		{
			this.tessellationControlShaderParts.put(id, new ShaderPart(id, GL40.GL_TESS_CONTROL_SHADER, this.rm.tessellationControlShaderParts().get(id).getData()));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public ShaderPart getTessellationControlShaderPart(String id)
	{
		return this.tessellationControlShaderParts.get(id);
	}
	
	private void generateTessellationEvaluationShaderPart(String id)
	{
		try
		{
			this.tessellationEvaluationShaderParts.put(id, new ShaderPart(id, GL40.GL_TESS_EVALUATION_SHADER, this.rm.tessellationEvaluationShaderParts().get(id).getData()));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public ShaderPart getTessellationEvaluationShaderPart(String id)
	{
		return this.tessellationEvaluationShaderParts.get(id);
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
			
			Set<String> removedPendingVertexShaderParts = Sets.newHashSet();
			for(String id : this.currentBlock.getPendingVertexShaderParts())
			{
				if(this.rm.vertexShaderParts().isAvailable(id))
				{
					generateVertexShaderPart(id);
					removedPendingVertexShaderParts.add(id);
				}
			}
			for(String id : removedPendingVertexShaderParts)
				this.currentBlock.getPendingVertexShaderParts().remove(id);
			
			Set<String> removedPendingFragmentShaderParts = Sets.newHashSet();
			for(String id : this.currentBlock.getPendingFragmentShaderParts())
			{
				if(this.rm.fragmentShaderParts().isAvailable(id))
				{
					
					generateFragmentShaderPart(id);
					removedPendingFragmentShaderParts.add(id);
				}
			}
			for(String id : removedPendingFragmentShaderParts)
				this.currentBlock.getPendingFragmentShaderParts().remove(id);
			
			Set<String> removedPendingGeometryShaderParts = Sets.newHashSet();
			for(String id : this.currentBlock.getPendingGeometryShaderParts())
			{
				if(this.rm.geometryShaderParts().isAvailable(id))
				{
					generateGeometryShaderPart(id);
					removedPendingGeometryShaderParts.add(id);
				}
			}
			for(String id : removedPendingGeometryShaderParts)
				this.currentBlock.getPendingGeometryShaderParts().remove(id);
			
			Set<String> removedPendingTessellationControlShaderParts = Sets.newHashSet();
			for(String id : this.currentBlock.getPendingTessellationControlShaderParts())
			{
				if(this.rm.tessellationControlShaderParts().isAvailable(id))
				{
					generateTessellationControlShaderPart(id);
					removedPendingTessellationControlShaderParts.add(id);
				}
			}
			for(String id : removedPendingTessellationControlShaderParts)
				this.currentBlock.getPendingTessellationControlShaderParts().remove(id);
			
			Set<String> removedPendingTessellationEvaluationShaderParts = Sets.newHashSet();
			for(String id : this.currentBlock.getPendingTessellationEvaluationShaderParts())
			{
				if(this.rm.tessellationEvaluationShaderParts().isAvailable(id))
				{
					generateTessellationEvaluationShaderPart(id);
					removedPendingTessellationEvaluationShaderParts.add(id);
				}
			}
			for(String id : removedPendingTessellationEvaluationShaderParts)
				this.currentBlock.getPendingTessellationEvaluationShaderParts().remove(id);
			
			if(this.currentBlock.isFinished()) this.currentBlock = null;
		}
	}
	
	public void addShader(String id, Shader shader)
	{
		this.shaders.put(id, shader);
	}
	
	public Shader getShader(String id)
	{
		return this.shaders.get(id);
	}
}
