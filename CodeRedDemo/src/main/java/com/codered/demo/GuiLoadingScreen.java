package com.codered.demo;

import com.codered.engine.EngineRegistry;
import com.codered.gui.GUIElement;
import com.codered.gui.GUIWindow;
import com.codered.rendering.texture.Texture;
import com.codered.utils.IGuiRenderer;

public class GuiLoadingScreen extends GUIWindow
{
	Texture background;
	
	public GuiLoadingScreen(IGuiRenderer renderer)
	{
		super(renderer);
		this.background = EngineRegistry.getResourceRegistry().get("res/materials/loadingscreen.png", Texture.class);
	}
	
	public void render()
	{
		drawTexturedRect(this.background, 0, 0, this.context.getWindow().getWidth(), this.context.getWindow().getHeight());
		
		super.render();
	}
	
	public boolean allowWorldProcessing()
	{
		return false;
	}

	public void response(GUIElement e)
	{
	}

	public int getMinX()
	{
		return 0;
	}

	public int getMaxX()
	{
		return this.context.getWindow().getWidth();
	}

	public int getMinY()
	{
		return 0;
	}

	public int getMaxY()
	{
		return this.context.getWindow().getHeight();
	}

}
