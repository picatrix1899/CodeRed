package com.codered.entities;

import org.barghos.core.tuple3.api.Tup3fR;
import org.barghos.math.utils.Transform3f;

public abstract class BaseEntity
{
	protected Transform3f transform = new Transform3f();
	
	protected BaseEntity parent;
	
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
	
	public Transform3f getTransform() { return this.transform; }
	
	public BaseEntity setPos(Tup3fR pos) { this.transform.setRelativePosition(pos); return this; }
	public BaseEntity setScale(Tup3fR scale) { this.transform.setRelativeScale(scale); return this; }
}
