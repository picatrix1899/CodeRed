package com.codered.model;

import java.util.ArrayList;
import java.util.List;

import org.barghos.core.ListUtils;
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
	private List<Triangle3f> triangles = new ArrayList<>();
	private List<TriangleData> triangleData = new ArrayList<>();
	private List<Vertex> vertices = new ArrayList<>();
	private List<Integer> indices = new ArrayList<>();
	
	private VAO vao;

	private Material material;
	
	public Mesh(List<Vertex> vertices, List<Integer> indices, List<Triangle3f> triangles, List<TriangleData> triangleData, Material material)
	{
		this.triangles = triangles;
		this.triangleData = triangleData;
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
		
		loadToVAO(pos, uvs, nrm, tng, indices);
		
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
	
	private void loadToVAO(Vec3[] positions, Vec2[] texCoords, Vec3[] normals, Vec3[] tangents, int[] indices)
	{
		this.vao = EngineRegistry.getVAOManager().getNewVAO();
		
		this.vao.storeIndices(indices, GL15.GL_STATIC_DRAW);
		
		this.vao.storeData(0, positions, 0, 0, GL15.GL_STATIC_DRAW);
		this.vao.storeData(1, texCoords, 0, 0, GL15.GL_STATIC_DRAW);
		this.vao.storeData(2, normals, 0, 0, GL15.GL_STATIC_DRAW);
		this.vao.storeData(3, tangents, 0, 0, GL15.GL_STATIC_DRAW);
	}
}