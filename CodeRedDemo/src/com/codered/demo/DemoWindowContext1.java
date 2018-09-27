package com.codered.demo;


import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import com.codered.BuiltInShaders;
import com.codered.StaticEntityTreeImpl;
import com.codered.demo.GlobalSettings.Keys;
import com.codered.entities.Camera;
import com.codered.entities.StaticEntity;
import com.codered.fbo.FBO;
import com.codered.fbo.FBOTarget;
import com.codered.input.InputConfiguration;
import com.codered.input.Key;
import com.codered.light.AmbientLight;
import com.codered.light.DirectionalLight;
import com.codered.material.Material;
import com.codered.primitives.TexturedQuad;
import com.codered.shaders.object.simple.AmbientLight_OShader;
import com.codered.shaders.object.simple.Colored_OShader;
import com.codered.shaders.object.simple.DirectionalLight_OShader;
import com.codered.shaders.object.simple.No_OShader;
import com.codered.shaders.object.simple.TexturedObjectShader;
import com.codered.texture.Texture;
import com.codered.utils.BindingUtils;
import com.codered.utils.EvalFunc;
import com.codered.utils.GLUtils;
import com.codered.utils.MathUtils;
import com.codered.utils.PrimitiveRenderer;
import com.codered.utils.RenderHelper;
import com.codered.utils.WindowContextHelper;
import com.codered.utils.WindowHint;
import com.codered.utils.WindowHint.GLProfile;
import com.codered.window.WindowRoutine;
import com.sun.prism.paint.Color;

import cmn.utilslib.color.colors.LDRColor3;
import cmn.utilslib.color.colors.api.ILDRColor3Base;
import cmn.utilslib.math.Quaternion;
import cmn.utilslib.math.matrix.Matrix4f;
import cmn.utilslib.math.vector.Vector2f;
import cmn.utilslib.math.vector.Vector3f;
import cmn.utilslib.math.vector.api.Vec3f;

public class DemoWindowContext1 extends WindowRoutine
{
	private Matrix4f projection;
	
	private AmbientLight ambient;
	private DirectionalLight directionalLight;
	
	private Player player;
	
	private boolean directional = true;
	
	private FBO fbo;
	
	public boolean showInventory = false;
	
	private GuiInventory inventory;
	
	private StaticEntityTreeImpl world;
	
	private FBO mirrorFBO;
	
	private TexturedQuad quad;
	
	private Vector3f viewportPos;
	private Quaternion viewportRotation;
	private Matrix4f viewportProjection;
	
	public void initWindowHints()
	{
		WindowHint.resizable(true);
		WindowHint.glVersion("4.2");
		WindowHint.glProfile(GLProfile.CORE);
		WindowHint.depthBits(24);
		WindowHint.doubleBuffering(true);
		WindowHint.samples(16);
	}

	private void resizeWindow(int width, int height)
	{
		this.fbo.resize(width, height);
	}
	
	public void init()
	{
		GlobalSettings.ingameInput = new InputConfiguration();
		InputConfiguration config = GlobalSettings.ingameInput;
		config.registerKey(Keys.k_forward);
		config.registerKey(Keys.k_back);
		config.registerKey(Keys.k_left);
		config.registerKey(Keys.k_right);
		config.registerKey(Keys.k_up);
		config.registerKey(Keys.k_exit);
		config.registerKey(Keys.k_turnLeft);
		config.registerKey(Keys.k_turnRight);
		config.registerKey(Keys.k_delete);
		config.registerKey(Key.Q);
		config.registerKey(Key.TAB);
		
		config.registerButton(Keys.b_moveCam);
		config.registerButton(Keys.b_fire);
		
		config.keyStroke.addHandler((src, dyn) -> {if(src.keyPresent(Key.ESCAPE)) this.context.getWindow().setWindowShouldClose(); });
		config.keyStroke.addHandler((src, dyn) -> {if(src.keyPresent(Key.Q)) this.directional = !this.directional; });
		config.keyStroke.addHandler((src, dyn) -> {if(src.keyPresent(Key.TAB)) {this.showInventory = true; this.inventory.open();} });
		
		this.context.getInputManager().setConfiguration(config);

		BuiltInShaders.init();

		this.context.getWindow().addResizeHandler((arg1, arg2) -> { resizeWindow(arg1.width, arg1.height); });
		
		this.projection = MathUtils.createProjectionMatrix2(this.context.getSize(), 60, 70, 0.1f, 1000);
		
		this.context.getResourceManager().WORLD.regTexturedModel("crate", "res/models/crate.obj", "res/materials/crate.mat");
		
		this.context.getResourceManager().GUI.regTexture("res/materials/gray_rsquare.png");
		this.context.getResourceManager().GUI.regTexture("res/materials/inventory-background.png");
		
		this.context.getResourceManager().GUI.regFont("res/fonts/arial");
		
		this.world = new StaticEntityTreeImpl();
		
		this.world.add(new StaticEntity("crate", new Vector3f(0,0,-40), 0, 0, 0));
		this.world.add(new StaticEntity("crate", new Vector3f(0,10,-40), 0, 0, 0));
		this.world.add(new StaticEntity("crate", new Vector3f(10,0,-40), 0, 0, 0));
		this.world.add(new StaticEntity("crate", new Vector3f(10,10,-40), 0, 0, 0));
		
		this.player = new Player(this.world);
		
		this.ambient = new AmbientLight(new LDRColor3(120, 100, 100), 1);
		this.directionalLight = new DirectionalLight(200, 100, 100, 2, 1.0f, -1.0f, 0);
		
		mirrorFBO = new FBO();
		mirrorFBO.applyColorTextureAttachment(FBOTarget.COLOR0, false);
		mirrorFBO.applyDepthBufferAttachment();
		
		this.quad = new TexturedQuad(new Vector3f(50,0,0), new Vector3f(0,0,50), new Material(new Texture(mirrorFBO.getAttachment(0).getId(), 50, 50, false), null, null,null, 0, 0));
		this.quad.getTransform().setPos(new Vector3f(-15,0,-70));
		this.quad.getTransform().rotate(90, 0, 0);
		
		this.viewportPos = this.quad.getTransform().getPos().addN(25, 25, 0);
		this.viewportRotation = Quaternion.getFromVector(new Vector3f(0,0, -1.000f));
		this.viewportProjection =MathUtils.createProjectionMatrix2(new Vector2f(50, 50), 30, 30, 0.0001f, 1000f);
		
		System.out.println(this.player.getCamera().getTotalRot().getForwardf());
		

		

		
		this.fbo = new FBO();
		this.fbo.applyColorTextureAttachments(true, 0, 1);
		this.fbo.applyDepthStencilBufferAttachment();
		
		GLUtils.multisample(true);
		
		this.inventory = new GuiInventory(this);
		
		PrimitiveRenderer.create();
	} 
	
