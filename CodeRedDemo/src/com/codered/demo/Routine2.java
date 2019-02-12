package com.codered.demo;

import org.barghos.core.color.LDRColor3;
import org.barghos.math.matrix.Mat4f;
import org.barghos.math.vector.Vec3f;
import org.lwjgl.opengl.GL11;

import com.codered.BuiltInShaders;
import com.codered.entities.Camera;
import com.codered.entities.StaticEntity;
import com.codered.light.AmbientLight;
import com.codered.light.DirectionalLight;
import com.codered.resource.ResourceBlock;
import com.codered.sh.ShaderProgram;
import com.codered.sh.ShaderSession;
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
	
	private boolean initializing;
	
	public ShaderProgram ambientShader;
	public ShaderProgram directionalLightShader;
	
	public void init()
	{
		BuiltInShaders.init();
		PrimitiveRenderer.create();
		
		ResourceBlock block = new ResourceBlock(true);
		block.addStaticMesh("res/models/crate.obj");
		block.addMaterial("res/materials/crate.json");
		block.addVertexShaderPart("res/shaders/o_ambientLight2.vs");
		block.addFragmentShaderPart("res/shaders/o_ambientLight2.fs");
		block.addVertexShaderPart("res/shaders/o_directionalLight.vs");
		block.addFragmentShaderPart("res/shaders/o_directionalLight.fs");
		this.context.getDRM().loadResourceBlock(block);
		
		this.initializing = true;
	}

	private void initPhase1()
	{
		ambientShader = new AmbientLightShader();
		ambientShader.addVertexShaderPart("res/shaders/o_ambientLight2.vs");
		ambientShader.addFragmentShaderPart("res/shaders/o_ambientLight2.fs");
		ambientShader.compile();
		
		directionalLightShader = new DirectionalLightShader();
		directionalLightShader.addVertexShaderPart("res/shaders/o_directionalLight.vs");
		directionalLightShader.addFragmentShaderPart("res/shaders/o_directionalLight.fs");
		directionalLightShader.compile();
		
		this.context.getResourceManager().WORLD.regTexturedModel("crate", "res/models/crate.obj", "res/materials/crate.json");
		
		this.projection = Mat4f.perspective(this.context.getWindow().getSize(), 70, 0.1, 1000);

		this.display = new StaticEntity("crate", new Vec3f(0,0,-40), 0, 0, 0);
		
		this.camera = new Camera(0, 0, 0, 0, 0, 0);
		
		this.ambient = new AmbientLight(new LDRColor3(120, 100, 100), 3);
		this.directionalLight = new DirectionalLight(200, 100, 100, 2, 1.0f, -1.0f, -1);
		
		GLUtils.multisample(true);
	}

	public void update(double delta)
	{
		if(this.initializing)
		{
			if(!this.context.getDRM().isOccupied())
			{
				initPhase1();
				this.initializing = false;
			}
		}
	}

	public void render(double delta)
	{
		if(this.initializing)
			return;
		renderWorldFromCamera(delta, camera);
	}

	private void renderWorldFromCamera(double delta, Camera cam)
	{
		GLUtils.depthFunc(EvalFunc.LEQUAL);

		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);

		try(ShaderSession ss = ambientShader.start())
		{
			ambientShader.setUniformValue("ambientLight.base.color", this.ambient.base.color);
			ambientShader.setUniformValue("ambientLight.base.intensity", this.ambient.base.intensity);
			
			RenderHelper.renderStaticEntity2(this.display, cam, ambientShader, this.projection);
		}
		
		if(DemoGame.getInstance().directional)
		{
			
			GLUtils.blend(true);
			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
			
			try(ShaderSession ss = directionalLightShader.start())
			{
				directionalLightShader.setUniformValue("directionalLight.base.color", this.directionalLight.base.color);
				directionalLightShader.setUniformValue("directionalLight.base.intensity", this.directionalLight.base.intensity);
				directionalLightShader.setUniformValue("directionalLight.direction", this.directionalLight.direction);

				RenderHelper.renderStaticEntity2(this.display, cam, directionalLightShader, this.projection);
			}
			
			GLUtils.blend(false);
		}

		GL11.glDisable(GL11.GL_CULL_FACE);
	}
	
	public void release()
	{
	}

}
