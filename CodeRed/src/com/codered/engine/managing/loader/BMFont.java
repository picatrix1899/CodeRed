package com.codered.engine.managing.loader;

import java.util.HashMap;

import com.codered.engine.shader.BMFontData;
import com.codered.engine.texture.Texture;
import com.codered.engine.utils.TextureUtils;
import com.codered.engine.utils.WindowContextHelper;
import com.codered.engine.window.WindowContext;

public class BMFont
{
	private BMFontData data;
	
	private Texture texture;

	private HashMap<Integer,CharData> chars = new HashMap<Integer,CharData>();
	
	public BMFont(BMFontData data) throws Exception
	{
		WindowContext context = WindowContextHelper.getCurrentContext();
		
		this.data = data;
		
		this.texture = TextureUtils.genTexture(data.file, context);
		
		for(BMFontData.CharData charData : this.data.data)
		{
			int c = charData.c;
			
			CharData d = new CharData();
			
			d.x = charData.x;
			d.y = charData.y;
			d.width = charData.width;
			d.height = charData.height;
			d.xOff = charData.xOff;
			d.yOff = charData.yOff;
			d.xAdvance = charData.xAdvance;
			
			this.chars.put(c, d);
		}
	}
	
	public int width()
	{
		return this.data.width;
	}
	
	public int height()
	{
		return this.data.height;
	}
	
	public CharData charData(String c)
	{
		int ch = c.charAt(0);
		
		return this.chars.get(ch);
	}
	
	public Texture getTexture()
	{
		return this.texture;
	}
	
	
	public int getTextureId()
	{
		return this.texture.getId();
	}
	
	public static class CharData
	{
		public int x;
		public int y;
		public int width;
		public int height;
		public int xOff;
		public int yOff;
		public int xAdvance;
	}
}
