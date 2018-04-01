package com.codered.engine.rendering;

import org.lwjgl.opengl.GL11;

import com.codered.engine.GLUtils;
import com.codered.engine.entities.Camera;
import com.codered.engine.entities.StaticEntity;
import com.codered.engine.light.PointLight;
import com.codered.engine.managing.Window;
import com.codered.engine.managing.World;
import com.codered.engine.shaders.object.SimpleObjectShader;

import cmn.utilslib.color.colors.LDRColor3;
import cmn.utilslib.math.matrix.Matrix4f;

public class DefaultEntityRenderer implements EntityRenderer
{
	private Camera camera;
	private World world;	
	private Matrix4f T_projection;
	
	public static final DefaultEntityRenderer instance = new DefaultEntityRenderer();
	
	public void init(StaticEntity e, World w, Camera c)
	{
		this.T_projection = Window.active.projectionMatrix;
		this.camera = c;
		this.world = w;
	}

	public void render(StaticEntity e)
	{
		
		GLUtils.toggleDepthTest(true);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		
		Window.active.getContext().soShaders.AmbientLight.loadAmbientLight(world.getEnviroment().ambient);
		renderObject(e, camera, Window.active.getContext().soShaders.AmbientLight);				
		
		
		GLUtils.toggleBlend(true);
		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);		
		
		Window.active.getContext().soShaders.DirectionalLight_N.loadDirectionalLight(world.getEnviroment().sun);
		renderObject(e, camera, Window.active.getContext().soShaders.DirectionalLight_N);
		
		
		for(PointLight p : this.world.getStaticPointLights())
		{
			Window.active.getContext().soShaders.PointLight_N.loadPointLight(p);
			
			renderObject(e, camera, Window.active.getContext().soShaders.PointLight_N);
		}
		
		if(e.highlighted)
		{
			Window.active.getContext().soShaders.Colored.loadViewMatrix(this.camera.getViewMatrix());
			Window.active.getContext().soShaders.Colored.loadProjectionMatrix(this.T_projection);
			
			BBRenderer.drawOBB3f(e.getModel().getPhysicalMesh().getOBBf(e.getTransformationMatrix(), e.getRotationMatrix()), LDRColor3.GREEN);
		}

		GLUtils.toggleBlend(false);
		GLUtils.toggleDepthTest(false);
	}

	public void renderObject(StaticEntity e, Camera c, SimpleObjectShader oShader)
	{
			oShader.setInput("material", e.getModel().getTexture());
			oShader.setInput("glow", e.getGlow());
			
			oShader.loadModelMatrix(e.getTransformationMatrix());
			oShader.loadProjectionMatrix(this.T_projection);
			oShader.loadCamera(c);
			
			oShader.use();			
			{	
				GLUtils.bindVAO(e.getModel().getModel().getVAO(), 0, 1, 2, 3);
				
				GL11.glDrawElements(GL11.GL_TRIANGLES, e.getModel().getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			oShader.stop();	
	}
	
}
