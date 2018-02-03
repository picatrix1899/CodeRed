package com.codered.demo;

import com.codered.engine.entities.DynamicEntity;
import com.codered.engine.managing.World;
import com.codered.engine.managing.models.TexturedModel;
import com.codered.engine.rendering.EntityRenderer;

import cmn.utilslib.math.vector.Vector3f;

public class RotatingBox extends DynamicEntity
{

	public RotatingBox(TexturedModel model, Vector3f pos, float rx, float ry, float rz)
	{
		super(model, pos, rx, ry, rz);
	}

	public void update(World w)
	{
		super.update(w);
		
		rotateYaw(2);
	}
	
	public EntityRenderer getRenderer()
	{
		return TestRenderer.INSTANCE;
	}
}
