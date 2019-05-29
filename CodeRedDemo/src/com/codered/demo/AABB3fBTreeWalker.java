package com.codered.demo;

import java.util.ArrayList;

import org.barghos.math.geometry.AABB3f;
import org.barghos.math.point.Point3f;
import org.barghos.structs.simpletree.btree.BTree;
import org.barghos.structs.simpletree.btree.BTreeWalker;


public class AABB3fBTreeWalker<T> extends BTreeWalker<T, AABB3f>
{

	public AABB3fBTreeWalker(BTree<T,AABB3f> tree)
	{
		super(tree);
	}

	public ArrayList<T> walk(AABB3f aabb)
	{
		return super.walk((AABB3f oldAABB) -> { return isOverlapping(oldAABB, aabb);
			
		});
	}

	protected boolean isOverlapping(AABB3f oldEvalData, AABB3f newEvalData)
	{
		Point3f minA = newEvalData.getMin();
		Point3f maxA = newEvalData.getMax();
		
		return isPointInside(minA, oldEvalData) || isPointInside(maxA, oldEvalData);
		
		
	}
	
	private boolean isPointInside(Point3f point, AABB3f area)
	{
		Point3f minB = area.getMin();
		Point3f maxB = area.getMax();
		
		return minB.x <= point.x && point.x <= maxB.x &&
			   minB.y <= point.y && point.y <= maxB.y &&
			   minB.z <= point.z && point.z <= maxB.z;
	}
}
