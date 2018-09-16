package com.codered.demo;


import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import com.codered.BuiltInShaders;
import com.codered.StaticEntityTreeImpl;
import com.codered.demo.GlobalSettings.Keys;
import com.codered.entities.StaticEntity;
import com.codered.fbo.FBO;
import com.codered.input.InputConfiguration;
import com.codered.input.Key;
import com.codered.light.AmbientLight;
import com.codered.light.DirectionalLight;
import com.codered.shaders.object.simple.AmbientLight_OShader;
import com.codered.shaders.object.simple.DirectionalLight_OShader;
import com.codered.shaders.object.simple.TexturedObjectShader;
import com.codered.utils.BindingUtils;
import com.codered.utils.EvalFunc;
import com.codered.utils.GLUtils;
import com.codered.utils.MathUtils;
import com.codered.utils.RenderHelper;
import com.codered.utils.WindowHint;
import com.codered.utils.WindowHint.GLProfile;
import com.codered.window.WindowRoutine;

import cmn.utilslib.color.colors.LDRColor3;
import cmn.utilslib.math.matrix.Matrix4f;
import cmn.utilslib.math.vector.Vector3f;

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
		
		this.fbo = new FBO();
		this.fbo.applyColorTextureAttachments(true, 0, 1);
		this.fbo.applyDepthStencilBufferAttachment();
		
		GLUtils.multisample(true);
		
		this.inventory = new GuiInventory(this);
	} 
	
	public void render(double delta)
	{
		BindingUtils.bindFramebuffer(this.fbo);
		GLUtils.clearAll();
		
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
	
	private void renderWorld(double delta)
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
			
			RenderHelper.renderStaticEntity(entity, this.player.getCamera(), shader, this.projection);
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
				RenderHelper.renderStaticEntity(entity, this.player.getCamera(), shader, this.projection);
				
			}
			
			GLUtils.blend(false);
		}

		GL11.glDisable(GL11.GL_CULL_FACE);
		
	}
	
	public void release()
	{
		super.release();
		
		this.fbo.release();
	}
	
}
