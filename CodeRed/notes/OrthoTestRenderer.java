//package com.codered.demo.renderer;
//
//
//import org.lwjgl.opengl.GL11;
//
//import com.codered.demo.Session;
//import com.codered.engine.GLUtils;
//import com.codered.engine.entities.Camera;
//import com.codered.engine.entities.StaticEntity;
//import com.codered.engine.light.AmbientLight;
//import com.codered.engine.light.DirectionalLight;
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
//import cmn.utilslib.color.colors.LDRColor3;
//import cmn.utilslib.math.matrix.Matrix4f;
//import cmn.utilslib.math.vector.Vector3f;
//
//public class OrthoTestRenderer implements CustomRenderer
//{
//
//	public static OrthoTestRenderer instance = new OrthoTestRenderer();
//	
//
//
//	public DirectionalLight sun = new DirectionalLight(new LDRColor3(128, 102, 0), 0.5f, new Vector3f(-1.0f, -1.0f, 0.0f));
//	public AmbientLight ambient = new AmbientLight(new LDRColor3(255,76,76), 0.5f);
//
//	
//	
//	private MasterRenderer master;	
//	private Camera camera;
//	private World world;	
//	private Matrix4f T_projection;
//
//	private MSFBO msMain;
//	private FBO main;	
//	
//	public void init(MasterRenderer master)
//	{
//		this.master = master;
//		this.T_projection = new Matrix4f().initOrtho(-100f, 100.0f, -100f, 100.0f, 1f, 140f);
//		//this.T_projection = WindowManager.active.projectionMatrix;
//		
//		this.camera = Session.get().getPlayer().getCamera();
//		this.world = Session.get().getWorld();
//		
//		msMain = new MSFBO(Window.active.WIDTH, Window.active.HEIGHT, 3);
//		msMain.applyColorAttachment(FBO.Target.COLOR0, true);
//		msMain.applyDepthBufferAttachment();
//		
//		main = new FBO(Window.active.WIDTH, Window.active.HEIGHT);
//		main.applyColorAttachment(FBO.Target.COLOR0, true);
//		main.applyDepthBufferAttachment();
//		
//		this.camera = new Camera();
//		this.camera.getTotalPos().set( new Vector3f(-30, 0, -40));
//		//StaticEntity e = Session.get().getWorld().getStaticEntity(2);
//		//Vec3f v = e.getTransformedAABB().center.subN(camera.getTotalPos());
//	}
//	
//	public void prepare()
//	{
//		this.T_projection = new Matrix4f().initOrtho(-0f, 10f, -0f, 10f, 0.01f, 1000f);
//		this.camera = Session.get().getPlayer().getCamera();
//		this.world = Session.get().getWorld();
//		
//		FBO.updateDraws();
//		
//		msMain.clearAttachment(FBO.Target.COLOR0);
//		main.clearAttachment(FBO.Target.COLOR0);
//	}
//		
//	
//	public void render()
//	{
//		GLUtils.bindFramebuffer(this.msMain);
//		
//		SOShaders.AmbientLight.loadAmbientLight(ambient);
//		STShaders.AmbientLight.loadAmbientLight(ambient);
//	
//		renderBoth(camera, SOShaders.No, STShaders.No);				
//		
//		msMain.blitAttachment(main, FBO.Target.COLOR0, FBO.Target.COLOR0);
//		
//		PPFilter.SimpleBlend().doPostProcess(this.master.master, main, this.master.master, FBO.Target.COLOR0, FBO.Target.COLOR0);
//	}
//	
//	public void renderBoth(Camera c, SimpleObjectShader oShader, SimpleTerrainShader tShader)
//	{
//		renderObjects(c, oShader);
//		renderTerrain(c, tShader);
//	}
//	
//	public void renderObjects(Camera c, SimpleObjectShader oShader)
//	{
//		for(StaticEntity e : this.world.getStaticEntities())
//		{
//			GLUtils.bindVAO(e.getModel().getModel().getVAO(), 0, 1, 2, 3);
//			
//			
//			oShader.setInput("material", e.getModel().getTexture());
//			oShader.setInput("glow", e.getGlow());
//			
//			oShader.loadModelMatrix(e.getTransformationMatrix());
//			oShader.loadProjectionMatrix(this.T_projection);
//			oShader.loadCamera(c);
//			
//			oShader.use();			
//			{
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
