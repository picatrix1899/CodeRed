package com.codered;

import java.util.Iterator;

import com.codered.entities.StaticEntity;

import cmn.utilslib.math.btree.AABB3fBTree;
import cmn.utilslib.math.btree.AABB3fBTreeBuilder;
import cmn.utilslib.math.btree.AABB3fBTreeWalker;

public class StaticEntityTreeImpl implements StaticEntityTree
{

	private AABB3fBTree<StaticEntity> tree = new AABB3fBTree<StaticEntity>();
	
	public AABB3fBTreeWalker<StaticEntity> walker = new AABB3fBTreeWalker<StaticEntity>(this.tree);
	public AABB3fBTreeBuilder<StaticEntity> builder = new AABB3fBTreeBuilder<StaticEntity>(this.tree);
	
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
