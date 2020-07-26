package com.codered.demo;

import com.codered.engine.EngineRegistry;
import com.codered.engine.ITickReceiver;
import com.codered.resource.ResManager;
import com.codered.utils.GLUtils;
import com.codered.window.WindowContext;

public class ResourceLoadingTickReceiver implements ITickReceiver
{

	public GuiLoadingScreen loadingScreen;
	public ResManager resManager;
	
	public void preUpdate()
	{
	}

	public void update(double timestep)
	{
		WindowContext c = EngineRegistry.getCurrentWindowContext();
		c.getWindow().update(timestep);
		
		this.resManager.update();
	}

	public void render(double timestep, double alpha)
	{
		GLUtils.clearAll();
		
		WindowContext c = EngineRegistry.getCurrentWindowContext();
		c.getWindow().render(timestep, alpha);
		
		this.loadingScreen.render();
		return;
	}

}
