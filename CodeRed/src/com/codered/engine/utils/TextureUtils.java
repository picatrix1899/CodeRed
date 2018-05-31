package com.codered.engine.utils;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;

import com.codered.engine.managing.Texture;
import com.codered.engine.managing.loader.data.TextureData;
import com.codered.engine.window.WindowContext;

import cmn.utilslib.essentials.BufferUtils;

public class TextureUtils
{
	public static Texture genTexture(TextureData data, WindowContext context)
	{
		int textureID = GL11.glGenTextures();

		ByteBuffer buffer = BufferUtils.wrapFlippedByteBuffer(data.data()); //4 for RGBA, 3 for RGB
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		


		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, data.width(), data.height(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, 0.5f);		
		
		if (context.getWindow().getCapabilities().GL_EXT_texture_filter_anisotropic)
		{
			float amount = Math.min(4, GL11.glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, amount);
		}
		
		return new Texture(textureID, data.width(), data.height(), false);
	}
}
