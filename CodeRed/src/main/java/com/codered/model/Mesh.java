package com.codered.model;

import java.util.ArrayList;
import java.util.List;

import org.barghos.core.ListUtils;
import org.barghos.math.geometry.ConvexTriangleMesh3f;
import org.barghos.math.geometry.Triangle3f;
import org.barghos.math.vector.vec2.Vec2;
import org.barghos.math.vector.vec3.Vec3;
import org.lwjgl.opengl.GL15;

import com.codered.engine.EngineRegistry;
import com.codered.managing.VAO;
import com.codered.rendering.material.Material;
import com.codered.resource.object.TriangleData;

public class Mesh
{
	private List<Vertex> vertices = new ArrayList<>();
	private List<Integer> indices = new ArrayList<>();
	
	private ConvexTriangleMesh3f collisionMesh;
	
	private VAO vao;

	private Material material;
	
	public Mesh(List<Vertex> vertices, List<Integer> indices, List<Triangle3f> triangles, List<TriangleData> triangleData, Material material)
	{
		this.collisionMesh = new ConvexTriangleMesh3f(triangles);
		
		this.vertices = vertices;
		this.indices = indices;
		this.material = material;
		
		setupMesh();
	}
	
	private void setupMesh()
	{
		int vertexCount = this.vertices.size();

		Vec3[] pos = new Vec3[vertexCount];
		Vec2[] uvs = new Vec2[vertexCount];
		Vec3[] nrm = new Vec3[vertexCount];
		Vec3[] tng = new Vec3[vertexCount];
		
		int[] indices =	 new int[this.indices.size()];

		for(int i = 0; i < this.vertices.size(); i++)
		{
			Vertex v = this.vertices.get(i);
			
			pos[i] = v.position;
			nrm[i] = v.normal;
			tng[i] = v.tangent;
			uvs[i] = v.texCoord;
		}

		indices = ListUtils.toIntArray(this.indices);
		
		this.vao = EngineRegistry.getVAOManager().getNewVAO();
		
		this.vao.storeIndices(indices, GL15.GL_STATIC_DRAW);
		
		this.vao.storeData(0, pos, 0, 0, GL15.GL_STATIC_DRAW);
		this.vao.storeData(1, uvs, 0, 0, GL15.GL_STATIC_DRAW);
		this.vao.storeData(2, nrm, 0, 0, GL15.GL_STATIC_DRAW);
		this.vao.storeData(3, tng, 0, 0, GL15.GL_STATIC_DRAW);
		
	}
	
	public VAO getVAO()
	{
		return this.vao;
	}
	
	public Material getMaterial()
	{
		return this.material;
	}
	
	public int getVertexCount()
	{
		return this.vertices.size();
	}
	
	public ConvexTriangleMesh3f getCollisionMesh()
	{
		return this.collisionMesh;
	}
}
