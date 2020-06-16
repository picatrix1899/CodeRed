package com.codered.demo;

import java.io.File;
import java.util.Iterator;

import org.barghos.core.debug.Debug;
import org.barghos.core.tuple.tuple2.Tup2i;
import org.barghos.core.tuple.tuple3.Tup3f;
import org.barghos.math.Maths;
import org.barghos.math.geometry.AABB3;
import org.barghos.math.matrix.Mat4;
import org.barghos.math.point.Point3;
import org.barghos.math.vector.quat.Quat;
import org.barghos.math.vector.vec2.Vec2;
import org.barghos.math.vector.vec3.Vec3;
import org.barghos.math.vector.vec3.Vec3Axis;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import com.codered.MemoryWatchdog;
import com.codered.MemoryWatchdog.MemorySession;
import com.codered.engine.Engine;
import com.codered.engine.EngineRegistry;
import com.codered.entities.Camera;
import com.codered.entities.StaticEntity;
import com.codered.entities.StaticModelEntity;
import com.codered.gui.font.FontType;
import com.codered.input.InputConfiguration;
import com.codered.model.Model;
import com.codered.rendering.fbo.FBO;
import com.codered.rendering.fbo.FBOTarget;
import com.codered.rendering.fbo.MSFBO;
import com.codered.rendering.light.AmbientLight;
import com.codered.rendering.light.DirectionalLight;
import com.codered.rendering.shader.ShaderProgram;
import com.codered.rendering.shader.ShaderSession;
import com.codered.rendering.texture.Texture;
import com.codered.resource.ResManager;
import com.codered.resource.ResourceRequestBlock;
import com.codered.utils.BindingUtils;
import com.codered.utils.EvalFunc;
import com.codered.utils.GLUtils;
import com.codered.window.WindowRoutine;

public class Routine1 extends WindowRoutine
{
	private Mat4 projection;
	
	private Player player;

	private GuiInventory inventory;
	private GuiLoadingScreen loadingScreen;
	
	private GUIRenderer guiRenderer;
	
	public StaticEntityTreeImpl world;
	
	public AmbientLight ambient;
	public DirectionalLight directionalLight;
	
	public ShaderProgram ambientShader;
	public ShaderProgram directionalLightShader;
	public ShaderProgram noGuiShader;
	public ShaderProgram fontGuiShader;
	public ShaderProgram deferredShader;
	
	public FontType font;
	
	public ResManager manager;
	
	public StaticModelEntity ent;
	
	public MSFBO fr;
	public FBO out;
	
	private FBO shadowFbo;
	private ShadowShader shadowShader;
	private ShadowBox shadowBox;
	private Mat4 projectionMatrix = Mat4.identity();
	private Mat4 lightViewMatrix = Mat4.identity();
	private Mat4 projectionViewMatrix =  Mat4.identity();
	private Mat4 offset = createOffset();
	
	private static final int SHADOW_MAP_SIZE = 2048;
	
