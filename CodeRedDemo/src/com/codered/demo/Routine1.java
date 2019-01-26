package com.codered.demo;

import java.util.Iterator;

import org.barghos.core.color.LDRColor3;
import org.barghos.math.matrix.Mat4f;
import org.barghos.math.vector.Vec3f;

import org.lwjgl.opengl.GL11;

import com.codered.BuiltInShaders;
import com.codered.Engine;
import com.codered.EngineRegistry;
import com.codered.StaticEntityTreeImpl;
import com.codered.demo.GlobalSettings.Keys;
import com.codered.entities.Camera;
import com.codered.entities.StaticEntity;
import com.codered.input.InputConfiguration;
import com.codered.input.Key;
import com.codered.light.AmbientLight;
import com.codered.light.DirectionalLight;
import com.codered.shaders.object.simple.AmbientLight_OShader;
import com.codered.shaders.object.simple.DirectionalLight_OShader;
import com.codered.utils.EvalFunc;
import com.codered.utils.GLUtils;
import com.codered.utils.PrimitiveRenderer;
import com.codered.utils.RenderHelper;
import com.codered.window.WindowRoutine;

public class Routine1 extends WindowRoutine
{

	private Mat4f projection;
	
	private AmbientLight ambient;
	private DirectionalLight directionalLight;
	
	private Player player;

	public boolean showInventory = false;
	
	private GuiInventory inventory;
	
	private StaticEntityTreeImpl world;
	public void init()
	{
		BuiltInShaders.init();
		PrimitiveRenderer.create();
		
		this.context.getResourceManager().GUI.loadTextureForced("res/materials/gray_rsquare.png");
		this.context.getResourceManager().GUI.loadTextureForced("res/materials/inventory-background.png");
		this.context.getResourceManager().GUI.regFont("res/fonts/arial");
		
		initPhase2();
	}

	public void initPhase2()
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
		
		config.keyStroke.addHandler((src) -> {if(src.keyPresent(Key.ESCAPE)) Engine.getInstance().stop(false); });
		config.keyStroke.addHandler((src) -> {if(src.keyPresent(Key.Q)) DemoGame.getInstance().directional = !DemoGame.getInstance().directional; });
		config.keyStroke.addHandler((src) -> {if(src.keyPresent(Key.TAB)) {this.showInventory = true; this.inventory.open();} });

		this.context.getInputManager().setConfiguration(config);

		this.context.getResourceManager().WORLD.regTexturedModel("crate", "res/models/crate.obj", "res/materials/crate.mat");

		this.projection = Mat4f.perspective(this.context.getWindow().getSize(), 70, 0.1, 1000);
		
		this.world = new StaticEntityTreeImpl();
		
		this.world.add(new StaticEntity("crate", new Vec3f(0,0,-40), 0, 0, 0));
		this.world.add(new StaticEntity("crate", new Vec3f(0,10,-40), 0, 0, 0));
		this.world.add(new StaticEntity("crate", new Vec3f(10,0,-40), 0, 0, 0));
		this.world.add(new StaticEntity("crate", new Vec3f(10,10,-40), 0, 0, 0));
		
		this.player = new Player(this.world);
		
		this.ambient = new AmbientLight(new LDRColor3(120, 100, 100), 3);
		this.directionalLight = new DirectionalLight(200, 100, 100, 2, 1.0f, -1.0f, 0);
		
		GLUtils.multisample(true);
		
		this.inventory = new GuiInventory(this);
	}
	
	public void update(double delta)
	{
		
	}

	public void render(double delta)
	{
		GLUtils.clearAll();

		if(this.showInventory)
		{
			if(this.inventory.allowWorldProcessing())
			{
				renderWorld(delta);

				GLUtils.blend(true);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			}

			this.inventory.render();
			
			if(this.inventory.allowWorldProcessing())
				GLUtils.blend(false);
		}
		else
		{
			renderWorld(delta);
		}
		
	}


	private void renderWorldFromCamera(double delta, Camera cam)
	{
		GLUtils.depthFunc(EvalFunc.LEQUAL);

		Iterator<StaticEntity> it = this.world.iterator();
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		
		AmbientLight_OShader shader1 = EngineRegistry.getShader(AmbientLight_OShader.class);
		shader1.start();
		shader1.u_ambientLight.set(this.ambient);
		
		while(it.hasNext())
		{
			RenderHelper.renderStaticEntity(it.next(), cam, shader1, this.projection);
		}
		
		shader1.stop();
		
		it = this.world.iterator();
		
		if(DemoGame.getInstance().directional)
		{
		

			GLUtils.blend(true);
			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
				
			DirectionalLight_OShader shader2 = EngineRegistry.getShader(DirectionalLight_OShader.class);
			shader2.start();
			shader2.u_directionalLight.set(this.directionalLight);
			
			while(it.hasNext())
			{
				RenderHelper.renderStaticEntity(it.next(), cam, shader2, this.projection);
			}
			
			shader2.stop();
			
			GLUtils.blend(false);
		}

		GL11.glDisable(GL11.GL_CULL_FACE);
	}
	
	private void renderWorld(double delta)
	{
		renderWorldFromCamera(delta, this.player.getCamera());
	}

	public void release()
	{
	}

}
