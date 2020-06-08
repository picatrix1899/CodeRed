package com.codered.managing;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryUtil;

import com.codered.ResourceHolder;
import com.codered.utils.BindingUtils;
import com.codered.utils.GLCommon;

import org.barghos.core.tuple.tuple2.Tup2fR;
import org.barghos.core.tuple.tuple3.Tup3fR;
import org.barghos.core.util.BufferUtils;

public class VAO implements ResourceHolder
{
	private int id;
	
	private Map<Integer,Integer> vbos = new HashMap<>();
	
	private int indicesVBO = 0;
	
	public VAO()
	{
		this.id = GLCommon.genVertexArrays();
	}
	
	public VAO(int id)
	{
		this.id = id;
	}
	
	public int getID()
	{
		return this.id;
	}
	
	public void storeData(int attrib, int blocksize, FloatBuffer buffer, int stride, int pointer, int drawFlag)
	{
		BindingUtils.bindVAO(this);
		
		int vboID = 0;

		if(!this.vbos.containsKey(attrib))
		{
			vboID = GLCommon.genBuffers();
			vbos.put(attrib, vboID);
		}
		else
			vboID = this.vbos.get(attrib);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, drawFlag);
		
		GL20.glVertexAttribPointer(attrib, blocksize, GL11.GL_FLOAT, false, stride, pointer);
	}
	
	public void storeData(int attrib, int blocksize, float[] data, int stride, int pointer, int drawFlag)
	{
		BindingUtils.bindVAO(this);
		
		int vboID = 0;

		if(!this.vbos.containsKey(attrib))
		{
			vboID = GLCommon.genBuffers();
			vbos.put(attrib, vboID);
		}
		else
			vboID = this.vbos.get(attrib);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		
		FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
		BufferUtils.copyToFlippedFloatBuffer(buffer, data);
		
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, drawFlag);
		
		GL20.glVertexAttribPointer(attrib, blocksize, GL11.GL_FLOAT, false, stride, pointer);
		
		MemoryUtil.memFree(buffer);
	}
	
	public void storeData(int attrib, int blocksize, int[] data, int stride, int pointer, int drawFlag)
	{
		BindingUtils.bindVAO(this);
		
		int vboID = 0;

		if(!this.vbos.containsKey(attrib))
		{
			vboID = GLCommon.genBuffers();
			vbos.put(attrib, vboID);
		}
		else
			vboID = this.vbos.get(attrib);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		
		IntBuffer buffer = MemoryUtil.memAllocInt(data.length);
		BufferUtils.copyToFlippedIntBuffer(buffer, data);
		
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, drawFlag);
		
		GL20.glVertexAttribPointer(attrib, blocksize, GL11.GL_INT, false, stride, pointer);
		
		MemoryUtil.memFree(buffer);
	}
	
	public void storeData(int attrib, Tup2fR[] data, int stride, int pointer, int drawFlag)
	{
		BindingUtils.bindVAO(this);
		
		int vboID = 0;

		if(!this.vbos.containsKey(attrib))
		{
			vboID = GLCommon.genBuffers();
			vbos.put(attrib, vboID);
		}
		else
			vboID = this.vbos.get(attrib);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		
		FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length * 2);
		BufferUtils.copyToFlippedTuple2FBuffer(buffer, data);
		
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, drawFlag);
		
		GL20.glVertexAttribPointer(attrib, 2, GL11.GL_FLOAT, false, stride, pointer);
		
		MemoryUtil.memFree(buffer);
	}
	
	public void storeData(int attrib, Tup3fR[] data, int stride, int pointer, int drawFlag)
	{
		BindingUtils.bindVAO(this);
		
		int vboID = 0;

		if(!this.vbos.containsKey(attrib))
		{
			vboID = GLCommon.genBuffers();
			vbos.put(attrib, vboID);
		}
		else
			vboID = this.vbos.get(attrib);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		
		FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length * 3);
		BufferUtils.copyToFlippedTuple3FBuffer(buffer, data);
		
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, drawFlag);
		
		GL20.glVertexAttribPointer(attrib, 3, GL11.GL_FLOAT, false, stride, pointer);
		
		MemoryUtil.memFree(buffer);
	}
	
	public void storeIndices(int[] indices, int drawflag)
	{
		BindingUtils.bindVAO(this);
		
		if(this.indicesVBO == 0) this.indicesVBO = GLCommon.genBuffers();

		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.indicesVBO);
		
		IntBuffer buffer = MemoryUtil.memAllocInt(indices.length);
		BufferUtils.copyToFlippedIntBuffer(buffer, indices);
				
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, drawflag);
		
		MemoryUtil.memFree(buffer);
	}

	public void storeIndices(IntBuffer buffer, int drawflag)
	{
		BindingUtils.bindVAO(this);
		
		if(this.indicesVBO == 0) this.indicesVBO = GLCommon.genBuffers();

		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.indicesVBO);
				
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, drawflag);
		
		MemoryUtil.memFree(buffer);
	}
	
	public void clearVBOs()
	{
		BindingUtils.bindVAO(this);
		
		for(int i : vbos.values())
		{
			GLCommon.deleteBuffers(i);
		}
		
		if(this.indicesVBO != 0)
		{
			GLCommon.deleteBuffers(this.indicesVBO);	
		}
		
		vbos.clear();
		
		indicesVBO = 0;
		
		GLCommon.deleteVertexArrays(this.id);

		this.id = GLCommon.genVertexArrays();
	}

	public void release(boolean forced)
	{
		BindingUtils.bindVAO(this);
		
		for(int i : vbos.values())
		{
			GLCommon.deleteBuffers(i);
		}
		
		if(this.indicesVBO != 0)
		{
			GLCommon.deleteBuffers(this.indicesVBO);	
		}
		
		GLCommon.deleteVertexArrays(this.id);
	}
}
