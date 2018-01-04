package com.codered.demo.renderer;

import org.lwjgl.opengl.GL11;

import com.codered.demo.Session;
import com.codered.engine.GLUtils;
import com.codered.engine.entities.Camera;
import com.codered.engine.entities.StaticEntity;
import com.codered.engine.managing.FBO;
import com.codered.engine.managing.MSFBO;
import com.codered.engine.managing.Window;
import com.codered.engine.managing.World;
import com.codered.engine.rendering.CustomRenderer;
import com.codered.engine.rendering.MasterRenderer;
import com.codered.engine.rendering.ppf.PPFilter;
import com.codered.engine.shaders.SOShaders;
import com.codered.engine.shaders.object.SimpleObjectShader;
import com.codered.engine.shaders.terrain.SimpleTerrainShader;
import com.codered.engine.terrain.Terrain;

import cmn.utilslib.math.matrix.Matrix4f;

public class BloomRenderer implements CustomRenderer
{

	public static BloomRenderer instance = new BloomRenderer();
	
	private MasterRenderer master;
	private Camera camera;
	private World world;
	private Matrix4f T_projection;
	
	private MSFBO msBloom;
	private FBO bloom;
	
	public void init(MasterRenderer master)
	{
		this.master = master;
		
		msBloom = new MSFBO(Window.active.WIDTH, Window.active.HEIGHT, 3);
		msBloom.applyColorAttachment(FBO.Target.COLOR0, true);
		msBloom.applyDepthBufferAttachment();
		
		bloom = new FBO(Window.active.WIDTH, Window.active.HEIGHT);
		bloom.applyColorAttachment(FBO.Target.COLOR0, true);
		bloom.applyDepthBufferAttachment();
	}

	public void prepare()
	{
		this.T_projection = Window.active.projectionMatrix;
		this.camera = Session.get().getPlayer().getCamera();
		this.world = Session.get().getWorld();
		
		msBloom.clearAttachment(FBO.Target.COLOR0);
		bloom.clearAttachment(FBO.Target.COLOR0);
	}

	public void render()
	{
		GLUtils.bindFramebuffer(this.msBloom);
		
		renderObjects(Session.get().getPlayer().getCamera(), SOShaders.Glow);
		
		msBloom.blitAttachment(bloom, FBO.Target.COLOR0, FBO.Target.COLOR0);
		
		PPFilter.RadialBlur().setAmplitude(0.1f).setCycles(20).setDelta(0.994f);
		PPFilter.RadialBlur().doPostProcess(bloom, FBO.Target.COLOR0, this.master.master, FBO.Target.COLOR0, true);	
	}
	
	public void renderBoth(Camera c, SimpleObjectShader oShader, SimpleTerrainShader tShader)
	{
		renderObjects(c, oShader);
		renderTerrain(c, tShader);
	}
	
	public void renderObjects(Camera c, SimpleObjectShader oShader)
	{
		for(StaticEntity e : this.world.getStaticEntities())
		{
			GLUtils.bindVAO(e.getModel().getModel().getVAO(), 0, 1, 2, 3);
			
			
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
