package com.codered.demo;

import java.io.File;

import org.lwjgl.opengl.GL11;

import com.codered.demo.GlobalSettings.Keys;

import com.codered.engine.BuiltInShaders;
import com.codered.engine.entities.Camera;
import com.codered.engine.entities.StaticEntity;
import com.codered.engine.fbo.FBO;
import com.codered.engine.fontMeshCreator.FontType;
import com.codered.engine.fontMeshCreator.GUIText;
import com.codered.engine.fontRendering.TextMaster;
import com.codered.engine.input.InputConfiguration;
import com.codered.engine.input.Key;
import com.codered.engine.light.AmbientLight;
import com.codered.engine.light.DirectionalLight;
import com.codered.engine.primitives.TexturedQuad;
import com.codered.engine.shaders.object.SimpleObjectShader;
import com.codered.engine.shaders.object.simple.AmbientLight_OShader;
import com.codered.engine.shaders.object.simple.DirectionalLight_OShader;
import com.codered.engine.utils.BindingUtils;
import com.codered.engine.utils.EvalFunc;
import com.codered.engine.utils.GLUtils;
import com.codered.engine.utils.MathUtils;
import com.codered.engine.utils.WindowHint;
import com.codered.engine.utils.WindowHint.GLProfile;
import com.codered.engine.window.WindowRoutine;

import cmn.utilslib.color.colors.LDRColor3;
import cmn.utilslib.math.matrix.Matrix4f;
import cmn.utilslib.math.vector.Vector2f;
import cmn.utilslib.math.vector.Vector3f;
import cmn.utilslib.math.vector.api.Vec3f;

public class DemoWindowContext1 extends WindowRoutine
{
	private StaticEntity entity;
	
	private Matrix4f projection;
	
	private AmbientLight ambient;
	private DirectionalLight directionalLight;
	
	private Player player;
	
	private boolean directional = true;
	
	private FBO fbo;
	
	public boolean showInventory = false;
	
	private GuiInventory inventory;
	
	private GUIText text;
	
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
		
		this.projection = MathUtils.createProjectionMatrix(this.context.getSize(), 60, 45, 0.1f, 1000);
		
		this.context.getResourceManager().WORLD.regTexturedModel("crate", "res/models/crate.obj", "res/materials/crate.mat");
		
		this.context.getResourceManager().GUI.regTexture("res/materials/gray_rsquare.png");
		this.context.getResourceManager().GUI.regTexture("res/materials/inventory-background.png");
		
		this.context.getResourceManager().GUI.regFont("res/fonts/arial");
		
		this.entity = new StaticEntity(this.context.getResourceManager().getTexturedModel("crate"), new Vector3f(0,5,-40), -45, 45, 0);
		
		this.player = new Player();
		
		this.ambient = new AmbientLight(new LDRColor3(120, 100, 100), 1);
		this.directionalLight = new DirectionalLight(200, 100, 100, 2, 1.0f, -1.0f, 0);
		
		this.fbo = new FBO();
		this.fbo.applyColorTextureAttachments(true, 0, 1);
		this.fbo.applyDepthStencilBufferAttachment();
		
		GLUtils.multisample(true);
		
		this.text = new GUIText("This Is a Sample Text",3, this.context.getResourceManager().getFont("res/fonts/arial"), new Vector2f(0,0), 1f, false);
		
		TextMaster.init();
		TextMaster.loadText(this.text);
		
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
			
			TextMaster.render();
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
		
		renderObject(this.entity, this.player.getCamera(), this.context.getShader(AmbientLight_OShader.class));			
	
		if(this.directional)
		{
			GLUtils.blend(true);
			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
			
			renderObject(this.entity, this.player.getCamera(), this.context.getShader(DirectionalLight_OShader.class));
			
			GLUtils.blend(false);
		}
	}
	
	private void renderObject(StaticEntity e, Camera c, SimpleObjectShader oShader)
	{
		oShader.u_camera.set(c);
		oShader.u_T_model.set(e.getTransformationMatrix());
		oShader.u_T_projection.set(this.projection);
		
		oShader.setInput("material", e.getModel().getMaterial());
		
		oShader.setInput("directionalLight", this.directionalLight);
		oShader.setInput("ambientLight", this.ambient);
		
		oShader.use();			
		{	
			BindingUtils.bindVAO(e.getModel().getModel().getVAO(), 0, 1, 2, 3);
			
			GL11.glDrawElements(GL11.GL_TRIANGLES, e.getModel().getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		}
		oShader.stop();	
	}
	
	public void release()
	{
		super.release();
		
		this.fbo.release();
	}
	
}
