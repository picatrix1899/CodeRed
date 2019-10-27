package com.codered.resource.object;

import org.barghos.math.experimental.point.Point3;
import org.barghos.math.point.Point3f;
import org.barghos.math.vector.Vec2f;
import org.barghos.math.vector.Vec3f;

public class Vertex
{
	public Point3 p;
	public Point3f pos;
	public Vec2f uv;
	public Vec3f normal;
	public Vec3f tangent = new Vec3f();
	public int index;
}
