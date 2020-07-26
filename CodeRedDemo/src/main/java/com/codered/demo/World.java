package com.codered.demo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.codered.entities.StaticModelEntity;
import com.codered.model.Model;

public class World
{
	private ArrayList<StaticModelEntity> entities = new ArrayList<>();
	
	public void add(StaticModelEntity entity)
	{
		this.entities.add(entity);
	}
	
	public Map<Model, List<StaticModelEntity>> getMapByModel()
	{
		return entities.stream().collect(Collectors.groupingBy(p -> p.getNewModel()));
	}
	
	public Iterator<StaticModelEntity> iterator()
	{
		return entities.iterator();
	}
}
