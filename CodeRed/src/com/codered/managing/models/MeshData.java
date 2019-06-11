package com.codered.managing.models;

import java.util.ArrayList;
import java.util.List;

import org.barghos.core.ListUtils;
import org.barghos.math.geometry.AABB3f;
import org.barghos.math.geometry.ConvexTriangleMesh3f;
import org.barghos.math.geometry.Triangle3f;
import org.barghos.math.matrix.Mat4f;
import org.barghos.math.point.Point3f;
import org.barghos.math.vector.Vec2f;
import org.barghos.math.vector.Vec3f;
import org.resources.objects.ObjectData;
import org.resources.objects.TriangleData;

import com.codered.CodeRed;

public class MeshData
{
	public ConvexTriangleMesh3f physicalMesh;
	public List<Triangle3f> triangles = new ArrayList<>();
	public List<TriangleData>triangleData = new ArrayList<>();
	public List<Integer> indices = new ArrayList<>();
	
	public Point3f[] pos;
	public Vec2f[] uvs;
	public Vec3f[] nrm;
	public Vec3f[] tng;
	public int[] ind;

	private float downscale = 1.0f;

	public MeshData(ObjectData obj)
	{
		this.indices = obj.indices;
		this.triangleData = obj.data;

		ConvexTriangleMesh3f mesh = new ConvexTriangleMesh3f(obj.triangles);

		AABB3f aabb = mesh.getAABBf();
		
		if(aabb.getMin().getY() != 0)
		{
			mesh.transform(Mat4f.translation(0, -aabb.getMin().getY() , 0), mesh);
		}
		
		float height = aabb.getMax().getY() - aabb.getMin().getY();
		
		if(height != 10.0f)
		{
			downscale = 1.0f + (10.0f - height) / height;
		}

		this.triangles = mesh.getTriangles();
		
		int triangleCount = this.triangles.size();
		int verticesCount = triangleCount * 3;
		
		this.pos = new Point3f[verticesCount];
		this.uvs = new Vec2f[verticesCount];
		this.nrm = new Vec3f[verticesCount];
		this.tng = new Vec3f[verticesCount];
		
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
		
		ind = ListUtils.toIntArray(this.indices);
	}
	
	public int getVertexCount()
	{
		return this.ind.length * 3;
	}

	public float getDownscaleScale()
	{
		return this.downscale;
	}

}
