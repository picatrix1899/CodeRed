package com.codered;

import java.io.IOException;

import com.codered.engine.EngineRegistry;
import com.codered.resource.ResourcePath;
import com.codered.resource.ResourcePath.ResourceDestination;
import com.codered.shader.MalformedShaderException;
import com.codered.shader.ShaderPartList;
import com.codered.shader.ShaderPartLoader;
import com.codered.shaders.gui.Font_GUIShader;
import com.codered.shaders.gui.No_GUIShader;
import com.codered.shaders.object.simple.AmbientLight_OShader;
import com.codered.shaders.object.simple.Colored_OShader;
import com.codered.shaders.object.simple.DirectionalLight_N_OShader;
import com.codered.shaders.object.simple.DirectionalLight_OShader;
import com.codered.shaders.object.simple.No_OShader;
import com.codered.utils.ShaderPartUtils;
import com.codered.window.WindowContext;
import com.codered.window.Window;

public class BuiltInShaders
{
	public static void init()
	{
		WindowContext context = EngineRegistry.getCurrentWindowContext();
		try
		{
			ResourcePath path = new ResourcePath();
			path.dest(ResourceDestination.EMBEDED);
			path.src(Window.class);
			
			path.base("/resources/shaders/object/simple/");
			loadDefaultEmbededShader(context, path, "o_ambientLight");
			loadDefaultEmbededShader(context, path, "o_colored");
			loadDefaultEmbededShader(context, path, "o_directionalLight");
			loadDefaultEmbededShader(context, path, "o_directionalLight_N");
			loadDefaultEmbededShader(context, path, "o_no");
//			loadDefaultEmbededShader(context, path, "o_pointLight_N");
			
//			path.base("/resources/shaders/terrain/simple/");
	//		loadDefaultEmbededShader(path, "t_ambientLight");
	//		loadDefaultEmbededShader(path, "t_directionalLight");
	//		loadDefaultEmbededShader(path, "t_directionalLight_N");
	//		loadDefaultEmbededShader(path, "t_no");
	//		loadDefaultEmbededShader(path, "t_pointLight_N");	
			
			path.base("/resources/shaders/gui/");
			loadDefaultEmbededShader(context, path, "gui_color");
			loadDefaultEmbededShader(context, path, "gui_no");
			loadDefaultEmbededShader(context, path, "gui_font");
			
			EngineRegistry.registerShader(AmbientLight_OShader.class);
			EngineRegistry.registerShader(No_OShader.class);
			EngineRegistry.registerShader(Colored_OShader.class);
			EngineRegistry.registerShader(DirectionalLight_N_OShader.class);
			EngineRegistry.registerShader(DirectionalLight_OShader.class);
			EngineRegistry.registerShader(No_GUIShader.class);
			EngineRegistry.registerShader(Font_GUIShader.class);
		}
		catch(MalformedShaderException | IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private static void loadDefaultEmbededShader(WindowContext context, ResourcePath res, String vfs) throws MalformedShaderException, IOException
	{
		res.file(vfs);
		
		ShaderPartList sh = EngineRegistry.getShaderParts().builtIn();

		res.extension(".vs");
		
		sh.loadVertexShader(vfs, ShaderPartLoader.readShader(ShaderPartUtils.toUrl(res).openStream(), "/resources/shaders/", res.src()));
		
		res.extension(".fs");
		sh.loadFragmentShader(vfs, ShaderPartLoader.readShader(ShaderPartUtils.toUrl(res).openStream(), "/resources/shaders/", res.src()));
	
	}
}
