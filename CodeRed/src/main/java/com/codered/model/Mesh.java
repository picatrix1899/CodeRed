package com.codered.model;

import java.util.Optional;

import org.barghos.math.geometry.ConvexTriangleMesh3f;

import com.codered.managing.VAO;
import com.codered.rendering.material.Material;

public class Mesh
{
	
	private VAO vao;
	private int vertexCount;
	private Optional<ConvexTriangleMesh3f> collisionMesh;
	private Optional<Material> material;

	public Mesh(VAO vao, int vertexCount, Optional<ConvexTriangleMesh3f> collisionMesh, Optional<Material> material)
	{
		this.vao = vao;
		this.vertexCount = vertexCount;
		this.collisionMesh = collisionMesh;
		this.material = material;
	}
	
	public VAO getVao()
	{
		return this.vao;
	}
	
	public int getVertexCount()
	{
		return this.vertexCount;
	}
	
	public Optional<ConvexTriangleMesh3f> getCollisionMesh()
	{
		return this.collisionMesh;
	}
	
	public Optional<Material> getMaterial()
	{
		return this.material;
	}
}
