package com.codered.demo;

import com.codered.engine.managing.World;
import com.codered.engine.managing.loader.TerrainLoader;
import com.codered.engine.managing.loader.WorldEntityLoader;
import com.codered.engine.managing.loader.WorldResourceLoader;
import com.codered.engine.terrain.Terrain;
import com.codered.engine.window.IWindowContext;

import cmn.utilslib.math.vector.Vector3f;

public class DemoWorld extends World
{

	private IWindowContext context;
	
	public DemoWorld(IWindowContext context)
	{
		this.context = context;
	}
	
	public void load()
	{
		WorldResourceLoader.load("testmap");
		
		this.context.getResourceManager().WORLD.regRawModel("terrain", new TerrainLoader().loadTerrain());
		
		WorldEntityLoader.load("testmap", this);
		
		addDynamicEntity(0, new RotatingBox(this.context.getResourceManager().getTexturedModel("crate"), Vector3f.TEMP.set(-20, 0, -30), 0.0f, 45.0f, 0.0f));

		//addStaticEntity(0, "crate", new Vector3f(0,0,0), 0,45,0);
		
		//getStaticEntity(12).getTransform().setScale(Vector3f.TEMP.set(2f, 2.5f, 2f));		
		
		//addStaticEntity(16, new HitTest(ResourceManager.getTexturedModel("boulder"), Vector3f.TEMP.set(40, 0, 0), 0.0f, 0.0f, 0.0f));
		
		addStaticTerrain(0, new Terrain(-1, -1, this.context.getResourceManager().getRawModel("terrain"), this.context.getResourceManager().getMaterial("steelfloor")));
		addStaticTerrain(1, new Terrain(-1, 0, this.context.getResourceManager().getRawModel("terrain"), this.context.getResourceManager().getMaterial("bricks2")));
	}

}
