package com.codered.resource.material;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.json.JSONObject;
import org.json.JSONTokener;

public class MaterialLoader
{
	public static MaterialData loadResource(String file) throws Exception
	{
		org.haze.png.Image albedoMapData = null;
		org.haze.png.Image normalMapData = null;
		float specPower = 0.0f;
		float specIntensity = 0.0f;

		BufferedReader reader = new BufferedReader(new FileReader(new File(file)));
		
		JSONTokener tokener = new JSONTokener(reader);
		JSONObject obj = (JSONObject) tokener.nextValue();
		
		if(obj.has("albedoMap"))
		{
			String albedoMap = obj.getString("albedoMap");
			albedoMapData = new org.haze.png.PNGReader().read(albedoMap);
		}
		
		if(obj.has("normalMap"))
		{
			String normalMap = obj.getString("normalMap");
			normalMapData =  new org.haze.png.PNGReader().read(normalMap);
		}

		
		if(obj.has("specularPower"))
			specPower = obj.getFloat("specularPower");

		if(obj.has("specularIntensity"))
			specPower = obj.getFloat("specularIntensity");
		
		
		reader.close();
		
		return new MaterialData(albedoMapData, normalMapData, specPower, specIntensity);
	}

}
