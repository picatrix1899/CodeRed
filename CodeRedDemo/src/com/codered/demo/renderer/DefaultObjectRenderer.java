package com.codered.demo.renderer;


import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL32;

import com.codered.demo.Session;
import com.codered.engine.GLUtils;
import com.codered.engine.entities.Camera;
import com.codered.engine.entities.StaticEntity;
import com.codered.engine.light.PointLight;
import com.codered.engine.managing.FBO;
import com.codered.engine.managing.MSFBO;
import com.codered.engine.managing.Window;
import com.codered.engine.managing.World;
import com.codered.engine.rendering.CustomRenderer;
import com.codered.engine.rendering.MasterRenderer;
import com.codered.engine.rendering.ppf.PPFilter;
import com.codered.engine.shaders.SOShaders;
import com.codered.engine.shaders.object.SimpleObjectShader;
import com.codered.engine.shaders.terrain.STShaders;
import com.codered.engine.shaders.terrain.SimpleTerrainShader;
import com.codered.engine.terrain.Terrain;

import cmn.utilslib.math.matrix.Matrix4f;
import cmn.utilslib.math.vector.api.Vec3fBase;

public class DefaultObjectRenderer implements CustomRenderer
{

	public static DefaultObjectRenderer instance = new DefaultObjectRenderer();

	
	private MasterRenderer master;	
	private Camera camera;
	private World world;	
	private Matrix4f T_projection;

	private MSFBO msMain;
	private FBO main;	
	
	public void init(MasterRenderer master)
	{
		this.master = master;
		
		msMain = new MSFBO(Window.active.WIDTH, Window.active.HEIGHT, 3);
		msMain.applyColorAttachment(FBO.Target.COLOR0, true);
		msMain.applyDepthBufferAttachment();
		
		main = new FBO(Window.active.WIDTH, Window.active.HEIGHT);
		main.applyColorAttachment(FBO.Target.COLOR0, true);
		main.applyDepthBufferAttachment();
	}
	
	public void prepare()
	{
		this.T_projection = Window.active.projectionMatrix;
		this.camera = Session.get().getPlayer().getCamera();
		this.world = Session.get().getWorld();
		
		FBO.updateDraws();
		
		msMain.clearAttachment(FBO.Target.COLOR0);
		main.clearAttachment(FBO.Target.COLOR0);
		
		StaticEntity e = this.world.getStaticEntity(0);
		
		e.rotateYaw(0.2f);
		
	}
	
	
	public void render()
	{
		GLUtils.bindFramebuffer(msMain);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		GLUtils.toggleDepthTest(true);
		GLUtils.toggleMultisample(true);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		
		SOShaders.AmbientLight.loadAmbientLight(world.getEnviroment().ambient);
		renderObject(camera, SOShaders.AmbientLight);				
		
		
		GLUtils.toggleBlend(true);
		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);		
		
		SOShaders.DirectionalLight_N.loadDirectionalLight(world.getEnviroment().sun);
		renderObject(camera, SOShaders.DirectionalLight_N);
		
		
		for(PointLight p : this.world.getStaticPointLights())
		{
			SOShaders.PointLight_N.loadPointLight(p);
			
			renderObject(camera, SOShaders.PointLight_N);
		}
		
		STShaders.AmbientLight.loadAmbientLight(world.getEnviroment().ambient);
	
		renderTerrain(camera, STShaders.AmbientLight);				

		STShaders.DirectionalLight_N.loadDirectionalLight(world.getEnviroment().sun);
			
		renderTerrain(camera, STShaders.DirectionalLight_N);
			
		for(PointLight p : this.world.getStaticPointLights())
		{
			STShaders.PointLight_N.loadPointLight(p);
			
			renderTerrain(camera, STShaders.PointLight_N);
		}
		
		GLUtils.toggleBlend(false);
		GLUtils.toggleMultisample(false);
		
		
		msMain.blitAttachment(main, FBO.Target.COLOR0, FBO.Target.COLOR0);
		main.resolveAttachmentToScreen(FBO.Target.COLOR0);
		//PPFilter.SimpleBlend().doPostProcess(this.master.master, main, this.master.master, FBO.Target.COLOR0, FBO.Target.COLOR0);
	}
	
	
	public void renderObject(Camera c, SimpleObjectShader oShader)
	{
		for(StaticEntity e : this.world.getStaticEntities())
		{
			e.getModel().getModel().getVAO().bind(0, 1, 2, 3);
			
			oShader.setInput("material", e.getModel().getTexture());
			oShader.setInput("glow", e.getGlow());
			
			oShader.loadModelMatrix(e.getTransformationMatrix());
			oShader.loadProjectionMatrix(this.T_projection);
			oShader.loadCamera(c);
			
			oShader.use();			
			{
				GL11.glDrawElements(GL11.GL_TRIANGLES, e.getModel().getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			oShader.stop();	
		}
	}
	
	public void renderTerrain(Camera c, SimpleTerrainShader tShader)
	{
		for(Terrain t : this.world.getStaticTerrains())
		{
			t.getModel().getVAO().bind(0, 1, 2, 3);
			
			tShader.setInput("material", t.getTexture());

			tShader.loadModelMatrix(t.getTransformationMatrix());
			tShader.loadProjectionMatrix(this.T_projection);
			tShader.loadCamera(camera);
			
			tShader.use();
			{
				GL11.glDrawElements(GL11.GL_TRIANGLES, t.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);	
			}
			tShader.stop();
		}
	}


}
