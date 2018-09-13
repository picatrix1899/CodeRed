package com.codered.managing.models;

import com.codered.material.Material;

import cmn.utilslib.math.geometry.ConcaveTriangleMesh3f;

public class TexturedModel
{
	
	private Mesh model;
	private Material mat;
	private ConcaveTriangleMesh3f pysicalModel;
	
	
	public TexturedModel(Mesh model, Material texture)
	{
		this.model = model;
		this.mat = texture;
		this.pysicalModel = new ConcaveTriangleMesh3f(model.triangles);
	}

	public ConcaveTriangleMesh3f getPhysicalMesh() { return this.pysicalModel; }
	
	public Mesh getModel() { return this.model; }

	public Material getMaterial() { return this.mat; }
	
}
