package com.codered;

public class ConvUtils
{
	public static cmn.utilslib.math.matrix.Matrix4f matToMatrix(org.barghos.math.matrix.Mat4f mat)
	{
		cmn.utilslib.math.matrix.Matrix4f out = new cmn.utilslib.math.matrix.Matrix4f();
		
		org.barghos.core.tuple.Tup4f r0 = mat.getRow(0);
		out.m0.set(r0.getX(), r0.getY(), r0.getZ(), r0.getW());
		
		org.barghos.core.tuple.Tup4f r1 = mat.getRow(1);
		out.m1.set(r1.getX(), r1.getY(), r1.getZ(), r1.getW());
		
		org.barghos.core.tuple.Tup4f r2 = mat.getRow(2);
		out.m2.set(r2.getX(), r2.getY(), r2.getZ(), r2.getW());
		
		org.barghos.core.tuple.Tup4f r3 = mat.getRow(3);
		out.m3.set(r3.getX(), r3.getY(), r3.getZ(), r3.getW());
		
		return out;
	}
	
	public static cmn.utilslib.math.vector.Vector3f vec3fToVector3f(org.barghos.math.vector.Vec3f vec)
	{
		return new cmn.utilslib.math.vector.Vector3f(vec.x, vec.y, vec.z);
	}
	
	public static org.barghos.math.vector.Vec3f vector3fToVec3f(cmn.utilslib.math.vector.Vector3f vec)
	{
		return new org.barghos.math.vector.Vec3f(vec.x, vec.y, vec.z);
	}
	
	public static cmn.utilslib.math.vector.Vector2f vec2fToVector2f(org.barghos.math.vector.Vec2f vec)
	{
		return new cmn.utilslib.math.vector.Vector2f(vec.x, vec.y);
	}
	
	public static org.barghos.math.vector.Vec2f vector2fToVec2f(cmn.utilslib.math.vector.Vector2f vec)
	{
		return new org.barghos.math.vector.Vec2f(vec.x, vec.y);
	}
	
	public static cmn.utilslib.math.geometry.Point3f bpoint3fToUPoint3f(org.barghos.math.point.Point3f p)
	{
		return new cmn.utilslib.math.geometry.Point3f(p.x, p.y, p.z);
	}
	
	public static cmn.utilslib.math.geometry.AABB3f bAABB3fToUAABB3f(org.barghos.math.geometry.AABB3f aabb)
	{
		return new cmn.utilslib.math.geometry.AABB3f(bpoint3fToUPoint3f(aabb.getCenter()), vec3fToVector3f(aabb.getHalfExtend()));
	}
	
	public static cmn.utilslib.math.geometry.OBB3f bOBB3fToUOBB3f(org.barghos.math.geometry.OBB3f obb)
	{
		return new cmn.utilslib.math.geometry.OBB3f(bpoint3fToUPoint3f(obb.getCenter()), vec3fToVector3f(obb.getHalfExtend()), matToMatrix(obb.getRotation()));
	}
}
