package com.codered.resource.loader;

import java.io.IOException;
import java.io.InputStream;

import java.util.concurrent.ExecutorService;

import org.barghos.core.FunctionWithException;
import org.barghos.core.future.ManagedFuturePool;
import org.barghos.core.thread.ExecutorServices;

import com.codered.resource.ResourceRequest;
import com.codered.resource.ResourceResponse;
import com.codered.resource.material.MaterialLoader;
import com.codered.resource.object.ObjectLoader;
import com.codered.resource.shaderpart.ShaderPartLoader;
import com.codered.resource.texture.TextureLoader;


public class ResourceLoaderImpl implements ResourceLoader
{
	private ExecutorService executorService;
	
	private ManagedFuturePool<Void> textureFuturePool;
	private ManagedFuturePool<Void> shaderPartFuturePool;

	public ResourceLoaderImpl()
	{
		this.executorService = ExecutorServices.newLimitedCachedThreadPool(5);
		
		this.textureFuturePool = new ManagedFuturePool<>(this.executorService, "CodeRed - Texture Loader");
		this.shaderPartFuturePool = new ManagedFuturePool<>(this.executorService, "CodeRed - Shader Part Loader");
	}

	public void start()
	{
		this.textureFuturePool.start();
	}
	
	public void stop()
	{
		this.textureFuturePool.stop();
	}

	private void loadResource(ResourceRequest request, FunctionWithException<InputStream,Object> processor)
	{
		ResourceResponse response = new ResourceResponse();
		
		try
		{
			InputStream stream = request.url.openStream();
			try
			{
				response.data = processor.apply(stream);
			}
			catch (Exception e)
			{
				response.exception = e;
			}
			finally
			{
				stream.close();
			}
		}
		catch (IOException e)
		{
			response.exception = e;
		}
		
		synchronized(request)
		{
			request.response = response;
		}
	}
	
	public void loadTexture(ResourceRequest request)
	{
		loadResource(request, (s) -> TextureLoader.loadResource(s));
	}
	
	public void loadShaderPart(ResourceRequest request)
	{
		loadResource(request, (s) -> ShaderPartLoader.loadResource(s));
	}
	
	public void loadStaticMesh(ResourceRequest request)
	{
		loadResource(request, (s) -> ObjectLoader.loadResource(s));
	}
	
	public void loadMaterial(ResourceRequest request)
	{
		loadResource(request, (s) -> MaterialLoader.loadResource(s));
	}
	
	public void loadTextureAsync(ResourceRequest request)
	{
		this.textureFuturePool.submit(() -> { loadTexture(request); return null; });
	}
	
	public void loadShaderPartAsync(ResourceRequest request)
	{
		this.shaderPartFuturePool.submit(() -> { loadShaderPart(request); return null; });
	}
	
	public void loadStaticMeshAsync(ResourceRequest request)
	{
		this.shaderPartFuturePool.submit(() -> { loadStaticMesh(request); return null; });
	}
	
	public void loadMaterialAsync(ResourceRequest request)
	{
		this.shaderPartFuturePool.submit(() -> { loadMaterial(request); return null; });
	}
}
