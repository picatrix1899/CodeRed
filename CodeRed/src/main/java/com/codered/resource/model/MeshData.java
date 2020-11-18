package com.codered.resource.model;

import java.util.List;

import org.barghos.math.geometry.ConvexTriangleMesh3f;

import com.codered.resource.material.MaterialData;

public class MeshData
{
	private int vertexCount;
	private ConvexTriangleMesh3f collisionMesh;
	private MaterialData material;
	private List<FaceData> faces;
	
	public MeshData(int vertexCount, List<FaceData> faces, ConvexTriangleMesh3f collisionMesh, MaterialData material)
	{
		this.vertexCount = vertexCount;
		this.faces = faces;
		this.collisionMesh = collisionMesh;
		this.material = material;
	}

	public int getVertexCount()
	{
		return this.vertexCount;
	}
	
	public ConvexTriangleMesh3f getCollisionMesh()
	{
		return this.collisionMesh;
	}
	
	public MaterialData getMaterial()
	{
		return this.material;
	}
	
	public List<FaceData> getFaces()
	{
		return this.faces;
	}
}
