package com.codered.managing;

public class Triangle
{
	private Vertex vA;
	private Vertex vB;
	private Vertex vC;
	
	public Vertex getVertexA()
	{
		return this.vA;
	}
	
	public Vertex getVertexB()
	{
		return this.vB;
	}
	
	public Vertex getVertexC()
	{
		return this.vC;
	}

	public Triangle setVertexA(Vertex v)
	{
		this.vA = v;
		
		return this;
	}
	
	public Triangle setVertexB(Vertex v)
	{
		this.vB = v;
		
		return this;
	}
	
	public Triangle setVertexC(Vertex v)
	{
		this.vC = v;
		
		return this;
	}
}
