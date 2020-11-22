package com.codered.demo;

import org.barghos.math.vec2.Vec2f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import com.codered.engine.EngineRegistry;
import com.codered.gui.elements.GUIText;
import com.codered.gui.font.FontType;
import com.codered.gui.font.TextMeshData;
import com.codered.managing.VAO;
import com.codered.rendering.shader.ShaderProgram;
import com.codered.rendering.shader.ShaderSession;
import com.codered.rendering.texture.Texture;
import com.codered.utils.BindingUtils;
import com.codered.utils.GLUtils;
import com.codered.utils.IGuiRenderer;
import com.codered.window.WindowContext;

public class GUIRenderer implements IGuiRenderer
{
	public ShaderProgram noGuiShader;
	public ShaderProgram fontGuiShader;

	private VAO vao;
	
	private WindowContext context;
	
	public GUIRenderer()
	{
		this.vao = new VAO();
		this.context = EngineRegistry.getCurrentWindowContext();
	}
	
	@Override
	public void drawTexturedRect(String t, float posX, float posY, float sizeX, float sizeY)
	{
		drawTexturedRect(EngineRegistry.getResourceRegistry().get(t, Texture.class), posX, posY, sizeX, sizeY);
	}

	public void drawTexturedRect(com.codered.rendering.texture.Texture t, float posX, float posY, float sizeX, float sizeY)
	{
		drawTexturedRect(t.getId(), posX, posY, sizeX, sizeY);
	}
	
	@Override
	public void drawTexturedRect(int t, float posX, float posY, float sizeX, float sizeY)
	{
		Vec2f[] vertices = new Vec2f[] {
			new Vec2f(posX, posY + sizeY),
			new Vec2f(posX, posY),
			new Vec2f(posX + sizeX, posY),	
			new Vec2f(posX + sizeX, posY +sizeY),
		};
		
		Vec2f[] uvs = new Vec2f[] {
			new Vec2f(0,1),	
			new Vec2f(0,0),				
			new Vec2f(1,0),	
			new Vec2f(1,1),		
		};

		vao.clearVBOs();
		vao.storeData(0, vertices, 0, 0, GL15.GL_STATIC_DRAW);
		vao.storeData(1, uvs, 0, 0, GL15.GL_STATIC_DRAW);
		vao.storeIndices(new int[] {0, 1, 2, 2, 3, 0}, GL15.GL_STATIC_DRAW);
		
		try(ShaderSession ss = this.noGuiShader.start())
		{	
			this.noGuiShader.setUniformValue(0, this.context.getWindow().getSize());
			this.noGuiShader.setUniformValue(1, t);
			
			BindingUtils.bindVAO(vao, 0, 1);
			GL11.glDrawElements(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_INT, 0);
		}
	}

	@Override
	public void drawText(String text, int fontsize, float x, float y, float width, float height, FontType font, boolean centeredHorizontal, boolean centeredVertical)
	{
		GUIText guitext = new GUIText(text, fontsize, x, y, width, height, font, centeredHorizontal, centeredVertical);
		
		drawText(guitext);
	}

	@Override
	public void drawText(GUIText text)
	{
		TextMeshData data = text.font.loadText(text);
		
		vao.clearVBOs();
		vao.storeData(0, 2, data.getVertexPositions(), 0, 0, GL15.GL_STATIC_DRAW);
		vao.storeData(1, 2, data.getTextureCoords(), 0, 0, GL15.GL_STATIC_DRAW);
		
		int vertexCount = data.getVertexCount();

		GLUtils.blend(true);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GLUtils.depthTest(false);

		try(ShaderSession ss = this.fontGuiShader.start())
		{
			this.fontGuiShader.setUniformValue(0, this.context.getWindow().getSize());
			this.fontGuiShader.setUniformValue(1, text.color);
			this.fontGuiShader.setUniformValue(2, text.font.getTexture().getId());
			
			BindingUtils.bindVAO(vao, 0, 1);
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCount);
		}

		GLUtils.blend(false);
		GLUtils.depthTest(true);
	}

	public void release(boolean forced)
	{
		this.vao.release(forced);
	}



	
}
