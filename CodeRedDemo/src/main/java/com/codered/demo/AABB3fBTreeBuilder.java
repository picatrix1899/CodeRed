package com.codered.demo;

import org.barghos.math.geometry.AABB3;
import org.barghos.math.point.Point3;
import org.barghos.math.point.Point3Pool;
import org.barghos.math.vector.vec3.Vec3;
import org.barghos.structs.simpletree.btree.BTree;
import org.barghos.structs.simpletree.btree.BTreeBuilder;
import org.barghos.structs.simpletree.btree.BTreeContent;
import org.barghos.structs.simpletree.btree.BTreeNode;

public class AABB3fBTreeBuilder<T> extends BTreeBuilder<T,AABB3>
{

	public AABB3fBTreeBuilder(BTree<T,AABB3> tree)
	{
		super(tree);
	}

	protected void recalculateEvalData(BTreeNode<T,AABB3> node)
	{
		if(node.b != null)
			node.evalData = mergeAABBs(node.a.evalData, node.b.evalData);
		else
			node.evalData = node.a.evalData;
	}
	
	public AABB3 mergeAABBs(AABB3 a, AABB3 b)
	{
		Point3 minA = a.getMin();
		Point3 maxA = a.getMax();
		Point3 minB = b.getMin();
		Point3 maxB = b.getMax();
		
		float minX = minA.getX() <= minB.getX() ? minA.getX() : minB.getX();
		float minY = minA.getY() <= minB.getY() ? minA.getY() : minB.getY();
		float minZ = minA.getZ() <= minB.getZ() ? minA.getZ() : minB.getZ();
		
		float maxX = maxA.getX() >= maxB.getX() ? maxA.getX() : maxB.getX();
		float maxY = maxA.getZ() >= maxB.getY() ? maxA.getY() : maxB.getY();
		float maxZ = maxA.getZ() >= maxB.getZ() ? maxA.getZ() : maxB.getZ();

		Vec3 min = new Vec3(minX, minY, minZ);
		Vec3 max = new Vec3(maxX, maxY, maxZ);
		
		Vec3 he = new Vec3();
		max.sub(min, he).mul(0.5f, he);
		
		Vec3 center = new Vec3();
		min.add(he, center);
		
		return new AABB3(center, he);
	}

	protected boolean isInRange(AABB3 oldEvalData, AABB3 newEvalData)
	{
		Point3 minA = newEvalData.getMin();
		Point3 minB = oldEvalData.getMin();
		Point3 maxA = newEvalData.getMax();
		Point3 maxB = oldEvalData.getMax();
		
		if(minA.getX() < minB.getX()) return false;
		if(minA.getY() < minB.getY()) return false;
		if(minA.getZ() < minB.getZ()) return false;
		
		if(maxA.getX() > maxB.getX()) return false;
		if(maxA.getY() > maxB.getY()) return false;
		if(maxA.getZ() > maxB.getZ()) return false;
		
		return true;
	}
	


	protected boolean getClosest(BTreeContent<T,AABB3> a, BTreeContent<T,AABB3> b,
			AABB3 newEvalData)
	{
		
		Point3 minEval = newEvalData.getMin();
		Point3 maxEval = newEvalData.getMax();
		Point3 minA = a.evalData.getMin();
		Point3 maxA = a.evalData.getMax();
		Point3 minB = b.evalData.getMin();
		Point3 maxB = b.evalData.getMax();
		
		Vec3 minEA = minA.subN(minEval);
		double distMinA = minEA.squaredLength();
		
		Vec3 minEB = minB.subN(minEval);
		double distMinB = minEB.squaredLength();
		
		Vec3 maxEA = maxA.subN(maxEval);
		double distMaxA = maxEA.squaredLength();
		
		Vec3 maxEB = maxB.subN(maxEval);
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
