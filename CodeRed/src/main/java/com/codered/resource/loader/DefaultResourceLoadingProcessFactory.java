package com.codered.resource.loader;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

import org.barghos.core.util.SupplierWithException;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryUtil;

import com.codered.engine.Engine;
import com.codered.engine.EngineRegistry;
import com.codered.managing.TextureManager;
import com.codered.managing.VAO;
import com.codered.model.Mesh;
import com.codered.model.Model;
import com.codered.rendering.material.Material;
import com.codered.rendering.shader.ShaderPart;
import com.codered.rendering.texture.Texture;
import com.codered.resource.ResourceRequest;
import com.codered.resource.material.MaterialData;
import com.codered.resource.material.MaterialLoader;
import com.codered.resource.model.FaceData;
import com.codered.resource.model.MeshData;
import com.codered.resource.model.ModelData;
import com.codered.resource.shaderpart.ShaderPartData;
import com.codered.resource.shaderpart.ShaderPartLoader;
import com.codered.resource.texture.TextureData;
import com.codered.utils.TextureUtils;

public class DefaultResourceLoadingProcessFactory implements IResourceLoadingProcessFactory
{
	public static final String TEXTURE = "texture";
	public static final String SHADERPART_VERTEX = "shaderpart_vertex";
	public static final String SHADERPART_FRAGMENT = "shaderpart_fragment";
	public static final String MATERIAL = "material";
	public static final String MODEL = "model";
	
	private TextureManager textureManager;
	
	private IResourceLoader loader;
	
	public void init()
	{
		this.textureManager = EngineRegistry.getTextureManager();
		this.loader = Engine.getInstance().getEngineSetup().resourceLoader;
	}
	
	public ResourceLoadingProcess generate(ResourceRequest request)
	{
		ResourceLoadingProcess process = new ResourceLoadingProcess();
		process.path = request.resourcePath;
		process.type = request.resourceType;
		
		switch(request.resourceType)
		{
			case TEXTURE:
			{
				process.loaderCall = () -> load(request, process, () -> loader.loadTexture(request.resourcePath));
				process.creatorCall = () -> create(process, (key, data) -> createTexture(key, data));
				break;
			}
			case SHADERPART_VERTEX:
			{
				process.loaderCall = () -> load(request, process, () -> ShaderPartLoader.loadResource(request.resourcePath));
				process.creatorCall = () -> create(process, (key, data) -> createVertexShaderPart(key, data));
				break;
			}
			case SHADERPART_FRAGMENT:
			{
				process.loaderCall = () -> load(request, process, () -> ShaderPartLoader.loadResource(request.resourcePath));
				process.creatorCall = () -> create(process, (key, data) -> createFragmentShaderPart(key, data));
				break;
			}
			case MATERIAL:
			{
				process.loaderCall = () -> load(request, process, () -> MaterialLoader.loadResource(request.resourcePath));
				process.creatorCall = () -> create(process, (key, data) -> createMaterial(key, data));
				break;
			}
			case MODEL:
			{
				process.loaderCall = () -> load(request, process, () -> loader.loadModel(request.resourcePath));
				process.creatorCall = () -> create(process, (key, data) -> createModel(key, data));
				break;
			}
		}
		
		return process;
	}
	
	private ResourceLoadingProcess load(ResourceRequest request, ResourceLoadingProcess process, SupplierWithException<Object> sup)
	{
		try
		{
			process.data = Optional.ofNullable(sup.get());
		}
		catch(Exception e)
		{
			process.exception = Optional.of(e);
		}
		
		return process;
	}
	
	private boolean create(ResourceLoadingProcess process, BiConsumer<String,Object> creator)
	{
		if(process.exception.isPresent() || process.data.isEmpty()) return false;
		
		creator.accept(process.path, process.data.get());
		
		return true;
	}
	
	private void createTexture(String key, Object data)
	{
		Texture t = TextureUtils.genTexture2(this.textureManager.create(), (TextureData)data);
		EngineRegistry.getResourceRegistry().textures().add(key, t);
	}
	
	private void createFragmentShaderPart(String key, Object data)
	{
		ShaderPart s = new ShaderPart(key, GL20.GL_FRAGMENT_SHADER, ((ShaderPartData)data).getData());
		EngineRegistry.getResourceRegistry().fragmentShaderParts().add(key, s);
	}
	
	private void createVertexShaderPart(String key, Object data)
	{
		ShaderPart s = new ShaderPart(key, GL20.GL_VERTEX_SHADER, ((ShaderPartData)data).getData());
		EngineRegistry.getResourceRegistry().vertexShaderParts().add(key, s);
	}
	
	private void createMaterial(String key, Object data)
	{
		MaterialData mdata = (MaterialData)data;
		
		Optional<Texture> diffuse = Optional.empty();
		Optional<Texture> normal = Optional.empty();
		
		if(mdata.getDiffuse().isPresent())
		{
			diffuse = Optional.of(TextureUtils.genTexture2(this.textureManager.create(), mdata.getDiffuse().get()));
		}
		
		if(mdata.getNormal().isPresent())
		{
			normal = Optional.of(TextureUtils.genTexture2(this.textureManager.create(), mdata.getNormal().get()));
		}
		
		Material m = new Material(diffuse, normal);
		EngineRegistry.getResourceRegistry().materials().add(key, m);
	}
	
