package com.codered.demo;

import org.barghos.core.color.LDRColor3;
import org.barghos.math.matrix.Mat4f;
import org.barghos.math.vector.Vec3f;
import org.lwjgl.opengl.GL11;

import com.codered.BuiltInShaders;
import com.codered.EngineRegistry;
import com.codered.entities.Camera;
import com.codered.entities.StaticEntity;
import com.codered.light.AmbientLight;
import com.codered.light.DirectionalLight;
import com.codered.shaders.object.simple.AmbientLight_OShader;
import com.codered.shaders.object.simple.DirectionalLight_OShader;
import com.codered.utils.EvalFunc;
import com.codered.utils.GLUtils;
import com.codered.utils.PrimitiveRenderer;
import com.codered.utils.RenderHelper;
import com.codered.window.WindowRoutine;

public class Routine2 extends WindowRoutine
{
	private Mat4f projection;
	
	private AmbientLight ambient;
	private DirectionalLight directionalLight;
	
	private Camera camera;
	
	private StaticEntity display;
	
	public void init()
	{
		BuiltInShaders.init();
		PrimitiveRenderer.create();
		
		this.context.getResourceManager().WORLD.regTexturedModel("crate", "res/models/crate.obj", "res/materials/crate.mat");

		this.projection = Mat4f.perspective(this.context.getWindow().getSize(), 70, 0.1, 1000);

		this.display = new StaticEntity("crate", new Vec3f(0,0,-40), 0, 0, 0);
		
		this.camera = new Camera(0, 0, 0, 0, 0, 0);
		
		this.ambient = new AmbientLight(new LDRColor3(120, 100, 100), 3);
		this.directionalLight = new DirectionalLight(200, 100, 100, 2, 1.0f, -1.0f, -1);
		
		GLUtils.multisample(true);
	}

	public void update(double delta)
	{
	}

	public void render(double delta)
	{
		renderWorldFromCamera(delta, camera);
	}

	private void renderWorldFromCamera(double delta, Camera cam)
	{
		GLUtils.depthFunc(EvalFunc.LEQUAL);

		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		
		AmbientLight_OShader shader1 = EngineRegistry.getShader(AmbientLight_OShader.class);
		shader1.start();
		shader1.u_ambientLight.set(this.ambient);
		
		RenderHelper.renderStaticEntity(this.display, cam, shader1, this.projection);
		
		shader1.stop();
		
		if(DemoGame.getInstance().directional)
		{
			GLUtils.blend(true);
			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
				
			DirectionalLight_OShader shader2 = EngineRegistry.getShader(DirectionalLight_OShader.class);
			shader2.start();
			shader2.u_directionalLight.set(this.directionalLight);
			
			RenderHelper.renderStaticEntity(this.display, cam, shader2, this.projection);
			
			shader2.stop();
			
			GLUtils.blend(false);
		}

		GL11.glDisable(GL11.GL_CULL_FACE);
	}
	
	public void release()
	{
	}

}
