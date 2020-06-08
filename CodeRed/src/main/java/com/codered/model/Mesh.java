package com.codered.model;

import java.util.Optional;

import org.barghos.math.geometry.ConvexTriangleMesh3;

import com.codered.ResourceHolder;
import com.codered.managing.VAO;
import com.codered.rendering.material.Material;

public class Mesh implements ResourceHolder
{
	
	private VAO vao;
	private int vertexCount;
	private ConvexTriangleMesh3 collisionMesh;
	private Material material;

	public Mesh(VAO vao, int vertexCount, ConvexTriangleMesh3 collisionMesh, Material material)
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
	
	public Optional<ConvexTriangleMesh3> getCollisionMesh()
	{
		return Optional.ofNullable(this.collisionMesh);
	}
	
	public Optional<Material> getMaterial()
	{
		return Optional.ofNullable(this.material);
	}

	public void release(boolean forced)
	{
		this.vao.release(forced);
		this.material.release(forced);
	}
}
