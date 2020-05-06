package com.codered.demo;

import java.io.File;
import java.util.Iterator;

import org.barghos.core.debug.Debug;
import org.barghos.math.matrix.Mat4f;
import org.lwjgl.opengl.GL11;

import com.codered.MemoryWatchdog;
import com.codered.MemoryWatchdog.MemorySession;
import com.codered.engine.EngineRegistry;
import com.codered.entities.Camera;
import com.codered.entities.StaticEntity;
import com.codered.gui.font.FontType;
import com.codered.rendering.fbo.FBO;
import com.codered.rendering.light.AmbientLight;
import com.codered.rendering.shader.ShaderProgram;
import com.codered.rendering.shader.ShaderSession;
import com.codered.resource.ResManager;
import com.codered.resource.ResourceRequestBlock;
import com.codered.utils.BindingUtils;
import com.codered.utils.EvalFunc;
import com.codered.utils.GLUtils;
import com.codered.window.WindowRoutine;

public class Routine2 extends WindowRoutine
{
	private Mat4f projection;

	public StaticEntityTreeImpl world;
	
	public AmbientLight ambient;
	
	public ShaderProgram ambientShader;
	
	public ResourceRequestBlock resBlock;
	
	private Camera cam;
	
	private FBO fbo;
	
	public ShaderProgram noGuiShader;
	public ShaderProgram fontGuiShader;
	
	public FontType font;
	
	private GUIRenderer guiRenderer;
	
	public ResManager manager;
	
	public void init()
	{
		manager = new ResManager();
		manager.init();
		
		try(MemorySession msession = MemoryWatchdog.start())
		{
			ResourceRequestBlock bl0 = new ResourceRequestBlock();
			bl0.loadTexture("res/materials/loadingscreen.png");
			bl0.loadTexture("res/fonts/arial.png");
			bl0.loadFragmentShaderPart("res/shaders/gui_no.fs");
			bl0.loadVertexShaderPart("res/shaders/gui_no.vs");
			bl0.loadFragmentShaderPart("res/shaders/gui_font.fs");
			bl0.loadVertexShaderPart("res/shaders/gui_font.vs");
			manager.loadAndWait(bl0);
		}
		Debug.println(MemoryWatchdog.getLastDelta());
		
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
		
		context.getWindow().show();
		
		ResourceRequestBlock resBlock = new ResourceRequestBlock();
		resBlock.loadVertexShaderPart("res/shaders/o_ambientLight2.vs");
		resBlock.loadFragmentShaderPart("res/shaders/o_ambientLight2.fs");
		resBlock.loadMaterial("res/materials/barrel2.json");
		this.manager.loadAndWait(resBlock);
		
		ambientShader = new AmbientLightShader();
		ambientShader.addVertexShaderPart("res/shaders/o_ambientLight2.vs");
		ambientShader.addFragmentShaderPart("res/shaders/o_ambientLight2.fs");
		ambientShader.compile();
		
		this.projection = Mat4f.perspective(this.context.getWindow().getWidth(), 60f, 0.1f, 1000f);
		
		this.world = new StaticEntityTreeImpl();

		this.ambient = new AmbientLight(120, 100, 100, 3);
		
		this.cam = new Camera(0.0f, 1.8f, 0.0f, 0.0f, 0.0f, 0.0f);
		
		GLUtils.multisample(true);
		
		this.fbo = new FBO(800, 600);
		this.fbo.applyColorTextureAttachment(0, false);
		this.fbo.applyDepthBufferAttachment();
		GLUtils.glDrawBuffersFirst();
	}
	
	public void preUpdate()
	{
	}

	public void update(double timestep)
	{
	}

	public void render(double timestep, double alpha)
	{
		BindingUtils.bindFramebuffer(this.fbo);
		GLUtils.clearAll();
		
		renderWorld(alpha);
		

		BindingUtils.bindFramebuffer(0);
		
		GLUtils.clearAll();

		this.fbo.resolveAttachmentToScreen(0);
	}

	public void release(boolean forced)
	{
		this.manager.release();
	}

	private void renderWorld(double alpha)
	{
		Camera cam = this.cam;
		
		GLUtils.depthFunc(EvalFunc.LEQUAL);

		GLUtils.cullFace(GL11.GL_BACK);

		try(ShaderSession ss = ambientShader.start())
		{
			ambientShader.setUniformValue(4, this.ambient.base.color);
			ambientShader.setUniformValue(5, this.ambient.base.intensity);
			
			for(Iterator<StaticEntity> it = this.world.iterator(); it.hasNext();)
			{
				RenderHelper.renderStaticEntity(it.next(), cam, ambientShader, this.projection, alpha);
			}
		}

		GLUtils.cullFace(false);
	}
}
