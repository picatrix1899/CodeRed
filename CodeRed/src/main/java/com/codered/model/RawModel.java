package com.codered.model;

import com.codered.managing.VAO;

public class RawModel
{
	
	private VAO vao;
	private int vertexCount;
	
	
	
	public RawModel(VAO vao, int vertexCount)
	{
		this.vao = vao;
		this.vertexCount = vertexCount;
	}
	
	
	public VAO getVAO() { return this.vao; }
	
	public int getVaoID() { return vao.getID(); }

	public int getVertexCount() { return vertexCount; }
	
}
