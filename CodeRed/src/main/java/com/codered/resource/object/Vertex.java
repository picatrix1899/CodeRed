package com.codered.resource.object;

import org.barghos.math.point.Point3;
import org.barghos.math.vector.vec2.Vec2;
import org.barghos.math.vector.vec3.Vec3;

public class Vertex
{
	public Point3 p;
	public Point3 pos;
	public Vec2 uv;
	public Vec3 normal;
	public Vec3 tangent = new Vec3();
	public int index;
}
