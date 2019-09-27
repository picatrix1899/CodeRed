package com.codered.managing;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL30;

public class DataManager
{
	private List<VAO> vaos = new ArrayList<>();
	
	
	public VAO getVAO()
	{
		VAO vao = new VAO(GL30.glGenVertexArrays());
		this.vaos.add(vao);
		return vao;
	}
	
	
	
	public void release()
	{
		int size = vaos.size();
		for(int i = 0; i < size; i++)
			vaos.get(i).release();
		vaos.clear();
		
	}
}
