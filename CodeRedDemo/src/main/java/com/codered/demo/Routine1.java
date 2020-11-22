package com.codered.demo;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.barghos.core.tuple2.Tup2i;
import org.barghos.core.tuple3.Tup3f;
import org.barghos.math.boundary.AABB3f;
import org.barghos.math.matrix.Mat4f;
import org.barghos.math.point.Point3f;
import org.barghos.math.quat.Quatf;
import org.barghos.math.utils.Maths;
import org.barghos.math.vec3.Vec3f;
import org.barghos.math.vec3.Vec3fAxis;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import com.codered.MemoryWatchdog;
import com.codered.MemoryWatchdog.MemorySession;
import com.codered.SweptTransform;
import com.codered.engine.Engine;
import com.codered.engine.EngineRegistry;
import com.codered.entities.Camera;
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
import com.codered.rendering.texture.Texture;
import com.codered.resource.ResManager;
import com.codered.resource.ResourceRequestBlock;
import com.codered.utils.BindingUtils;
import com.codered.utils.EvalFunc;
import com.codered.utils.GLUtils;
import com.codered.window.WindowContext;
import com.codered.window.WindowRoutine;

public class Routine1 extends WindowRoutine
{
	private Mat4f projection;
	private Mat4f proj;
	private Mat4f pr;
	private boolean p;
	
	private Player player;

	private GuiInventory inventory;
	private GuiLoadingScreen loadingScreen;
	
	private GUIRenderer guiRenderer;

	public World w;
	
	public AmbientLight ambient;
	public DirectionalLight directionalLight;
	
	public ShaderProgram ambientShader;
	public ShaderProgram directionalLightShader;
	public ShaderProgram noGuiShader;
	public ShaderProgram fontGuiShader;
	public ShaderProgram deferredShader;
	public ShaderProgram coloredShader;
	
	public FontType font;
	
	public ResManager manager;
	
	public StaticModelEntity ent;
	
	public MSFBO fr;
	public FBO out;
	
	private FBO shadowFbo;
	private ShadowShader shadowShader;
	private ShadowBox shadowBox;
	private Mat4f projectionMatrix = Mat4f.identity();
	private Mat4f lightViewMatrix = Mat4f.identity();
	private Mat4f projectionViewMatrix =  Mat4f.identity();
	private Mat4f offset = createOffset();

	private WindowContext context;
	
	private static final int SHADOW_MAP_SIZE = 4096;

