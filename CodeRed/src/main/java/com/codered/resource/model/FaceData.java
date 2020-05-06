package com.codered.resource.model;

import org.barghos.core.tuple.tuple3.Tup3fR;
import org.barghos.math.vector.vec3.Vec3;
import org.barghos.math.vector.vec3.Vec3R;

public class FaceData
{
	private VertexData vertexA;
	private VertexData vertexB;
	private VertexData vertexC;
	private Vec3 normal = new Vec3();
	
	public FaceData(VertexData vA, VertexData vB, VertexData vC, Tup3fR normal)
	{
		this.vertexA = vA;
		this.vertexB = vB;
		this.vertexC = vC;
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
	
	public Vec3R getNormal()
	{
		return this.normal;
	}
}
