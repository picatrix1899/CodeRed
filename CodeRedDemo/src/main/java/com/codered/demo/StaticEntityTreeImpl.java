package com.codered.demo;

import java.util.Iterator;

import com.codered.entities.StaticEntity;
import com.codered.entities.StaticModelEntity;

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
		if(entity instanceof StaticModelEntity)
		{
			StaticModelEntity e = (StaticModelEntity)entity;
			for(com.codered.model.Mesh mesh : e.getNewModel().getMeshes())
			{
				builder.add(entity, mesh.getCollisionMesh().get().getAABBf());
			}
			
		}
		else
		{
			builder.add(entity, entity.getModel().getModel().getCollisionMesh().get().getAABBf());
		}
		

		walker.refreshLeafList();
	}

}
