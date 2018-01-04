package com.codered.engine.entities;

import com.codered.engine.managing.World;
import com.codered.engine.managing.models.TexturedModel;

import cmn.utilslib.math.vector.Vector3f;

public class DynamicEntity extends StaticEntity
{
	
	public DynamicEntity(TexturedModel model, Vector3f pos, float rx, float ry, float rz)
	{
		super(model, pos, rx, ry, rz);
	}

	
	
	public void update(World w) {}
}
