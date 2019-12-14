package com.codered.demo;

import java.io.File;
import java.util.Iterator;

import org.barghos.math.matrix.Mat4f;
import org.barghos.math.vector.vec3.Vec3;
import org.lwjgl.opengl.GL11;

import com.codered.assimp.AssimpLoader;
import com.codered.assimp.ModelData;
import com.codered.engine.EngineRegistry;
import com.codered.entities.Camera;
import com.codered.entities.StaticEntity;
import com.codered.gui.font.FontType;
import com.codered.managing.models.Mesh;
import com.codered.managing.models.TexturedModel;
import com.codered.rendering.fbo.FBO;
import com.codered.rendering.light.AmbientLight;
import com.codered.rendering.shader.ShaderProgram;
import com.codered.rendering.shader.ShaderSession;
import com.codered.resource.ResourceRequest;
import com.codered.resource.ResourceRequestBlock;
import com.codered.utils.BindingUtils;
import com.codered.utils.EvalFunc;
import com.codered.utils.GLUtils;
import com.codered.window.WindowRoutine;

public class Routine2 extends WindowRoutine
{
	private Mat4f projection;
	
	private boolean initializing;
	
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
	
	public void init()
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
		
		
		this.initializing = true;
		
		resBlock = new ResourceRequestBlock(true);
		resBlock.addVertexShaderPart(ResourceRequest.getFile("res/shaders/o_ambientLight2.vs"));
		resBlock.addFragmentShaderPart(ResourceRequest.getFile("res/shaders/o_ambientLight2.fs"));
		resBlock.addMaterial(ResourceRequest.getFile("res/materials/barrel2.json"));
		EngineRegistry.getResourceManager().load(resBlock);
		
		ModelData md = AssimpLoader.load("res/models/barrel.obj");
		Mesh newMesh1 = new Mesh().loadFromMesh(md.meshes.get(0));
		EngineRegistry.getResourceRegistry().staticMeshes().add("res/models/barrel.obj", newMesh1);
	}

	private void initPhase1()
	{
		ambientShader = new AmbientLightShader();
		ambientShader.addVertexShaderPart("res/shaders/o_ambientLight2.vs");
		ambientShader.addFragmentShaderPart("res/shaders/o_ambientLight2.fs");
		ambientShader.compile();
		
		this.projection = Mat4f.perspective(this.context.getWindow().getWidth(), 60f, 0.1f, 1000f);
		
		this.world = new StaticEntityTreeImpl();
		
		TexturedModel crate1 = new TexturedModel(EngineRegistry.getResourceRegistry().staticMeshes().get("res/models/barrel.obj"),
		EngineRegistry.getResourceRegistry().materials().get("res/materials/barrel2.json"));

		this.world.add(new StaticEntity(crate1, new Vec3(0,0,-4), 0, 45, 0));
		
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
		if(this.initializing)
		{
			if(this.resBlock.isFinished())
			{
				initPhase1();
				this.initializing = false;
			}
		}
	}

	public void render(double timestep, double alpha)
	{
		if(this.initializing)
		{
			return;
		}
		
		BindingUtils.bindFramebuffer(this.fbo);
		GLUtils.clearAll();
		
		renderWorld(alpha);
		

		BindingUtils.bindFramebuffer(0);
		
		GLUtils.clearAll();

		this.fbo.resolveAttachmentToScreen(0);
	}

	public void release(boolean forced)
	{
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
				RenderHelper.renderStaticEntity2(it.next(), cam, ambientShader, this.projection, alpha);
			}
		}

		GLUtils.cullFace(false);
	}
}
