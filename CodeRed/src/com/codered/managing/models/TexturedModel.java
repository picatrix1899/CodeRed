package com.codered.managing.models;

import org.barghos.math.geometry.ConvexTriangleMesh3f;

import com.codered.engine.EngineRegistry;
import com.codered.material.Material;

public class TexturedModel
{
	private ConvexTriangleMesh3f physicalMesh;
	private String model;
	private String mat;
	
	public TexturedModel(String model, String texture)
	{
		this.model = model;
		this.mat = texture;
		
		Mesh mesh = EngineRegistry.getCurrentWindowContext().getDRM().getStaticMesh(this.model);
		
		this.physicalMesh = new ConvexTriangleMesh3f(mesh.triangles);

	}
	
	public ConvexTriangleMesh3f getPhysicalMesh() { return this.physicalMesh; }
	
	public Mesh getModel() { return EngineRegistry.getCurrentWindowContext().getDRM().getStaticMesh(this.model); }

	public Material getMaterial() { return EngineRegistry.getCurrentWindowContext().getDRM().getMaterial(this.mat); }
	
}
