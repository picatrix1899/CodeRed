package com.codered;

import java.util.Iterator;

import com.codered.entities.StaticEntity;

import cmn.utilslib.math.btree.BTree;
import cmn.utilslib.math.btree.BTreeWalker;
import cmn.utilslib.math.geometry.AABB3f;

public class StaticEntityTreeImpl implements StaticEntityTree
{

	private BTree<StaticEntity, AABB3f> tree = new BTree<StaticEntity, AABB3f>();
	
	private BTreeWalker<StaticEntity, AABB3f> walker = new BTreeWalker<StaticEntity,AABB3f>(this.tree);
	
	public Iterator<StaticEntity> iterator()
	{
		return walker.valueIterator();
	}

	public void add(StaticEntity entity)
	{
	}

}
