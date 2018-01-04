package com.codered.engine.rendering;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.codered.engine.managing.FBO;
import com.codered.engine.managing.Window;
import com.codered.engine.rendering.ppf.PPFilter;

import cmn.utilslib.essentials.Auto;




public class MasterRenderer
{
	
	private ArrayList<CustomRenderer> customRenderers = Auto.ArrayList();
	
	public FBO master;
	
	public MasterRenderer()
	{
		//GL11.glEnable(GL11.GL_CULL_FACE);
		//GL11.glCullFace(GL11.GL_BACK);
		
		master = new FBO(Window.active.WIDTH, Window.active.HEIGHT);
		master.applyColorAttachment(FBO.Target.COLOR0, true);
		
		FBO.updateDraws();
	}

	public void addRenderer(CustomRenderer renderer)
	{
		this.customRenderers.add(renderer);
	}

	public void render()
	{
		master.clearAllAttachments();
		
		for(CustomRenderer r : this.customRenderers)
		{
			r.prepare();
			r.render();
		}
		
		//PPFilter.HDR().setExposure(2f);
		//PPFilter.HDR().doPostProcess(master, FBO.Target.COLOR0, master, FBO.Target.COLOR0, false);
		
		//master.resolveAttachmentToScreen(FBO.Target.COLOR0);
	}
	
	public void init()
	{
		
		//PrimitiveRenderer.create();
		for(CustomRenderer r : this.customRenderers)
		{
			r.init(this);
		}
	}
	
}
