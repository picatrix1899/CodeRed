package com.codered.managing.models;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.barghos.math.geometry.AABB3f;
import org.barghos.math.geometry.ConvexTriangleMesh3f;
import org.barghos.math.geometry.Triangle3f;
import org.barghos.math.matrix.Mat4f;
import org.barghos.math.vector.Quat;
import org.barghos.math.vector.vec3.Vec3;
import org.barghos.math.vector.vec3.Vec3Pool;
import org.lwjgl.opengl.GL15;
import org.lwjgl.system.MemoryUtil;

import com.codered.CodeRed;
import com.codered.engine.EngineRegistry;
import com.codered.managing.VAO;

public class Mesh
{
	public ConvexTriangleMesh3f physicalMesh;
	public int vertexCount;
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
		return this.vertexCount;
	}
	
	public float getScale()
	{
		return this.downscale;
	}
	
	public Mesh calculateDownscaleAndYCorrection()
	{
		AABB3f aabb = this.physicalMesh.getAABBf();
		Vec3 min = aabb.getMin(Vec3Pool.get());
		Vec3 max = aabb.getMax(Vec3Pool.get());
		
		if(min.getY() != 0)
		{
			this.yc = -min.getY();			
		}
		
		if(max.getY() - min.getY() != CodeRed.SCALE_ONE_METER)
		{
			float d = CodeRed.SCALE_ONE_METER - (max.getY() - min.getY());
			
			float o = 1.0f / (max.getY() - min.getY());
			
			float r = 0.0f;
			
			r = o * d;
			
			downscale = 1.0f + (r);
		}
		
		Vec3Pool.store(min, max);
		
		return this;
	}
	
	public Mat4f getMatrix()
	{
		return Mat4f.modelMatrix(new Vec3(0.0f,  yc,  0.0f), new Quat(), new Vec3(downscale));
	}
	
	public float getYCorrection()
	{
		return this.yc;
	}
	
	public Mesh loadFromMesh(org.haze.obj.Mesh obj)
	{
		List<Triangle3f> triangles = new ArrayList<>();
		
		this.vertexCount = obj.faces.size() * 3;
		
		FloatBuffer bp = MemoryUtil.memAllocFloat(this.vertexCount * 3);
		FloatBuffer bn = MemoryUtil.memAllocFloat(this.vertexCount * 3);
		FloatBuffer buv = MemoryUtil.memAllocFloat(this.vertexCount * 2);
		FloatBuffer bt = MemoryUtil.memAllocFloat(this.vertexCount * 3);
		IntBuffer bind = MemoryUtil.memAllocInt(this.vertexCount * 3);
		
		int vIndex = 0;
		for(int i = 0; i < obj.faces.size(); i++)
		{
			org.haze.obj.Face face = obj.faces.get(i);
			
			bp.put(face.vertexA.position.getX());
			bp.put(face.vertexA.position.getY());
			bp.put(face.vertexA.position.getZ());
			bp.put(face.vertexB.position.getX());
			bp.put(face.vertexB.position.getY());
			bp.put(face.vertexB.position.getZ());
			bp.put(face.vertexC.position.getX());
			bp.put(face.vertexC.position.getY());
			bp.put(face.vertexC.position.getZ());
			
			bn.put(face.vertexA.normal.getX());
			bn.put(face.vertexA.normal.getY());
			bn.put(face.vertexA.normal.getZ());
			bn.put(face.vertexB.normal.getX());
			bn.put(face.vertexB.normal.getY());
			bn.put(face.vertexB.normal.getZ());
			bn.put(face.vertexC.normal.getX());
			bn.put(face.vertexC.normal.getY());
			bn.put(face.vertexC.normal.getZ());
			
			bt.put(face.vertexA.tangent.getX());
			bt.put(face.vertexA.tangent.getY());
			bt.put(face.vertexA.tangent.getZ());
			bt.put(face.vertexB.tangent.getX());
			bt.put(face.vertexB.tangent.getY());
			bt.put(face.vertexB.tangent.getZ());
			bt.put(face.vertexC.tangent.getX());
			bt.put(face.vertexC.tangent.getY());
			bt.put(face.vertexC.tangent.getZ());
			
			buv.put(face.vertexA.uv.getX());
			buv.put(-face.vertexA.uv.getY());
			buv.put(face.vertexB.uv.getX());
			buv.put(-face.vertexB.uv.getY());
			buv.put(face.vertexC.uv.getX());
			buv.put(-face.vertexC.uv.getY());

			bind.put(vIndex++);
			bind.put(vIndex++);
			bind.put(vIndex++);
			
			Triangle3f tr = new Triangle3f(face.vertexA.position, face.vertexB.position, face.vertexC.position);
			
			triangles.add(tr);
		}

		

		bp.flip();
		buv.flip();
		bn.flip();
		bt.flip();
		bind.flip();
		
		loadToVAO0(bp, buv, bn, bt, bind);
		
		MemoryUtil.memFree(bp);
		MemoryUtil.memFree(buv);
		MemoryUtil.memFree(bn);
		MemoryUtil.memFree(bt);

		this.physicalMesh = new ConvexTriangleMesh3f(triangles);

		calculateDownscaleAndYCorrection();
		
		return this;
	}
	
	private Mesh loadToVAO0(FloatBuffer positions, FloatBuffer texCoords, FloatBuffer normals, FloatBuffer tangents, IntBuffer indices)
	{
		this.vao = EngineRegistry.getVAOManager().getNewVAO();
		
		this.vao.storeIndices(indices, GL15.GL_STATIC_DRAW);
		
		this.vao.storeData(0, 3, positions, 0, 0, GL15.GL_STATIC_DRAW);
		this.vao.storeData(1, 2, texCoords, 0, 0, GL15.GL_STATIC_DRAW);
		this.vao.storeData(2, 3, normals, 0, 0, GL15.GL_STATIC_DRAW);
		this.vao.storeData(3, 3, tangents, 0, 0, GL15.GL_STATIC_DRAW);
		
		return this;
	}
}
