package com.codered.engine.fontRendering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL15;

import com.codered.engine.fontMeshCreator.FontType;
import com.codered.engine.fontMeshCreator.GUIText;
import com.codered.engine.fontMeshCreator.TextMeshData;
import com.codered.engine.managing.VAO;

public class TextMaster
{
	private static Map<FontType, List<GUIText>> texts = new HashMap<FontType, List<GUIText>>();
	private static FontRenderer renderer;
	
	public static void init()
	{
		renderer = new FontRenderer();
	}
	
	public static void render()
	{
		renderer.render(texts);
	}
	
	public static void loadText(GUIText text)
	{
		FontType font = text.getFont();
		TextMeshData data = font.loadText(text);
		VAO vao = new VAO();
		vao.storeData(0, 2, data.getVertexPositions(), 0, 0, GL15.GL_STATIC_DRAW);
		vao.storeData(1, 2, data.getTextureCoords(), 0, 0, GL15.GL_STATIC_DRAW);
		text.setVAOInfo(vao, data.getVertexCount());
		
		List<GUIText> textBatch = texts.get(font);
		if(textBatch == null)
		{
			textBatch = new ArrayList<GUIText>();
			texts.put(font, textBatch);
		}
		textBatch.add(text);
	}

	public static void removeText(GUIText text)
	{
		List<GUIText> textBatch = texts.get(text.getFont());
		textBatch.remove(text);
		if(textBatch.isEmpty())
		{
			texts.remove(text.getFont());
		}
	}
}
