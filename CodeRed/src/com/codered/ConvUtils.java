package com.codered;

public class ConvUtils
{
	public static org.barghos.core.api.tuple.ITup3fR ITup3R_new_old(org.barghos.core.tuple.tuple3.Tup3fR t)
	{
		return new org.barghos.core.tuple.Tup3f(t.getX(), t.getY(), t.getZ());
	}
	
	public static org.barghos.math.geometry.AABB3f AABB3f_new_old(org.barghos.math.experimental.geometry.AABB3f t)
	{
		org.barghos.math.experimental.point.Point3 center = t.getCenter();
		org.barghos.math.experimental.vector.vec3.Vec3 he = t.getHalfExtend();
		return new org.barghos.math.geometry.AABB3f(center.getX(), center.getY(), center.getZ(), he.getX(), he.getY(), he.getZ());
	}
	
	public static org.barghos.math.experimental.geometry.AABB3f AABB3f_old_new(org.barghos.math.geometry.AABB3f t)
	{
		
		org.barghos.math.point.Point3f center = t.getCenter();
		org.barghos.math.vector.Vec3f he = t.getHalfExtend();
		return new org.barghos.math.experimental.geometry.AABB3f(center.getX(), center.getY(), center.getZ(), he.getX(), he.getY(), he.getZ());
	}
	
	public static org.barghos.math.experimental.vector.Quat Quat_old_new(org.barghos.math.vector.Quat t)
	{
		return new org.barghos.math.experimental.vector.Quat((float)t.getW(), (float)t.getX(), (float)t.getY(), (float)t.getZ());
	}
	
	public static org.barghos.math.vector.Quat Quat_new_old(org.barghos.math.experimental.vector.Quat t)
	{
		return new org.barghos.math.vector.Quat(t.getW(), t.getX(), t.getY(), t.getZ());
	}
	
	public static org.barghos.math.matrix.Mat4f Mat4f_new_old(org.barghos.math.experimental.matrix.Mat4f t)
	{
		org.barghos.math.matrix.Mat4f m = new org.barghos.math.matrix.Mat4f();
		org.barghos.core.tuple.tuple4.Tup4f c0 = t.getColumn(0);
		org.barghos.core.tuple.tuple4.Tup4f c1 = t.getColumn(1);
		org.barghos.core.tuple.tuple4.Tup4f c2 = t.getColumn(2);
		org.barghos.core.tuple.tuple4.Tup4f c3 = t.getColumn(3);
		m.setColumn(0, c0.getX(), c0.getY(), c0.getZ(), c0.getW());
		m.setColumn(1, c1.getX(), c1.getY(), c1.getZ(), c1.getW());
		m.setColumn(2, c2.getX(), c2.getY(), c2.getZ(), c2.getW());
		m.setColumn(3, c3.getX(), c3.getY(), c3.getZ(), c3.getW());
		
		return m;
	}
	
	public static org.barghos.math.experimental.matrix.Mat4f Mat4f_old_new(org.barghos.math.matrix.Mat4f t)
	{
		org.barghos.math.experimental.matrix.Mat4f m = new org.barghos.math.experimental.matrix.Mat4f();
		org.barghos.core.tuple.Tup4f c0 = t.getColumn(0);
		org.barghos.core.tuple.Tup4f c1 = t.getColumn(1);
		org.barghos.core.tuple.Tup4f c2 = t.getColumn(2);
		org.barghos.core.tuple.Tup4f c3 = t.getColumn(3);
		m.setColumn(0, c0.getX(), c0.getY(), c0.getZ(), c0.getW());
		m.setColumn(1, c1.getX(), c1.getY(), c1.getZ(), c1.getW());
		m.setColumn(2, c2.getX(), c2.getY(), c2.getZ(), c2.getW());
		m.setColumn(3, c3.getX(), c3.getY(), c3.getZ(), c3.getW());
		
		return m;
	}
	
	public static org.barghos.math.vector.Vec3f Vec3_new_old(org.barghos.math.experimental.vector.vec3.Vec3 t)
	{
		return new org.barghos.math.vector.Vec3f(t.getX(), t.getY(), t.getZ());
	}
	
	public static org.barghos.math.experimental.vector.vec3.Vec3 Vec3_old_new(org.barghos.math.vector.Vec3f t)
	{
		return new org.barghos.math.experimental.vector.vec3.Vec3(t.getX(), t.getY(), t.getZ());
	}
}
