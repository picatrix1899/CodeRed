package com.codered.utils;

import org.barghos.math.matrix.Mat4f;
import org.lwjgl.opengl.GL11;

import com.codered.engine.EngineRegistry;
import com.codered.entities.Camera;
import com.codered.entities.StaticEntity;
import com.codered.gui.elements.GUIText;
import com.codered.managing.VAO;
import com.codered.primitives.TexturedQuad;
import com.codered.sh.Shader;
import com.codered.shaders.gui.Font_GUIShader;
import com.codered.shaders.object.simple.TexturedObjectShader;
import com.codered.window.WindowContext;

import cmn.utilslib.math.matrix.Matrix4f;
import cmn.utilslib.math.vector.Vector3f;

public class RenderHelper
{
	public static void renderGuiTextDefault(GUIText text, VAO vao, int vertexCount)
	{
		WindowContext context = EngineRegistry.getCurrentWindowContext();
		
		GLUtils.blend(true);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GLUtils.depthTest(false);
		

		
		Font_GUIShader shader = EngineRegistry.getShader(Font_GUIShader.class);
		shader.start();
		shader.u_fontAtlas.set(text.font.getTexture());
		shader.u_color.set(text.color);
		shader.u_screenSpace.set(context.getWindow().getSize());
		
		BindingUtils.bindVAO(vao, 0, 1);
		
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCount);
		
		shader.stop();

		GLUtils.blend(false);
		GLUtils.depthTest(true);
	}
	
	public static void renderStaticEntity(StaticEntity e, Camera c, TexturedObjectShader oShader, Mat4f projection)
	{
		oShader.u_camera.set(c);
		oShader.u_T_model.set(e.getTransformationMatrix());
		oShader.u_T_projection.set(projection);
		oShader.u_material.set(e.getModel().getMaterial());

		BindingUtils.bindVAO(e.getModel().getModel().getVAO(), 0, 1, 2, 3);
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, e.getModel().getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
	}
	
	public static void renderStaticEntity2(StaticEntity e, Camera c, Shader oShader, Mat4f projection)
	{
		oShader.setUniformValue("T_model", e.getTransformationMatrix());
		oShader.setUniformValue("T_projection", projection);
		oShader.setUniformValue("camera.T_view", c.getViewMatrix());
		oShader.setUniformValue("material.albedoMap", e.getModel().getMaterial().getAlbedoMap().getId());

		oShader.load();
		
		BindingUtils.bindVAO(e.getModel().getModel().getVAO(), 0, 1, 2, 3);
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, e.getModel().getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
	}
	
	public static void renderStaticEntity(StaticEntity e, Matrix4f c, Vector3f pos, TexturedObjectShader oShader, Mat4f projection)
	{
		oShader.u_camera.set(c, pos);
		oShader.u_T_model.set(e.getTransformationMatrix());
		oShader.u_T_projection.set(projection);
		oShader.u_material.set(e.getModel().getMaterial());

		BindingUtils.bindVAO(e.getModel().getModel().getVAO(), 0, 1, 2, 3);
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, e.getModel().getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
	}
	
	public static void renderTexturedQuad(TexturedQuad e, Camera c, TexturedObjectShader oShader, Mat4f projection)
	{
		oShader.u_camera.set(c);
		oShader.u_T_model.set(e.getTransformationMatrix());
		oShader.u_T_projection.set(projection);
		oShader.u_material.set(e.getMaterial());

		BindingUtils.bindVAO(e.getVao(), 0, 1, 2, 3);
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_INT, 0);
	}
}
