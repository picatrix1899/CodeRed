package com.codered;

import org.barghos.core.tuple3.api.Tup3fR;
import org.barghos.math.matrix.Mat4;
import org.barghos.math.vector.quat.Quatf;
import org.barghos.math.vector.vec3.Vec3f;

public class Transform implements ITransform
{
	private Vec3f newPos;
	private EulerRotation newRot;
	private Vec3f newScale;
	
	private ITransform parent;
	
	public Transform()
	{
		this.newPos = new Vec3f();
		this.newRot = new EulerRotation();
		this.newScale = new Vec3f(1, 1, 1);
	}
	
	public void swap()
	{
	}

	
	
	public Transform setScale(Tup3fR scale) { this.newScale.set(scale); return this; }
	public Vec3f getScale() { return this.newScale; }
	
	public Transform setParent(ITransform parent) { this.parent = parent; return this; }
	public ITransform getParent() { return this.parent; }
	
	
	public Transform rotate(float pitch, float yaw, float roll) { this.newRot.rotate(pitch, yaw, roll); return this; }
	public Transform rotate(Tup3fR v, float angle) { this.newRot.rotate(v, angle); return this; }
	public Quatf getRot() { return this.newRot.getRotation(); }
	public Quatf getTransformedRot()
	{
		if(this.parent != null)
			return this.parent.getTransformedRot().mul(this.newRot.getRotation(), null);
		return this.newRot.getRotation();
	}	
	public Transform setPos(Tup3fR pos) { this.newPos.set(pos); return this; }
	public Vec3f getPos() { return this.newPos; }
	public Vec3f getTransformedPos()
	{
		if(this.parent != null)
			return this.parent.getTransformationMatrix().transform(this.newPos, new Vec3f());
		return this.newPos;
	}

	public Mat4 getTransformationMatrix()
	{
		if(this.parent == null)
			return Mat4.modelMatrix(this.newPos, this.newRot.getRotation(), this.newScale);

		return this.parent.getTransformationMatrix().mul(Mat4.modelMatrix(this.newPos, this.newRot.getRotation(), this.newScale), null);
	}

	public Vec3f getPos(float alpha)
	{
		return getPos();
	}

	public Vec3f getTransformedPos(float alpha)
	{
		return getTransformedPos();
	}

	public Quatf getRot(float alpha)
	{
		return getRot();
	}

	public Quatf getTransformedRot(float alpha)
	{
		return getTransformedRot();
	}

	public Vec3f getScale(float alpha)
	{
		return getScale();
	}

	public Mat4 getTransformationMatrix(float alpha)
	{
		return getTransformationMatrix();
	}

	public EulerRotation getRotation()
	{
		return this.newRot;
	}

	public Transform setRotation(EulerRotation rot)
	{
		this.newRot.set(rot);
		return this;
	}
}