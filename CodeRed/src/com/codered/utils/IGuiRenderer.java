package com.codered.utils;

import com.codered.font.FontType;
import com.codered.gui.elements.GUIText;
import com.codered.texture.Texture;

public interface IGuiRenderer
{
	void drawTexturedRect(String t, float posX, float posY, float sizeX, float sizeY);
	void drawTexturedRect(Texture t, float posX, float posY, float sizeX, float sizeY);
	void drawTexturedRect(int t, float posX, float posY, float sizeX, float sizeY);
	void drawText(String text, int fontsize, float x, float y, float width, float height, FontType font, boolean centeredHorizontal, boolean centeredVertical);
	void drawText(String text, int fontsize, float x, float y, float width, float height, String font, boolean centeredHorizontal, boolean centeredVertical);
	void drawText(GUIText text);
}