	public void init()
	{	
		Engine.getInstance().getEngineSetup().resourceLoadingProcessFactory.init();
		this.context = EngineRegistry.getCurrentWindow().getContext();
		
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
		bl0.loadFragmentShaderPart("res/shaders/o_colored.fs");
		bl0.loadVertexShaderPart("res/shaders/o_colored.vs");
		bl0.loadVertexShaderPart("res/shaders/shadowVertexShader.txt");
		bl0.loadFragmentShaderPart("res/shaders/shadowFragmentShader.txt");
		bl0.loadModel("res/models/nanosuit.obj");
		manager.loadAndBlock(bl0);
		
		ambientShader = new AmbientLightShader();
		directionalLightShader = new DirectionalLightShader();
		deferredShader = new DeferredShader();
		shadowShader = new ShadowShader();
		coloredShader = new ColoredShader();
		
		RenderHelper.ambientLightShader = ambientShader;
		RenderHelper.directionalLightShader = directionalLightShader;
		RenderHelper.deferredShader = deferredShader;
		RenderHelper.shadowShader = shadowShader;
		RenderHelper.coloredShader = coloredShader;
		
		this.projection = Mat4f.perspective(this.context.getWindow().getWidth(), this.context.getWindow().getHeight(), 60f, 0.1f, 1000f);
		
		this.w = new World();

		this.out = new FBO();
		this.out.addTextureAttachments(FBOTarget.DEPTH, FBOTarget.COLOR0, FBOTarget.COLOR1, FBOTarget.COLOR2);
		this.fr = new MSFBO(16);
		this.fr.addTextureAttachments(FBOTarget.DEPTH, FBOTarget.COLOR0, FBOTarget.COLOR1, FBOTarget.COLOR2);
		
		Model model = EngineRegistry.getResourceRegistry().get("res/models/nanosuit.obj", Model.class);
		
		this.ent = new StaticModelEntity(model, new Vec3f(-10, 0, -10), 0, 0, 0);
		AABB3f aabb = this.ent.getAABB();
		float height = aabb.getHalfExtend().mul(2.0f).getY();
		Tup3f mscale = new Tup3f(1.8f / height);
		this.ent.getTransform().setScale(mscale);
		this.ent.getTransform().setPos(new Tup3f(-10, -aabb.getMin().getY(), -10));
		this.w.add(ent);
		
		this.w.add(new StaticModelEntity(model, new Vec3f(-10, 0, -30), 0, 0, 0, mscale));
//		this.w.add(new StaticModelEntity(model, new Vec3f(-10, 0, -40), 0, 0, 0, mscale));
//		this.w.add(new StaticModelEntity(model, new Vec3f(-20, 0, -10), 0, 0, 0, mscale));
//		this.w.add(new StaticModelEntity(model, new Vec3f(-20, 0, -20), 0, 0, 0, mscale));
//		this.w.add(new StaticModelEntity(model, new Vec3f(-20, 0, -30), 0, 0, 0, mscale));
//		this.w.add(new StaticModelEntity(model, new Vec3f(-20, 0, -40), 0, 0, 0, mscale));
//		this.w.add(new StaticModelEntity(model, new Vec3f(-30, 0, -10), 0, 0, 0, mscale));
//		this.w.add(new StaticModelEntity(model, new Vec3f(-30, 0, -20), 0, 0, 0, mscale));
//		this.w.add(new StaticModelEntity(model, new Vec3f(-30, 0, -30), 0, 0, 0, mscale));
//		this.w.add(new StaticModelEntity(model, new Vec3f(-30, 0, -40), 0, 0, 0, mscale));
//		this.w.add(new StaticModelEntity(model, new Vec3f(-40, 0, -10), 0, 0, 0, mscale));
//		this.w.add(new StaticModelEntity(model, new Vec3f(-40, 0, -20), 0, 0, 0, mscale));
//		this.w.add(new StaticModelEntity(model, new Vec3f(-40, 0, -30), 0, 0, 0, mscale));
//		this.w.add(new StaticModelEntity(model, new Vec3f(-40, 0, -40), 0, 0, 0, mscale));
		
		this.ambient = new AmbientLight(120, 100, 100, 1);
		this.directionalLight = new DirectionalLight(200, 100, 100, 10, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0f);
		
		this.player = new Player(new Point3f(0, 0, 0));
		
		shadowBox = new ShadowBox(lightViewMatrix, this.player.getCamera(), 60f, 0.1f);
		
		shadowFbo = new FBO(new Tup2i(SHADOW_MAP_SIZE));
		shadowFbo.addTextureAttachment(FBOTarget.DEPTH);
		shadowFbo.addTextureAttachment(FBOTarget.COLOR0);
		BindingUtils.bindFramebuffer(shadowFbo);
		GLUtils.glDrawBuffers(GL30.GL_COLOR_ATTACHMENT0);
		BindingUtils.unbindFramebuffer();
		
		GLUtils.multisample(true);
		
		this.inventory = new GuiInventory(this, this.guiRenderer, this.font);
		this.inventory.button.background = this.shadowFbo.getAttachmentId(FBOTarget.COLOR0);
	}

