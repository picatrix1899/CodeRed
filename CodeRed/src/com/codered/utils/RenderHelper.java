package com.codered.utils;

import org.lwjgl.opengl.GL11;

import com.codered.entities.Camera;
import com.codered.entities.StaticEntity;
import com.codered.gui.elements.GUIText;
import com.codered.managing.VAO;
import com.codered.primitives.TexturedQuad;
import com.codered.shaders.gui.Font_GUIShader;
import com.codered.shaders.object.simple.TexturedObjectShader;
import com.codered.window.WindowContext;

import cmn.utilslib.math.matrix.Matrix4f;
import cmn.utilslib.math.vector.Vector3f;

public class RenderHelper
{
	public static void renderGuiTextDefault(GUIText text, VAO vao, int vertexCount)
	{
		WindowContext context = WindowContextHelper.getCurrentContext();
		
		GLUtils.blend(true);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GLUtils.depthTest(false);
		
		Font_GUIShader shader = context.getShader(Font_GUIShader.class);
		
		shader.loadAtlas(text.font.getTexture().getId());
		
		BindingUtils.bindVAO(vao, 0, 1);
		
		shader.loadColor(text.color);
		shader.loadScreenSpace(context.getSize());
		shader.use();
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCount);
		shader.stop();

		GLUtils.blend(false);
		GLUtils.depthTest(true);
	}
	
	public static void renderStaticEntity(StaticEntity e, Camera c, TexturedObjectShader oShader, Matrix4f projection)
	{
		oShader.u_camera.set(c);
		oShader.u_T_model.set(e.getTransformationMatrix());
		oShader.u_T_projection.set(projection);
		oShader.u_material.set(e.getModel().getMaterial());
		
		oShader.use();			
		{	
			BindingUtils.bindVAO(e.getModel().getModel().getVAO(), 0, 1, 2, 3);
			
			GL11.glDrawElements(GL11.GL_TRIANGLES, e.getModel().getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		}
		oShader.stop();	
	}
	
	public static void renderStaticEntity(StaticEntity e, Matrix4f c, Vector3f pos, TexturedObjectShader oShader, Matrix4f projection)
	{
		oShader.u_camera.set(c, pos);
		oShader.u_T_model.set(e.getTransformationMatrix());
		oShader.u_T_projection.set(projection);
		oShader.u_material.set(e.getModel().getMaterial());
		
		oShader.use();			
		{	
			BindingUtils.bindVAO(e.getModel().getModel().getVAO(), 0, 1, 2, 3);
			
			GL11.glDrawElements(GL11.GL_TRIANGLES, e.getModel().getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		}
		oShader.stop();	
	}
	
	public static void renderTexturedQuad(TexturedQuad e, Camera c, TexturedObjectShader oShader, Matrix4f projection)
	{
		oShader.u_camera.set(c);
		oShader.u_T_model.set(e.getTransformationMatrix());
		oShader.u_T_projection.set(projection);
		oShader.u_material.set(e.getMaterial());
		
		oShader.use();			
		{	
			BindingUtils.bindVAO(e.getVao(), 0, 1, 2, 3);
			
			GL11.glDrawElements(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_INT, 0);
		}
		oShader.stop();	
	}
}
