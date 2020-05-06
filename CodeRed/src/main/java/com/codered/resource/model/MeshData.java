package com.codered.resource.model;

import java.util.List;
import java.util.Optional;

import org.barghos.math.geometry.ConvexTriangleMesh3f;

import com.codered.resource.material.MaterialData;

public class MeshData
{
	private int vertexCount;
	private Optional<ConvexTriangleMesh3f> collisionMesh;
	private Optional<MaterialData> material;
	private List<FaceData> faces;
	
	public MeshData(int vertexCount, List<FaceData> faces, Optional<ConvexTriangleMesh3f> collisionMesh, Optional<MaterialData> material)
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
	
	public Optional<ConvexTriangleMesh3f> getCollisionMesh()
	{
		return this.collisionMesh;
	}
	
	public Optional<MaterialData> getMaterial()
	{
		return this.material;
	}
	
	public List<FaceData> getFaces()
	{
		return this.faces;
	}
}
