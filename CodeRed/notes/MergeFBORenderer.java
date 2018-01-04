//package com.codered.demo.renderer;
//
//
//import org.lwjgl.opengl.GL11;
//import org.lwjgl.opengl.GL12;
//import org.lwjgl.opengl.GL13;
//import org.lwjgl.opengl.GL15;
//import org.lwjgl.opengl.GL32;
//
//import com.codered.demo.Session;
//import com.codered.engine.GLUtils;
//import com.codered.engine.entities.Camera;
//import com.codered.engine.entities.StaticEntity;
//import com.codered.engine.light.PointLight;
//import com.codered.engine.managing.FBO;
//import com.codered.engine.managing.MSFBO;
//import com.codered.engine.managing.Window;
//import com.codered.engine.managing.World;
//import com.codered.engine.rendering.CustomRenderer;
//import com.codered.engine.rendering.MasterRenderer;
//import com.codered.engine.rendering.ppf.PPFilter;
//import com.codered.engine.shaders.SOShaders;
//import com.codered.engine.shaders.STShaders;
//import com.codered.engine.shaders.object.SimpleObjectShader;
//import com.codered.engine.shaders.terrain.SimpleTerrainShader;
//import com.codered.engine.terrain.Terrain;
//
//import cmn.utilslib.math.matrix.Matrix4f;
//import cmn.utilslib.math.vector.api.Vec3fBase;
//
//public class MergeFBORenderer implements CustomRenderer
//{
//
//	public static MergeFBORenderer instance = new MergeFBORenderer();
//
//	
//	private MasterRenderer master;	
//	private Camera camera;
//	private World world;	
//	private Matrix4f T_projection;
//
//	private MSFBO msMain;
//	private FBO mainA;
//	private FBO mainB;
//	private FBO out;
//	
//	public void init(MasterRenderer master)
//	{
//		this.master = master;
//		
//		msMain = new MSFBO(Window.active.WIDTH, Window.active.HEIGHT, 3);
//		msMain.applyColorAttachment(FBO.Target.COLOR0, true);
//		msMain.applyDepthBufferAttachment();
//		
//		mainA = new FBO(Window.active.WIDTH, Window.active.HEIGHT);
//		mainA.applyColorAttachment(FBO.Target.COLOR0, true);
//		mainA.applyDepthColorAttachment();
//		
//		mainB = new FBO(Window.active.WIDTH, Window.active.HEIGHT);
//		mainB.applyColorAttachment(FBO.Target.COLOR0, true);
//		mainB.applyDepthColorAttachment();
//		
//		out = new FBO(Window.active.WIDTH, Window.active.HEIGHT);
//		out.applyColorAttachment(FBO.Target.COLOR0, true);
//		out.applyDepthColorAttachment();
//	}
//	
//	public void prepare()
//	{
//		this.T_projection = Window.active.projectionMatrix;
//		this.camera = Session.get().getPlayer().getCamera();
//		this.world = Session.get().getWorld();
//		
//		FBO.updateDraws();
//		
//		msMain.clearAttachment(FBO.Target.COLOR0);
//		mainA.clearAttachment(FBO.Target.COLOR0);
//		mainB.clearAttachment(FBO.Target.COLOR0);
//		out.clearAllAttachments();
//		
//		StaticEntity e = this.world.getStaticEntity(0);
//		
//		e.rotateYaw(0.2f);
//		
//	}
//	
//	
//	public void render()
//	{
//		GLUtils.bindFramebuffer(msMain);
//		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
//
//		GLUtils.toggleDepthTest(true);
//		GLUtils.toggleMultisample(true);
//		
//		renderObject(camera, SOShaders.No);
//		
//		GLUtils.toggleMultisample(false);
//		GLUtils.toggleDepthTest(false);
//		
//		msMain.blitAttachment(mainA, FBO.Target.COLOR0, FBO.Target.COLOR0);
//		
//		msMain.clearAllAttachments();
//		
//		GLUtils.toggleDepthTest(true);
//		GLUtils.toggleMultisample(true);
//		
//		renderTerrain(camera, STShaders.No);
//		
//		GLUtils.toggleMultisample(false);
//		GLUtils.toggleDepthTest(false);
//		
//		msMain.blitAttachment(mainB, FBO.Target.COLOR0, FBO.Target.COLOR0);
//		
//		GLUtils.toggleDepthTest(true);
//		
//		PPFilter.DepthTest().doPostProcess(mainA, FBO.Target.COLOR0, mainB, FBO.Target.COLOR0, out, FBO.Target.COLOR0, false);
//	
//		GLUtils.toggleDepthTest(false);
//		
//		//PPFilter.Contrast().setContrast(2.0f);
//		
//		//PPFilter.Contrast().doPostProcess(mainA, FBO.Target.COLOR0, mainA, FBO.Target.COLOR0, false);
//		
//		PPFilter.DepthTest().getFBO().resolveAttachmentToScreen(FBO.Target.COLOR0);
//		
//		//mainA.resolveAttachmentToScreen(FBO.Target.COLOR0);
//	}
//	
//	
//	public void renderObject(Camera c, SimpleObjectShader oShader)
//	{
//		for(StaticEntity e : this.world.getStaticEntities())
//		{
//			oShader.setInput("material", e.getModel().getTexture());
//			oShader.setInput("glow", e.getGlow());
//			
//			oShader.loadModelMatrix(e.getTransformationMatrix());
//			oShader.loadProjectionMatrix(this.T_projection);
//			oShader.loadCamera(c);
//			
//			oShader.use();			
//			{	
//				GLUtils.bindVAO(e.getModel().getModel().getVAO(), 0, 1, 2, 3);
//				
//				GL11.glDrawElements(GL11.GL_TRIANGLES, e.getModel().getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
//			}
//			oShader.stop();	
//		}
//	}
//	
//	public void renderTerrain(Camera c, SimpleTerrainShader tShader)
//	{
//		for(Terrain t : this.world.getStaticTerrains())
//		{
//			GLUtils.bindVAO(t.getModel().getVAO(), 0, 1, 2, 3);
//			
//			tShader.setInput("material", t.getTexture());
//
//			tShader.loadModelMatrix(t.getTransformationMatrix());
//			tShader.loadProjectionMatrix(this.T_projection);
//			tShader.loadCamera(camera);
//			
//			tShader.use();
//			{
//				GL11.glDrawElements(GL11.GL_TRIANGLES, t.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);	
//			}
//			tShader.stop();
//		}
//	}
//
//
//}
