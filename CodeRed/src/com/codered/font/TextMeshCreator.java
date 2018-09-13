package com.codered.font;

import java.util.ArrayList;
import java.util.List;

import com.codered.gui.elements.GUIText;

public class TextMeshCreator
{

	protected static final double LINE_HEIGHT = 0.03f;
	protected static final int SPACE_ASCII = 32;

	private MetaFile metaData;

	protected TextMeshCreator(MetaFile metaFile)
	{
		this.metaData = metaFile;
	}

	protected TextMeshData createTextMesh(GUIText text)
	{
		List<Line> lines = createStructure(text);
		TextMeshData data = createQuadVertices(text, lines);
		return data;
	}

	private List<Line> createStructure(GUIText text)
	{
		double fontsize = text.getScaledFontSize();
		
		char[] chars = text.textString.toCharArray();
		List<Line> lines = new ArrayList<Line>();
		Line currentLine = new Line(metaData.getSpaceWidth() * fontsize, Math.ceil(text.width));
		Word currentWord = new Word();
		for (char c : chars)
		{
			int ascii = (int) c;
			if (ascii == SPACE_ASCII) 
			{
				boolean added = currentLine.attemptToAddWord(currentWord, fontsize);
				if (!added) 
				{
					if((lines.size() + 1) * metaData.lineHeight * fontsize > text.height) break;
					
					lines.add(currentLine);
					currentLine = new Line(metaData.getSpaceWidth() * fontsize, text.width);
					currentLine.attemptToAddWord(currentWord, fontsize);
				}
				currentWord = new Word();
				continue;
			}
			Character character = metaData.getCharacter(ascii);
			currentWord.addCharacter(character, fontsize);
		}
		completeStructure(lines, currentLine, currentWord, text);
		return lines;
	}

	private void completeStructure(List<Line> lines, Line currentLine, Word currentWord, GUIText text)
	{
		double fontsize = text.getScaledFontSize();
		
		boolean added = currentLine.attemptToAddWord(currentWord, fontsize);
		if (!added) 
		{
			if((lines.size() + 1) * metaData.lineHeight * fontsize > text.height) return;
			
			lines.add(currentLine);
			currentLine = new Line(metaData.getSpaceWidth() * fontsize, text.width);
			currentLine.attemptToAddWord(currentWord, fontsize);
		}
		lines.add(currentLine);
	}

	private TextMeshData createQuadVertices(GUIText text, List<Line> lines) 
	{
		double fontsize = text.getScaledFontSize();
		
		text.numberOfLines = lines.size();
		double curserX = 0f;
		double curserY = 0f;
		List<Float> vertices = new ArrayList<Float>();
		List<Float> textureCoords = new ArrayList<Float>();
		for (Line line : lines) 
		{
			if (text.centerTextHorizontal) 
			{
				curserX = (line.getMaxLength() - line.getLineLength()) * 0.5;
			}
			
			if(text.centerTextVertical)
			{
				double fullTextHeight = metaData.lineHeight * fontsize * lines.size();
				
				curserY = (text.height - fullTextHeight) * 0.5;
			}
			
			for (Word word : line.getWords()) 
			{
				for (Character letter : word.getCharacters()) 
				{
					addVerticesForCharacter(curserX, curserY, letter, vertices, fontsize);
					addTexCoords(textureCoords, letter.getxTextureCoord(), letter.getyTextureCoord(),
							letter.getXMaxTextureCoord(), letter.getYMaxTextureCoord());
					curserX += letter.getxAdvance() * fontsize;
				}
				curserX += metaData.getSpaceWidth() * fontsize;
			}
			curserX = 0;
			curserY += metaData.lineHeight * fontsize;
		}		
		return new TextMeshData(listToArray(vertices), listToArray(textureCoords));
	}

	private void addVerticesForCharacter(double curserX, double curserY, Character character, List<Float> vertices, double fontsize) 
	{
		double x = curserX + (character.getxOffset() * fontsize);
		double y = curserY + (character.getyOffset() * fontsize);
		double maxX = x + (character.getSizeX() * fontsize);
		double maxY = y + (character.getSizeY() * fontsize);
		addVertices(vertices, x, y, maxX, maxY);
	}

	private static void addVertices(List<Float> vertices, double x, double y, double maxX, double maxY) 
	{
		vertices.add((float) x);
		vertices.add((float) y);
		vertices.add((float) x);
		vertices.add((float) maxY);
		vertices.add((float) maxX);
		vertices.add((float) maxY);
		vertices.add((float) maxX);
		vertices.add((float) maxY);
		vertices.add((float) maxX);
		vertices.add((float) y);
		vertices.add((float) x);
		vertices.add((float) y);
	}

	private static void addTexCoords(List<Float> texCoords, double x, double y, double maxX, double maxY) 
	{
		texCoords.add((float) x);
		texCoords.add((float) y);
		texCoords.add((float) x);
		texCoords.add((float) maxY);
		texCoords.add((float) maxX);
		texCoords.add((float) maxY);
		texCoords.add((float) maxX);
		texCoords.add((float) maxY);
		texCoords.add((float) maxX);
		texCoords.add((float) y);
		texCoords.add((float) x);
		texCoords.add((float) y);
	}

	
	private static float[] listToArray(List<Float> listOfFloats) 
	{
		float[] array = new float[listOfFloats.size()];
		for (int i = 0; i < array.length; i++) 
		{
			array[i] = listOfFloats.get(i);
		}
		return array;
	}

}
