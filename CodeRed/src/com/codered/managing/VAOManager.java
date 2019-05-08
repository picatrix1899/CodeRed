package com.codered.managing;

import java.util.ArrayList;
import java.util.List;

public class VAOManager
{
	private List<VAO> vaos = new ArrayList<>();
	
	public VAO getNewVAO()
	{
		VAO vao = new VAO();
		this.vaos.add(vao);
		return vao;
	}
	
	public void release()
	{
		int size = vaos.size();
		for(int i = 0; i < size; i++)
		{
			vaos.get(i).release();
		}
		
		vaos.clear();
	}
}