	private void renderDot(Vector3f v, ILDRColor3Base color)
	{
		Colored_OShader shader = this.context.getShader(Colored_OShader.class);
		shader.loadProjectionMatrix(this.projection);
		shader.loadViewMatrix(this.player.getCamera().getViewMatrix());
		PrimitiveRenderer.drawPoint(v, color, 20);
	}
	
	public void render(double delta)
	{
		BindingUtils.bindFramebuffer(this.mirrorFBO);
		GLUtils.clearAll();
		
		Vector3f forward = this.viewportPos.subN(this.player.getCamera().getTotalPos()).reflect(new Vector3f(0,0,-1)).negate();
		
	 	double angle = Math.atan2(forward.x, forward.z); // Note: I expected atan2(z,x) but OP reported success with atan2(x,z) instead! Switch around if you see 90° off.
		viewportRotation.x = 0;
		viewportRotation.y = 1 * Math.sin( angle/2 );
		viewportRotation.z = 0;
		viewportRotation.w = Math.cos( angle/ 2);
		viewportRotation.rotate(new Vector3f(0,0,1), 180);
		renderWorldFromCamera(delta, Matrix4f.viewMatrix(this.viewportPos, this.viewportRotation), this.viewportPos);
		
		
		
		BindingUtils.bindFramebuffer(this.fbo);
		GLUtils.clearAll();

		renderDot(this.quad.getTransform().getPos(), LDRColor3.BLUE);
		renderDot(this.quad.getTransform().getPos().addN(50,0,0), LDRColor3.BLUE);
		
		if(this.showInventory)
		{
			renderWorld(delta);

			GLUtils.blend(true);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			renderInventory(delta);

			GLUtils.blend(false);
		}
		else
		{
			renderWorld(delta);
		}
		
		this.fbo.resolveAttachmentToScreen(0);
	}
	
	private void renderInventory(double delta)
	{
		this.inventory.render();
	}
	
	private void renderMirrorPerspective(double delta)
	{
		TexturedObjectShader shader = this.context.getShader(No_OShader.class);
		RenderHelper.renderTexturedQuad(this.quad, this.player.getCamera(), shader, this.projection);
	}
	
	private void renderWorldFromCamera(double delta, Matrix4f cam, Vector3f pos)
	{
		GLUtils.depthFunc(EvalFunc.LEQUAL);
		
		TexturedObjectShader shader = this.context.getShader(AmbientLight_OShader.class);
		shader.setInput("ambientLight", this.ambient);
		shader.u_camera.set(null);
		
		Iterator<StaticEntity> it = this.world.iterator();
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		
		while(it.hasNext())
		{
			StaticEntity entity = it.next();
			
			RenderHelper.renderStaticEntity(entity, cam, pos, shader, this.viewportProjection);
		}
		
		it = this.world.iterator();
		
		if(this.directional)
		{
		

			GLUtils.blend(true);
			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
				
			while(it.hasNext())
			{
				StaticEntity entity = it.next();

				shader = this.context.getShader(DirectionalLight_OShader.class);
				shader.setInput("directionalLight", this.directionalLight);
				shader.u_camera.set(null);
				
				RenderHelper.renderStaticEntity(entity, cam, pos, shader, this.viewportProjection);
				
			}
			
			GLUtils.blend(false);
		}

		GL11.glDisable(GL11.GL_CULL_FACE);
	}
	
	private void renderWorldFromCamera(double delta, Camera cam)
	{
		GLUtils.depthFunc(EvalFunc.LEQUAL);
		
		TexturedObjectShader shader = this.context.getShader(AmbientLight_OShader.class);
		shader.setInput("ambientLight", this.ambient);
		
		Iterator<StaticEntity> it = this.world.iterator();
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		
		while(it.hasNext())
		{
			StaticEntity entity = it.next();
			
			RenderHelper.renderStaticEntity(entity, cam, shader, this.projection);
		}
		
		it = this.world.iterator();
		
		if(this.directional)
		{
		

			GLUtils.blend(true);
			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
				
			while(it.hasNext())
			{
				StaticEntity entity = it.next();

				shader = this.context.getShader(DirectionalLight_OShader.class);
				shader.setInput("directionalLight", this.directionalLight);
				RenderHelper.renderStaticEntity(entity, cam, shader, this.projection);
				
			}
			
			GLUtils.blend(false);
		}

		GL11.glDisable(GL11.GL_CULL_FACE);
	}
	
	private void renderWorld(double delta)
	{
		renderWorldFromCamera(delta, this.player.getCamera());
		renderMirrorPerspective(delta);
	}
	
	public void release()
	{
		super.release();
		
		this.fbo.release();
	}
	
}
