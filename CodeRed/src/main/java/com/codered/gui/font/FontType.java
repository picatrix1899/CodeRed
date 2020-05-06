package com.codered.gui.font;

import java.io.File;

import com.codered.gui.elements.GUIText;

/**
 * Represents a font. It holds the font's texture atlas as well as having the
 * ability to create the quad vertices for any text using this font.
 * 
 * @author Karl
 *
 */
public class FontType
{

	private com.codered.rendering.texture.Texture texture;
	private MetaFile meta;
	private TextMeshCreator loader;

	/**
	 * Creates a new font and loads up the data about each character from the
	 * font file.
	 * 
	 * @param textureAtlas
	 *            - the ID of the font atlas texture.
	 * @param fontFile
	 *            - the font file containing information about each character in
	 *            the texture atlas.
	 */
	public FontType(com.codered.rendering.texture.Texture texture, File fontFile)
	{
		this.texture = texture;
		this.meta = new MetaFile(fontFile);
		this.loader = new TextMeshCreator(meta);
	
	}

	/**
	 * @return The font texture atlas.
	 */
	public com.codered.rendering.texture.Texture getTexture() { return texture; }

	public MetaFile getMeta()
	{
		return this.meta;
	}
	
	/**
	 * Takes in an unloaded text and calculate all of the vertices for the quads
	 * on which this text will be rendered. The vertex positions and texture
	 * coords and calculated based on the information from the font file.
	 * 
	 * @param text
	 *            - the unloaded text.
	 * @return Information about the vertices of all the quads.
	 */
	public TextMeshData loadText(GUIText text) { return loader.createTextMesh(text); }

}