	public void init()
	{	
		Engine.getInstance().getEngineSetup().resourceLoadingProcessFactory.init();
		
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
		
		this.noGuiShader = new NoGUIShader();
		this.fontGuiShader = new FontGuiShader();
		
		this.guiRenderer = new GUIRenderer();
		this.guiRenderer.noGuiShader = this.noGuiShader;
		this.guiRenderer.fontGuiShader = this.fontGuiShader;

		this.font = new FontType(EngineRegistry.getResourceRegistry().get("res/fonts/arial.png", Texture.class), new File("res/fonts/arial.fnt"));
		
		this.loadingScreen = new GuiLoadingScreen(this.guiRenderer);
		
		ResourceLoadingTickReceiver resourceLoadingReceiver = new ResourceLoadingTickReceiver();
		resourceLoadingReceiver.loadingScreen = this.loadingScreen;
		resourceLoadingReceiver.resManager = this.manager;
		
		Engine.getInstance().getEngineSetup().resourceTickRoutine.setTickReceiver(resourceLoadingReceiver);

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
		
		context.getWindow().show();
		
		ResourceRequestBlock bl0 = new ResourceRequestBlock();
		bl0.loadTexture("res/materials/gray_rsquare.png");
		bl0.loadTexture("res/materials/inventory-background.png");
		bl0.loadFragmentShaderPart("res/shaders/o_ambientLight2.fs");
		bl0.loadVertexShaderPart("res/shaders/o_ambientLight2.vs");
		bl0.loadFragmentShaderPart("res/shaders/o_directionalLight.fs");
		bl0.loadVertexShaderPart("res/shaders/o_directionalLight.vs");
		bl0.loadFragmentShaderPart("res/shaders/o_deferred.fs");
		bl0.loadVertexShaderPart("res/shaders/o_deferred.vs");
		bl0.loadVertexShaderPart("res/shaders/shadowVertexShader.txt");
		bl0.loadFragmentShaderPart("res/shaders/shadowFragmentShader.txt");
		bl0.loadModel("res/models/nanosuit.obj");
		manager.loadAndBlock(bl0);
		
		ambientShader = new AmbientLightShader();
		directionalLightShader = new DirectionalLightShader();
		deferredShader = new DeferredShader();
		shadowShader = new ShadowShader();
		
		this.projection = Mat4.perspective(this.context.getWindow().getWidth(), 60f, 0.1f, 1000f);

		
		this.world = new StaticEntityTreeImpl();

		this.out = new FBO();
		this.out.addTextureAttachments(FBOTarget.DEPTH, FBOTarget.COLOR0, FBOTarget.COLOR1, FBOTarget.COLOR2);
		this.fr = new MSFBO(16);
		this.fr.addTextureAttachments(FBOTarget.DEPTH, FBOTarget.COLOR0, FBOTarget.COLOR1, FBOTarget.COLOR2);
		
		Model model = EngineRegistry.getResourceRegistry().get("res/models/nanosuit.obj", Model.class);
		
		this.ent = new StaticModelEntity(model, new Vec3(-10, 0, -10), 0, 0, 0);
		AABB3 aabb = this.ent.getAABB();
		float height = aabb.getHalfExtend().mul(2.0f).getY();
		Tup3f mscale = new Tup3f(1.8f / height);
		this.ent.getTransform().setScale(mscale);
		this.ent.getTransform().setPos(new Tup3f(-10, -aabb.getMin().getY(), -10));
		this.world.add(ent);
		
		this.world.add(new StaticModelEntity(model, new Vec3(-10, 0, -20), 0, 0, 0, mscale));
		this.world.add(new StaticModelEntity(model, new Vec3(-10, 0, -30), 0, 0, 0, mscale));
		this.world.add(new StaticModelEntity(model, new Vec3(-10, 0, -40), 0, 0, 0, mscale));
		this.world.add(new StaticModelEntity(model, new Vec3(-20, 0, -10), 0, 0, 0, mscale));
		this.world.add(new StaticModelEntity(model, new Vec3(-20, 0, -20), 0, 0, 0, mscale));
		this.world.add(new StaticModelEntity(model, new Vec3(-20, 0, -30), 0, 0, 0, mscale));
		this.world.add(new StaticModelEntity(model, new Vec3(-20, 0, -40), 0, 0, 0, mscale));
		this.world.add(new StaticModelEntity(model, new Vec3(-30, 0, -10), 0, 0, 0, mscale));
		this.world.add(new StaticModelEntity(model, new Vec3(-30, 0, -20), 0, 0, 0, mscale));
		this.world.add(new StaticModelEntity(model, new Vec3(-30, 0, -30), 0, 0, 0, mscale));
//		this.world.add(new StaticModelEntity(model, new Vec3(-30, 0, -40), 0, 0, 0, mscale));
//		this.world.add(new StaticModelEntity(model, new Vec3(-40, 0, -10), 0, 0, 0, mscale));
//		this.world.add(new StaticModelEntity(model, new Vec3(-40, 0, -20), 0, 0, 0, mscale));
//		this.world.add(new StaticModelEntity(model, new Vec3(-40, 0, -30), 0, 0, 0, mscale));
//		this.world.add(new StaticModelEntity(model, new Vec3(-40, 0, -40), 0, 0, 0, mscale));
		
		this.ambient = new AmbientLight(120, 100, 100, 3);
		this.directionalLight = new DirectionalLight(200, 100, 100, 2, 1.0f, -1.0f, 0);
		
		this.player = new Player(this.world, new Point3(-5, 0, -5));
		
		shadowBox = new ShadowBox(lightViewMatrix, this.player.getCamera(), 60f, 0.1f);
		
		shadowFbo = new FBO(new Tup2i(SHADOW_MAP_SIZE));
		shadowFbo.addTextureAttachment(FBOTarget.DEPTH);
		BindingUtils.bindFramebuffer(shadowFbo);
		GL30.glDrawBuffer(GL11.GL_NONE);
		BindingUtils.unbindFramebuffer();
		
		GLUtils.multisample(true);
		
		this.inventory = new GuiInventory(this, this.guiRenderer, this.font);
		this.inventory.pic = this.out.getAttachment(FBOTarget.COLOR1).getId() ;
		this.inventory.button.background = this.shadowFbo.getAttachmentId(FBOTarget.DEPTH);
	}

