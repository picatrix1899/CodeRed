package com.codered.texture;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TextureLoader
{
	public static TextureData loadTexture(String fileName)
	{
		return loadTexture(new File(fileName));
	}
	
	public static TextureData loadTexture(File img)
	{
		try
		{
			BufferedImage image = ImageIO.read(img);

			int height = image.getHeight();
			int width = image.getWidth();
			
			int sum = width * height;
			
			int[] pixels = new int[sum];
			image.getRGB(0, 0, width, height, pixels, 0, width);


			byte[] arr = new byte[sum * 4];
			

			int pixel;
			int pos;
			for(int y = 0; y < height; y++)
			{
				for(int x = 0; x < width; x++)
				{
					pixel = pixels[y * width + x];
					
					pos = y * (width * 4) + (x * 4);
					arr[pos] = ((byte) ((pixel >> 16) & 0xFF));     // Red component
					arr[pos + 1] = ((byte) ((pixel >> 8) & 0xFF));      // Green component
					arr[pos + 2] = ((byte) (pixel & 0xFF));               // Blue component
					arr[pos + 3] = ((byte) ((pixel >> 24) & 0xFF));    // Alpha component
				}
			}

			return new TextureData(arr, width, height);
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	

}