	public void update(double delta)
	{
		if(this.context.getInputManager().isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) Engine.getInstance().stop(false);
			
		if(this.context.getInputManager().isKeyPressed(GLFW.GLFW_KEY_Q)) { DemoGame.getInstance().directional = !DemoGame.getInstance().directional; }
		
		this.player.update(delta);

		playerPos.set(this.player.getEyePos());

		this.inventory.update();
	}

	public void render(double delta, double alpha)
	{
		GLUtils.clearAll();

		this.inventory.render();

		renderWorld(alpha);

		renderAxis(alpha);
	}

	private void renderAxis(double alpha)
	{
		RenderHelper.renderArrow(this.projection, this.player.getCamera(), new Point3f(0,0,0), new Point3f(10, 0, 0), new Tup3f(1,0,0), alpha);
		RenderHelper.renderArrow(this.projection, this.player.getCamera(), new Point3f(0,0,0), new Point3f(0, 10, 0), new Tup3f(0,1,0), alpha);
		RenderHelper.renderArrow(this.projection, this.player.getCamera(), new Point3f(0,0,0), new Point3f(0, 0, 10), new Tup3f(0,0,1), alpha);
		
		RenderHelper.renderArc(this.projection, this.player.getCamera(), new Point3f(0,0,0), new Point3f(10, 0, 0), new Point3f(0, 10, 0),
				16, new Tup3f(1,1,0), alpha);
		
		Vec3f headPos = this.ent.getTransform().getTransformedPos((float)alpha).addN(0,1.6f,0);
		Quatf rot = this.ent.getTransform().getTransformedRot((float)alpha);
		Vec3f lookDir = rot.transform(Vec3fAxis.AXIS_Z, new Vec3f()).normal();
		
		RenderHelper.renderArrow(this.projection, this.player.getCamera(), headPos, headPos.addN(0, 0, -1), new Tup3f(1,0,0), alpha);
		RenderHelper.renderArrow(this.projection, this.player.getCamera(), headPos, headPos.addN(lookDir), new Tup3f(0,1,0), alpha);
	}
	
	Vec3f playerPos = new Vec3f();
	
	private void renderShadowMap(double alpha)
	{
		Mat4f lightProjection = Mat4f.ortho(-10f, 10f, -10f, 10f, 0.001f, 1000f);
		
		Vec3f pos = this.directionalLight.pos;
		
		Mat4f lightView = Mat4f.lookAt(pos, pos.addN(this.directionalLight.direction), Vec3fAxis.AXIS_Y);
		
		Mat4f lightSpace = lightProjection.mul(lightView);
		
		GL11.glViewport(0, 0, SHADOW_MAP_SIZE, SHADOW_MAP_SIZE);
		BindingUtils.bindFramebuffer(this.shadowFbo);
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT);

		GLUtils.cullFace(GL11.GL_FRONT);
		
		Map<Model, List<StaticModelEntity>> m = this.w.getMapByModel();
		
		for(Model model : m.keySet())
		{
			RenderHelper.renderShadowMap(model, m.get(model), lightSpace, alpha);
		}
		
		BindingUtils.unbindFramebuffer();
		
		GLUtils.cullFace(false);
		
		GL11.glViewport(0, 0, this.context.getWindow().getWidth(), this.context.getWindow().getHeight());
	}
	
