package com.codered.managing.models;

import java.util.ArrayList;

import org.barghos.math.matrix.Mat4f;
import org.barghos.math.vector.Quat;
import org.barghos.math.vector.Vec3f;

import org.lwjgl.opengl.GL15;

import com.codered.EngineRegistry;
import com.codered.managing.VAO;
import com.codered.managing.loader.TriangleData;
import com.codered.managing.loader.data.OBJFile;

import cmn.utilslib.essentials.Auto;
import cmn.utilslib.essentials.ListUtils;
import cmn.utilslib.math.geometry.Triangle3f;
import cmn.utilslib.math.vector.Vector2f;
import cmn.utilslib.math.vector.Vector3f;
public class Mesh
{
	public ArrayList<Triangle3f> triangles = Auto.ArrayList();
	public ArrayList<TriangleData>triangleData = Auto.ArrayList();
	public ArrayList<Integer> indices = Auto.ArrayList();
	
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
	
	private void calculateAABB(Vector3f[] p)
	{
		
		Vector3f min_point = new Vector3f(1000.0f);
		Vector3f max_point = new Vector3f(0.0f);
		
		for(Vector3f pos : p)
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
	
	
	public Mesh setAABB(Vector3f min, Vector3f max)
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
	
	public Mesh loadFromObj(OBJFile obj)
	{
		this.indices = obj.indices;
		this.triangles = obj.triangles;
		this.triangleData = obj.data;

		int triangleCount = this.triangles.size();
		int verticesCount = triangleCount * 3;
		
		Vector3f[] pos = new Vector3f[verticesCount];
		Vector2f[] uvs = new Vector2f[verticesCount];
		Vector3f[] nrm = new Vector3f[verticesCount];
		Vector3f[] tng = new Vector3f[verticesCount];
		
		int[] indices =	 new int[this.indices.size()];
		
		Triangle3f tr;
		TriangleData td; 
		
		for(int i = 0; i < triangleCount; i++)
		{
			tr = this.triangles.get(i); 
			td = this.triangleData.get(i);
			
			pos[i * 3] = tr.a.asVector3f(new Vector3f());
			pos[i * 3 + 1] = tr.b.asVector3f(new Vector3f());
			pos[i * 3 + 2] = tr.c.asVector3f(new Vector3f());
			
			uvs[i * 3] = td.uvA.mulN(1,-1);
			uvs[i * 3 + 1] = td.uvB.mulN(1,-1);
			uvs[i * 3 + 2] = td.uvC.mulN(1,-1);
			
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
	
	private Mesh loadToVAO0(Vector3f[] positions,Vector2f[] texCoords,Vector3f[] normals, Vector3f[] tangents, int[] indices)
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
