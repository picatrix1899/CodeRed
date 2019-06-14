package com.codered.resourcemanager.materials;

import java.io.InputStream;

import org.json.JSONObject;
import org.json.JSONTokener;

import com.codered.resourcemanager.utils.ResourceLoader;

public class MaterialLoader extends ResourceLoader<MaterialData>
{
	protected MaterialData loadResource(InputStream stream) throws Exception
	{
		String albedoMap = "";
		String normalMap = "";
		String displacementMap = "";
		float specPower = 0.0f;
		float specIntensity = 0.0f;
		
		JSONTokener tokener = new JSONTokener(stream);
		JSONObject obj = (JSONObject) tokener.nextValue();
		
		if(obj.has("albedoMap"))
			albedoMap = obj.getString("albedoMap");
		
		if(obj.has("normalMap"))
			normalMap = obj.getString("normalMap");
		
		if(obj.has("displacementMap"))
			displacementMap = obj.getString("displacementMap");
		
		if(obj.has("specularPower"))
			specPower = obj.getFloat("specularPower");

		if(obj.has("specularIntensity"))
			specPower = obj.getFloat("specularIntensity");
		
		return new MaterialData(albedoMap, normalMap,displacementMap, specPower, specIntensity);
	}

}
