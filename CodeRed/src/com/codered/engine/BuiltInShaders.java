package com.codered.engine;

import java.io.IOException;

import com.codered.engine.managing.loader.ShaderPartLoader;
import com.codered.engine.resource.ResourcePath;
import com.codered.engine.resource.ResourcePath.ResourceDestination;
import com.codered.engine.shader.MalformedShaderException;
import com.codered.engine.shader.ShaderPartList;
import com.codered.engine.utils.ShaderPartUtils;
import com.codered.engine.utils.WindowContextHelper;
import com.codered.engine.window.WindowContext;
import com.codered.engine.window.WindowImpl;

public class BuiltInShaders
{
	public static void init()
	{
		WindowContext context = WindowContextHelper.getCurrentContext();
		
		try
		{
			ResourcePath path = new ResourcePath();
			path.dest(ResourceDestination.EMBEDED);
			path.src(WindowImpl.class);
			
			path.base("/resources/shaders/object/simple/");
			loadDefaultEmbededShader(context, path, "o_ambientLight");
	//		loadDefaultEmbededShader(context, path, "o_colored");
			loadDefaultEmbededShader(context, path, "o_directionalLight");
			loadDefaultEmbededShader(context, path, "o_directionalLight_N");
	//		loadDefaultEmbededShader(context, path, "o_glow");
	//		loadDefaultEmbededShader(context, path, "o_no");
	//		loadDefaultEmbededShader(context, path, "o_pointLight_N");
			
			path.base("/resources/shaders/terrain/simple/");
	//		loadDefaultEmbededShader(path, "t_ambientLight");
	//		loadDefaultEmbededShader(path, "t_directionalLight");
	//		loadDefaultEmbededShader(path, "t_directionalLight_N");
	//		loadDefaultEmbededShader(path, "t_no");
	//		loadDefaultEmbededShader(path, "t_pointLight_N");
			
			path.base("/resources/shaders/ppf/");
	//		loadDefaultEmbededShader(path, "ppf_blend");
	//		
			
			ShaderPartList sh = context.getShaderParts().builtIn();
			
			path.file("ppf_blurHorizontal").extension(".vs");
			sh.loadVertexShader("ppf_blurHorizontal", ShaderPartLoader.readShader(ShaderPartUtils.toUrl(path).openStream(), "/resources/shaders/", path.src()));
			
			path.file("ppf_blurVertical").extension(".vs");
			sh.loadVertexShader("ppf_blurVertical", ShaderPartLoader.readShader(ShaderPartUtils.toUrl(path).openStream(), "/resources/shaders/", path.src()));
			
			path.file("ppf_blur").extension(".fs");
			sh.loadFragmentShader("ppf_blur", ShaderPartLoader.readShader(ShaderPartUtils.toUrl(path).openStream(), "/resources/shaders/", path.src()));

			loadDefaultEmbededShader(context, path, "ppf_brightness");
			loadDefaultEmbededShader(context, path, "ppf_contrast");
			loadDefaultEmbededShader(context, path, "ppf_contrastMS");
	//		loadDefaultEmbededShader(path, "ppf_depthMap");
	//		loadDefaultEmbededShader(path, "ppf_depthTest");
	//		loadDefaultEmbededShader(path, "ppf_depthTestMS");
			loadDefaultEmbededShader(context, path, "ppf_hdr");
	//		loadDefaultEmbededShader(path, "ppf_invert");
			loadDefaultEmbededShader(context, path, "ppf_no");
	//		loadDefaultEmbededShader(path, "ppf_radialBlur");
			
			path.base("/resources/shaders/gui/");
			loadDefaultEmbededShader(context, path, "gui_color");
			loadDefaultEmbededShader(context, path, "gui_no");
	//		loadDefaultEmbededShadercontext, (path, "gui_texture");
		}
		catch(MalformedShaderException | IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private static void loadDefaultEmbededShader(WindowContext context, ResourcePath res, String vfs) throws MalformedShaderException, IOException
	{
		res.file(vfs);
		
		ShaderPartList sh = context.getShaderParts().builtIn();
		
		res.extension(".vs");
		sh.loadVertexShader(vfs, ShaderPartLoader.readShader(ShaderPartUtils.toUrl(res).openStream(), "/resources/shaders/", res.src()));
		
		res.extension(".fs");
		sh.loadFragmentShader(vfs, ShaderPartLoader.readShader(ShaderPartUtils.toUrl(res).openStream(), "/resources/shaders/", res.src()));
	}
}
