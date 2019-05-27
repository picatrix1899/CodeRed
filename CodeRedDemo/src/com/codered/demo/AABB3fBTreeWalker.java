package com.codered.demo;

import java.util.ArrayList;

import org.barghos.math.geometry.AABB3f;
import org.barghos.math.vector.Vec3f;
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
		Vec3f minA = Vec3f.sub(newEvalData.getCenter(), newEvalData.getHalfExtend(), null);
		Vec3f maxA = Vec3f.add(newEvalData.getCenter(), newEvalData.getHalfExtend(), null);
		
		return isPointInside(minA, oldEvalData) || isPointInside(maxA, oldEvalData);
		
		
	}
	
	private boolean isPointInside(Vec3f point, AABB3f area)
	{
		Vec3f minB = Vec3f.sub(area.getCenter(), area.getHalfExtend(), null);
		Vec3f maxB = Vec3f.add(area.getCenter(), area.getHalfExtend(), null);
		
		return minB.x <= point.x && point.x <= maxB.x &&
			   minB.y <= point.y && point.y <= maxB.y &&
			   minB.z <= point.z && point.z <= maxB.z;
	}
}
