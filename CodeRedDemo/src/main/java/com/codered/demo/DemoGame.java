package com.codered.demo;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import com.codered.engine.Engine;
import com.codered.engine.EngineSetup;
import com.codered.engine.FixedTimestepTickRoutine;
import com.codered.utils.DebugInfo;
import com.codered.utils.GLCommon;
import com.codered.window.Window;
import com.codered.window.WindowContext;
import com.codered.window.WindowHint;
import com.codered.window.WindowHint.GLProfile;

public class DemoGame extends Engine
{
	private static DemoGame instance;
	
	public static DemoGame getInstance() { return instance; }

	private WindowContext context1;

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
		WindowHint.autoShowWindow(false);
	}
	
	public void init()
	{
		this.context1.initWindow();
		
		this.context1.init();
		
		this.context1.getWindow().WindowClose.addHandler((arg1) -> Engine.getInstance().stop(false));
		
		printDebugInfo();
	}
	
	public void preUpdate()
	{
		this.context1.preUpdate();
	}
	
	public void update(double delta)
	{
		this.context1.update(delta);
	}

	public void render(double delta, double alpha)
	{
		this.context1.render(delta, alpha);
	}

	
	public void release(boolean forced)
	{
		this.context1.release(forced);
		
		GLCommon.report(System.out);
	}

}
