package com.codered.resource.material;

import java.io.File;
import java.io.InputStream;

import org.json.JSONObject;
import org.json.JSONTokener;

import com.codered.resource.texture.TextureData;
import com.codered.resource.texture.TextureLoader;

public class MaterialLoader
{
	public static MaterialData loadResource(InputStream stream) throws Exception
	{
		TextureData albedoMapData = null;
		TextureData normalMapData = null;
		float specPower = 0.0f;
		float specIntensity = 0.0f;
		
		JSONTokener tokener = new JSONTokener(stream);
		JSONObject obj = (JSONObject) tokener.nextValue();
		
		if(obj.has("albedoMap"))
		{
			String albedoMap = obj.getString("albedoMap");
			albedoMapData = TextureLoader.loadResource(new File(albedoMap).toURI().toURL().openStream());
		}
		
		if(obj.has("normalMap"))
		{
			String normalMap = obj.getString("normalMap");
			normalMapData = TextureLoader.loadResource(new File(normalMap).toURI().toURL().openStream());
		}

		
		if(obj.has("specularPower"))
			specPower = obj.getFloat("specularPower");

		if(obj.has("specularIntensity"))
			specPower = obj.getFloat("specularIntensity");
		
		
		return new MaterialData(albedoMapData, normalMapData, specPower, specIntensity);
	}

}
