package com.codered.engine.terrain;

import com.codered.engine.managing.Material;
import com.codered.engine.managing.models.RawModel;
import com.codered.engine.rendering.DefaultTerrainRenderer;
import com.codered.engine.rendering.TerrainRenderer;

import cmn.utilslib.math.matrix.Matrix4f;
import cmn.utilslib.math.vector.Vector3f;

public class Terrain
{
	public static final int SIZE = 800;
	public static final int VERTEX_COUNT = 100;
	public static final int VERTEX_COUNT_MINUS_ONE = VERTEX_COUNT  - 1;
	
	private float x;
	private float z;
	private RawModel model;
	private Material texture;
	public long id;

	public Terrain(int gridX, int gridZ, RawModel model, Material texture)
	{
		this.texture = texture;
		this.model = model;
		this.x = gridX * SIZE;
		this.z = gridZ * SIZE;
	}

	public TerrainRenderer getRenderer()
	{
		return DefaultTerrainRenderer.instance;
	}
	
	public float getX()
	{
		return x;
	}

	public float getZ()
	{
		return z;
	}

	public RawModel getModel()
	{
		return model;
	}

	public Material getTexture()
	{
		return texture;
	}
	
	public Matrix4f getTransformationMatrix()
	{
		return Matrix4f.translation(new Vector3f(getX(), 0.0f, getZ()));
	}
}
