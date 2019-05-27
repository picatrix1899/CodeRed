package com.codered.demo;

import org.barghos.math.geometry.AABB3f;
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
		Vec3f minA = Vec3f.sub(a.getCenter(), a.getHalfExtend(), null);
		Vec3f maxA = Vec3f.add(a.getCenter(), a.getHalfExtend(), null);
		Vec3f minB = Vec3f.sub(b.getCenter(), b.getHalfExtend(), null);
		Vec3f maxB = Vec3f.add(b.getCenter(), b.getHalfExtend(), null);
		
		float minX = minA.x <= minB.x ? minA.x : minB.x;
		float minY = minA.y <= minB.y ? minA.y : minB.y;
		float minZ = minA.z <= minB.z ? minA.z : minB.z;
		
		float maxX = maxA.x >= maxB.x ? maxA.x : maxB.x;
		float maxY = maxA.y >= maxB.y ? maxA.y : maxB.y;
		float maxZ = maxA.z >= maxB.z ? maxA.z : maxB.z;

		return new AABB3f(new Vec3f(minX, minY, minZ), new Vec3f(maxX, maxY, maxZ));
	}

	protected boolean isInRange(AABB3f oldEvalData, AABB3f newEvalData)
	{
		Vec3f minA = Vec3f.sub(newEvalData.getCenter(),newEvalData.getHalfExtend(), null);
		Vec3f minB = Vec3f.sub(oldEvalData.getCenter(), oldEvalData.getHalfExtend(), null);
		Vec3f maxA = Vec3f.add(newEvalData.getCenter(), newEvalData.getHalfExtend(), null);
		Vec3f maxB = Vec3f.add(oldEvalData.getCenter(), oldEvalData.getHalfExtend(), null);
		
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
		
		Vec3f minEval = Vec3f.sub(newEvalData.getCenter(),newEvalData.getHalfExtend(), null);
		Vec3f maxEval = Vec3f.add(newEvalData.getCenter(), newEvalData.getHalfExtend(), null);
		Vec3f minA = Vec3f.sub(a.evalData.getCenter(), a.evalData.getHalfExtend(), null);
		Vec3f maxA = Vec3f.add(a.evalData.getCenter(), a.evalData.getHalfExtend(), null);
		Vec3f minB = Vec3f.sub(b.evalData.getCenter(), b.evalData.getHalfExtend(), null);
		Vec3f maxB = Vec3f.add(b.evalData.getCenter(), b.evalData.getHalfExtend(), null);
		
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