	public void update(double delta)
	{
		if(!DemoGame.getInstance().showInventory)
			if(this.context.getInputManager().isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) Engine.getInstance().stop(false);

//			if(!DemoGame.getInstance().showInventory)
//			{
				if(this.context.getInputManager().isKeyPressed(GLFW.GLFW_KEY_Q)) { DemoGame.getInstance().directional = !DemoGame.getInstance().directional; }
				if(this.context.getInputManager().isKeyPressed(GLFW.GLFW_KEY_TAB)) { DemoGame.getInstance().showInventory = true; this.inventory.open(); }
				this.player.update(delta);
				ent.rotate(new Vec3(Vec3Axis.AXIS_NY), 2);
//			}
//			else
//			{
//				this.inventory.update();
//			}
	}

	public void render(double delta, double alpha)
	{
		GLUtils.clearAll();

//		if(DemoGame.getInstance().showInventory)
//		{
//			if(this.inventory.allowWorldProcessing())
//			{
//				GLUtils.blend(false);
//
//				//renderShadowMap(delta);
//				
//				//GLUtils.blend(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//			}
//
//			this.inventory.render();
//			
//			if(this.inventory.allowWorldProcessing())
//				GLUtils.blend(false);
//		}
//		else
//		{
//		
			renderWorld(alpha);
//			
//		}
		
	}

	private void renderShadowMap(double alpha)
	{
		Camera cam = this.player.getCamera();
		
		shadowBox.update();
		Vec3 sunPosition = directionalLight.direction.invert(null).mul(1000000);
		Vec3 lightDirection = new Vec3(-sunPosition.getX(), -sunPosition.getY(), -sunPosition.getZ());
		this.projectionMatrix.initOrtho(shadowBox.getWidth(), shadowBox.getHeight(), shadowBox.getLength());
		updateLightViewMatrix(lightDirection, shadowBox.getCenter());
		Mat4.mul(projectionMatrix, lightViewMatrix, projectionViewMatrix);

		BindingUtils.bindFramebuffer(this.shadowFbo);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		try(ShaderSession ss = shadowShader.start())
		{
			for(Iterator<StaticEntity> it = this.world.iterator(); it.hasNext();)
			{
				RenderHelper.renderStaticEntityForShadowMap(it.next(), cam, shadowShader, this.projection, alpha);
			}
		}
		BindingUtils.unbindFramebuffer();
	}
	
