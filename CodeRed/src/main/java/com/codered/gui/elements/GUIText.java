package com.codered.gui.elements;

import org.barghos.math.vector.vec3.Vec3;

import com.codered.font.FontType;

/**
 * Represents a piece of text in the game.
 * 
 * @author Karl
 *
 */
public class GUIText
{

	public String textString;
	public float posX;
	public float posY;
	public float width;
	public float height;
	
	public int fontsize;
	
	public Vec3 color = new Vec3(0f, 0f, 0f);

	public int numberOfLines;

	public FontType font;

	public boolean centerTextHorizontal = false;
	public boolean centerTextVertical = false;
	
	/**
	 * Creates a new text, loads the text's quads into a VAO, and adds the text
	 * to the screen.
	 * 
	 * @param text
	 *            - the text.
	 * @param fontSize
	 *            - the font size of the text, where a font size of 1 is the
	 *            default size.
	 * @param font
	 *            - the font that this text should use.
	 * @param position
	 *            - the position on the screen where the top left corner of the
	 *            text should be rendered. The top left corner of the screen is
	 *            (0, 0) and the bottom right is (1, 1).
	 * @param maxLineLength
	 *            - basically the width of the virtual page in terms of screen
	 *            width (1 is full screen width, 0.5 is half the width of the
	 *            screen, etc.) Text cannot go off the edge of the page, so if
	 *            the text is longer than this length it will go onto the next
	 *            line. When text is centered it is centered into the middle of
	 *            the line, based on this line length value.
	 * @param centered
	 *            - whether the text should be centered or not.
	 */
	public GUIText(String text, int fontsize, float x, float y, float width, float height, FontType font, boolean centeredHorizontal, boolean centeredVertical)
	{
		this.textString = text;
		this.posX = x;
		this.posY = y;
		this.font = font;
		this.width = width;
		this.height = height;
		this.centerTextHorizontal = centeredHorizontal;
		this.centerTextVertical = centeredVertical;
		this.fontsize = fontsize;
	}
	
	public double getScaledFontSize()
	{
		return this.fontsize * this.font.getMeta().fontsizeScalar;
	}
}
