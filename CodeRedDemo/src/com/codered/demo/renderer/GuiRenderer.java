package com.codered.demo.renderer;


import com.codered.demo.Session;
import com.codered.engine.GLUtils;
import com.codered.engine.rendering.CustomRenderer;
import com.codered.engine.rendering.MasterRenderer;


public class GuiRenderer implements CustomRenderer
{

	public static GuiRenderer instance = new GuiRenderer();

	
	private MasterRenderer master;	
	
	public void init(MasterRenderer master)
	{
		this.master = master;
	}
	
	public void prepare()
	{
	}
	
	
	public void render()
	{
		GLUtils.bindFramebuffer(this.master.master);
		
		Session.get().getPlayer().window.render();
	}
	


}
