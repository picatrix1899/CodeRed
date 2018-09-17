package com.codered;

import java.io.IOException;

import com.codered.ppf.PPF_BlurH;
import com.codered.ppf.PPF_BlurV;
import com.codered.ppf.PPF_Brightness;
import com.codered.ppf.PPF_Contrast;
import com.codered.ppf.PPF_DepthMap;
import com.codered.ppf.PPF_HDR;
import com.codered.ppf.PPF_Invert;
import com.codered.ppf.PPF_No;
import com.codered.ppf.PPF_RadialBlur;
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
import com.codered.shaders.postprocess.filter.BlurH_PPFilter;
import com.codered.shaders.postprocess.filter.BlurV_PPFilter;
import com.codered.shaders.postprocess.filter.Brightness_PPFilter;
import com.codered.shaders.postprocess.filter.ContrastMS_PPFilter;
import com.codered.shaders.postprocess.filter.Contrast_PPFilter;
import com.codered.shaders.postprocess.filter.DepthMap_PPFilter;
import com.codered.shaders.postprocess.filter.HDR_PPFilter;
import com.codered.shaders.postprocess.filter.Invert_PPFilter;
import com.codered.shaders.postprocess.filter.No_PPFilter;
import com.codered.shaders.postprocess.filter.RadialBlur_PPFilter;
import com.codered.utils.ShaderPartUtils;
import com.codered.utils.WindowContextHelper;
import com.codered.window.WindowContext;
import com.codered.window.WindowImpl;

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
			loadDefaultEmbededShader(context, path, "o_colored");
			loadDefaultEmbededShader(context, path, "o_directionalLight");
			loadDefaultEmbededShader(context, path, "o_directionalLight_N");
	//		loadDefaultEmbededShader(context, path, "o_glow");
			loadDefaultEmbededShader(context, path, "o_no");
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
			loadDefaultEmbededShader(context, path, "ppf_depthMap");
			loadDefaultEmbededShader(context, path, "ppf_hdr");
			loadDefaultEmbededShader(context, path, "ppf_invert");
			loadDefaultEmbededShader(context, path, "ppf_no");
			loadDefaultEmbededShader(context, path, "ppf_radialBlur");
			
			path.base("/resources/shaders/gui/");
			loadDefaultEmbededShader(context, path, "gui_color");
			loadDefaultEmbededShader(context, path, "gui_no");
			loadDefaultEmbededShader(context, path, "gui_font");
			
			context.addShader(AmbientLight_OShader.class);
			context.addShader(No_OShader.class);
			context.addShader(Colored_OShader.class);
			context.addShader(DirectionalLight_N_OShader.class);
			context.addShader(DirectionalLight_OShader.class);
			context.addShader(BlurH_PPFilter.class);
			context.addShader(BlurV_PPFilter.class);
			context.addShader(Brightness_PPFilter.class);
			context.addShader(Contrast_PPFilter.class);
			context.addShader(HDR_PPFilter.class);
			context.addShader(No_PPFilter.class);
			context.addShader(ContrastMS_PPFilter.class);
			context.addShader(RadialBlur_PPFilter.class);
			context.addShader(DepthMap_PPFilter.class);
			context.addShader(Invert_PPFilter.class);
			context.addShader(No_GUIShader.class);
			context.addShader(Font_GUIShader.class);
			
			context.addPPF(PPF_BlurH.class);
			context.addPPF(PPF_BlurV.class);
			context.addPPF(PPF_Brightness.class);
			context.addPPF(PPF_Contrast.class);
			context.addPPF(PPF_HDR.class);
			context.addPPF(PPF_No.class);
			context.addPPF(PPF_RadialBlur.class);
			context.addPPF(PPF_DepthMap.class);
			context.addPPF(PPF_Invert.class);
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
