package com.codered.managing;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.codered.utils.BindingUtils;

import cmn.utilslib.essentials.Auto;
import cmn.utilslib.essentials.BufferUtils;
import cmn.utilslib.math.vector.Vector4f;
import cmn.utilslib.math.vector.api.Vec2fBase;
import cmn.utilslib.math.vector.api.Vec3fBase;

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
	
	public static void clearAll()
	{
		for(VAO vao : vaos)
		{
			vao.clear();
		}
		
		vaos.clear();
	}
	
	
	public void storeData(int attrib, int blocksize, float[] data, int stride, int pointer, int drawFlag)
	{
		BindingUtils.bindVAO(this);
		
		int vboID = 0;

		if(!this.vbos.containsKey(attrib))
		{
			vboID = GL15.glGenBuffers();
			vbos.put(attrib, vboID);
		}
		else
			vboID = this.vbos.get(attrib);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		
		FloatBuffer buffer = BufferUtils.wrapFlippedFloatBuffer(data);
		
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, drawFlag);
		
		GL20.glVertexAttribPointer(attrib, blocksize, GL11.GL_FLOAT, false, stride, pointer);
	}
	
	public void storeData(int attrib, int blocksize, int[] data, int stride, int pointer, int drawFlag)
	{
		BindingUtils.bindVAO(this);
		
		int vboID = 0;

		if(!this.vbos.containsKey(attrib))
		{
			vboID = GL15.glGenBuffers();
			vbos.put(attrib, vboID);
		}
		else
			vboID = this.vbos.get(attrib);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		
		IntBuffer buffer = BufferUtils.wrapFlippedIntBuffer(data);
		
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, drawFlag);
		
		GL20.glVertexAttribPointer(attrib, blocksize, GL11.GL_INT, false, stride, pointer);
	}
	
	public void storeData(int attrib, Vec2fBase[] data, int stride, int pointer, int drawFlag)
	{
		BindingUtils.bindVAO(this);
		
		int vboID = 0;

		if(!this.vbos.containsKey(attrib))
		{
			vboID = GL15.glGenBuffers();
			vbos.put(attrib, vboID);
		}
		else
			vboID = this.vbos.get(attrib);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		
		FloatBuffer buffer = BufferUtils.wrapFlippedVector2FBuffer(data);
		
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, drawFlag);
		
		GL20.glVertexAttribPointer(attrib, 2, GL11.GL_FLOAT, false, stride, pointer);
	}
	
	public void storeData(int attrib, Vec3fBase[] data, int stride, int pointer, int drawFlag)
	{
		BindingUtils.bindVAO(this);
		
		int vboID = 0;

		if(!this.vbos.containsKey(attrib))
		{
			vboID = GL15.glGenBuffers();
			vbos.put(attrib, vboID);
		}
		else
			vboID = this.vbos.get(attrib);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		
		FloatBuffer buffer = BufferUtils.wrapFlippedVector3FBuffer(data);
		
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, drawFlag);
		
		GL20.glVertexAttribPointer(attrib, 3, GL11.GL_FLOAT, false, stride, pointer);
	}
	
	public void storeData(int attrib, Vector4f[] data, int stride, int pointer, int drawFlag)
	{
		BindingUtils.bindVAO(this);
		
		int vboID = 0;

		if(!this.vbos.containsKey(attrib))
		{
			vboID = GL15.glGenBuffers();
			vbos.put(attrib, vboID);
		}
		else
			vboID = this.vbos.get(attrib);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		
		FloatBuffer buffer = BufferUtils.wrapFlippedVector4FBuffer(data);
		
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, drawFlag);
		
		GL20.glVertexAttribPointer(attrib, 4, GL11.GL_FLOAT, false, stride, pointer);
}
	
	public void storeIndices(int[] indices, int drawflag)
	{
		BindingUtils.bindVAO(this);
		
		if(this.indicesVBO == 0) this.indicesVBO = GL15.glGenBuffers();

		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.indicesVBO);
		
		IntBuffer buffer = BufferUtils.wrapFlippedIntBuffer(indices);
		
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, drawflag);
	}
	
	public void clearVBOs()
	{
		BindingUtils.bindVAO(this);
		
		for(int i : vbos.values())
		{
			GL15.glDeleteBuffers(i);
		}
		
		if(this.indicesVBO != 0)
		{
			GL15.glDeleteBuffers(this.indicesVBO);	
		}
		
		vbos.clear();
		
		indicesVBO = 0;
		
		GL30.glDeleteVertexArrays(this.id);
		
		vaos.remove(this);
		
		this.id = GL30.glGenVertexArrays();
		
		vaos.add(this);
	}
	
	public void clear()
	{
		BindingUtils.bindVAO(this);
		
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
