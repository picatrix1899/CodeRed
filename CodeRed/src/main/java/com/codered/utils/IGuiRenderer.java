package com.codered.utils;

import com.codered.gui.elements.GUIText;
import com.codered.gui.font.FontType;

public interface IGuiRenderer
{
	void drawTexturedRect(String t, float posX, float posY, float sizeX, float sizeY);
	void drawTexturedRect(com.codered.rendering.texture.Texture t, float posX, float posY, float sizeX, float sizeY);
	void drawTexturedRect(int t, float posX, float posY, float sizeX, float sizeY);
	void drawText(String text, int fontsize, float x, float y, float width, float height, FontType font, boolean centeredHorizontal, boolean centeredVertical);
	void drawText(GUIText text);
}
