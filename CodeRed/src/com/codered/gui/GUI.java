package com.codered.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import com.codered.font.FontType;
import com.codered.font.TextMeshData;
import com.codered.gui.elements.GUIText;
import com.codered.managing.VAO;
import com.codered.shaders.gui.Color_GUIShader;
import com.codered.shaders.gui.No_GUIShader;
import com.codered.utils.BindingUtils;
import com.codered.utils.RenderHelper;
import com.codered.utils.WindowContextHelper;
import com.codered.window.WindowContext;

import cmn.utilslib.color.colors.api.IColor3Base;
import cmn.utilslib.math.vector.Vector2f;
import cmn.utilslib.math.vector.api.Vec2f;

public abstract class GUI
{
	
	protected VAO vao;
	protected VAO vao2;
	
	protected WindowContext context;
	
	public abstract void render();
	public abstract void update();
	
	public GUI()
	{
		this.vao = new VAO();
		this.context = WindowContextHelper.getCurrentContext();
	}
	
	protected void setMousePos(float x, float y)
	{
		this.context.getInputManager().setMousePos(x, this.context.getHeight() - y);
	}
	
	protected void drawTexturedRect(String t, float posX, float posY, float sizeX, float sizeY)
	{
		drawTexturedRect(this.context.getResourceManager().getTexture(t).getId(), posX, posY, sizeX, sizeY);
	}
	
	protected void drawTexturedRect(int t, float posX, float posY, float sizeX, float sizeY)
	{

		
		Vector2f[] vertices = new Vector2f[4];
		
		vertices[0] = new Vector2f(posX, posY + sizeY);
		vertices[1] = new Vector2f(posX, posY);
		vertices[2] = new Vector2f(posX + sizeX, posY);		
		vertices[3] = new Vector2f(posX + sizeX, posY +sizeY);		
		
		
		Vector2f[] uvs = new Vector2f[] {
		
		new Vector2f(0,0),				
		new Vector2f(0,1),		
		new Vector2f(1,1),		
		new Vector2f(1,0),		

		};

		vao.clearVBOs();
		vao.storeData(0, vertices, 0, 0, GL15.GL_STATIC_DRAW);
		vao.storeData(1, uvs, 0, 0, GL15.GL_STATIC_DRAW);
		vao.storeIndices(new int[] {0, 1, 2, 2, 3, 0}, GL15.GL_STATIC_DRAW);
		
		BindingUtils.bindVAO(vao, 0, 1, 2, 3);
		
		this.context.getShader(No_GUIShader.class).loadTextureMap(t);
		this.context.getShader(No_GUIShader.class).use();
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_INT, 0);
		
		this.context.getShader(No_GUIShader.class).stop();
	}
	
	protected void drawLine(IColor3Base c, float width, Vector2f start, Vector2f... p)
	{
		Vector2f[] vertices = new Vector2f[1 + p.length];
		vertices[0] = start;
		
		for(int i = 0; i < p.length; i++)
		{
			vertices[i + 1] = p[i];
		}
		
		vao.storeData(0, vertices, 0, 0, GL15.GL_STATIC_DRAW);
		
		BindingUtils.bindVAO(vao, 0);
		
		this.context.getShader(Color_GUIShader.class).setInput("color", c);
		this.context.getShader(Color_GUIShader.class).use();
		
		GL11.glLineWidth(width);
		GL11.glDrawArrays(GL11.GL_LINE_STRIP, 0, vertices.length);
		
		this.context.getShader(Color_GUIShader.class).stop();
	}
	
	protected void drawPoints(IColor3Base c, float size, Vector2f start, Vector2f... p)
	{
		Vector2f[] vertices = new Vector2f[1 + p.length];
		vertices[0] = start;
		for(int i = 0; i < p.length; i++)
		{
			vertices[i + 1] = p[i];
		}
		
		vao.storeData(0, vertices, 0, 0, GL15.GL_STATIC_DRAW);
		
		BindingUtils.bindVAO(vao, 0);
		
		this.context.getShader(Color_GUIShader.class).setInput("color", c);
		this.context.getShader(Color_GUIShader.class).use();
		
		GL11.glPointSize(size);
		GL11.glDrawArrays(GL11.GL_POINTS, 0, vertices.length);
		
		this.context.getShader(Color_GUIShader.class).stop();
	}
	
	protected void drawColoredRect(IColor3Base c, float posX, float posY, float sizeX, float sizeY)
	{
		Vec2f[] vertices = new Vec2f[4];
		
		vertices[0] = new Vector2f(posX + sizeX, posY);		
		vertices[1] = new Vector2f(posX, posY);
		vertices[2] = new Vector2f(posX + sizeX, posY +sizeY);		
		vertices[3] = new Vector2f(posX, posY + sizeY);

		vao.storeData(0, vertices, 0, 0, GL15.GL_STATIC_DRAW);
		
		BindingUtils.bindVAO(vao, 0);
		
		this.context.getShader(Color_GUIShader.class).setInput("color", c);
		this.context.getShader(Color_GUIShader.class).use();
		
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		
		this.context.getShader(Color_GUIShader.class).stop();
	}
	
	protected void drawText(String text, int fontsize, float x, float y, float width, float height, FontType font, boolean centeredHorizontal, boolean centeredVertical)
	{
		GUIText guitext = new GUIText(text, fontsize, x, y, width, height, font, centeredHorizontal, centeredVertical);
		
		drawText(guitext);
	}
	
	protected void drawText(String text, int fontsize, float x, float y, float width, float height, String font, boolean centeredHorizontal, boolean centeredVertical)
	{
		GUIText guitext = new GUIText(text, fontsize, x, y, width, height, font, centeredHorizontal, centeredVertical);
		
		drawText(guitext);
	}
	
	protected void drawText(GUIText text)
	{
		TextMeshData data = text.font.loadText(text);
		
		vao.clearVBOs();
		vao.storeData(0, 2, data.getVertexPositions(), 0, 0, GL15.GL_STATIC_DRAW);
		vao.storeData(1, 2, data.getTextureCoords(), 0, 0, GL15.GL_STATIC_DRAW);
		
		int vertexCount = data.getVertexCount();
		
		RenderHelper.renderGuiTextDefault(text, vao, vertexCount);
	}
	
	public boolean mouseIsInsideInclusive(float minX, float minY, float maxX, float maxY)
	{
		float mX = this.context.getInputManager().getMouseX();
		float mY = this.context.getInputManager().getMouseY();

		boolean bX = mX >= minX && mX <= maxX;
		boolean bY = mY >= minY && mY <= maxY;
		
		return bX && bY;
	}

	public boolean mouseIsInsideExclusive(float minX, float minY, float maxX, float maxY)
	{
		float mX = this.context.getInputManager().getMouseX();
		float mY = this.context.getInputManager().getMouseY();

		boolean bX = mX > minX && mX < maxX;
		boolean bY = mY > minY && mY < maxY;
		
		return bX && bY;
	}
}
