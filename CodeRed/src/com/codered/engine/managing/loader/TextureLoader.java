package com.codered.engine.managing.loader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import com.codered.engine.managing.Paths;
import com.codered.engine.managing.loader.data.TextureData;

public class TextureLoader
{
	public static TextureData loadTexture(String fileName)
	{
		
		File img = new File(Paths.p_materials + fileName + Paths.e_png);

		try
		{
			BufferedImage image = ImageIO.read(img);

			int[] pixels = new int[image.getWidth() * image.getHeight()];
			image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

			ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4); //4 for RGBA, 3 for RGB

			for(int y = 0; y < image.getHeight(); y++)
			{
				for(int x = 0; x < image.getWidth(); x++)
				{
					int pixel = pixels[y * image.getWidth() + x];
					buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
					buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
					buffer.put((byte) (pixel & 0xFF));               // Blue component
					buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component
				}
			}

			buffer.flip();

			return new TextureData(buffer, image.getWidth(), image.getHeight());
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	

}
