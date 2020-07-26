package com.codered.utils;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import com.codered.engine.EngineRegistry;
import com.codered.rendering.texture.Texture;
import com.codered.resource.texture.TextureData;

public class TextureUtils
{
	public static Texture genTexture2(int id, TextureData data)
	{
		int textureID = id;

		ByteBuffer buffer = MemoryUtil.memAlloc(data.width * data.height * 4);

		for(int y = 0; y < data.height; y++)
		{
			for(int x = 0; x < data.width; x++)
			{
				int pixel = data.pixels[y * data.width + x];
				
				buffer.put((byte)((pixel >> 16) & 0xFF)); // Red component
				buffer.put((byte)((pixel >> 8) & 0xFF)); // Green component
				buffer.put((byte)(pixel & 0xFF)); // Blue component
				buffer.put((byte)((pixel >> 24) & 0xFF)); //Alpha component
			}
		}

		buffer.flip();
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, data.width, data.height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, 0.5f);		
		
		MemoryUtil.memFree(buffer);
		
		if (EngineRegistry.getCurrentWindowContext().getWindow().getCapabilities().GL_EXT_texture_filter_anisotropic)
		{
			float amount = Math.min(3, GL11.glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, amount);
		}
		
		return new Texture(textureID, data.width, data.height);
	}	
}
