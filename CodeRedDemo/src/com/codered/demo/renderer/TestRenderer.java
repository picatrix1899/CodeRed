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
import cmn.utilslib.math.vector.api.Vec3fBase;

public class TestRenderer implements CustomRenderer
{
	public static TestRenderer instance = new TestRenderer();

	
	private MasterRenderer master;	
	private Camera camera;
	private World world;	
	private Matrix4f T_projection;

	private MSFBO msMain;
	private FBO main;
	private FBO sec;
	private FBO out;
	private FBO out2;
	
	public void init(MasterRenderer master)
	{
		this.master = master;
		
		msMain = new MSFBO(Window.active.WIDTH, Window.active.HEIGHT, 3);
		msMain.applyColorAttachment(FBO.Target.COLOR0, true);
		msMain.applyDepthBufferAttachment();
		
		main = new FBO(Window.active.WIDTH, Window.active.HEIGHT);
		main.applyColorAttachment(FBO.Target.COLOR0, true);
		main.applyDepthColorAttachment();
		
		sec = new FBO(Window.active.WIDTH, Window.active.HEIGHT);
		sec.applyColorAttachment(FBO.Target.COLOR0, true);
		sec.applyDepthColorAttachment();
		
		out = new FBO(Window.active.WIDTH, Window.active.HEIGHT);
		out.applyColorAttachment(FBO.Target.COLOR0, true);
		out.applyColorAttachment(FBO.Target.COLOR1, true);
		out.applyDepthColorAttachment();
		
		out2 = new FBO(Window.active.WIDTH, Window.active.HEIGHT);
		out2.applyColorAttachment(FBO.Target.COLOR0, true);
		out2.applyDepthColorAttachment();
	}
	
	public void prepare()
	{
		this.T_projection = Window.active.projectionMatrix;
		this.camera = Session.get().getPlayer().getCamera();
		this.world = Session.get().getWorld();
		
		FBO.updateDraws();
		
		msMain.clearAllAttachments();
		main.clearAllAttachments();
		sec.clearAllAttachments();
		
		StaticEntity e = this.world.getStaticEntity(0);
		
		e.rotateYaw(0.2f);
		
	}
		
	public double projectionScalar(Vec3fBase a, Vec3fBase b)
	{
		return a.dot(b) / a.length();	
	}
	
	
	public void render()
	{
		GLUtils.bindFramebuffer(this.msMain);
		
		renderObject(camera, world.getStaticEntity(0), SOShaders.No);				
		
		this.msMain.blitAttachment(sec, FBO.Target.COLOR0, FBO.Target.COLOR0);
		
		
		msMain.clearAllAttachments();
		
		
		GLUtils.bindFramebuffer(this.msMain);
		
		renderObject(camera, world.getStaticEntity(12), SOShaders.No);					

		this.msMain.blitAttachment(main, FBO.Target.COLOR0, FBO.Target.COLOR0);

			
		PPFilter.DepthTest().doPostProcess(sec, FBO.Target.DEPTH, main, FBO.Target.DEPTH, out, FBO.Target.COLOR0, false);

		PPFilter.DepthMap().doPostProcess(out, FBO.Target.DEPTH, out, FBO.Target.COLOR0, false);
		out.blitAttachment(master.master, FBO.Target.COLOR0, FBO.Target.COLOR0, true);
	}
	
	public void renderObject(Camera c, StaticEntity e, SimpleObjectShader oShader)
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
