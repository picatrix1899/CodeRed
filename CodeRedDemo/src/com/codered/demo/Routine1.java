package com.codered.demo;

import java.io.File;
import java.util.Iterator;

import org.barghos.core.profiler.CascadingProfiler.ProfilingSession;
import org.barghos.core.testcolor.LDRColor3;
import org.barghos.math.matrix.Mat4f;
import org.barghos.math.vector.Vec3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.codered.Profiling;
import com.codered.engine.CriticalEngineStateException;
import com.codered.engine.Engine;
import com.codered.engine.EngineRegistry;
import com.codered.entities.Camera;
import com.codered.entities.StaticEntity;
import com.codered.font.FontType;
import com.codered.input.InputConfiguration;
import com.codered.light.AmbientLight;
import com.codered.light.DirectionalLight;
import com.codered.managing.models.TexturedModel;
import com.codered.resource.ResourceRequest;
import com.codered.resource.ResourceRequestBlock;
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
	
	public FontType font;
	
	public ResourceRequestBlock resBlock;
	
	public void init()
	{
		try(ProfilingSession session = Profiling.CPROFILER.startSession("RoutineInit"))
		{
			ResourceRequestBlock bl1 = new ResourceRequestBlock(false);
			bl1.addTexture(ResourceRequest.getFile("res/materials/loadingscreen.png"));
			bl1.addTexture(ResourceRequest.getFile("res/fonts/arial.png"));
			bl1.addFragmentShaderPart(ResourceRequest.getFile("res/shaders/gui_no.fs"));
			bl1.addVertexShaderPart(ResourceRequest.getFile("res/shaders/gui_no.vs"));
			bl1.addFragmentShaderPart(ResourceRequest.getFile("res/shaders/gui_font.fs"));
			bl1.addVertexShaderPart(ResourceRequest.getFile("res/shaders/gui_font.vs"));
			
			EngineRegistry.getResourceManager().load(bl1);
			
			this.noGuiShader = new NoGUIShader();
			this.noGuiShader.addFragmentShaderPart(EngineRegistry.getResourceRegistry().fragmentShaderParts().get("res/shaders/gui_no.fs"));
			this.noGuiShader.addVertexShaderPart(EngineRegistry.getResourceRegistry().vertexShaderParts().get("res/shaders/gui_no.vs"));
			this.noGuiShader.compile();
			
			this.fontGuiShader = new FontGuiShader();
			this.fontGuiShader.addFragmentShaderPart(EngineRegistry.getResourceRegistry().fragmentShaderParts().get("res/shaders/gui_font.fs"));
			this.fontGuiShader.addVertexShaderPart(EngineRegistry.getResourceRegistry().vertexShaderParts().get("res/shaders/gui_font.vs"));
			this.fontGuiShader.compile();
			
			this.guiRenderer = new GUIRenderer();
			this.guiRenderer.noGuiShader = this.noGuiShader;
			this.guiRenderer.fontGuiShader = this.fontGuiShader;

			this.font = new FontType(EngineRegistry.getResourceRegistry().textures().get("res/fonts/arial.png"), new File("res/fonts/arial.fnt"));
			
			this.loadingScreen = new GuiLoadingScreen(this.guiRenderer);
			
			this.initializing = true;
	
			InputConfiguration cdf = new InputConfiguration();
			cdf.registerKey(GLFW.GLFW_KEY_ESCAPE);
			cdf.registerKey(GLFW.GLFW_KEY_Q);
			cdf.registerKey(GLFW.GLFW_KEY_TAB);
			cdf.registerKey(GLFW.GLFW_KEY_W);
			cdf.registerKey(GLFW.GLFW_KEY_A);
			cdf.registerKey(GLFW.GLFW_KEY_S);
			cdf.registerKey(GLFW.GLFW_KEY_D);
			cdf.registerKey(GLFW.GLFW_KEY_C);
			cdf.registerMouseButton(2);
			
			this.context.getInputManager().pushInputConfiguration(cdf);
			
			resBlock = new ResourceRequestBlock(true);
			resBlock.addVertexShaderPart(ResourceRequest.getFile("res/shaders/o_ambientLight2.vs"));
			resBlock.addFragmentShaderPart(ResourceRequest.getFile("res/shaders/o_ambientLight2.fs"));
			resBlock.addVertexShaderPart(ResourceRequest.getFile("res/shaders/o_directionalLight.vs"));
			resBlock.addFragmentShaderPart(ResourceRequest.getFile("res/shaders/o_directionalLight.fs"));
			resBlock.addTexture(ResourceRequest.getFile("res/materials/gray_rsquare.png"));
			resBlock.addTexture(ResourceRequest.getFile("res/materials/inventory-background.png"));
			resBlock.addStaticMesh(ResourceRequest.getFile("res/models/crate.obj"));
			resBlock.addMaterial(ResourceRequest.getFile("res/materials/crate.json"));
			EngineRegistry.getResourceManager().load(resBlock);
		}
	}

	public void initPhase1()
	{
		ambientShader = new AmbientLightShader();
		ambientShader.addVertexShaderPart(EngineRegistry.getResourceRegistry().vertexShaderParts().get("res/shaders/o_ambientLight2.vs"));
		ambientShader.addFragmentShaderPart(EngineRegistry.getResourceRegistry().fragmentShaderParts().get("res/shaders/o_ambientLight2.fs"));
		ambientShader.compile();
		
		directionalLightShader = new DirectionalLightShader();
		directionalLightShader.addVertexShaderPart(EngineRegistry.getResourceRegistry().vertexShaderParts().get("res/shaders/o_directionalLight.vs"));
		directionalLightShader.addFragmentShaderPart(EngineRegistry.getResourceRegistry().fragmentShaderParts().get("res/shaders/o_directionalLight.fs"));
		directionalLightShader.compile();

		this.projection = Mat4f.perspective(this.context.getWindow().getWidth(), 60, 0.1, 1000);
		
		this.world = new StaticEntityTreeImpl();
		
		TexturedModel crate = new TexturedModel(EngineRegistry.getResourceRegistry().staticMeshes().get("res/models/crate.obj"),
				EngineRegistry.getResourceRegistry().materials().get("res/materials/crate.json"));
		
		this.world.add(new StaticEntity(crate, new Vec3f(0,0,-40), 0, 45, 0));
		this.world.add(new StaticEntity(crate, new Vec3f(0,10,-40), 0, 45, 0));
		this.world.add(new StaticEntity(crate, new Vec3f(10,0,-40), 0, 0, 0));
		this.world.add(new StaticEntity(crate, new Vec3f(10,10,-40), 0, 0, 0));
		
		this.ambient = new AmbientLight(new LDRColor3(120, 100, 100), 3);
		this.directionalLight = new DirectionalLight(200, 100, 100, 2, 1.0f, -1.0f, 0);
		
		this.player = new Player(this.world);
		
		GLUtils.multisample(true);
		
		this.inventory = new GuiInventory(this, this.guiRenderer, this.font);
	}
	
	public void update(double delta)
	{
		try(ProfilingSession session = Profiling.CPROFILER.startSession("RoutineUpdate"))
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
					if(this.context.getInputManager().isKeyPressed(GLFW.GLFW_KEY_C)) { throw new CriticalEngineStateException(new Exception("test")); }
					this.player.update(delta);
				}
				else
				{
					this.inventory.update();
				}
	
			}
		}
	}

	public void render(double delta, double alpha)
	{
		try(ProfilingSession session = Profiling.CPROFILER.startSession("RoutineRender"))
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
