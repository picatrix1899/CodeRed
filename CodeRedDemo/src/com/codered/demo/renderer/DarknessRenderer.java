package com.codered.demo.renderer;


import com.codered.engine.managing.FBO;
import com.codered.engine.rendering.CustomRenderer;
import com.codered.engine.rendering.MasterRenderer;
import com.codered.engine.rendering.ppf.PPFilter;


public class DarknessRenderer implements CustomRenderer
{

	public static DarknessRenderer instance = new DarknessRenderer();
	
	public MasterRenderer master;
	
	public void init(MasterRenderer master)
	{
		this.master = master;
	}
	
	public void prepare() { }
		
	
	public void render()
	{
		PPFilter.Contrast().setContrast(6.0f);
		PPFilter.Contrast().doPostProcess(this.master.master, FBO.Target.COLOR0, this.master.master, FBO.Target.COLOR0, false);
	}



}
