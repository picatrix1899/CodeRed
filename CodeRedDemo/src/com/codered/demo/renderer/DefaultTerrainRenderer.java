package com.codered.demo.renderer;


import org.lwjgl.opengl.GL11;

import com.codered.demo.Session;
import com.codered.engine.GLUtils;
import com.codered.engine.entities.Camera;
import com.codered.engine.light.PointLight;
import com.codered.engine.managing.FBO;
import com.codered.engine.managing.MSFBO;
import com.codered.engine.managing.Window;
import com.codered.engine.managing.World;
import com.codered.engine.rendering.CustomRenderer;
import com.codered.engine.rendering.MasterRenderer;
import com.codered.engine.rendering.ppf.Blend;
import com.codered.engine.rendering.ppf.PPFilter;
import com.codered.engine.shaders.STShaders;
import com.codered.engine.shaders.terrain.SimpleTerrainShader;
import com.codered.engine.terrain.Terrain;

import cmn.utilslib.math.matrix.Matrix4f;

public class DefaultTerrainRenderer implements CustomRenderer
{

	public static DefaultTerrainRenderer instance = new DefaultTerrainRenderer();

	
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
	}
		
	
	public void render()
	{
		GLUtils.bindFramebuffer(this.msMain);
		
		STShaders.AmbientLight.loadAmbientLight(world.getEnviroment().ambient);
	
		renderTerrain(camera, STShaders.AmbientLight);				
			
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
		GL11.glDepthMask(false);
		GL11.glDepthFunc(GL11.GL_EQUAL);
			
			
		STShaders.DirectionalLight_N.loadDirectionalLight(world.getEnviroment().sun);
			
		renderTerrain(camera, STShaders.DirectionalLight_N);
			
		for(PointLight p : this.world.getStaticPointLights())
		{
			STShaders.PointLight_N.loadPointLight(p);
				
			renderTerrain(camera, STShaders.PointLight_N);
		}
			
		GL11.glDepthFunc(GL11.GL_LESS);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		
		msMain.blitAttachment(main, FBO.Target.COLOR0, FBO.Target.COLOR0);
		PPFilter.SimpleBlend().setBlendFunc(Blend.ONE, Blend.ONE);
		PPFilter.SimpleBlend().doPostProcess(this.master.master, main, this.master.master, FBO.Target.COLOR0, FBO.Target.COLOR0);
	}
	
	
	public void renderTerrain(Camera c, SimpleTerrainShader tShader)
	{
		for(Terrain t : this.world.getStaticTerrains())
		{
			GLUtils.bindVAO(t.getModel().getVAO(), 0, 1, 2, 3);
			
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
