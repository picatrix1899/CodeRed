package com.codered.demo;

import java.util.Iterator;

import org.barghos.core.color.LDRColor3;
import org.barghos.math.matrix.Mat4f;
import org.barghos.math.vector.Vec3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.codered.Profiling;
import com.codered.StaticEntityTreeImpl;
import com.codered.engine.Engine;
import com.codered.entities.Camera;
import com.codered.entities.StaticEntity;
import com.codered.input.InputConfiguration;
import com.codered.light.AmbientLight;
import com.codered.light.DirectionalLight;
import com.codered.resource.ResourceBlock;
import com.codered.shader.ShaderProgram;
import com.codered.shader.ShaderSession;
import com.codered.utils.EvalFunc;
import com.codered.utils.GLUtils;
import com.codered.window.WindowRoutine;

public class Routine1 extends WindowRoutine
{
	private Mat4f projection;
	
	private Player player;

	private GuiInventory inventory;
	private GuiLoadingScreen loadingScreen;
	
	private GUIRenderer guiRenderer;
	
	public StaticEntityTreeImpl world;
	
	public AmbientLight ambient;
	public DirectionalLight directionalLight;
	
	private boolean initializing;
	
	public ShaderProgram ambientShader;
	public ShaderProgram directionalLightShader;
	public ShaderProgram noGuiShader;
	public ShaderProgram fontGuiShader;
	
	public void init()
	{
		ResourceBlock block1 = new ResourceBlock(true);
		block1.addTexture("res/materials/loadingscreen.png");
		block1.addFragmentShaderPart("res/shaders/gui_no.fs");
		block1.addVertexShaderPart("res/shaders/gui_no.vs");
		block1.addFragmentShaderPart("res/shaders/gui_font.fs");
		block1.addVertexShaderPart("res/shaders/gui_font.vs");
		block1.addFragmentShaderPart("res/shaders/gui_font.fs");
		block1.addVertexShaderPart("res/shaders/gui_font.vs");
		this.context.getDRM().loadResourceBlockForced(block1);
		
		this.noGuiShader = new NoGUIShader();
		this.noGuiShader.addFragmentShaderPart("res/shaders/gui_no.fs");
		this.noGuiShader.addVertexShaderPart("res/shaders/gui_no.vs");
		this.noGuiShader.compile();
		
		this.fontGuiShader = new FontGuiShader();
		this.fontGuiShader.addFragmentShaderPart("res/shaders/gui_font.fs");
		this.fontGuiShader.addVertexShaderPart("res/shaders/gui_font.vs");
		this.fontGuiShader.compile();
		
		this.guiRenderer = new GUIRenderer();
		this.guiRenderer.noGuiShader = this.noGuiShader;
		this.guiRenderer.fontGuiShader = this.fontGuiShader;
		
		loadingScreen = new GuiLoadingScreen(this.guiRenderer);
		
		this.initializing = true;

		InputConfiguration cdf = new InputConfiguration();
		cdf.registerKey(GLFW.GLFW_KEY_ESCAPE);
		cdf.registerKey(GLFW.GLFW_KEY_Q);
		cdf.registerKey(GLFW.GLFW_KEY_TAB);
		cdf.registerKey(GLFW.GLFW_KEY_W);
		cdf.registerKey(GLFW.GLFW_KEY_A);
		cdf.registerKey(GLFW.GLFW_KEY_S);
		cdf.registerKey(GLFW.GLFW_KEY_D);
		cdf.registerMouseButton(2);
		
		this.context.getInputManager().pushInputConfiguration(cdf);
		
		ResourceBlock block2 = new ResourceBlock(true);
		block2.addTexture("res/materials/gray_rsquare.png");
		block2.addTexture("res/materials/inventory-background.png");
		block2.addStaticMesh("res/models/crate.obj");
		block2.addMaterial("res/materials/crate.json");
		block2.addVertexShaderPart("res/shaders/o_ambientLight2.vs");
		block2.addFragmentShaderPart("res/shaders/o_ambientLight2.fs");
		block2.addVertexShaderPart("res/shaders/o_directionalLight.vs");
		block2.addFragmentShaderPart("res/shaders/o_directionalLight.fs");
		this.context.getDRM().loadResourceBlock(block2);
		
		this.context.getResourceManager().GUI.regFont("res/fonts/arial");
	}

