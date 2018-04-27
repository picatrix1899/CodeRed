package com.codered.engine.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import com.codered.engine.resource.ResourcePath;

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
					break;
				}
				case LOCAL:
				{
					url = new File(res.toString()).toURI().toURL();
					break;
				}
				case URL:
				{
					url = new URL(res.toString());
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
