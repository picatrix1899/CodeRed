package com.codered.demo;

import com.codered.engine.managing.ResourceManager;
import com.codered.engine.managing.World;
import com.codered.engine.managing.loader.TerrainLoader;
import com.codered.engine.terrain.Terrain;

import cmn.utilslib.color.colors.LDRColor3;
import cmn.utilslib.math.vector.Vector3f;

public class DemoWorld extends World
{

	public void load()
	{
		loadResources();
		
//		addStaticEntity(0, "crate", 	Vector3f.TEMP.set(0, 0,0), 0.0f, 45.0f, 0.0f);
		addDynamicEntity(0, new RotatingBox(ResourceManager.getTexturedModel("crate"), Vector3f.TEMP.set(-20, 0, -30), 0.0f, 45.0f, 0.0f));
		addStaticEntity(1, "crate", 	Vector3f.TEMP.set(-20, 10, -30), 0.0f, 0.0f, 0.0f);
		addStaticEntity(2, "crate", 	Vector3f.TEMP.set(-20, 0, -40), 0.0f, 0.0f, 0.0f);
		addStaticEntity(3, "crate", 	Vector3f.TEMP.set(-20, 10, -40), 0.0f, 0.0f, 0.0f);
		addStaticEntity(4, "crate", 	Vector3f.TEMP.set(-20, 0, -20), 0.0f, 0.0f, 0.0f);
		addStaticEntity(5, "crate", 	Vector3f.TEMP.set(-20, 10, -20), 0.0f, 0.0f, 0.0f);
		
		addStaticEntity(6, "crate", 	Vector3f.TEMP.set(-37, 0, -30), 0.0f, 20.0f, 0.0f);
		addStaticEntity(7, "crate", 	Vector3f.TEMP.set(-37, 10, -30), 0.0f, 0.0f, 0.0f);
		addStaticEntity(8, "crate", 	Vector3f.TEMP.set(-37, 0, -40), 0.0f, 0.0f, 0.0f);
		addStaticEntity(9, "crate", 	Vector3f.TEMP.set(-37, 10, -40), 0.0f, 0.0f, 0.0f);
		addStaticEntity(10, "crate", 	Vector3f.TEMP.set(-37, 0, -20), 0.0f, 0.0f, 0.0f);
		addStaticEntity(11, "crate", 	Vector3f.TEMP.set(-37, 10, -20), 0.0f, 0.0f, 0.0f);
		addStaticEntity(12, "tesla", 	Vector3f.TEMP.set(-52, 0,-20), 0.0f, 45.0f, 0.0f);
		addStaticEntity(13, "barrel", 	Vector3f.TEMP.set(-52, 0,-40), 0.0f, 0.0f, 0.0f);
		addStaticEntity(14, "barrel", 	Vector3f.TEMP.set(-52, 10,-40), 0.0f, 0.0f, 0.0f);
		addStaticEntity(15, "boulder", 	Vector3f.TEMP.set(-52, 0,-80), 0.0f, 0.0f, 0.0f);
//		
//		
		getStaticEntity(12).getTransform().setScale(Vector3f.TEMP.set(2f, 2.5f, 2f));		
		
		addStaticTerrain(0, new Terrain(-1, -1, ResourceManager.getRawModel("terrain"), ResourceManager.getMaterial("steelfloor")));
		addStaticTerrain(1, new Terrain(-1, 0, ResourceManager.getRawModel("terrain"), ResourceManager.getMaterial("bricks2")));
//		
		addStaticPointLight(0, Vector3f.TEMP.set(-30, 2f, -40), new LDRColor3(255, 0, 0), 10f, 0.0f, 0.1f, 0.4f);
		addStaticPointLight(1, Vector3f.TEMP.set(-30, 8f, -60), new LDRColor3(0, 255, 0), 10f, 0.37f, 0f, 0.4f);
	}
	
	
	private void loadResources()
	{
		ResourceManager.registerStaticMesh("crate", 	"crate");
		ResourceManager.registerStaticMesh("tesla", 	"tesla");
		ResourceManager.registerStaticMesh("barrel", 	"barrel");
		ResourceManager.registerStaticMesh("boulder", 	"boulder");
		
		ResourceManager.registerRawModel("terrain", new TerrainLoader().loadTerrain());
		
		ResourceManager.registerColorMap("pacman1", "pac01");
		ResourceManager.registerColorMap("button", "grass");
		
		ResourceManager.registerMaterialFromFile("crate");
		ResourceManager.registerMaterialFromFile("bricks2");
		ResourceManager.registerMaterialFromFile("steelfloor");
		ResourceManager.registerMaterialFromFile("barrel_very_wet");
		ResourceManager.registerMaterialFromFile("boulder_wet");
		ResourceManager.registerMaterialFromFile("tesla_very_wet");
		
		ResourceManager.registerTexturedModel("crate", 			"crate", 		"crate");
		ResourceManager.registerTexturedModel("tesla", 			"tesla", 		"tesla_very_wet");
		ResourceManager.registerTexturedModel("barrel", 		"barrel", 		"barrel_very_wet");
		ResourceManager.registerTexturedModel("boulder", 		"boulder", 		"boulder_wet");
		
		ResourceManager.registerGlowMap("tesla_glow", "tesla_glow");
		ResourceManager.registerGlowMap("crate_glow", "crate_glow");
	}

}
