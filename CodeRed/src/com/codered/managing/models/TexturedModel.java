package com.codered.managing.models;

import com.codered.engine.EngineRegistry;
import com.codered.material.Material;

import cmn.utilslib.math.geometry.ConcaveTriangleMesh3f;

public class TexturedModel
{
	private ConcaveTriangleMesh3f pysicalModel;
	private String model;
	private String mat;
	
	public TexturedModel(String model, String texture)
	{
		this.model = model;
		this.mat = texture;
		
		Mesh mesh = EngineRegistry.getCurrentWindowContext().getDRM().getStaticMesh(this.model);
		
		this.pysicalModel = new ConcaveTriangleMesh3f(mesh.triangles);
	}

	public ConcaveTriangleMesh3f getPhysicalMesh() { return this.pysicalModel; }
	
	public Mesh getModel() { return EngineRegistry.getCurrentWindowContext().getDRM().getStaticMesh(this.model); }

	public Material getMaterial() { return EngineRegistry.getCurrentWindowContext().getDRM().getMaterial(this.mat); }
	
}
