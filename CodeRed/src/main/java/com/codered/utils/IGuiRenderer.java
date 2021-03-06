package com.codered.utils;

import com.codered.ResourceHolder;
import com.codered.gui.elements.GUIText;
import com.codered.gui.font.FontType;
import com.codered.rendering.texture.Texture;

public interface IGuiRenderer extends ResourceHolder
{
	void drawTexturedRect(String t, float posX, float posY, float sizeX, float sizeY);
	void drawTexturedRect(Texture t, float posX, float posY, float sizeX, float sizeY);
	void drawTexturedRect(int t, float posX, float posY, float sizeX, float sizeY);
	void drawText(String text, int fontsize, float x, float y, float width, float height, FontType font, boolean centeredHorizontal, boolean centeredVertical);
	void drawText(GUIText text);
}
