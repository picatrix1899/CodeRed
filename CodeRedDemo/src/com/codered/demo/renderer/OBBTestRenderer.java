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
import com.codered.engine.rendering.BBRenderer;
import com.codered.engine.rendering.CustomRenderer;
import com.codered.engine.rendering.MasterRenderer;
import com.codered.engine.rendering.PrimitiveRenderer;
import com.codered.engine.rendering.ppf.PPFilter;
import com.codered.engine.shaders.DBGShaders;
import com.codered.engine.shaders.SOShaders;
import com.codered.engine.shaders.STShaders;
import com.codered.engine.shaders.object.SimpleObjectShader;
import com.codered.engine.shaders.terrain.SimpleTerrainShader;
import com.codered.engine.terrain.Terrain;

import cmn.utilslib.color.colors.LDRColor3;
import cmn.utilslib.math.geometry.OBB3f;
import cmn.utilslib.math.geometry.OBBOBBResolver;
import cmn.utilslib.math.matrix.Matrix3f;
import cmn.utilslib.math.matrix.Matrix4f;
import cmn.utilslib.math.vector.Vector3f;
import cmn.utilslib.math.vector.api.Vec3fBase;

public class OBBTestRenderer implements CustomRenderer
{

	public static OBBTestRenderer instance = new OBBTestRenderer();

	
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
		
	public double projectionScalar(Vec3fBase a, Vec3fBase b)
	{
		return a.dot(b) / a.length();	
	}
	
	
	public void render()
	{
		GLUtils.bindFramebuffer(this.msMain);
		
		DBGShaders.Colored.loadProjectionMatrix(this.T_projection);
		DBGShaders.Colored.loadViewMatrix(this.camera.getViewMatrix());
			
		StaticEntity e = this.world.getStaticEntity(0);
			
		OBB3f obb = e.getModel().getPhysicalMesh().getOBBf(e.getTransformationMatrix(), e.getRotationMatrix());
		OBB3f pla = Session.get().getPlayer().getTransformedAABB().getOBBf();
			
		Matrix3f m = obb.getModelSpaceMatrix();

		Vector3f nX = new Vector3f(m.m0).normalize();
		Vector3f nY = new Vector3f(m.m1).normalize();
		Vector3f nZ = new Vector3f(m.m2).normalize();

		PrimitiveRenderer.drawLine(obb.center.asVector3f(), obb.center.asVector3f().add(nX.mulN(20)), LDRColor3.RED);
		PrimitiveRenderer.drawLine(obb.center.asVector3f(), obb.center.asVector3f().add(nY.mulN(20)), LDRColor3.GREEN);
		PrimitiveRenderer.drawLine(obb.center.asVector3f(), obb.center.asVector3f().add(nZ.mulN(20)), LDRColor3.BLUE);
			
		PrimitiveRenderer.drawLine(Vector3f.ZERO, Vector3f.aX.mulN(30), LDRColor3.RED);
		PrimitiveRenderer.drawLine(Vector3f.ZERO, Vector3f.aY.mulN(30), LDRColor3.GREEN);
		PrimitiveRenderer.drawLine(Vector3f.ZERO, Vector3f.aZ.mulN(30), LDRColor3.BLUE);
			
		BBRenderer.drawOBB3f(pla, Matrix4f.identity(), LDRColor3.ORANGE);
			
		if(OBBOBBResolver.iOBBOBB3f(pla, obb))
		{
			BBRenderer.drawOBB3f(obb, Matrix4f.identity(), LDRColor3.RED);
		}
		else
		{
			BBRenderer.drawOBB3f(obb, Matrix4f.identity(), LDRColor3.BLUE);
		}
		
		SOShaders.AmbientLight.loadAmbientLight(world.getEnviroment().ambient);
	
			renderObject(camera, SOShaders.No);				
			
//			GL11.glEnable(GL11.GL_BLEND);
//			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
//			GL11.glDepthMask(false);
//			GL11.glDepthFunc(GL11.GL_EQUAL);
//			
//			
//			SimpleObjectShader.DirectionalLight_N().loadDirectionalLight(world.getEnviroment().sun);
//			
//			renderObject(camera, SimpleObjectShader.DirectionalLight_N());
//			
//			for(PointLight p : this.world.getStaticPointLights())
//			{
//				SimpleObjectShader.PointLight_N().loadPointLight(p);
//				
//				renderObject(camera, SimpleObjectShader.PointLight_N());
//			}
//			
//			GL11.glDepthFunc(GL11.GL_LESS);
//			GL11.glDepthMask(true);
//			GL11.glDisable(GL11.GL_BLEND);

			STShaders.AmbientLight.loadAmbientLight(world.getEnviroment().ambient);
	
//			renderTerrain(camera, SimpleTerrainShader.No());				
			
//			GL11.glEnable(GL11.GL_BLEND);
//			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
//			GL11.glDepthMask(false);
//			GL11.glDepthFunc(GL11.GL_EQUAL);
//			
//			
//			SimpleTerrainShader.DirectionalLight_N().loadDirectionalLight(world.getEnviroment().sun);
//			
//			renderTerrain(camera, SimpleTerrainShader.DirectionalLight_N());
//			
//			for(PointLight p : this.world.getStaticPointLights())
//			{
//				SimpleTerrainShader.PointLight_N().loadPointLight(p);
//				
//				renderTerrain(camera, SimpleTerrainShader.PointLight_N());
//			}
//			
//			GL11.glDepthFunc(GL11.GL_LESS);
//			GL11.glDepthMask(true);
//			GL11.glDisable(GL11.GL_BLEND);
		
		
		msMain.blitAttachment(main, FBO.Target.COLOR0, FBO.Target.COLOR0);
		PPFilter.SimpleBlend().doPostProcess(this.master.master, main, this.master.master, FBO.Target.COLOR0, FBO.Target.COLOR0);
	}
	
	
	public void renderObject(Camera c, SimpleObjectShader oShader)
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
