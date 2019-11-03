package com.codered.demo;

import java.util.ArrayList;

import org.barghos.math.geometry.AABB3f;
import org.barghos.math.point.Point3;
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
		Point3 minA = newEvalData.getMin();
		Point3 maxA = newEvalData.getMax();

		return isPointInside(minA, oldEvalData) || isPointInside(maxA, oldEvalData);
	}
	
	private boolean isPointInside(Point3 point, AABB3f area)
	{
		Point3 minB = area.getMin();
		Point3 maxB = area.getMax();
		
		return minB.getX() <= point.getX() && point.getX() <= maxB.getX() &&
			   minB.getY() <= point.getY() && point.getY() <= maxB.getY() &&
			   minB.getZ() <= point.getZ() && point.getZ() <= maxB.getZ();
	}
}
