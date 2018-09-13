package com.codered.font;

import java.util.ArrayList;
import java.util.List;

/**
 * During the loading of a text this represents one word in the text.
 * @author Karl
 *
 */
public class Word 
{
	
	private List<Character> characters = new ArrayList<Character>();
	private double width = 0;
	
	/**
	 * Adds a character to the end of the current word and increases the screen-space width of the word.
	 * @param character - the character to be added.
	 */
	protected void addCharacter(Character character, double fontsize)
	{
		characters.add(character);
		width += character.getxAdvance() * fontsize;
	}
	
	/**
	 * @return The list of characters in the word.
	 */
	protected List<Character> getCharacters() { return characters; }
	
	/**
	 * @return The width of the word in terms of screen size.
	 */
	protected double getWordWidth() { return width; }

}
