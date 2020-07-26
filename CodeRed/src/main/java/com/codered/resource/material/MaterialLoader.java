package com.codered.resource.material;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.haze.png.Image;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.codered.resource.texture.TextureData;

public class MaterialLoader
{
	public static MaterialData loadResource(String file) throws Exception
	{
		TextureData albedoMapData = null;
		TextureData normalMapData = null;

		BufferedReader reader = new BufferedReader(new FileReader(new File(file)));
		
		JSONTokener tokener = new JSONTokener(reader);
		JSONObject obj = (JSONObject) tokener.nextValue();
		
		if(obj.has("albedoMap"))
		{
			String albedoMap = obj.getString("albedoMap");
			Image img1 = new org.haze.png.PNGReader().read(albedoMap);
			
			albedoMapData = new TextureData();
			albedoMapData.pixels = img1.pixels;
			albedoMapData.height = img1.height;
			albedoMapData.width = img1.width;
		}
		
		if(obj.has("normalMap"))
		{
			String normalMap = obj.getString("normalMap");
			Image img2 = new org.haze.png.PNGReader().read(normalMap);
			
			normalMapData = new TextureData();
			normalMapData.pixels = img2.pixels;
			normalMapData.height = img2.height;
			normalMapData.width = img2.width;
		}
	
		reader.close();
		
		return new MaterialData(albedoMapData, normalMapData);
	}

}
