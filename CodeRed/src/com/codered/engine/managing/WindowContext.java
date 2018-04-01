package com.codered.engine.managing;

import com.codered.engine.rendering.WorldRenderer;
import com.codered.engine.shaders.GUIShaders;
import com.codered.engine.shaders.PPFShaders;
import com.codered.engine.shaders.SOShaders;
import com.codered.engine.shaders.STShaders;

public class WindowContext
{
	private Window window;
	
	//public DBGShaders dbgShaders = new DBGShaders();
	public GUIShaders guiShaders = new GUIShaders();
	public PPFShaders ppfShaders = new PPFShaders();
	public SOShaders soShaders = new SOShaders();
	public STShaders stShaders = new STShaders();
	
	public WorldRenderer worldRenderer = new WorldRenderer();
	
	public WindowContext(Window window)
	{
		this.window = window;
	}
	
	public Window getWindow()
	{
		return this.window;
	}
}
