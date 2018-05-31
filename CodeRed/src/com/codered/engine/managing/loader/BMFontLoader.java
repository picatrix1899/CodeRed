package com.codered.engine.managing.loader;

import java.io.BufferedReader;
import java.io.FileReader;

import com.codered.engine.managing.loader.data.BMFontData;
import com.codered.engine.managing.loader.data.BMFontData.CharData;

public class BMFontLoader
{
	public static BMFontData load(String file)
	{
		BMFontData data = new BMFontData();
		
		try
		{
			
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = "";

			while((line = reader.readLine()) != null)
			{
				if(line.startsWith("info "))
				{
					data.title = line.split(" face=\"")[1].split(" size=")[0];
					data.title = data.title.substring(0, data.title.length() - 2);

					data.size = Integer.parseInt(line.split(" size=")[1].split(" bold=")[0]);
				}
				else if(line.startsWith("common "))
				{
					data.lineHeight = Integer.parseInt(line.split(" lineHeight=")[1].split(" base=")[0]);
					
					data.base = Integer.parseInt(line.split(" base=")[1].split(" scaleW=")[0]);
					
					data.width = Integer.parseInt(line.split(" scaleW=")[1].split(" scaleH=")[0]);
					
					data.height = Integer.parseInt(line.split(" scaleH=")[1].split(" pages=")[0]);
				}
				else if(line.startsWith("page "))
				{
					data.file = line.split(" file=\"")[1];
					data.file = data.file.substring(0, data.file.length() - 2);
				}
				else if(line.startsWith("char "))
				{
					CharData cData = new CharData();
					
					cData.c = (char)Integer.parseInt(line.split(" id=")[1].split(" x=")[0].trim());
					
					cData.x = Integer.parseInt(line.split(" x=")[1].split(" y=")[0].trim());
					cData.y = Integer.parseInt(line.split(" y=")[1].split(" width")[0].trim());
					
					cData.width = Integer.parseInt(line.split(" width=")[1].split("height=")[0].trim());
					cData.height = Integer.parseInt(line.split(" height=")[1].split(" xoffset=")[0].trim());
					
					cData.xOff = Integer.parseInt(line.split(" xoffset=")[1].split(" yoffset=")[0].trim());
					cData.yOff = Integer.parseInt(line.split(" yoffset=")[1].split( "xadvance=")[0].trim());
					
					cData.xAdvance = Integer.parseInt(line.split(" xadvance=")[1].split(" page=")[0].trim());
				
					data.data.add(cData);
				}
			}
			
			reader.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return data;
	}
}
