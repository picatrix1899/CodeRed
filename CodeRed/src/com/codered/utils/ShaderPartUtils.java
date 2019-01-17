package com.codered.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import com.codered.resource.ResourcePath;

public class ShaderPartUtils
{
	public static URL toUrl(ResourcePath res)
	{
		URL url = null;
		
		try
		{
			switch(res.dest())
			{
				case EMBEDED:
				{
					url = res.src().getResource(res.toString());
					System.out.println(res.toString());
					break;
				}
				case LOCAL:
				{
					url = new File(res.toString()).toURI().toURL();
					System.out.println("LOCAL");
					break;
				}
				case URL:
				{
					url = new URL(res.toString());
					System.out.println("URL");
					break;
				}
			}
			
			if(url == null) throw new IOException();
			

		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return url;
	}
}
