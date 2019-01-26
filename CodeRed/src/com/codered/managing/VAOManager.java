package com.codered.managing;

import java.util.Set;

import com.google.common.collect.Sets;

public class VAOManager
{
	private Set<VAO> vaos = Sets.newHashSet();
	
	public VAO getNewVAO()
	{
		VAO vao = new VAO();
		this.vaos.add(vao);
		return vao;
	}
	
	public void release()
	{
		for(VAO vao : vaos)
		{
			vao.release();
		}
		
		vaos.clear();
	}
}
