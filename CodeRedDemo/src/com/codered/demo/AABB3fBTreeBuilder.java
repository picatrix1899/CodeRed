package com.codered.demo;

import org.barghos.math.geometry.AABB3f;
import org.barghos.math.point.Point3f;
import org.barghos.math.vector.Vec3f;
import org.barghos.structs.simpletree.btree.BTree;
import org.barghos.structs.simpletree.btree.BTreeBuilder;
import org.barghos.structs.simpletree.btree.BTreeContent;
import org.barghos.structs.simpletree.btree.BTreeNode;

public class AABB3fBTreeBuilder<T> extends BTreeBuilder<T,AABB3f>
{

	public AABB3fBTreeBuilder(BTree<T,AABB3f> tree)
	{
		super(tree);
	}

	protected void recalculateEvalData(BTreeNode<T,AABB3f> node)
	{
		if(node.b != null)
			node.evalData = mergeAABBs(node.a.evalData, node.b.evalData);
		else
			node.evalData = node.a.evalData;
	}
	
	public AABB3f mergeAABBs(AABB3f a, AABB3f b)
	{
		Point3f minA = a.getMin();
		Point3f maxA = a.getMax();
		Point3f minB = b.getMin();
		Point3f maxB = b.getMax();
		
		float minX = minA.x <= minB.x ? minA.x : minB.x;
		float minY = minA.y <= minB.y ? minA.y : minB.y;
		float minZ = minA.z <= minB.z ? minA.z : minB.z;
		
		float maxX = maxA.x >= maxB.x ? maxA.x : maxB.x;
		float maxY = maxA.y >= maxB.y ? maxA.y : maxB.y;
		float maxZ = maxA.z >= maxB.z ? maxA.z : maxB.z;

		Vec3f min = new Vec3f(minX, minY, minZ);
		Vec3f max = new Vec3f(maxX, maxY, maxZ);
		
		Vec3f he = new Vec3f();
		max.sub(min, he).mul(0.5, he);
		
		Vec3f center = new Vec3f();
		min.add(he, center);
		
		return new AABB3f(center, he);
	}

	protected boolean isInRange(AABB3f oldEvalData, AABB3f newEvalData)
	{
		Point3f minA = newEvalData.getMin();
		Point3f minB = oldEvalData.getMin();
		Point3f maxA = newEvalData.getMax();
		Point3f maxB = oldEvalData.getMax();
		
		if(minA.x < minB.x) return false;
		if(minA.y < minB.y) return false;
		if(minA.z < minB.z) return false;
		
		if(maxA.x > maxB.x) return false;
		if(maxA.y > maxB.y) return false;
		if(maxA.z > maxB.z) return false;
		
		return true;
	}
	


	protected boolean getClosest(BTreeContent<T,AABB3f> a, BTreeContent<T,AABB3f> b,
			AABB3f newEvalData)
	{
		
		Point3f minEval = newEvalData.getMin();
		Point3f maxEval = newEvalData.getMax();
		Point3f minA = a.evalData.getMin();
		Point3f maxA = a.evalData.getMax();
		Point3f minB = b.evalData.getMin();
		Point3f maxB = b.evalData.getMax();
		
		Vec3f minEA = Vec3f.sub(minA, minEval, null);
		double distMinA = minEA.squaredLength();
		
		Vec3f minEB = Vec3f.sub(minB, minEval, null);
		double distMinB = minEB.squaredLength();
		
		Vec3f maxEA = Vec3f.sub(maxA, maxEval, null);
		double distMaxA = maxEA.squaredLength();
		
		Vec3f maxEB = Vec3f.sub(maxB, maxEval, null);
		double distMaxB = maxEB.squaredLength();

		double smallestX = distMinA;
		boolean out = true;
		
		if(distMinB < smallestX)
		{
			smallestX = distMinB;
			out = false;
		}
		
		if(distMaxA < smallestX)
		{
			smallestX = distMaxA;
			out = true;
		}
		
		if(distMaxB < smallestX)
		{
			smallestX = distMaxB;
			out = false;
		}
		
		return out;
	}
}
