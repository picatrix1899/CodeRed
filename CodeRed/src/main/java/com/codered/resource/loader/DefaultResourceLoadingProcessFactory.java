package com.codered.resource.loader;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

import org.barghos.core.util.BufferUtils;
import org.barghos.core.util.SupplierWithException;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryUtil;

import com.codered.engine.Engine;
import com.codered.engine.EngineRegistry;
import com.codered.managing.VAO;
import com.codered.model.Mesh;
import com.codered.model.Model;
import com.codered.rendering.material.Material;
import com.codered.rendering.shader.FragmentShaderPart;
import com.codered.rendering.shader.VertexShaderPart;
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
import com.codered.utils.GLCommon;
import com.codered.utils.TextureUtils;

public class DefaultResourceLoadingProcessFactory implements IResourceLoadingProcessFactory
{
	public static final String TEXTURE = "texture";
	public static final String SHADERPART_VERTEX = "shaderpart_vertex";
	public static final String SHADERPART_FRAGMENT = "shaderpart_fragment";
	public static final String MATERIAL = "material";
	public static final String MODEL = "model";

	private IResourceLoader loader;
	
	public void init()
	{
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
		Texture t = TextureUtils.genTexture2(GLCommon.genTexture(), (TextureData)data);
		EngineRegistry.getResourceRegistry().addResource(key, t, Texture.class);
	}
	
	private void createFragmentShaderPart(String key, Object data)
	{
		FragmentShaderPart s = new FragmentShaderPart(key, GL20.GL_FRAGMENT_SHADER, ((ShaderPartData)data).getData());
		EngineRegistry.getResourceRegistry().addResource(key, s, FragmentShaderPart.class);
	}
	
	private void createVertexShaderPart(String key, Object data)
	{
		VertexShaderPart s = new VertexShaderPart(key, GL20.GL_VERTEX_SHADER, ((ShaderPartData)data).getData());
		EngineRegistry.getResourceRegistry().addResource(key, s, VertexShaderPart.class);
	}
	
	private void createMaterial(String key, Object data)
	{
		MaterialData mdata = (MaterialData)data;
		
		Texture diffuse = null;
		Texture normal = null;
		
		if(mdata.getDiffuse() != null)
		{
			diffuse = TextureUtils.genTexture2(GLCommon.genTexture(), mdata.getDiffuse());
		}
		
		if(mdata.getNormal() != null)
		{
			normal = TextureUtils.genTexture2(GLCommon.genTexture(), mdata.getNormal());
		}
		
		Material m = new Material(diffuse, normal);
		EngineRegistry.getResourceRegistry().addResource(key, m, Material.class);
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
				BufferUtils.put(bp, dFace.getVertexA().getPosition(), dFace.getVertexB().getPosition(), dFace.getVertexC().getPosition());
				BufferUtils.put(bn, dFace.getVertexA().getNormal(), dFace.getVertexB().getNormal(), dFace.getVertexC().getNormal());
				BufferUtils.put(bt, dFace.getVertexA().getTangent(), dFace.getVertexB().getTangent(), dFace.getVertexC().getTangent());

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
			
			VAO vao = new VAO();
			
			vao.storeIndices(bind, GL15.GL_STATIC_DRAW);
			
			vao.storeData(0, 3, bp, 0, 0, GL15.GL_STATIC_DRAW);
			vao.storeData(1, 2, buv, 0, 0, GL15.GL_STATIC_DRAW);
			vao.storeData(2, 3, bn, 0, 0, GL15.GL_STATIC_DRAW);
			vao.storeData(3, 3, bt, 0, 0, GL15.GL_STATIC_DRAW);

			MemoryUtil.memFree(bp);
			MemoryUtil.memFree(buv);
			MemoryUtil.memFree(bn);
			MemoryUtil.memFree(bt);
			
			Material material = null;
			
			if(dmesh.getMaterial() != null)
			{
				Texture diffuse = null;
				Texture normal = null;
				
				if(dmesh.getMaterial().getDiffuse() != null)
				{
					diffuse = TextureUtils.genTexture2(GLCommon.genTexture(), dmesh.getMaterial().getDiffuse());
				}
				
				if(dmesh.getMaterial().getNormal() != null)
				{
					normal = TextureUtils.genTexture2(GLCommon.genTexture(), dmesh.getMaterial().getNormal());
				}

				material = new Material(diffuse, normal);
			}
			
			meshes.add(new Mesh(vao, dmesh.getVertexCount(), dmesh.getCollisionMesh(), material));
		}
		
		Model model = new Model(meshes);
		
		EngineRegistry.getResourceRegistry().addResource(key, model,  Model.class);
	}
}