//	private void renderShadowMap(double alpha)
//	{
//		shadowBox.update();
//		
//		Vec3f lightDirection = this.directionalLight.pos.invert(null);
//		//this.projectionMatrix.initOrtho(shadowBox.getWidth(), shadowBox.getHeight(), shadowBox.getLength());
//		this.projectionMatrix.initOrtho(-10, 10, -10, 10, 0.001f, 1000f);
//		updateLightViewMatrix(lightDirection, shadowBox.getCenter());
//		Mat4.mul(projectionMatrix, lightViewMatrix, projectionViewMatrix);
//
//		GL11.glViewport(0, 0, SHADOW_MAP_SIZE, SHADOW_MAP_SIZE);
//		BindingUtils.bindFramebuffer(this.shadowFbo);
//		
//		GL11.glEnable(GL11.GL_DEPTH_TEST);
//		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT);
//
//		GLUtils.cullFace(GL11.GL_FRONT);
//		
//		Map<Model, List<StaticModelEntity>> m = this.w.getMapByModel();
//		
//		for(Model model : m.keySet())
//		{
//			RenderHelper.renderShadowMap(model, m.get(model), this.projectionViewMatrix, alpha);
//		}
//		
//		BindingUtils.unbindFramebuffer();
//		
//		GLUtils.cullFace(false);
//		
//		GL11.glViewport(0, 0, this.context.getWindow().getWidth(), this.context.getWindow().getHeight());
//	}
	
	private void renderDeferred(double alpha)
	{
		Camera cam = this.player.getCamera();
		
		GLUtils.depthFuncAndEnable(EvalFunc.LEQUAL);

		GLUtils.cullFace(GL11.GL_BACK);

		Map<Model, List<StaticModelEntity>> m = this.w.getMapByModel();
		
		for(Model model : m.keySet())
		{
			RenderHelper.renderDeferred(model, m.get(model), cam, this.projection, alpha);
		}
		
		GLUtils.cullFace(false);
	}

	private void renderWorld(double alpha)
	{
		Camera cam = this.player.getCamera();
		
		GLUtils.depthFuncAndEnable(EvalFunc.LEQUAL);

		GLUtils.cullFace(GL11.GL_BACK);
		
		Map<Model, List<StaticModelEntity>> m = this.w.getMapByModel();
		
		renderShadowMap(alpha);
		
		for(Model model : m.keySet())
		{
			RenderHelper.renderAmbientLight(model, m.get(model), cam, this.projection, this.ambient, alpha);
		}
		
		if(DemoGame.getInstance().directional)
		{
			GLUtils.blend(GL11.GL_ONE, GL11.GL_ONE);
			GL11.glDepthMask(false);
			GLUtils.depthFunc(EvalFunc.EQUAL);
			
			for(Model model : m.keySet())
			{
				RenderHelper.renderDirectionalLight(model, m.get(model), cam, this.projection, this.directionalLight, alpha, getToShadowMapSpaceMatrix(), this.shadowFbo.getAttachmentId(FBOTarget.DEPTH));
			}
			
			GLUtils.depthFunc(EvalFunc.LESS);
			GL11.glDepthMask(true);
			GLUtils.blend(false);
		}

		GLUtils.cullFace(false);
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
	
	private void updateLightViewMatrix(Vec3f direction, Vec3f center) {
//		direction.normal();
//		center.invert();
//		lightViewMatrix.initIdentity();
//		float pitch = (float) Math.acos(new Vec2(direction.getX(), direction.getZ()).length());
//		lightViewMatrix.rotate(Quatf.getFromAxis( new Vec3f(1, 0, 0), (float) (pitch * Maths.RAD_TO_DEG)));
//		
//		float yaw = (float) (((float) Math.atan(direction.getX() / direction.getZ())) * Maths.RAD_TO_DEG);
//		yaw = direction.getZ() > 0 ? yaw - 180 : yaw;
//		lightViewMatrix.rotate(Quatf.getFromAxis(new Vec3f(0, 1, 0), -yaw));
//		lightViewMatrix.translate(center);
		
		 lightViewMatrix.initLookAt(center, center.addN(direction), Vec3fAxis.AXIS_Y);
	}
	
	public Mat4f getToShadowMapSpaceMatrix() {
		return Mat4f.mul(offset, projectionViewMatrix, null);
	}
	
	private Mat4f createOffset() {
		Mat4f offset = new Mat4f();
		offset.translate3D(new Vec3f(0.5f, 0.5f, 0.5f));
		offset.scale3D(new Vec3f(0.5f, 0.5f, 0.5f));
		return offset;
	}
}
