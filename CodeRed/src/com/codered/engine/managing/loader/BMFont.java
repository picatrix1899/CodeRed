package com.codered.engine.managing.loader;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import com.codered.engine.managing.Texture;
import com.codered.engine.managing.loader.data.BMFontData;
import com.codered.engine.utils.GLUtils;

public class BMFont
{

	private BMFontData data;
	
	private Texture texture;
	
	private ByteBuffer im;	

	public BMFont(BMFontData data) throws Exception
	{
		this.data = data;
		
		InputStream gz = new FileInputStream();
		
		DataInputStream stream = new DataInputStream(gz);
		
		this.width = stream.readInt();
		this.height = stream.readInt();
		
		byte[] b = new byte[this.width * this.height * 4];
		
		stream.read(b);
		
		this.im = BufferUtils.createByteBuffer(b.length);
		this.im.put(b);
		this.im.flip();
		
		this.uvwa = new float[this.comp.length() * 4];
		
		int s = 0;
		
		for(int i = 0; i < this.comp.length(); i ++)
		{
			
			s = i * 4;
			
			this.uvwa[s + 0] = stream.readFloat();
			this.uvwa[s + 1] = stream.readFloat();
			this.uvwa[s + 2] = stream.readFloat();
			this.uvwa[s + 3] = stream.readFloat();
			
		}
		
		loadTexture();
		
		stream.close();
	}
	
	private void loadTexture()
	{
		this.texture = Texture.createTexture(this.width, this.height, false);
    
		GLUtils.bindTexture2D(this.texture.getId());
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, this.width, this.height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, this.im);
	}
}
