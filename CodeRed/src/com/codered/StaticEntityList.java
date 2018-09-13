package com.codered;

import java.util.ArrayList;
import java.util.Iterator;

import com.codered.entities.StaticEntity;

public class StaticEntityList implements StaticEntityTree
{
	private ArrayList<StaticEntity> list = new ArrayList<StaticEntity>();

	public Iterator<StaticEntity> iterator()
	{
		return list.iterator();
	}

	public void add(StaticEntity entity)
	{
		this.list.add(entity);
	}
	
	
}
