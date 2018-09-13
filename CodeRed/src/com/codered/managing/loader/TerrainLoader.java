package com.codered.managing.loader;

import java.util.ArrayList;

import org.lwjgl.opengl.GL15;

import com.codered.managing.Triangle;
import com.codered.managing.VAO;
import com.codered.managing.Vertex;
import com.codered.managing.models.RawModel;
import com.codered.terrain.Terrain;
import com.google.common.collect.Lists;

import cmn.utilslib.essentials.ListUtils;
import cmn.utilslib.math.geometry.Point3f;
import cmn.utilslib.math.vector.Vector2f;
import cmn.utilslib.math.vector.Vector3f;
import cmn.utilslib.math.vector.api.Vec2f;
import cmn.utilslib.math.vector.api.Vec2fBase;
import cmn.utilslib.math.vector.api.Vec3f;
import cmn.utilslib.math.vector.api.Vec3fBase;

public class TerrainLoader
{

	private RawModel loadToVAO(Vec3fBase[] positions, Vec2fBase[] texCoords, Vec3fBase[] normals, Vec3fBase[] tangents, int[] indices)
	{
		VAO vao = new VAO();
		
		vao.storeIndices(indices, GL15.GL_STATIC_DRAW);
		
		vao.storeData(0, positions, 0, 0, GL15.GL_STATIC_DRAW);
		vao.storeData(1, texCoords, 0, 0, GL15.GL_STATIC_DRAW);
		vao.storeData(2, normals, 0, 0, GL15.GL_STATIC_DRAW);
		vao.storeData(3, tangents, 0, 0, GL15.GL_STATIC_DRAW);
		
		
		return new RawModel(vao, indices.length);

	}
	
	public RawModel loadTerrain()
	{

		ArrayList<Integer> indices = Lists.newArrayList();
		ArrayList<Triangle> triangles = Lists.newArrayList();
		ArrayList<Vertex> vertices = Lists.newArrayList();
		
		Point3f pos;
		Vector2f texCoord;
		Vector3f normal;
		Vertex vertex;
		Triangle triangle;
		
		for(int i = 0; i < Terrain.VERTEX_COUNT; i++)
		{
			for(int j = 0; j < Terrain.VERTEX_COUNT; j++)
			{
		
				vertex = new Vertex();
				
				texCoord = new Vector2f();
				texCoord.setX((float)j / Terrain.VERTEX_COUNT_MINUS_ONE);
				texCoord.setY((float)i / Terrain.VERTEX_COUNT_MINUS_ONE);
				
				pos = new Point3f();
				pos.setX(texCoord.getX() * Terrain.SIZE);
				pos.setY(0.0f);
				pos.setZ(texCoord.getY() * Terrain.SIZE);
				
				normal = new Vector3f(0.0f, 1.0f, 0.0f);
				
				vertex.pos = pos;
				vertex.normal = normal;
				vertex.uv = texCoord;
				
				vertices.add(vertex);
			}
		}
		
		for(int gz = 0; gz < Terrain.VERTEX_COUNT_MINUS_ONE; gz++)
		{
			for(int gx = 0; gx < Terrain.VERTEX_COUNT_MINUS_ONE; gx++)
			{
				int topLeft = gz * Terrain.VERTEX_COUNT + gx;
				int topRight = topLeft + 1;
				int bottomLeft = (gz + 1) * Terrain.VERTEX_COUNT + gx;
				int bottomRight = bottomLeft + 1;
				
				indices.add(topLeft);
				indices.add(bottomLeft);
				indices.add(topRight);
				
				triangle = new Triangle();
				
				triangle.setVertexA(vertices.get(topLeft)).setVertexB(vertices.get(bottomRight)).setVertexC(vertices.get(topRight));
				
				triangles.add(triangle);
				
				indices.add(topRight);
				indices.add(bottomLeft);
				indices.add(bottomRight);
				
				triangle = new Triangle();
				
				triangle.setVertexA(vertices.get(topRight)).setVertexB(vertices.get(bottomLeft)).setVertexC(vertices.get(bottomRight));
				
				triangles.add(triangle);
			}
		}
	
		for(int i = 0; i < triangles.size(); i++)
		{
			calculateTangents(triangles.get(i));
		}
		
		Vector3f[] _positions = new Vector3f[vertices.size()];
		Vector3f[] _normals = new Vector3f[vertices.size()];
		Vector3f[] _tangents = new Vector3f[vertices.size()];
		Vector2f[] _texCoords = new Vector2f[vertices.size()];
	
		
		int[] _indices = ListUtils.toIntArray(indices);	
		
		Vertex v;
		
		for(int i = 0; i < vertices.size(); i++)
		{
			v = vertices.get(i);
			
			_positions[i] = v.pos.asVector3f(new Vector3f());
			_normals[i] = v.normal;
			_texCoords[i] = v.uv;
			_tangents[i] = v.tangent;
		}
		

		
		return loadToVAO(_positions, _texCoords, _normals, _tangents, _indices);
	}
	
	private void calculateTangents(Triangle t)
	{
		Vec3f deltaPos1 = t.getVertexB().pos.vectorFromf(t.getVertexA().pos, new Vector3f());
		Vec3f deltaPos2 = t.getVertexC().pos.vectorFromf(t.getVertexA().pos, new Vector3f());
		
		Vec2f uv0 = t.getVertexA().uv;
		Vec2f uv1 = t.getVertexB().uv;
		Vec2f uv2 = t.getVertexC().uv;
		
		Vec2f deltaUv1 = uv1.subN(uv0);
		Vec2f deltaUv2 = uv2.subN(uv0);

		float r = 1.0f / (deltaUv1.getX() * deltaUv2.getY() - deltaUv1.getY() * deltaUv2.getX());
		deltaPos1.mul(deltaUv2.getY());
		deltaPos2.mul(deltaUv1.getY());
		Vec3f tangent = deltaPos1.subN(deltaPos2);
		tangent.mul(r);
		
		t.getVertexA().tangent.add(tangent);
		t.getVertexB().tangent.add(tangent);
		t.getVertexC().tangent.add(tangent);
	}

}
