package com.codered.demo;

import org.barghos.math.matrix.Mat4;
import org.lwjgl.opengl.GL11;

import com.codered.entities.Camera;
import com.codered.entities.StaticEntity;
import com.codered.entities.StaticModelEntity;
import com.codered.rendering.shader.Shader;
import com.codered.utils.BindingUtils;



public class RenderHelper
{
	
	public static void renderStaticEntity(StaticEntity e, Camera c, Shader oShader, Mat4 projection, double alpha)
	{
		if(e instanceof StaticModelEntity)
		{
			StaticModelEntity entity = (StaticModelEntity)e;
			
			oShader.setUniformValue(0, entity.getTransformationMatrix());
			
			for(com.codered.model.Mesh mesh : entity.getNewModel().getMeshes())
			{
				if(mesh.getMaterial().isPresent())
				{
					oShader.setUniformValue(3, mesh.getMaterial().get());
					oShader.load();
					
					BindingUtils.bindVAO(mesh.getVao(), 0, 1, 2, 3);
					
					GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
				}
			}
		}
		else
		{
			oShader.setUniformValue(0, e.getTransformationMatrix());
			oShader.setUniformValue(1, projection);
			oShader.setUniformValue(2, c, alpha);
			oShader.setUniformValue(3, e.getModel().getMaterial());
			oShader.load();
			
			BindingUtils.bindVAO(e.getModel().getModel().getVao(), 0, 1, 2, 3);
			
			GL11.glDrawElements(GL11.GL_TRIANGLES, e.getModel().getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		}
	}
	
	public static void renderStaticEntityForShadowMap(StaticEntity e, Camera c, Shader oShader, Mat4 projection, double alpha)
	{
		if(e instanceof StaticModelEntity)
		{
			StaticModelEntity entity = (StaticModelEntity)e;
			
			Mat4 mvp = projection.clone();
			mvp.mul(c.getLerpedViewMatrix((float)alpha));
			mvp.mul(entity.getTransformationMatrix());
			
			oShader.setUniformValue(0, mvp);
			
			for(com.codered.model.Mesh mesh : entity.getNewModel().getMeshes())
			{
				if(mesh.getMaterial().isPresent())
				{
					oShader.load();
					
					BindingUtils.bindVAO(mesh.getVao(), 0, 1, 2, 3);
					
					GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
				}
			}
		}
		else
		{
			Mat4 mvp = projection.clone();
			mvp.mul(c.getLerpedViewMatrix((float)alpha));
			mvp.mul(e.getTransformationMatrix());
			
			oShader.setUniformValue(0, mvp);
			oShader.load();
			
			BindingUtils.bindVAO(e.getModel().getModel().getVao(), 0, 1, 2, 3);
			
			GL11.glDrawElements(GL11.GL_TRIANGLES, e.getModel().getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		}
	}
}
