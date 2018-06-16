package com.codered.demo;

import com.codered.engine.entities.StaticEntity;
import com.codered.engine.managing.models.TexturedModel;
import com.codered.engine.rendering.EntityRenderer;

import cmn.utilslib.math.vector.Vector3f;

public class HitTest extends StaticEntity
{

	public HitTest(TexturedModel model, Vector3f pos, float rx, float ry, float rz)
	{
		super(model, pos, rx, ry, rz);
	}

	public EntityRenderer getRenderer()
	{
		return HitRenderer.INSTANCE;
	}
}
