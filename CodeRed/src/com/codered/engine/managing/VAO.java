package com.codered.engine.managing;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import cmn.utilslib.essentials.Auto;
import cmn.utilslib.essentials.BufferUtils;
import cmn.utilslib.math.vector.Vector4f;
import cmn.utilslib.math.vector.api.Vec2fBase;
import cmn.utilslib.math.vector.api.Vec3fBase;
import cmn.utilslib.math.vector.api.Vec4f;

public class VAO
{
	
	public static ArrayList<VAO> vaos = Auto.ArrayList();
	
	private int id;
	
	private HashMap<Integer,Integer> vbos = Auto.HashMap();
	
	private int indicesVBO = 0;
	
	public VAO()
	{
		
		this.id = GL30.glGenVertexArrays();
		
		vaos.add(this);
	}
	
	public int getID()
	{
		return this.id;
	}
	
	public void bind(int... ids)
	{
		GL30.glBindVertexArray(this.id);
		
		for(int i : ids)
		{
			GL20.glEnableVertexAttribArray(i);
		}
	}
	
	public static void clearAll()
	{
		for(VAO vao : vaos)
		{
			vao.clear();
		}
		
		vaos.clear();
	}
	
	
	public void storeData(int attrib, int blocksize, float[] data, int drawFlag)
	{
		bind();
		
		if(this.vbos.containsKey(attrib))
		{
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vbos.get(attrib));
			
			FloatBuffer buffer = BufferUtils.wrapFlippedFloatBuffer(data);
			
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, drawFlag);
			
			GL20.glVertexAttribPointer(attrib, blocksize, GL11.GL_FLOAT, false, 0, 0);
			
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		}
		else
		{
			int vboID = GL15.glGenBuffers();
			
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
			
			FloatBuffer buffer = BufferUtils.wrapFlippedFloatBuffer(data);
			
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, drawFlag);
			
			GL20.glVertexAttribPointer(attrib, blocksize, GL11.GL_FLOAT, false, 0, 0);
			
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
			
			vbos.put(attrib, vboID);				
		}	
	}
	
	public void storeData(int attrib, int blocksize, int[] data, int drawFlag)
	{
		bind();
		
		if(this.vbos.containsKey(attrib))
		{
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vbos.get(attrib));
			
			IntBuffer buffer = BufferUtils.wrapFlippedIntBuffer(data);
			
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, drawFlag);
			
			GL20.glVertexAttribPointer(attrib, blocksize, GL11.GL_INT, false, 0, 0);
			
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		}
		else
		{
			int vboID = GL15.glGenBuffers();
			
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
			
			IntBuffer buffer = BufferUtils.wrapFlippedIntBuffer(data);
			
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, drawFlag);
			
			GL20.glVertexAttribPointer(attrib, blocksize, GL11.GL_INT, false, 0, 0);
			
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

			vbos.put(attrib, vboID);	
		}
	}
	
	public void storeData(int attrib, Vec2fBase[] data, int drawFlag)
	{
		bind();
		
		if(this.vbos.containsKey(attrib))
		{
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vbos.get(attrib));
			
			FloatBuffer buffer = BufferUtils.wrapFlippedVector2FBuffer(data);
			
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, drawFlag);
			
			GL20.glVertexAttribPointer(attrib, 2, GL11.GL_FLOAT, false, 0, 0);
			
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		}
		else
		{
			int vboID = GL15.glGenBuffers();
			
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
			
			FloatBuffer buffer = BufferUtils.wrapFlippedVector2FBuffer(data);
			
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, drawFlag);
			
			GL20.glVertexAttribPointer(attrib, 2, GL11.GL_FLOAT, false, 0, 0);
			
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
			
			vbos.put(attrib, vboID);				
		}	
	}
	
	public void storeData(int attrib, Vec3fBase[] data, int drawFlag)
	{
		bind();
		
		if(this.vbos.containsKey(attrib))
		{
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vbos.get(attrib));
			
			FloatBuffer buffer = BufferUtils.wrapFlippedVector3FBuffer(data);
			
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, drawFlag);
			
			GL20.glVertexAttribPointer(attrib, 3, GL11.GL_FLOAT, false, 0, 0);
			
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		}
		else
		{
			int vboID = GL15.glGenBuffers();
			
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
			
			FloatBuffer buffer = BufferUtils.wrapFlippedVector3FBuffer(data);
			
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, drawFlag);
			
			GL20.glVertexAttribPointer(attrib, 3, GL11.GL_FLOAT, false, 0, 0);
			
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
			
			vbos.put(attrib, vboID);			
		}
	}
	
	public void storeData(int attrib, Vector4f[] data, int drawFlag)
	{
		bind();
		
		if(this.vbos.containsKey(attrib))
		{
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vbos.get(attrib));
			
			FloatBuffer buffer = BufferUtils.wrapFlippedVector4FBuffer(data);
			
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, drawFlag);
			
			GL20.glVertexAttribPointer(attrib, Vec4f.DIMENSIONS, GL11.GL_FLOAT, false, 0, 0);
			
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		}
		else
		{
			int vboID = GL15.glGenBuffers();
			
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
			
			FloatBuffer buffer = BufferUtils.wrapFlippedVector4FBuffer(data);
			
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, drawFlag);
			
			GL20.glVertexAttribPointer(attrib, Vec4f.DIMENSIONS, GL11.GL_FLOAT, false, 0, 0);
			
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
			
			vbos.put(attrib, vboID);				
		}
	}
	
	public void storeIndices(int[] indices, int drawflag)
	{
		bind();
		
		if(this.indicesVBO != 0)
		{
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.indicesVBO);
			
			IntBuffer buffer = BufferUtils.wrapFlippedIntBuffer(indices);
			
			GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, drawflag);
		}
		else
		{
			int vboID = GL15.glGenBuffers();
			
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
			
			IntBuffer buffer = BufferUtils.wrapFlippedIntBuffer(indices);
			
			GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, drawflag);
		
			this.indicesVBO = vboID;
		}
	}
	
	
	public void clear()
	{
		for(int i : vbos.values())
		{
			GL15.glDeleteBuffers(i);
		}
		
		if(this.indicesVBO != 0)
		{
			GL15.glDeleteBuffers(this.indicesVBO);	
		}
		
		GL30.glDeleteVertexArrays(this.id);
	}
}
