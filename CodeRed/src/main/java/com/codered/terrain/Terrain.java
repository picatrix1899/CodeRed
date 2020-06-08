package com.codered.terrain;

import org.barghos.math.matrix.Mat4;
import org.barghos.math.vector.vec3.Vec3;

import com.codered.model.RawModel;
import com.codered.rendering.material.Material;

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
	
	public Mat4 getTransformationMatrix()
	{
		return Mat4.translation(new Vec3(getX(), 0.0f, getZ()));
	}
}
