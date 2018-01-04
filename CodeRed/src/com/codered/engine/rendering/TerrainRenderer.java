package com.codered.engine.rendering;

import com.codered.engine.entities.Camera;
import com.codered.engine.managing.World;
import com.codered.engine.terrain.Terrain;

public interface TerrainRenderer
{
	void init(Terrain e, World w, Camera c);
	void render(Terrain e);
}
