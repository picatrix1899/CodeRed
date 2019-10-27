package com.codered.managing.models;

import org.barghos.math.geometry.ConvexTriangleMesh3f;

import com.codered.material.Material;

public class TexturedModel
{
	private ConvexTriangleMesh3f physicalMesh;
	private org.barghos.math.experimental.geometry.ConvexTriangleMesh3f pMesh;
	private Material material;
	private Mesh model;
	
	public TexturedModel(Mesh model, Material material)
	{
		this.model = model;
		this.material = material;
		
		this.pMesh = new org.barghos.math.experimental.geometry.ConvexTriangleMesh3f(this.model.triangles);
	}
	
	public ConvexTriangleMesh3f getPhysicalMesh() { return this.physicalMesh; }
	
	public org.barghos.math.experimental.geometry.ConvexTriangleMesh3f getPMesh() { return this.pMesh; }
	
	public Mesh getModel() { return this.model; }

	public Material getMaterial() { return this.material; }
	
}
