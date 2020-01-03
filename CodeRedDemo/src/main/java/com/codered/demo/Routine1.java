package com.codered.demo;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.barghos.math.matrix.Mat4f;
import org.barghos.math.vector.vec3.Vec3;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.codered.MemoryWatchdog;
import com.codered.MemoryWatchdog.MemorySession;
import com.codered.engine.Engine;
import com.codered.engine.EngineRegistry;
import com.codered.entities.Camera;
import com.codered.entities.StaticEntity;
import com.codered.gui.font.FontType;
import com.codered.input.InputConfiguration;
import com.codered.managing.models.Mesh;
import com.codered.managing.models.TexturedModel;
import com.codered.rendering.light.AmbientLight;
import com.codered.rendering.light.DirectionalLight;
import com.codered.rendering.shader.ShaderProgram;
import com.codered.rendering.shader.ShaderSession;
import com.codered.resource.ResourceRequestBlock;
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
	
	public FontType font;
	
	public ResourceRequestBlock resBlock;
	
	public void init()
	{
		try(MemorySession msession = MemoryWatchdog.start())
		{
			ResourceRequestBlock bl1 = new ResourceRequestBlock(false);
			bl1.addTexture("res/materials/loadingscreen.png");
			bl1.addTexture("res/fonts/arial.png");
			bl1.addFragmentShaderPart("res/shaders/gui_no.fs");
			bl1.addVertexShaderPart("res/shaders/gui_no.vs");
			bl1.addFragmentShaderPart("res/shaders/gui_font.fs");
			bl1.addVertexShaderPart("res/shaders/gui_font.vs");
			EngineRegistry.getResourceManager().load(bl1);
		}
		System.out.println(MemoryWatchdog.getLastDelta());

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

		this.font = new FontType(EngineRegistry.getResourceRegistry().textures().get("res/fonts/arial.png"), new File("res/fonts/arial.fnt"));
		
		this.loadingScreen = new GuiLoadingScreen(this.guiRenderer);
		
		this.initializing = true;

		InputConfiguration cdf = KeyBindings.main;
		cdf.registerKey(GLFW.GLFW_KEY_ESCAPE);
		cdf.registerKey(GLFW.GLFW_KEY_Q);
		cdf.registerKey(GLFW.GLFW_KEY_TAB);
		cdf.registerKey(KeyBindings.forward);
		cdf.registerKey(GLFW.GLFW_KEY_A);
		cdf.registerKey(GLFW.GLFW_KEY_S);
		cdf.registerKey(GLFW.GLFW_KEY_D);
		cdf.registerKey(GLFW.GLFW_KEY_C);
		cdf.registerMouseButton(2);
		
		this.context.getInputManager().pushInputConfiguration(cdf);
		
		resBlock = new ResourceRequestBlock(true);
		resBlock.addVertexShaderPart("res/shaders/o_ambientLight2.vs");
		resBlock.addFragmentShaderPart("res/shaders/o_ambientLight2.fs");
		resBlock.addVertexShaderPart("res/shaders/o_directionalLight.vs");
		resBlock.addFragmentShaderPart("res/shaders/o_directionalLight.fs");
		resBlock.addTexture("res/materials/gray_rsquare.png");
		resBlock.addTexture("res/materials/inventory-background.png");
		resBlock.addMaterial("res/materials/tesla_dry.json");
		EngineRegistry.getResourceManager().load(resBlock);
		
		org.haze.obj.OBJLoader objloader = new org.haze.obj.OBJLoader();
		try
		{
			org.haze.obj.Model objmodel = objloader.read("res/models/tesla.obj");
			org.haze.obj.Mesh objmesh = objmodel.meshes.get(0);
			
			Mesh newMesh = new Mesh().loadFromMesh(objmesh);

			EngineRegistry.getResourceRegistry().staticMeshes().add("res/models/tesla.obj", newMesh);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void initPhase1()
	{
		resBlock = null;
		ambientShader = new AmbientLightShader();
		ambientShader.addVertexShaderPart("res/shaders/o_ambientLight2.vs");
		ambientShader.addFragmentShaderPart("res/shaders/o_ambientLight2.fs");
		ambientShader.compile();
		
		directionalLightShader = new DirectionalLightShader();
		directionalLightShader.addVertexShaderPart("res/shaders/o_directionalLight.vs");
		directionalLightShader.addFragmentShaderPart("res/shaders/o_directionalLight.fs");
		directionalLightShader.compile();

		this.projection = Mat4f.perspective(this.context.getWindow().getWidth(), 60f, 0.1f, 1000f);
		
		this.world = new StaticEntityTreeImpl();
		
		TexturedModel crate = new TexturedModel(EngineRegistry.getResourceRegistry().staticMeshes().get("res/models/tesla.obj"),
				EngineRegistry.getResourceRegistry().materials().get("res/materials/tesla_dry.json"));

		this.world.add(new StaticEntity(crate, new Vec3(0,0,-4), 0, 45, 0));
		this.world.add(new StaticEntity(crate, new Vec3(0,1,-4), 0, 45, 0));
		this.world.add(new StaticEntity(crate, new Vec3(1,0,-4), 0, 0, 0));
		this.world.add(new StaticEntity(crate, new Vec3(1,1,-4), 0, 0, 0));
		
		this.ambient = new AmbientLight(120, 100, 100, 3);
		this.directionalLight = new DirectionalLight(200, 100, 100, 2, 1.0f, -1.0f, 0);
		
		this.player = new Player(this.world);
		
		GLUtils.multisample(true);
		
		this.inventory = new GuiInventory(this, this.guiRenderer, this.font);
	}
	
	public void update(double delta)
	{
		if(!DemoGame.getInstance().showInventory)
			if(this.context.getInputManager().isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) Engine.getInstance().stop(false);
		
		if(this.initializing)
		{
			if(this.resBlock.isFinished())
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
	}

	public void render(double delta, double alpha)
	{
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
			renderWorld(alpha);
		}
	}

	private void renderWorld(double alpha)
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
				RenderHelper.renderStaticEntity2(it.next(), cam, ambientShader, this.projection, alpha);
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
					RenderHelper.renderStaticEntity2(it.next(), cam, directionalLightShader, this.projection, alpha);
				}
			}
			
			GLUtils.blend(false);
		}

		GLUtils.cullFace(false);
	}

	public void release(boolean forced)
	{
	}

	public void preUpdate()
	{
		if(!this.initializing)
		{
			this.player.preUpdate();
		}
	}
}
