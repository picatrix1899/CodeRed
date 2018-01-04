package com.codered.demo;

import static org.lwjgl.opengl.GL11.glClearColor;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

import org.lwjgl.glfw.GLFW;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import cmn.utilslib.color.colors.LDRColor3;
import cmn.utilslib.math.matrix.Matrix4f;
import cmn.utilslib.math.vector.Vector2f;
import cmn.utilslib.math.vector.Vector3f;

import com.codered.demo.GlobalSettings.Keys;
import com.codered.demo.renderer.DefaultObjectRenderer;
import com.codered.demo.renderer.NewObjectRenderer;
import com.codered.demo.renderer.WriteDepthTestRenderer2;
import com.codered.engine.DebugInfo;
import com.codered.engine.Game;
import com.codered.engine.Input;
import com.codered.engine.entities.StaticEntity;
import com.codered.engine.managing.FBO;
import com.codered.engine.managing.Paths;
import com.codered.engine.managing.ResourceManager;
import com.codered.engine.managing.VAO;
import com.codered.engine.managing.Window;
import com.codered.engine.managing.World;
import com.codered.engine.managing.loader.LambdaFont;
import com.codered.engine.rendering.MasterRenderer;
import com.codered.engine.rendering.PrimitiveRenderer;
import com.codered.engine.rendering.TextRenderer;
import com.codered.engine.shaders.gui.GUIShader;
import com.codered.engine.shaders.object.DerefObjectShader;
import com.codered.engine.shaders.object.SimpleObjectShader;
import com.codered.engine.shaders.postprocess.filter.PPFShader;
import com.codered.engine.shaders.shader.MalformedShaderException;
import com.codered.engine.shaders.shader.ShaderNotFoundException;
import com.codered.engine.shaders.terrain.SimpleTerrainShader;
import com.google.common.collect.Lists;


@SuppressWarnings("unused")
public class DemoGame extends Game
{

	private MasterRenderer renderer;
	
	public void release()
	{
		VAO.clearAll();
		
		SimpleObjectShader.clean();
		SimpleTerrainShader.clean();
		PPFShader.clean();
		DerefObjectShader.clean();
		
		Window.closeDisplays();
	}
	
	public void printDebugInfo()
	{
		DebugInfo info = new DebugInfo();
		
		info.add("OpenGL/LWJGL Version: ", GL11.glGetString(GL11.GL_VERSION));
		info.add("Supported Color Attachments: ", "" + GL11.glGetInteger(GL30.GL_MAX_COLOR_ATTACHMENTS));

		info.print();
		
	}

	public void init()
	{
		
		printDebugInfo();
		
		
		Input i = Window.active.getInputManager();
		i.registerKey(Keys.k_forward);
		i.registerKey(Keys.k_back);
		i.registerKey(Keys.k_left);
		i.registerKey(Keys.k_right);
		i.registerKey(Keys.k_up);
		i.registerKey(Keys.k_exit);
		i.registerKey(Keys.k_turnLeft);
		i.registerKey(Keys.k_turnRight);
		
		i.registerButton(Keys.b_moveCam);
		i.registerButton(0);
		
		if(!new File(Paths.p_materials).exists()) new File(Paths.p_materials).mkdirs();
		if(!new File(Paths.p_models).exists()) new File(Paths.p_models).mkdirs();
		if(!new File(Paths.p_shaders).exists()) new File(Paths.p_shaders).mkdirs();
		if(!new File(Paths.p_fonts).exists()) new File(Paths.p_fonts).mkdirs();
		
		glClearColor(0,0,0,1);		
		
		renderer = new MasterRenderer();

//		renderer.addRenderer(OrthoTestRenderer.instance);
//		renderer.addRenderer(DerefRenderer.instance);
//		renderer.addRenderer(Test2Renderer.instance);
//		renderer.addRenderer(OBBTestRenderer.instance);
//		renderer.addRenderer(WriteDepthTestRenderer2.instance);
		renderer.addRenderer(DefaultObjectRenderer.instance);
//		renderer.addRenderer(DefaultTerrainRenderer.instance);

//		renderer.addRenderer(BloomRenderer.instance);
//		renderer.addRenderer(DarknessRenderer.instance);
//		renderer.addRenderer(GuiRenderer.instance);
		
		ResourceManager.registerFont("lucida", new File(Paths.p_fonts + "lucida" + Paths.e_lambdafont));
			
		Session.get().setWorld(new DemoWorld());
		Session.get().setPlayer(new Player());
		Session.get().getPlayer().window = new GUIIngameOverlay();
		
		renderer.init();
	}

	public void update()
	{
		Window.active.updateDisplay();
		
		if(GLFW.glfwGetKey(Window.active.window, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS) stop();
		if(GLFW.glfwWindowShouldClose(Window.active.window)) stop();
		
		if(Session.get().getPlayer().window.allowWorldProcessing())
		{
			Session.get().getPlayer().update();		
		}
		
		Session.get().getPlayer().window.update();
	}

	public void render()
	{
		renderer.render();			
	}

	public void preInit()
	{
		Window w = new Window("main", 800, 600, "CoderRed 3");
		w.bind();
	}
	
}
