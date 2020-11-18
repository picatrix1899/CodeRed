package com.codered.resource.model;

import org.barghos.core.tuple3.api.Tup3fR;
import org.barghos.math.vector.vec3.Vec3f;
import org.barghos.math.vector.vec3.Vec3fR;

public class FaceData
{
	private VertexData vertexA;
	private VertexData vertexB;
	private VertexData vertexC;
	private Vec3f normal = new Vec3f();
	
	public FaceData(VertexData vA, VertexData vB, VertexData vC, Tup3fR normal)
	{
		this.vertexA = vA;
		this.vertexB = vB;
		this.vertexC = vC;
		this.normal.set(normal);
	}
	
	public VertexData getVertexA()
	{
		return this.vertexA;
	}
	
	public VertexData getVertexB()
	{
		return this.vertexB;
	}
	
	public VertexData getVertexC()
	{
		return this.vertexC;
	}
	
	public Vec3fR getNormal()
	{
		return this.normal;
	}
}
