package com.codered.entities;

import cmn.utilslib.math.Quaternion;
import cmn.utilslib.math.Transform;
import cmn.utilslib.math.vector.Vector3f;


public abstract class BaseEntity
{
	protected Transform transform = new Transform();
	
	protected BaseEntity parent;
	
	public Transform getTransform() { return this.transform; }
	public Vector3f getPos() { return this.transform.getPos(); }
	public Quaternion getRot() { return this.transform.getRot(); }
	
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
	
	public BaseEntity setPos(Vector3f pos) { this.transform.setPos(pos); return this; }
	public BaseEntity rotatePitch(float rx)  { this.transform.rotate(rx, 0.0f, 0.0f); return this; }
	public BaseEntity rotateYaw(float ry)  { this.transform.rotate(0.0f, ry, 0.0f); return this; }
	public BaseEntity rotateZ(float rz)  { this.transform.rotate(0.0f, 0.0f, rz); return this; }
	public BaseEntity rotate(Vector3f v, float angle) {this.transform.rotate(v, angle); return this;}
	
}