	private void createModel(String key, Object data)
	{
		ModelData mdata = (ModelData)data;
		
		List<Mesh> meshes = new ArrayList<>();
		
		for(MeshData dmesh : mdata.getMeshes())
		{
			FloatBuffer bp = MemoryUtil.memAllocFloat(dmesh.getVertexCount() * 3);
			FloatBuffer bn = MemoryUtil.memAllocFloat(dmesh.getVertexCount() * 3);
			FloatBuffer buv = MemoryUtil.memAllocFloat(dmesh.getVertexCount()* 2);
			FloatBuffer bt = MemoryUtil.memAllocFloat(dmesh.getVertexCount() * 3);
			IntBuffer bind = MemoryUtil.memAllocInt(dmesh.getVertexCount() * 3);
			
			int vIndex = 0;
			for(FaceData dFace : dmesh.getFaces())
			{
				bp.put(dFace.getVertexA().getPosition().getX());
				bp.put(dFace.getVertexA().getPosition().getY());
				bp.put(dFace.getVertexA().getPosition().getZ());
				bp.put(dFace.getVertexB().getPosition().getX());
				bp.put(dFace.getVertexB().getPosition().getY());
				bp.put(dFace.getVertexB().getPosition().getZ());
				bp.put(dFace.getVertexC().getPosition().getX());
				bp.put(dFace.getVertexC().getPosition().getY());
				bp.put(dFace.getVertexC().getPosition().getZ());
				
				bn.put(dFace.getVertexA().getNormal().getX());
				bn.put(dFace.getVertexA().getNormal().getY());
				bn.put(dFace.getVertexA().getNormal().getZ());
				bn.put(dFace.getVertexB().getNormal().getX());
				bn.put(dFace.getVertexB().getNormal().getY());
				bn.put(dFace.getVertexB().getNormal().getZ());
				bn.put(dFace.getVertexC().getNormal().getX());
				bn.put(dFace.getVertexC().getNormal().getY());
				bn.put(dFace.getVertexC().getNormal().getZ());
				
				bt.put(dFace.getVertexA().getTangent().getX());
				bt.put(dFace.getVertexA().getTangent().getY());
				bt.put(dFace.getVertexA().getTangent().getZ());
				bt.put(dFace.getVertexB().getTangent().getX());
				bt.put(dFace.getVertexB().getTangent().getY());
				bt.put(dFace.getVertexB().getTangent().getZ());
				bt.put(dFace.getVertexC().getTangent().getX());
				bt.put(dFace.getVertexC().getTangent().getY());
				bt.put(dFace.getVertexC().getTangent().getZ());
				
				buv.put(dFace.getVertexA().getUV().getX());
				buv.put(-dFace.getVertexA().getUV().getY());
				buv.put(dFace.getVertexB().getUV().getX());
				buv.put(-dFace.getVertexB().getUV().getY());
				buv.put(dFace.getVertexC().getUV().getX());
				buv.put(-dFace.getVertexC().getUV().getY());

				bind.put(vIndex++);
				bind.put(vIndex++);
				bind.put(vIndex++);
			}

			bp.flip();
			buv.flip();
			bn.flip();
			bt.flip();
			bind.flip();
			
			VAO vao = EngineRegistry.getVAOManager().getNewVAO();
			
			vao.storeIndices(bind, GL15.GL_STATIC_DRAW);
			
			vao.storeData(0, 3, bp, 0, 0, GL15.GL_STATIC_DRAW);
			vao.storeData(1, 2, buv, 0, 0, GL15.GL_STATIC_DRAW);
			vao.storeData(2, 3, bn, 0, 0, GL15.GL_STATIC_DRAW);
			vao.storeData(3, 3, bt, 0, 0, GL15.GL_STATIC_DRAW);

			MemoryUtil.memFree(bp);
			MemoryUtil.memFree(buv);
			MemoryUtil.memFree(bn);
			MemoryUtil.memFree(bt);
			
			Optional<Material> material = Optional.empty();
			
			if(dmesh.getMaterial().isPresent())
			{
				Optional<Texture> diffuse = Optional.empty();
				Optional<Texture> normal = Optional.empty();
				
				if(dmesh.getMaterial().get().getDiffuse().isPresent())
				{
					diffuse = Optional.of(TextureUtils.genTexture2(this.textureManager.create(), dmesh.getMaterial().get().getDiffuse().get()));
				}
				
				if(dmesh.getMaterial().get().getNormal().isPresent())
				{
					normal = Optional.of(TextureUtils.genTexture2(this.textureManager.create(), dmesh.getMaterial().get().getNormal().get()));
				}

				material = Optional.of(new Material(diffuse, normal));
			}
			
			meshes.add(new Mesh(vao, dmesh.getVertexCount(), dmesh.getCollisionMesh(), material));
		}
		
		Model model = new Model(meshes);
		
		EngineRegistry.getResourceRegistry().models().add(key, model);
	}
}
