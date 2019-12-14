package com.codered.model;

import java.util.ArrayList;
import java.util.List;

public class StaticModel
{
	private List<Mesh> meshes = new ArrayList<>();
	
	public StaticModel(List<Mesh> meshes)
	{
		this.meshes = meshes;
	}
	
	public void draw(float alpha)
	{
		
	}
}
