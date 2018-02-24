package com.codered.demo;

import com.codered.engine.managing.ResourceManager;
import com.codered.engine.managing.World;
import com.codered.engine.managing.loader.WorldEntityLoader;
import com.codered.engine.managing.loader.WorldResourceLoader;
import com.codered.engine.terrain.Terrain;

import cmn.utilslib.math.vector.Vector3f;

public class DemoWorld extends World
{

	public void load()
	{
		WorldResourceLoader.load("testmap");
		WorldEntityLoader.load("testmap", this);
		
		addDynamicEntity(0, new RotatingBox(ResourceManager.getTexturedModel("crate"), Vector3f.TEMP.set(-20, 0, -30), 0.0f, 45.0f, 0.0f));

		//addStaticEntity(0, "crate", new Vector3f(0,0,0), 0,45,0);
		
		//getStaticEntity(12).getTransform().setScale(Vector3f.TEMP.set(2f, 2.5f, 2f));		
		
		//addStaticEntity(16, new HitTest(ResourceManager.getTexturedModel("boulder"), Vector3f.TEMP.set(40, 0, 0), 0.0f, 0.0f, 0.0f));
		
		addStaticTerrain(0, new Terrain(-1, -1, ResourceManager.getRawModel("terrain"), ResourceManager.getMaterial("steelfloor")));
		addStaticTerrain(1, new Terrain(-1, 0, ResourceManager.getRawModel("terrain"), ResourceManager.getMaterial("bricks2")));
	}

}
