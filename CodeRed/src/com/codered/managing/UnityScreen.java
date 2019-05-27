package com.codered.managing;

import org.barghos.math.vector.Vec2f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import com.codered.engine.EngineRegistry;
import com.codered.utils.BindingUtils;

public class UnityScreen
{
	public final VAO vao;
	
	public UnityScreen()
	{
		Vec2f[] CORNERS = new Vec2f[] { new Vec2f(-1, 1), new Vec2f(-1, -1), new Vec2f(1, 1), new Vec2f(1, -1) };
		
		vao = EngineRegistry.getVAOManager().getNewVAO();
		vao.storeData(0, CORNERS, 0, 0, GL15.GL_STATIC_DRAW);
		vao.storeIndices(new int[] {0, 1, 2, 3}, GL15.GL_STATIC_DRAW);
	}
	
	public void draw()
	{
		BindingUtils.bindVAO(this.vao, 0);
		
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
	}
}
