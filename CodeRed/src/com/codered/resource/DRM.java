package com.codered.resource;

import java.io.File;
import java.util.ListIterator;
import java.util.Map;

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
			for(ListIterator<String> it = this.currentBlock.getPendingTextures().listIterator(); it.hasNext();)
			{
				String id = it.next();
				
				if(this.rm.textures().isAvailable(id))
				{
					generateTexture(id);
					it.remove();
				}
			}

			for(ListIterator<String> it = this.currentBlock.getPendingStaticMeshes().listIterator(); it.hasNext();)
			{
				String id = it.next();
				
				if(this.rm.staticMeshs().isAvailable(id))
				{
					generateStaticMesh(id);
					it.remove();
				}
			}
			
			for(ListIterator<String> it = this.currentBlock.getPendingMaterials().listIterator(); it.hasNext();)
			{
				String id = it.next();
				
				if(this.rm.materials().isAvailable(id))
				{
					generateMaterial(id);
					it.remove();
				}
			}
			
			for(ListIterator<String> it = this.currentBlock.getPendingVertexShaderParts().listIterator(); it.hasNext();)
			{
				String id = it.next();
				
				if(this.rm.vertexShaderParts().isAvailable(id))
				{
					generateVertexShaderPart(id);
					it.remove();
				}
			}
			
			for(ListIterator<String> it = this.currentBlock.getPendingFragmentShaderParts().listIterator(); it.hasNext();)
			{
				String id = it.next();
				
				if(this.rm.fragmentShaderParts().isAvailable(id))
				{
					generateFragmentShaderPart(id);
					it.remove();
				}
			}
			
			for(ListIterator<String> it = this.currentBlock.getPendingGeometryShaderParts().listIterator(); it.hasNext();)
			{
				String id = it.next();
				
				if(this.rm.geometryShaderParts().isAvailable(id))
				{
					generateGeometryShaderPart(id);
					it.remove();
				}
			}
			
			for(ListIterator<String> it = this.currentBlock.getPendingTessellationControlShaderParts().listIterator(); it.hasNext();)
			{
				String id = it.next();
				
				if(this.rm.tessellationControlShaderParts().isAvailable(id))
				{
					generateTessellationControlShaderPart(id);
					it.remove();
				}
			}
			
			for(ListIterator<String> it = this.currentBlock.getPendingTessellationEvaluationShaderParts().listIterator(); it.hasNext();)
			{
				String id = it.next();
				
				if(this.rm.tessellationEvaluationShaderParts().isAvailable(id))
				{
					generateTessellationEvaluationShaderPart(id);
					it.remove();
				}
			}
			
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
