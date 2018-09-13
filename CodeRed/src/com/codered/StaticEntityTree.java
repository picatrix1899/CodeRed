package com.codered;

import java.util.Iterator;

import com.codered.entities.StaticEntity;

public interface StaticEntityTree
{
	public Iterator<StaticEntity> iterator();
	
	public void add(StaticEntity entity);
}
