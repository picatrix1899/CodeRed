package com.codered.demo;

import java.util.Iterator;
import java.util.List;

import org.barghos.math.matrix.Mat4;
import org.lwjgl.opengl.GL11;

import com.codered.entities.Camera;
import com.codered.entities.StaticEntity;
import com.codered.entities.StaticModelEntity;
import com.codered.model.Mesh;
import com.codered.model.Model;
import com.codered.rendering.light.AmbientLight;
import com.codered.rendering.light.DirectionalLight;
import com.codered.rendering.shader.Shader;
import com.codered.rendering.shader.ShaderProgram;
import com.codered.rendering.shader.ShaderSession;
import com.codered.utils.BindingUtils;



public class RenderHelper
{
	public static ShaderProgram directionalLightShader;
	public static ShaderProgram ambientLightShader;
	public static ShaderProgram deferredShader;
	public static ShaderProgram shadowShader;
	
	public static void renderAmbientLight(Model model, List<StaticModelEntity> entities, Camera cam, Mat4 projection,
			AmbientLight light, double alpha)
	{
		try(ShaderSession ss = ambientLightShader.start())
		{
			ambientLightShader.setUniformValue(4, light.base.color);
			ambientLightShader.setUniformValue(5, light.base.intensity);
			ambientLightShader.setUniformValue(1, projection);
			ambientLightShader.setUniformValue(2, cam, alpha);

			for(Mesh mesh : model.getMeshes())
			{	
				if(mesh.getMaterial().isPresent())
				{
					ambientLightShader.setUniformValue(3, mesh.getMaterial().get());
					
					BindingUtils.bindVAO(mesh.getVao(), 0, 1, 2, 3);
					
					for(StaticModelEntity entity : entities)
					{
						ambientLightShader.setUniformValue(0, entity.getTransformationMatrix());
						
						GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
					}
				}
			}
		}
	}
	
	public static void renderDirectionalLight(Model model, List<StaticModelEntity> entities, Camera cam, Mat4 projection,
			DirectionalLight light, double alpha, Mat4 lightSpace, int shadowMap)
	{
		try(ShaderSession ss = directionalLightShader.start())
		{
			directionalLightShader.setUniformValue(4, light.base.color);
			directionalLightShader.setUniformValue(5, light.base.intensity);
			directionalLightShader.setUniformValue(6, light.direction);
			directionalLightShader.setUniformValue(1, projection);
			directionalLightShader.setUniformValue(2, cam, alpha);
			directionalLightShader.setUniformValue(7, lightSpace);
			directionalLightShader.setUniformValue(8, shadowMap);
			for(Mesh mesh : model.getMeshes())
			{	
				if(mesh.getMaterial().isPresent())
				{
					directionalLightShader.setUniformValue(3, mesh.getMaterial().get());
					
					BindingUtils.bindVAO(mesh.getVao(), 0, 1, 2, 3);
					
					for(StaticModelEntity entity : entities)
					{
						directionalLightShader.setUniformValue(0, entity.getTransformationMatrix());
						
						GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
					}
				}
			}
		}
	}
	
	public static void renderDeferred(Model model, List<StaticModelEntity> entities, Camera cam, Mat4 projection, double alpha)
	{
		try(ShaderSession ss = deferredShader.start())
		{
			deferredShader.setUniformValue(1, projection);
			deferredShader.setUniformValue(2, cam, alpha);

			for(Mesh mesh : model.getMeshes())
			{	
				if(mesh.getMaterial().isPresent())
				{
					deferredShader.setUniformValue(3, mesh.getMaterial().get());
					
					BindingUtils.bindVAO(mesh.getVao(), 0, 1, 2, 3);
					
					for(StaticModelEntity entity : entities)
					{
						deferredShader.setUniformValue(0, entity.getTransformationMatrix());
						
						GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
					}
				}
			}
		}
	}
	
	
	public static void renderShadowMap(Model model, List<StaticModelEntity> entities, Mat4 projection, double alpha)
	{
		Mat4 mvp = projection.clone();
		
		try(ShaderSession ss = shadowShader.start())
		{
			for(Mesh mesh : model.getMeshes())
			{	
				if(mesh.getMaterial().isPresent())
				{
					BindingUtils.bindVAO(mesh.getVao(), 0, 1, 2, 3);
					
					for(StaticModelEntity entity : entities)
					{
						shadowShader.setUniformValue(0, mvp.mul(entity.getTransformationMatrix(), null));
						
						GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
					}
				}
			}
		}
	}
}
