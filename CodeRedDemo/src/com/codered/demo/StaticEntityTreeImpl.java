package com.codered.demo;

import java.util.Iterator;

import com.codered.entities.StaticEntity;

public class StaticEntityTreeImpl
{

	private AABB3fBTree<StaticEntity> tree = new AABB3fBTree<>();
	
	public AABB3fBTreeWalker<StaticEntity> walker = new AABB3fBTreeWalker<>(this.tree);
	public AABB3fBTreeBuilder<StaticEntity> builder = new AABB3fBTreeBuilder<>(this.tree);
	
	public Iterator<StaticEntity> iterator()
	{
		return walker.valueIterator();
	}

	public void add(StaticEntity entity)
	{
		builder.add(entity, entity.getModel().getPhysicalMesh().getAABBf());
		walker.refreshLeafList();
	}

}
