package com.codered.resource.model;

import org.barghos.core.tuple2.api.Tup2fR;
import org.barghos.core.tuple3.api.Tup3fR;
import org.barghos.math.vector.vec2.Vec2f;
import org.barghos.math.vector.vec2.api.Vec2fR;
import org.barghos.math.vector.vec3.Vec3;
import org.barghos.math.vector.vec3.Vec3R;

public class VertexData
{
	private Vec3 position = new Vec3();
	private Vec3 normal = new Vec3();
	private Vec3 tangent = new Vec3();
	private Vec2f uv = new Vec2f();
	
	public VertexData(Tup3fR position, Tup3fR normal, Tup3fR tangent, Tup2fR uv)
	{
		this.position.set(position);
		this.normal.set(normal);
		this.tangent.set(tangent);
		this.uv.set(uv);
	}
	
	public Vec3R getPosition()
	{
		return this.position;
	}
	
	public Vec3R getNormal()
	{
		return this.normal;
	}
	
	public Vec3R getTangent()
	{
		return this.tangent;
	}
	
	public Vec2fR getUV()
	{
		return this.uv;
	}
}
