package com.codered.resource.material;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.json.JSONObject;
import org.json.JSONTokener;

import com.codered.resource.texture.TextureData;
import com.codered.resource.texture.TextureLoader;

public class MaterialLoader
{
	public static MaterialData loadResource(String file) throws Exception
	{
		TextureData albedoMapData = null;
		TextureData normalMapData = null;
		float specPower = 0.0f;
		float specIntensity = 0.0f;

		BufferedReader reader = new BufferedReader(new FileReader(new File(file)));
		
		JSONTokener tokener = new JSONTokener(reader);
		JSONObject obj = (JSONObject) tokener.nextValue();
		
		if(obj.has("albedoMap"))
		{
			String albedoMap = obj.getString("albedoMap");
			albedoMapData = TextureLoader.loadResource(albedoMap);
		}
		
		if(obj.has("normalMap"))
		{
			String normalMap = obj.getString("normalMap");
			normalMapData = TextureLoader.loadResource(normalMap);
		}

		
		if(obj.has("specularPower"))
			specPower = obj.getFloat("specularPower");

		if(obj.has("specularIntensity"))
			specPower = obj.getFloat("specularIntensity");
		
		
		reader.close();
		
		return new MaterialData(albedoMapData, normalMapData, specPower, specIntensity);
	}

}
