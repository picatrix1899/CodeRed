package com.codered.managing.models;

import org.barghos.math.geometry.ConvexTriangleMesh3f;

import com.codered.material.Material;

public class TexturedModel
{
	private ConvexTriangleMesh3f physicalMesh;
	private Material material;
	private Mesh model;
	
	public TexturedModel(Mesh model, Material material)
	{
		this.model = model;
		this.material = material;
		
		this.physicalMesh = new ConvexTriangleMesh3f(this.model.triangles);
	}
	
	public ConvexTriangleMesh3f getPhysicalMesh() { return this.physicalMesh; }
	
	public Mesh getModel() { return this.model; }

	public Material getMaterial() { return this.material; }
	
}
