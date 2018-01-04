package com.codered.engine.rendering;

import org.lwjgl.opengl.GL11;

import com.codered.engine.GLUtils;
import com.codered.engine.entities.Camera;
import com.codered.engine.light.PointLight;
import com.codered.engine.managing.Window;
import com.codered.engine.managing.World;
import com.codered.engine.shaders.STShaders;
import com.codered.engine.shaders.terrain.SimpleTerrainShader;
import com.codered.engine.terrain.Terrain;

import cmn.utilslib.math.matrix.Matrix4f;

public class DefaultTerrainRenderer implements TerrainRenderer
{
	private Camera camera;
	private World world;	
	private Matrix4f T_projection;
	
	public static final DefaultTerrainRenderer instance = new DefaultTerrainRenderer();
	
	public void init(Terrain t, World w, Camera c)
	{
		this.T_projection = Window.active.projectionMatrix;
		this.camera = c;
		this.world = w;
	}

	public void render(Terrain t)
	{
		
		GLUtils.toggleDepthTest(true);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		
		STShaders.AmbientLight.loadAmbientLight(world.getEnviroment().ambient);
		renderTerrain(t, camera, STShaders.AmbientLight);				
		
		
		GLUtils.toggleBlend(true);
		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);		
		
		STShaders.DirectionalLight_N.loadDirectionalLight(world.getEnviroment().sun);
		renderTerrain(t, camera, STShaders.DirectionalLight_N);
		
		
		for(PointLight p : this.world.getStaticPointLights())
		{
			STShaders.PointLight_N.loadPointLight(p);
			
			renderTerrain(t, camera, STShaders.PointLight_N);
		}
		
		GLUtils.toggleBlend(false);
		GLUtils.toggleDepthTest(false);
	}

	public void renderTerrain(Terrain t, Camera c, SimpleTerrainShader tShader)
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
