package com.codered.gui.font;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides functionality for getting the values from a font file.
 * 
 * @author Karl
 *
 */
public class MetaFile
{

	private static final int SPACE_ASCII = 32;

	private static final String SPLITTER = " ";

	private double spaceWidth;

	private Map<Integer, Character> metaData = new HashMap<Integer, Character>();

	public int imageWidth;
	public int imageHeight;
	
	public int lineHeight;
	public int baselineHeight;
	
	private BufferedReader reader;
	private Map<String, String> values = new HashMap<String, String>();
	
	public double fontsizeScalar;
	
	/**
	 * Opens a font file in preparation for reading.
	 * 
	 * @param file
	 *            - the font file.
	 */
	protected MetaFile(File file)
	{
		openFile(file);
		processNextLine();
		this.fontsizeScalar = 1.0 / getValueOfVariable("size");
		loadLineSizes();
		this.imageWidth = getValueOfVariable("scaleW");
		this.imageHeight = getValueOfVariable("scaleH");
		loadCharacterData(this.imageWidth);
		close();
	}

	protected double getSpaceWidth() { return spaceWidth; }

	protected Character getCharacter(int ascii) { return metaData.get(ascii); }

	/**
	 * Read in the next line and store the variable values.
	 * 
	 * @return {@code true} if the end of the file hasn't been reached.
	 */
	private boolean processNextLine()
	{
		values.clear();
		String line = null;
		try
		{
			line = reader.readLine();
		}
		catch (IOException e1)
		{
		}
		
		if (line == null)
		{
			return false;
		}
		
		for (String part : line.split(SPLITTER))
		{
			String[] valuePairs = part.split("=");
			if (valuePairs.length == 2)
			{
				values.put(valuePairs[0], valuePairs[1]);
			}
		}
		return true;
	}

	/**
	 * Gets the {@code int} value of the variable with a certain name on the
	 * current line.
	 * 
	 * @param variable
	 *            - the name of the variable.
	 * @return The value of the variable.
	 */
	private int getValueOfVariable(String variable) { return Integer.parseInt(values.get(variable)); }

	/**
	 * Closes the font file after finishing reading.
	 */
	private void close()
	{
		try
		{
			reader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Opens the font file, ready for reading.
	 * 
	 * @param file
	 *            - the font file.
	 */
	private void openFile(File file)
	{
		try
		{
			reader = new BufferedReader(new FileReader(file));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.err.println("Couldn't read font meta file!");
		}
	}

	/**
	 * Loads information about the line height for this font in pixels, and uses
	 * this as a way to find the conversion rate between pixels in the texture
	 * atlas and screen-space.
	 */
	private void loadLineSizes()
	{
		processNextLine();
		this.lineHeight = getValueOfVariable("lineHeight");
		this.baselineHeight = getValueOfVariable("base");
	}

	/**
	 * Loads in data about each character and stores the data in the
	 * {@link Character} class.
	 * 
	 * @param imageWidth
	 *            - the width of the texture atlas in pixels.
	 */
	private void loadCharacterData(int imageWidth)
	{
		processNextLine();
		processNextLine();
		while (processNextLine())
		{
			Character c = loadCharacter(imageWidth);
			if (c != null) {
				metaData.put(c.getId(), c);
			}
		}
	}

	/**
	 * Loads all the data about one character in the texture atlas and converts
	 * it all from 'pixels' to 'screen-space' before storing. The effects of
	 * padding are also removed from the data.
	 * 
	 * @param imageSize
	 *            - the size of the texture atlas in pixels.
	 * @return The data about the character.
	 */
	private Character loadCharacter(int imageSize)
	{
		int id = getValueOfVariable("id");
		if (id == SPACE_ASCII)
		{
			this.spaceWidth = getValueOfVariable("xadvance");
			return null;
		}
		double xTex = ((double) getValueOfVariable("x")) / this.imageWidth;
		double yTex = ((double) getValueOfVariable("y")) / this.imageHeight;
		int width = getValueOfVariable("width");
		int height = getValueOfVariable("height");
		
		double quadWidth = width;
		double quadHeight = height;
		double xTexSize = (double) width / this.imageWidth;
		double yTexSize = (double) height / this.imageHeight;
		double xOff = getValueOfVariable("xoffset");
		double yOff = getValueOfVariable("yoffset");
		double xAdvance = getValueOfVariable("xadvance");
		
		return new Character(id, xTex, yTex, xTexSize, yTexSize, xOff, yOff, quadWidth, quadHeight, xAdvance);
	}
}
