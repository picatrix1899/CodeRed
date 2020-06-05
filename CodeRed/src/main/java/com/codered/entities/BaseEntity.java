package com.codered.entities;

import org.barghos.math.vector.quat.Quat;
import org.barghos.math.vector.vec3.Vec3;

import com.codered.Transform;

public abstract class BaseEntity
{
	protected Transform transform = new Transform();
	
	protected BaseEntity parent;
	
	public Transform getTransform() { return this.transform; }
	
	public Vec3 getPos() { return this.transform.getPos(); }
	public Quat getRot() { return this.transform.getRot(); }
	
	public long id;

	public BaseEntity setParent(BaseEntity entity)
	{
		this.parent = entity;
		this.transform.setParent(this.parent.getTransform());
		
		return this;
	}
	
	public BaseEntity getParent()
	{
		return this.parent;
	}
	
	public BaseEntity setPos(Vec3 pos) { this.transform.setPos(pos); return this; }
	public BaseEntity rotatePitch(float rx)  { this.transform.rotate(rx, 0.0f, 0.0f); return this; }
	public BaseEntity rotateYaw(float ry)  { this.transform.rotate(0.0f, ry, 0.0f); return this; }
	public BaseEntity rotateZ(float rz)  { this.transform.rotate(0.0f, 0.0f, rz); return this; }
	public BaseEntity rotate(Vec3 v, float angle) {this.transform.rotate(v, angle); return this;}
	
}
