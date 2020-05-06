package com.codered.gui;

import com.codered.engine.EngineRegistry;
import com.codered.gui.elements.GUIText;
import com.codered.gui.font.FontType;
import com.codered.managing.VAO;
import com.codered.utils.IGuiRenderer;
import com.codered.window.WindowContext;

public abstract class GUI
{
	
	protected VAO vao;
	
	protected WindowContext context;
	
	public abstract void render();
	public abstract void update();
	
	public static GUITextRenderCallback textRenderCallback;
	
	protected IGuiRenderer renderer;
	
	public interface GUITextRenderCallback
	{
		void renderText(GUIText text, VAO vao, int vertexCount);
	}
	
	public GUI(IGuiRenderer renderer)
	{	
		this.renderer = renderer;
		this.vao = EngineRegistry.getVAOManager().getNewVAO();
		this.context = EngineRegistry.getCurrentWindowContext();
	}
	
	protected void setMousePos(float x, float y)
	{
		this.context.getMouse().setMousePos(x, this.context.getWindow().getHeight() - y);
	}
	
	protected void drawTexturedRect(String t, float posX, float posY, float sizeX, float sizeY)
	{
		this.renderer.drawTexturedRect(t, posX, posY, sizeX, sizeY);
	}
	
	protected void drawTexturedRect(com.codered.rendering.texture.Texture t, float posX, float posY, float sizeX, float sizeY)
	{
		this.renderer.drawTexturedRect(t, posX, posY, sizeX, sizeY);
	}
	
	protected void drawTexturedRect(int t, float posX, float posY, float sizeX, float sizeY)
	{
		this.renderer.drawTexturedRect(t, posX, posY, sizeX, sizeY);
	}
	
	protected void drawText(String text, int fontsize, float x, float y, float width, float height, FontType font, boolean centeredHorizontal, boolean centeredVertical)
	{
		this.renderer.drawText(text, fontsize, x, y, width, height, font, centeredHorizontal, centeredVertical);
	}
	
	protected void drawText(GUIText text)
	{
		this.renderer.drawText(text);
	}
	
	public boolean mouseIsInsideInclusive(float minX, float minY, float maxX, float maxY)
	{
		float mX = this.context.getMouse().getCurrentPos().getX();
		float mY = this.context.getMouse().getCurrentPos().getY();

		boolean bX = mX >= minX && mX <= maxX;
		boolean bY = mY >= minY && mY <= maxY;
		
		return bX && bY;
	}

	public boolean mouseIsInsideExclusive(float minX, float minY, float maxX, float maxY)
	{
		float mX = this.context.getMouse().getCurrentPos().getX();
		float mY = this.context.getMouse().getCurrentPos().getY();

		boolean bX = mX > minX && mX < maxX;
		boolean bY = mY > minY && mY < maxY;
		
		return bX && bY;
	}
}
