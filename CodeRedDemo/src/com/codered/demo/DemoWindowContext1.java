package com.codered.demo;

import org.lwjgl.opengl.GL11;

import com.codered.demo.GlobalSettings.Keys;

import com.codered.engine.BuiltInShaders;
import com.codered.engine.entities.Camera;
import com.codered.engine.entities.StaticEntity;
import com.codered.engine.fbo.FBOTarget;
import com.codered.engine.fbo.MSFBO;
import com.codered.engine.input.InputConfiguration;
import com.codered.engine.input.Key;
import com.codered.engine.light.AmbientLight;
import com.codered.engine.light.DirectionalLight;
import com.codered.engine.shaders.object.SimpleObjectShader;
import com.codered.engine.shaders.object.simple.AmbientLight_OShader;
import com.codered.engine.shaders.object.simple.DirectionalLight_N_OShader;
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
import cmn.utilslib.math.vector.Vector3f;

public class DemoWindowContext1 extends WindowRoutine
{
	private StaticEntity entity;
	
	private Matrix4f projection;
	
	private AmbientLight ambient;
	private DirectionalLight directionalLight;
	
	private Player player;
	
	private boolean directional = true;
	
	private MSFBO fbo;
	
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
		InputConfiguration config = new InputConfiguration();
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
		
		config.registerButton(Keys.b_moveCam);
		config.registerButton(Keys.b_fire);
		
		this.context.getInputManager().setConfiguration(config);
		
		this.context.getInputManager().keyStroke.addHandler((a,b) -> {if(a.response.keyPresent(Key.ESCAPE)) this.context.getWindow().setWindowShouldClose(); });
		this.context.getInputManager().keyStroke.addHandler((a,b) -> {if(a.response.keyPresent(Key.Q)) this.directional = !this.directional; }); 
		
		BuiltInShaders.init();
		
		this.context.addShader(AmbientLight_OShader.class);
		this.context.addShader(DirectionalLight_N_OShader.class);
		this.context.addShader(DirectionalLight_OShader.class);
		
		this.context.getWindow().addResizeHandler((arg1, arg2) -> { resizeWindow(arg1.width, arg1.height); });
		
		this.projection = MathUtils.createProjectionMatrix(this.context.getSize(), 60, 45, 0.1f, 1000);
		
		this.context.getResourceManager().WORLD.regTexturedModel("crate", "res/models/crate.obj", "res/materials/crate.mat");
		
		this.entity = new StaticEntity(this.context.getResourceManager().getTexturedModel("crate"), new Vector3f(0,0,-40), -45, 45, 0);
		
		this.player = new Player();
		
		this.ambient = new AmbientLight(new LDRColor3(120, 100, 100), 1);
		
		this.directionalLight = new DirectionalLight(200, 100, 100, 2, 1.0f, -1.0f, 0);
		
		this.fbo = new MSFBO(16);
		this.fbo.applyColorTextureAttachment(FBOTarget.COLOR0, false);
		this.fbo.applyDepthStencilTextureAttachment();
	} 

	public void render(double delta)
	{
		BindingUtils.bindFramebuffer(this.fbo);
		GLUtils.clearAll();
		
		GLUtils.depthFunc(EvalFunc.LEQUAL);
		
		renderObject(this.entity, this.player.getCamera(), this.context.getShader(AmbientLight_OShader.class));			
		
		if(this.directional)
		{
			GLUtils.blend(true);
			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
			renderObject(this.entity, this.player.getCamera(), this.context.getShader(DirectionalLight_N_OShader.class));
			

			GLUtils.blend(false);
		}

		GLUtils.depthTest(false);
		
		this.fbo.resolveAttachmentToScreen(FBOTarget.COLOR0);
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
