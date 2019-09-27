package com.codered.managing.models;

import java.util.ArrayList;
import java.util.List;

import org.barghos.core.ListUtils;
import org.barghos.math.geometry.Triangle3f;
import org.barghos.math.matrix.Mat4f;
import org.barghos.math.point.Point3f;
import org.barghos.math.vector.Quat;
import org.barghos.math.vector.Vec2f;
import org.barghos.math.vector.Vec3f;

import org.lwjgl.opengl.GL15;

import com.codered.engine.EngineRegistry;
import com.codered.managing.VAO;
import com.codered.resource.object.ObjectData;
import com.codered.resource.object.TriangleData;

public class Mesh
{
	public List<Triangle3f> triangles = new ArrayList<>();
	public List<TriangleData>triangleData = new ArrayList<>();
	public List<Integer> indices = new ArrayList<>();
	
	private float yc = 0.0f;
	
	private float downscale = 1.0f;
	
	public VAO vao;
	
	public VAO getVAO()
	{
		return this.vao;
	}
	
	public int getVAOID()
	{
		return this.vao.getID();
	}
	
	public int getVertexCount()
	{
		return this.triangles.size() * 3;
	}
	
	public int getTriangleCount()
	{
		return this.triangles.size();
	}
	
	public float getScale()
	{
		return this.downscale;
	}
	
	private void calculateAABB(Point3f[] p)
	{
		
		Vec3f min_point = new Vec3f(1000.0f);
		Vec3f max_point = new Vec3f(0.0f);
		
		for(Point3f pos : p)
		{
			if(pos.getX() < min_point.getX())
			{
				min_point.setX(pos.getX());
			}
			
			if(pos.getY() < min_point.getY())
			{
				min_point.setY(pos.getY());
			}
			
			if(pos.getZ() < min_point.getZ())
			{
				min_point.setZ(pos.getZ());
			}
			
			if(pos.getX() > max_point.getX())
			{
				max_point.setX(pos.getX());
			}
			
			if(pos.getY() > max_point.getY())
			{
				max_point.setY(pos.getY());
			}
			
			if(pos.getZ() > max_point.getZ())
			{
				max_point.setZ(pos.getZ());
			}
		}
		
		setAABB(min_point, max_point);
	}
	
	
	public Mesh setAABB(Vec3f min, Vec3f max)
	{

		if(min.getY() != 0)
		{
			this.yc = -min.getY();			
		}
		
		if(max.getY() - min.getY() != 10.0f)
		{
			float d = 10.0f - (max.getY() - min.getY());
			
			float o = 1.0f / (max.getY() - min.getY());
			
			float r = 0.0f;
			
			r = o * d;
			
			downscale = 1.0f + (r);
		}
		
		
		return this;
	}
	
	public Mat4f getMatrix()
	{
		return Mat4f.modelMatrix(new Vec3f(0.0f,  yc,  0.0f), new Quat(), new Vec3f(downscale));
	}
	
	public float getYCorrection()
	{
		return this.yc;
	}
	
	public Mesh loadFromObj(ObjectData obj)
	{
		

		this.indices = obj.indices;
		this.triangles = obj.triangles;
		this.triangleData = obj.data;

		int triangleCount = this.triangles.size();
		int verticesCount = triangleCount * 3;
		
		Point3f[] pos = new Point3f[verticesCount];
		Vec2f[] uvs = new Vec2f[verticesCount];
		Vec3f[] nrm = new Vec3f[verticesCount];
		Vec3f[] tng = new Vec3f[verticesCount];
		
		int[] indices =	 new int[this.indices.size()];
		
		Triangle3f tr;
		TriangleData td; 
		
		for(int i = 0; i < triangleCount; i++)
		{
			tr = this.triangles.get(i); 
			td = this.triangleData.get(i);
			
			pos[i * 3] = tr.getP1(null);
			pos[i * 3 + 1] = tr.getP2(null);
			pos[i * 3 + 2] = tr.getP3(null);
			
			uvs[i * 3] = td.uvA.mul(1,-1, null);
			uvs[i * 3 + 1] = td.uvB.mul(1,-1, null);
			uvs[i * 3 + 2] = td.uvC.mul(1,-1, null);
			
			nrm[i * 3] = td.normalA;
			nrm[i * 3 + 1] = td.normalB;
			nrm[i * 3 + 2] = td.normalC;
			
			tng[i * 3] = td.tangentA;
			tng[i * 3 + 1] = td.tangentB;
			tng[i * 3 + 2] = td.tangentC;
			
		}
		
		calculateAABB(pos);
		
		indices = ListUtils.toIntArray(this.indices);
		
		return loadToVAO0(pos, uvs, nrm, tng, indices);
		
	}
	
	private Mesh loadToVAO0(Point3f[] positions, Vec2f[] texCoords, Vec3f[] normals, Vec3f[] tangents, int[] indices)
	{
		this.vao = EngineRegistry.getVAOManager().getNewVAO();
		
		this.vao.storeIndices(indices, GL15.GL_STATIC_DRAW);
		
		this.vao.storeData(0, positions, 0, 0, GL15.GL_STATIC_DRAW);
		this.vao.storeData(1, texCoords, 0, 0, GL15.GL_STATIC_DRAW);
		this.vao.storeData(2, normals, 0, 0, GL15.GL_STATIC_DRAW);
		this.vao.storeData(3, tangents, 0, 0, GL15.GL_STATIC_DRAW);
		
		return this;
	}
}
