package com.codered;

import java.util.ArrayDeque;
import java.util.Deque;

import com.codered.entities.StaticEntity;

import cmn.utilslib.math.btree.BTree;
import cmn.utilslib.math.btree.BTreeContent;
import cmn.utilslib.math.btree.BTreeEvaluation;
import cmn.utilslib.math.btree.BTreeLeaf;
import cmn.utilslib.math.btree.BTreeNode;
import cmn.utilslib.math.geometry.AABB3f;
import cmn.utilslib.math.vector.Vector3f;

public class StaticEntityTreeBuilder
{
	private BTree<StaticEntity,AABB3f> tree;
	
	public StaticEntityTreeBuilder(BTree<StaticEntity,AABB3f> tree)
	{
		this.tree = tree;
	}
	
	public void add(StaticEntity entity, AABB3f aabb, BTreeEvaluation<AABB3f> evaluation)
	{
		BTreeNode<StaticEntity,AABB3f> currentNode = null;
		
		Vector3f min = aabb.center.asVector3f().sub(aabb.halfExtend);
		Vector3f max = aabb.center.asVector3f().add(aabb.halfExtend);
		
		Vector3f minA = currentNode.a.evalData.center.asVector3f().sub(aabb.halfExtend);
		Vector3f maxA = currentNode.a.evalData.center.asVector3f().add(aabb.halfExtend);
		
		
		
		if(currentNode.b != null)
		{
			Vector3f minB = currentNode.b.evalData.center.asVector3f().sub(aabb.halfExtend);
			Vector3f maxB = currentNode.b.evalData.center.asVector3f().add(aabb.halfExtend);
		}

	}
	
	
	public BTreeNode<StaticEntity,AABB3f> walkToNode(BTreeEvaluation<AABB3f> evaluation)
	{
		Deque<BTreeContent<StaticEntity,AABB3f>> queue = new ArrayDeque<BTreeContent<StaticEntity,AABB3f>>();
		
		BTreeContent<StaticEntity,AABB3f> current;
		BTreeNode<StaticEntity,AABB3f> currentNode;
		BTreeLeaf<StaticEntity,AABB3f> currentLeaf;
		
		queue.add(this.tree);
		
		while(!queue.isEmpty())
		{
			current = queue.poll();
			
			if(current instanceof BTreeNode)
			{
				currentNode = (BTreeNode<StaticEntity,AABB3f>)current;
				
				if(evaluation.eval(currentNode.b.evalData))
				{
					queue.addFirst(currentNode.b);
				}
				
				if(evaluation.eval(currentNode.a.evalData))
				{
					queue.addFirst(currentNode.a);
				}
			}
			else
			{
				currentLeaf = (BTreeLeaf<StaticEntity,AABB3f>)current;
				return currentLeaf;
			}
		}
		
		return null;
	}
}
