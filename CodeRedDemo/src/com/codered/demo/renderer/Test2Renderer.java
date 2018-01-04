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
import com.codered.engine.rendering.deref.Deref;
import com.codered.engine.shaders.SOShaders;
import com.codered.engine.shaders.object.SimpleObjectShader;
import com.codered.engine.shaders.terrain.SimpleTerrainShader;
import com.codered.engine.terrain.Terrain;

import cmn.utilslib.math.matrix.Matrix4f;

public class Test2Renderer implements CustomRenderer
{

	public static Test2Renderer instance = new Test2Renderer();


	private MasterRenderer master;
	private Camera camera;
	private World world;
	private Matrix4f T_projection;
	
	private MSFBO deref;
	private FBO src;

	public void init(MasterRenderer master)
	{
		this.master = master;
		
		deref = new MSFBO(Window.active.WIDTH, Window.active.HEIGHT, 3);
		deref.applyColorAttachment(FBO.Target.COLOR0, true);
		deref.applyColorAttachment(FBO.Target.COLOR1, true);
		deref.applyColorAttachment(FBO.Target.COLOR2, true);
		deref.applyColorAttachment(FBO.Target.COLOR3, true);
		deref.applyColorAttachment(FBO.Target.COLOR4, true);
		deref.applyDepthBufferAttachment();
		
		src = new FBO(deref.getWidth(), deref.getHeight());
		src.applyColorAttachment(FBO.Target.COLOR0, true);
		src.applyColorAttachment(FBO.Target.COLOR1, true);
		src.applyColorAttachment(FBO.Target.COLOR2, true);
		src.applyColorAttachment(FBO.Target.COLOR3, true);
		src.applyColorAttachment(FBO.Target.COLOR4, true);
		src.applyColorAttachment(FBO.Target.COLOR5, true);
		src.applyColorAttachment(FBO.Target.COLOR6, true);
		src.applyColorAttachment(FBO.Target.COLOR7, true);
		src.applyDepthBufferAttachment();
	}
	
	public void prepare()
	{
		this.T_projection = Window.active.projectionMatrix;
		this.camera = Session.get().getPlayer().getCamera();
		this.world = Session.get().getWorld();
		
		FBO.updateDraws();
		
		deref.clearAllAttachments();
		src.clearAllAttachments();
	}
	
	public void render()
	{
		GLUtils.bindFramebuffer(this.deref);
		
		renderObjects(this.camera, SOShaders.Deref);
		//renderTerrain(this.camera, SimpleTerrainShader.Deref());
		
		deref.blitAttachment(src, FBO.Target.COLOR0, FBO.Target.COLOR0);
		deref.blitAttachment(src, FBO.Target.COLOR1, FBO.Target.COLOR1);
		deref.blitAttachment(src, FBO.Target.COLOR2, FBO.Target.COLOR2);
		deref.blitAttachment(src, FBO.Target.COLOR3, FBO.Target.COLOR3);
		deref.blitAttachment(src, FBO.Target.COLOR4, FBO.Target.COLOR4);

		Deref.No().doPostProcess(src, FBO.Target.COLOR0, this.master.master, FBO.Target.COLOR0, true);
	}
	
	
	public void renderObjects(Camera c, SimpleObjectShader oShader)
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