	public void initPhase1()
	{
		ambientShader = new AmbientLightShader();
		ambientShader.addVertexShaderPart("res/shaders/o_ambientLight2.vs");
		ambientShader.addFragmentShaderPart("res/shaders/o_ambientLight2.fs");
		ambientShader.compile();
		
		directionalLightShader = new DirectionalLightShader();
		directionalLightShader.addVertexShaderPart("res/shaders/o_directionalLight.vs");
		directionalLightShader.addFragmentShaderPart("res/shaders/o_directionalLight.fs");
		directionalLightShader.compile();
		
		this.context.getResourceManager().WORLD.regTexturedModel("crate", "res/models/crate.obj", "res/materials/crate.json");
		
		this.projection = Mat4f.perspective(this.context.getWindow().getWidth(), 60, 0.1, 1000);
		
		this.world = new StaticEntityTreeImpl();
		
		this.world.add(new StaticEntity("crate", new Vec3f(0,0,-40), 0, 0, 0));
		this.world.add(new StaticEntity("crate", new Vec3f(0,10,-40), 0, 0, 0));
		this.world.add(new StaticEntity("crate", new Vec3f(10,0,-40), 0, 0, 0));
		this.world.add(new StaticEntity("crate", new Vec3f(10,10,-40), 0, 0, 0));
		
		this.ambient = new AmbientLight(new LDRColor3(120, 100, 100), 3);
		this.directionalLight = new DirectionalLight(200, 100, 100, 2, 1.0f, -1.0f, 0);
		
		this.player = new Player(this.world);
		
		GLUtils.multisample(true);
		
		this.inventory = new GuiInventory(this, this.guiRenderer);
	}
	
	public void update(double delta)
	{
		Profiling.PROFILER.StartProfile("RoutineUpdate");
		if(!DemoGame.getInstance().showInventory)
			if(this.context.getInputManager().isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) Engine.getInstance().stop(false);
		
		if(this.initializing)
		{
			if(!this.context.getDRM().isOccupied())
			{
				initPhase1();
				this.initializing = false;
			}
		}
		else
		{
			if(!DemoGame.getInstance().showInventory)
			{
				if(this.context.getInputManager().isKeyPressed(GLFW.GLFW_KEY_Q)) { DemoGame.getInstance().directional = !DemoGame.getInstance().directional; }
				if(this.context.getInputManager().isKeyPressed(GLFW.GLFW_KEY_TAB)) { DemoGame.getInstance().showInventory = true; this.inventory.open(); }
				this.player.update(delta);
			}
			else
			{
				this.inventory.update();
			}

		}
		Profiling.PROFILER.StopProfile("RoutineUpdate");
	}

	public void render(double delta, double alpha)
	{
		Profiling.PROFILER.StartProfile("RoutineRender");
		GLUtils.clearAll();

		if(this.initializing)
		{
			this.loadingScreen.render();
			return;
		}
		
		if(DemoGame.getInstance().showInventory)
		{
			if(this.inventory.allowWorldProcessing())
			{
				renderWorld(delta);

				GLUtils.blend(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			}

			this.inventory.render();
			
			if(this.inventory.allowWorldProcessing())
				GLUtils.blend(false);
		}
		else
		{
			renderWorld(delta);
		}
		
		Profiling.PROFILER.StopProfile("RoutineRender");
	}

	private void renderWorld(double delta)
	{
		Camera cam = this.player.getCamera();
		
		GLUtils.depthFunc(EvalFunc.LEQUAL);

		GLUtils.cullFace(GL11.GL_BACK);

		try(ShaderSession ss = ambientShader.start())
		{
			ambientShader.setUniformValue(4, this.ambient.base.color);
			ambientShader.setUniformValue(5, this.ambient.base.intensity);
			
			for(Iterator<StaticEntity> it = this.world.iterator(); it.hasNext();)
			{
				RenderHelper.renderStaticEntity2(it.next(), cam, ambientShader, this.projection);
			}
		}
		
		if(DemoGame.getInstance().directional)
		{
			
			GLUtils.blend(GL11.GL_ONE, GL11.GL_ONE);
			
			try(ShaderSession ss = directionalLightShader.start())
			{
				directionalLightShader.setUniformValue(4, this.directionalLight.base.color);
				directionalLightShader.setUniformValue(5, this.directionalLight.base.intensity);
				directionalLightShader.setUniformValue(6, this.directionalLight.direction);
				
				for(Iterator<StaticEntity> it = this.world.iterator(); it.hasNext();)
				{
					RenderHelper.renderStaticEntity2(it.next(), cam, directionalLightShader, this.projection);
				}
			}
			
			GLUtils.blend(false);
		}

		GLUtils.cullFace(false);
	}

	public void release(boolean forced)
	{
	}
}
