package com.codered.demo;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import com.codered.engine.Engine;
import com.codered.engine.EngineSetup;
import com.codered.engine.FixedTimestepTickRoutine;
import com.codered.utils.DebugInfo;
import com.codered.utils.GLCommon;
import com.codered.window.Window;
import com.codered.window.WindowHint.GLProfile;
import com.codered.window.WindowHints;

public class DemoGame extends Engine
{
	private static DemoGame instance;
	
	public static DemoGame getInstance() { return instance; }

	private Window window1;
	
	private Routine1 routine;
	
	public boolean showInventory = false;
	public boolean directional = true;
	
	public DemoGame()
	{
		EngineSetup setup = new EngineSetup();
		setup.mainTickRoutine = new FixedTimestepTickRoutine();
		setup.resourceTickRoutine = new FixedTimestepTickRoutine();

		setup(setup);
		
		instance = this;
		Thread.currentThread().setName("CodeRedDemo");
		
		WindowHints hints = new WindowHints();
		hints.resizable(true);
		hints.glVersion("4.5");
		hints.glProfile(GLProfile.CORE);
		hints.doubleBuffering(true);
		hints.samples(16);
		hints.autoShowWindow(false);
		
		window1 = new Window(800, 600, "CoderRed 3 Main", hints);
		this.routine = new Routine1();
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
	
	public void init()
	{
		this.window1.init();
		this.window1.WindowClose.addHandler((arg1) -> Engine.getInstance().stop(false));
		
		this.routine.init();
		
		printDebugInfo();
	}
	
	public void preUpdate()
	{
		this.routine.preUpdate();
	}
	
	public void update(double delta)
	{
		this.window1.update(delta);
		this.routine.update(delta);
		this.window1.postUpdate(delta);
	}

	public void render(double delta, double alpha)
	{
		this.window1.render(delta, alpha);
		this.routine.render(delta, alpha);
	}

	
	public void release(boolean forced)
	{
		this.routine.release(forced);
		this.window1.release(forced);
		
		GLCommon.report(System.out);
	}

}
