package com.codered.demo;

import java.util.Iterator;

import org.barghos.core.color.LDRColor3;
import org.barghos.math.matrix.Mat4f;
import org.barghos.math.vector.vec3.Vec3;
import org.lwjgl.opengl.GL11;

import com.codered.assimp.AssimpLoader;
import com.codered.assimp.ModelData;
import com.codered.engine.EngineRegistry;
import com.codered.entities.Camera;
import com.codered.entities.StaticEntity;
import com.codered.managing.models.Mesh;
import com.codered.managing.models.TexturedModel;
import com.codered.rendering.light.AmbientLight;
import com.codered.rendering.shader.ShaderProgram;
import com.codered.rendering.shader.ShaderSession;
import com.codered.resource.ResourceRequest;
import com.codered.resource.ResourceRequestBlock;
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
	
	public void init()
	{
		this.initializing = true;
		
		resBlock = new ResourceRequestBlock(true);
		resBlock.addVertexShaderPart(ResourceRequest.getFile("res/shaders/o_ambientLight2.vs"));
		resBlock.addFragmentShaderPart(ResourceRequest.getFile("res/shaders/o_ambientLight2.fs"));
		//resBlock.addStaticMesh(ResourceRequest.getFile("res/models/crate.obj"));
		resBlock.addMaterial(ResourceRequest.getFile("res/materials/crate.json"));
		EngineRegistry.getResourceManager().load(resBlock);
		
		ModelData md = AssimpLoader.load("res/models/nanosuit.obj");
		System.out.println(md.meshes.size());
		Mesh newMesh = new Mesh().loadFromMesh(md.meshes.get(0));
		EngineRegistry.getResourceRegistry().staticMeshes().add("res/models/crate.obj", newMesh);
	}

	private void initPhase1()
	{
		ambientShader = new AmbientLightShader();
		ambientShader.addVertexShaderPart(EngineRegistry.getResourceRegistry().vertexShaderParts().get("res/shaders/o_ambientLight2.vs"));
		ambientShader.addFragmentShaderPart(EngineRegistry.getResourceRegistry().fragmentShaderParts().get("res/shaders/o_ambientLight2.fs"));
		ambientShader.compile();
		
		this.projection = Mat4f.perspective(this.context.getWindow().getWidth(), 60f, 0.1f, 1000f);
		
		this.world = new StaticEntityTreeImpl();
		
		TexturedModel crate = new TexturedModel(EngineRegistry.getResourceRegistry().staticMeshes().get("res/models/crate.obj"),
		EngineRegistry.getResourceRegistry().materials().get("res/materials/crate.json"));
		
		this.world.add(new StaticEntity(crate, new Vec3(0,0,-4), 0, 45, 0));
		
		this.ambient = new AmbientLight(new LDRColor3(120, 100, 100), 3);
		
		this.cam = new Camera(0.0f, 1.8f, 0.0f, 0.0f, 0.0f, 0.0f);
		
		GLUtils.multisample(true);
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
		GLUtils.clearAll();
		if(this.initializing)
		{
			return;
		}
		
		renderWorld(alpha);
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
