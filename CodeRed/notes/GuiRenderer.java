//package com.codered.demo.renderer;
//
//
//import org.lwjgl.opengl.GL11;
//
//import com.codered.demo.Session;
//import com.codered.engine.GLUtils;
//import com.codered.engine.rendering.CustomRenderer;
//import com.codered.engine.rendering.MasterRenderer;
//
//
//public class GuiRenderer implements CustomRenderer
//{
//
//	public static GuiRenderer instance = new GuiRenderer();
//
//	
//	private MasterRenderer master;	
//	
//	public void init(MasterRenderer master)
//	{
//		this.master = master;
//	}
//	
//	public void prepare()
//	{
//	}
//	
//	
//	public void render()
//	{
//		GLUtils.bindFramebuffer(0);
//		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
//		
//		Session.get().getPlayer().window.render();
//	}
//	
//
//
//}
