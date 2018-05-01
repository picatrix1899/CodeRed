package com.codered.engine;

import java.io.IOException;

import com.codered.engine.managing.loader.ShaderPartLoader;
import com.codered.engine.resource.GlobalResourceManager;
import com.codered.engine.resource.ResourcePath;
import com.codered.engine.resource.ResourcePath.ResourceDestination;
import com.codered.engine.shader.MalformedShaderException;
import com.codered.engine.shader.ShaderPartList;
import com.codered.engine.utils.ShaderPartUtils;
import com.codered.engine.window.IWindowContext;
import com.codered.engine.window.Window;

public class BuiltInShaders
{
	public static void preloadResources()
	{
		try
		{
			ResourcePath path = new ResourcePath();
			path.dest(ResourceDestination.EMBEDED);
			path.src(Window.class);
			
			path.base("/resources/shaders/object/simple/");
			registerDefaultEmbededShader(path, "o_ambientLight");
//			registerDefaultEmbededShader(path, "o_colored");
			registerDefaultEmbededShader(path, "o_directionalLight");
			registerDefaultEmbededShader(path, "o_directionalLight_N");
//			registerDefaultEmbededShader(path, "o_glow");
//			registerDefaultEmbededShader(path, "o_no");
//			registerDefaultEmbededShader(path, "o_pointLight_N");
			
			path.base("/resources/shaders/terrain/simple/");
//			registerDefaultEmbededShader(path, "t_ambientLight");
//			registerDefaultEmbededShader(path, "t_directionalLight");
//			registerDefaultEmbededShader(path, "t_directionalLight_N");
//			registerDefaultEmbededShader(path, "t_no");
//			registerDefaultEmbededShader(path, "t_pointLight_N");
			
			path.base("/resources/shaders/ppf/");
//			registerDefaultEmbededShader(path, "ppf_blend");
//			
//			GlobalResourceManager.INSTANCE.regShaderPart("fs_ppf_blur", ShaderPartLoader.readShader(ShaderPartUtils.toUrl(path.file("ppf_blur").extension(".fs")).openStream(), path.src()));
//			GlobalResourceManager.INSTANCE.regShaderPart("vs_ppf_blurHorizontal", ShaderPartLoader.readShader(ShaderPartUtils.toUrl(path.file("ppf_blurHorizontal").extension(".vs")).openStream(), path.src()));
//			GlobalResourceManager.INSTANCE.regShaderPart("vs_ppf_blurVertical", ShaderPartLoader.readShader(ShaderPartUtils.toUrl(path.file("ppf_blurVertical").extension(".vs")).openStream(), path.src()));

//			registerDefaultEmbededShader(path, "ppf_brightness");
//			registerDefaultEmbededShader(path, "ppf_contrast");
//			registerDefaultEmbededShader(path, "ppf_contrastMS");
//			registerDefaultEmbededShader(path, "ppf_depthMap");
//			registerDefaultEmbededShader(path, "ppf_depthTest");
//			registerDefaultEmbededShader(path, "ppf_depthTestMS");
//			registerDefaultEmbededShader(path, "ppf_hdr");
//			registerDefaultEmbededShader(path, "ppf_invert");
//			registerDefaultEmbededShader(path, "ppf_no");
//			registerDefaultEmbededShader(path, "ppf_radialBlur");
			
			path.base("/resources/shaders/gui/");
//			registerDefaultEmbededShader(path, "gui_color");
//			registerDefaultEmbededShader(path, "gui_no");
//			registerDefaultEmbededShader(path, "gui_texture");
//			
		}
		catch(MalformedShaderException | IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void init(IWindowContext context)
	{
		try
		{
			loadDefaultEmbededShader(context, "o_ambientLight");
//			loadDefaultEmbededShader(context, "o_colored");
			loadDefaultEmbededShader(context, "o_directionalLight");
			loadDefaultEmbededShader(context, "o_directionalLight_N");
//			loadDefaultEmbededShader(context, "o_glow");
//			loadDefaultEmbededShader(context, "o_no");
//			loadDefaultEmbededShader(context, "o_pointLight_N");
			
//			loadDefaultEmbededShader(context, "t_ambientLight");
//			loadDefaultEmbededShader(context, "t_directionalLight");
//			loadDefaultEmbededShader(context, "t_directionalLight_N");
//			loadDefaultEmbededShader(context, "t_no");
//			loadDefaultEmbededShader(context, "t_pointLight_N");
			
//			loadDefaultEmbededShader(context, "ppf_blend");
//			
//			context.getShaderParts().builtIn().loadFragmentShader("ppf_blur", GlobalResourceManager.INSTANCE.getShaderPart("fs_ppf_blur"));
//			context.getShaderParts().builtIn().loadVertexShader("ppf_blurHorizontal", GlobalResourceManager.INSTANCE.getShaderPart("vs_ppf_blurHorizontal"));
//			context.getShaderParts().builtIn().loadVertexShader("ppf_blurVertical", GlobalResourceManager.INSTANCE.getShaderPart("vs_ppf_blurVertical"));
//			
//			loadDefaultEmbededShader(context, "ppf_brightness");
//			loadDefaultEmbededShader(context, "ppf_contrast");
//			loadDefaultEmbededShader(context, "ppf_contrastMS");
//			loadDefaultEmbededShader(context, "ppf_depthMap");
//			loadDefaultEmbededShader(context, "ppf_depthTest");
//			loadDefaultEmbededShader(context, "ppf_depthTestMS");
//			loadDefaultEmbededShader(context, "ppf_hdr");
//			loadDefaultEmbededShader(context, "ppf_invert");
//			loadDefaultEmbededShader(context, "ppf_no");
//			loadDefaultEmbededShader(context, "ppf_radialBlur");
			
//			loadDefaultEmbededShader(context, "gui_color");
//			loadDefaultEmbededShader(context, "gui_no");
//			loadDefaultEmbededShader(context, "gui_texture");
			
		}
		catch(MalformedShaderException e)
		{
			e.printStackTrace();
		}
	}
	
	private static void loadDefaultEmbededShader(IWindowContext context, String vfs) throws MalformedShaderException 
	{
		ShaderPartList sh = context.getShaderParts().builtIn();
		
		sh.loadVertexShader(vfs, GlobalResourceManager.INSTANCE.getShaderPart("vs_" + vfs));
		sh.loadFragmentShader(vfs, GlobalResourceManager.INSTANCE.getShaderPart("fs_" + vfs));
	}
	
	private static void registerDefaultEmbededShader(ResourcePath res, String vfs) throws MalformedShaderException, IOException
	{
		res.file(vfs);
		
		res.extension(".vs");
		GlobalResourceManager.INSTANCE.regShaderPart("vs_" + vfs, ShaderPartLoader.readShader(ShaderPartUtils.toUrl(res).openStream(), res.src()));

		res.extension(".fs");
		GlobalResourceManager.INSTANCE.regShaderPart("fs_" + vfs, ShaderPartLoader.readShader(ShaderPartUtils.toUrl(res).openStream(), res.src()));
	}
}
