package com.codered.demo;

import org.barghos.math.matrix.Mat4f;

import org.lwjgl.opengl.GL11;

import com.codered.entities.Camera;
import com.codered.entities.StaticEntity;
import com.codered.shader.Shader;
import com.codered.utils.BindingUtils;



public class RenderHelper
{
	
	public static void renderStaticEntity2(StaticEntity e, Camera c, Shader oShader, Mat4f projection, double alpha)
	{
		oShader.setUniformValue(0, e.getTransformationMatrix());
		oShader.setUniformValue(1, projection);
		oShader.setUniformValue(2, c, alpha);
		oShader.setUniformValue(3, e.getModel().getMaterial());
		oShader.load();
		
		BindingUtils.bindVAO(e.getModel().getModel().getVAO(), 0, 1, 2, 3);
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, e.getModel().getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
	}
}