	private void renderDeferred(double alpha)
	{
		Camera cam = this.player.getCamera();
		
		GLUtils.depthFuncAndEnable(EvalFunc.LEQUAL);

		GLUtils.cullFace(GL11.GL_BACK);

		try(ShaderSession ss = deferredShader.start())
		{
			for(Iterator<StaticEntity> it = this.world.iterator(); it.hasNext();)
			{
				RenderHelper.renderStaticEntity(it.next(), cam, deferredShader, this.projection, alpha);
			}
		}
		
		GLUtils.cullFace(false);
	}
	
	private void renderWorld(double alpha)
	{
		Camera cam = this.player.getCamera();
		
	//	GLUtils.depthFuncAndEnable(EvalFunc.LEQUAL);

		GLUtils.cullFace(GL11.GL_BACK);

		try(ShaderSession ss = ambientShader.start())
		{
			ambientShader.setUniformValue(4, this.ambient.base.color);
			ambientShader.setUniformValue(5, this.ambient.base.intensity);
			ambientShader.setUniformValue(1, projection);
			ambientShader.setUniformValue(2, cam, alpha);
			
			for(Iterator<StaticEntity> it = this.world.iterator(); it.hasNext();)
			{

				
				RenderHelper.renderStaticEntity(it.next(), cam, ambientShader, this.projection, alpha);
			}
		}
		
		if(DemoGame.getInstance().directional)
		{
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
			GL11.glDepthMask(false);
			GL11.glDepthFunc(GL11.GL_EQUAL);
				
			directionalLightShader.start();
			directionalLightShader.setUniformValue(4, this.directionalLight.base.color);
			directionalLightShader.setUniformValue(5, this.directionalLight.base.intensity);
			directionalLightShader.setUniformValue(6, this.directionalLight.direction);
			directionalLightShader.setUniformValue(1, projection);
			directionalLightShader.setUniformValue(2, cam, alpha);
		
			for(Iterator<StaticEntity> it = this.world.iterator(); it.hasNext();)
			{
				RenderHelper.renderStaticEntity(it.next(), cam, directionalLightShader, this.projection, alpha);
			}
			
			directionalLightShader.stop();
			
			GL11.glDepthFunc(GL11.GL_LESS);
			GL11.glDepthMask(true);
			GL11.glDisable(GL11.GL_BLEND);
				
//			GLUtils.blend(false);
		}

//		GLUtils.cullFace(false);
	}

	public void release(boolean forced)
	{
		this.manager.release();
		this.guiRenderer.release(forced);
		this.ambientShader.release(forced);
		this.directionalLightShader.release(forced);
		this.noGuiShader.release(forced);
		this.fontGuiShader.release(forced);
	}

	public void preUpdate()
	{
		this.player.preUpdate();
	}
	
	private void updateLightViewMatrix(Vec3 direction, Vec3 center) {
		direction.normal();
		center.invert();
		lightViewMatrix.initIdentity();
		float pitch = (float) Math.acos(new Vec2(direction.getX(), direction.getZ()).length());
		lightViewMatrix.rotate(Quat.getFromAxis( new Vec3(1, 0, 0), (float) (pitch * Maths.RAD_TO_DEG)));
		
		float yaw = (float) (((float) Math.atan(direction.getX() / direction.getZ())) * Maths.RAD_TO_DEG);
		yaw = direction.getZ() > 0 ? yaw - 180 : yaw;
		lightViewMatrix.rotate(Quat.getFromAxis(new Vec3(0, 1, 0), yaw));
		lightViewMatrix.translate(center);
	}
	
	public Mat4 getToShadowMapSpaceMatrix() {
		return Mat4.mul(offset, projectionViewMatrix, null);
	}
	
	private Mat4 createOffset() {
		Mat4 offset = new Mat4();
		offset.translate(new Vec3(0.5f, 0.5f, 0.5f));
		offset.scale(new Vec3(0.5f, 0.5f, 0.5f));
		return offset;
	}
}
