package com.codered.terrain;

import org.barghos.math.matrix.Mat4f;
import org.barghos.math.vector.Vec3f;

import com.codered.managing.models.RawModel;
import com.codered.material.Material;

public class Terrain
{
	public static final int SIZE = 800;
	public static final int VERTEX_COUNT = 100;
	public static final int VERTEX_COUNT_MINUS_ONE = VERTEX_COUNT  - 1;
	
	private float x;
	private float z;
	private RawModel model;
	private Material material;
	public long id;

	public Terrain(int gridX, int gridZ, RawModel model, Material material)
	{
		this.model = model;
		this.material = material;
		this.x = gridX * SIZE;
		this.z = gridZ * SIZE;
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

	public Material getMaterial()
	{
		return this.material;
	}
	
	public Mat4f getTransformationMatrix()
	{
		return Mat4f.translation(new Vec3f(getX(), 0.0f, getZ()));
	}
}
