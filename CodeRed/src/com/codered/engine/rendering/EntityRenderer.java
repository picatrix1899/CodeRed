package com.codered.engine.rendering;

import com.codered.engine.entities.Camera;
import com.codered.engine.entities.StaticEntity;
import com.codered.engine.managing.World;

public interface EntityRenderer
{
	void init(StaticEntity e, World w, Camera c);
	void render(StaticEntity e);
}
