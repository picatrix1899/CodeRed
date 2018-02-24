package com.codered.demo;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import cmn.utilslib.color.colors.LDRColor3;
import cmn.utilslib.math.matrix.Matrix4f;
import cmn.utilslib.math.vector.Vector2f;
import cmn.utilslib.math.vector.Vector3f;

import com.codered.engine.DebugInfo;
import com.codered.engine.GLUtils;
import com.codered.engine.Game;
import com.codered.engine.entities.DynamicEntity;
import com.codered.engine.entities.StaticEntity;
import com.codered.engine.managing.Paths;
import com.codered.engine.managing.ResourceManager;
import com.codered.engine.managing.VAO;
import com.codered.engine.managing.Window;
import com.codered.engine.managing.World;
import com.codered.engine.fbo.FBO;
import com.codered.engine.input.Input;
import com.codered.engine.input.Input2;
import com.codered.engine.input.Key;
import com.codered.engine.managing.loader.LambdaFont;
import com.codered.engine.rendering.PrimitiveRenderer;
import com.codered.engine.rendering.TextRenderer;
import com.codered.engine.rendering.WorldRenderer;
import com.codered.engine.shaders.gui.GUIShader;
import com.codered.engine.shaders.object.SimpleObjectShader;
import com.codered.engine.shaders.postprocess.filter.PPFShader;
import com.codered.engine.shaders.shader.MalformedShaderException;
import com.codered.engine.shaders.shader.ShaderNotFoundException;
import com.codered.engine.shaders.terrain.SimpleTerrainShader;

import com.codered.demo.GlobalSettings.Keys;

import com.google.common.collect.Lists;


@SuppressWarnings("unused")
public class DemoGame extends Game
{
	public void release()
	{
		VAO.clearAll();
		
		SimpleObjectShader.clean();
		SimpleTerrainShader.clean();
		PPFShader.clean();
		
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
		
		Window.named_windows.get("main").CloseRequested.addHandler((a,b) -> {stop();});
		
		Input i = Window.named_windows.get("main").getInputManager();
		i.registerKey(Keys.k_forward);
		i.registerKey(Keys.k_back);
		i.registerKey(Keys.k_left);
		i.registerKey(Keys.k_right);
		i.registerKey(Keys.k_up);
		i.registerKey(Keys.k_exit);
		i.registerKey(Keys.k_turnLeft);
		i.registerKey(Keys.k_turnRight);
		i.registerKey(Keys.k_delete);
		
		i.registerButton(Keys.b_moveCam);
		i.registerButton(0);
		
		Input2 i2 = Window.named_windows.get("main").getInputManager2();
		i2.registerKey(Keys.k_forward);
		i2.registerKey(Keys.k_back);
		i2.registerKey(Keys.k_left);
		i2.registerKey(Keys.k_right);
		i2.registerKey(Keys.k_up);
		i2.registerKey(Keys.k_exit);
		i2.registerKey(Keys.k_turnLeft);
		i2.registerKey(Keys.k_turnRight);
		i2.registerKey(Keys.k_delete);

		i2.keyStroke.addHandler((a,b) -> {if(a.response.keyPresent(Key.ESCAPE)) {stop();} });
		
		GL11.glClearColor(0,0,0,1);		

		ResourceManager.registerFont("lucida", new File(Paths.p_fonts + "lucida" + Paths.e_lambdafont));
			
		Session.get().setWorld(new DemoWorld());
		Session.get().setPlayer(new Player());
		Session.get().getPlayer().window = new GUIIngameOverlay();
		
		PrimitiveRenderer.create();
	}

	public void update()
	{
		Window.active.updateDisplay();
		
		if(Window.active.getId() == "main")
		{
			if(Session.get().getPlayer().window.allowWorldProcessing())
			{

				for(DynamicEntity e : Session.get().getWorld().getDynamicEntities())
				{
					e.update(Session.get().getWorld());
				}
					
				Session.get().getPlayer().update();
			}
			
			Session.get().getPlayer().window.update();
		}
	}

	public void render()
	{
		if(Window.active.getId() == "main")
		{
			GLUtils.bindFramebuffer(0);
			
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			
			WorldRenderer.bindFramebuffer();
			
			WorldRenderer.render(Session.get().getWorld(), Session.get().getPlayer().getCamera());	
			
			Session.get().getPlayer().window.render();
		}
	}

	public void preInit()
	{
		GLFW.glfwInit();
		
		GLFW.glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
		
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 4);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
		GLFW.glfwWindowHint(GLFW.GLFW_DEPTH_BITS, 24);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
		
		Window w = new Window("main", 800, 600, "CoderRed 3");
		w.bind();
	}
	
}
