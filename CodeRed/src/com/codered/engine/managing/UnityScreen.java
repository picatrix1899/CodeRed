package com.codered.engine.managing;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import com.codered.engine.managing.models.RawModel;
import com.codered.engine.utils.BindingUtils;

import cmn.utilslib.math.vector.Vector2f;
import cmn.utilslib.math.vector.api.Vec2f;

public class UnityScreen
{
	public final RawModel quad;
	
	public UnityScreen()
	{
		Vec2f[] CORNERS = new Vector2f[] { new Vector2f(-1, 1), new Vector2f(-1, -1), new Vector2f(1, 1), new Vector2f(1, -1) };
		
		VAO vao = new VAO();
		vao.storeData(0, CORNERS, 0, 0, GL15.GL_STATIC_DRAW);
		vao.storeIndices(new int[] {0, 1, 2, 3}, GL15.GL_STATIC_DRAW);
		quad = new RawModel(vao, 4);
	}
	
	public void draw()
	{
		BindingUtils.bindVAO(quad.getVAO(), 0);
		
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
	}
}
