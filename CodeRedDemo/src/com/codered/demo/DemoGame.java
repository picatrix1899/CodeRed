package com.codered.demo;

import org.resources.ResourceManager;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import com.codered.Profiling;
import com.codered.engine.Engine;
import com.codered.utils.DebugInfo;
import com.codered.utils.WindowHint;
import com.codered.utils.WindowHint.GLProfile;
import com.codered.window.Window;
import com.codered.window.WindowContext;

public class DemoGame extends Engine
{
	private static DemoGame instance;
	
	public static DemoGame getInstance() { return instance; }

	private WindowContext context1;
	
	public boolean showInventory = false;
	public boolean directional = true;
	
	public DemoGame()
	{
		instance = this;
		
		Window w1 = new Window(800, 600, "CoderRed 3 Main", 0);
		w1.setWindowHintCallback(() -> initWindowHints());
		this.context1 = new WindowContext("main", w1, new Routine1());
	}

	private void printDebugInfo()
	{
		DebugInfo info = new DebugInfo();
		info.add("OpenGL/LWJGL Version: ", GL11.glGetString(GL11.GL_VERSION));
		info.add("Supported Color Attachments: ", "" + GL11.glGetInteger(GL30.GL_MAX_COLOR_ATTACHMENTS));
		info.add("Supported Texture Size:", "" + GL11.glGetInteger(GL11.GL_MAX_TEXTURE_SIZE));
		info.add("Supported TexArray Layers:", "" + GL11.glGetInteger(GL30.GL_MAX_ARRAY_TEXTURE_LAYERS));
		info.print();
	}
	
	private void initWindowHints()
	{
		WindowHint.resizable(true);
		WindowHint.glVersion("4.5");
		WindowHint.glProfile(GLProfile.CORE);
		WindowHint.depthBits(24);
		WindowHint.doubleBuffering(true);
		WindowHint.samples(16);
	}
	
	public void init()
	{
		Profiling.PROFILER.StartProfile("GameInit");
		ResourceManager resources = ResourceManager.getInstance();
		resources.start();
	
		this.context1.initWindow();
		
		this.context1.init();
		this.context1.getWindow().WindowClose.addHandler((arg1) -> Engine.getInstance().stop(false));
		
		printDebugInfo();
		Profiling.PROFILER.StopProfile("GameInit");
	}
	
	public void update(double delta)
	{
		Profiling.PROFILER.StartProfile("GameUpdate");
		this.context1.update(delta);
		Profiling.PROFILER.StopProfile("GameUpdate");
	}

	public void render(double delta, double alpha)
	{
		Profiling.PROFILER.StartProfile("GameRender");
		this.context1.render(delta, alpha);
		Profiling.PROFILER.StopProfile("GameRender");
	}

	
	public void release(boolean forced)
	{
		Profiling.PROFILER.StartProfile("GameRelease");
		ResourceManager resources = ResourceManager.getInstance();
		resources.stop();
		
		this.context1.release(forced);
		Profiling.PROFILER.StopProfile("GameRelease");
		
		System.out.print(Profiling.PROFILER.dump());
	}

}
