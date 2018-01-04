package com.codered.engine.managing;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import com.codered.engine.GLUtils;
import com.codered.engine.managing.models.RawModel;

import cmn.utilslib.math.vector.Vector2f;
import cmn.utilslib.math.vector.api.Vec2f;

public class UnityScreen
{
	public static final Vec2f[] CORNERS = new Vector2f[] { new Vector2f(-1, 1), new Vector2f(-1, -1), new Vector2f(1, 1), new Vector2f(1, -1) };
	public static final Vec2f CENTER = Vec2f.ZERO.clone();
	
	public static final RawModel quad;
	
	static
	{
		VAO vao = new VAO();
		vao.storeData(0, CORNERS, GL15.GL_STATIC_DRAW);
		quad = new RawModel(vao, 4);
		
	}
	
	public static void draw()
	{
		GLUtils.bindVAO(quad.getVAO(), 0);
		
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
	}
}
