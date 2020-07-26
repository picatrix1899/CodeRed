package com.codered;

import org.barghos.core.tuple3.api.Tup3fR;
import org.barghos.math.matrix.Mat4;
import org.barghos.math.vector.quat.Quat;
import org.barghos.math.vector.vec3.Vec3;

public class Transform implements ITransform
{
	private Vec3 newPos;
	private EulerRotation newRot;
	private Vec3 newScale;
	
	private ITransform parent;
	
	public Transform()
	{
		this.newPos = new Vec3();
		this.newRot = new EulerRotation();
		this.newScale = new Vec3(1, 1, 1);
	}
	
	public void swap()
	{
	}

	
	
	public Transform setScale(Tup3fR scale) { this.newScale.set(scale); return this; }
	public Vec3 getScale() { return this.newScale; }
	
	public Transform setParent(ITransform parent) { this.parent = parent; return this; }
	public ITransform getParent() { return this.parent; }
	
	
	public Transform rotate(float pitch, float yaw, float roll) { this.newRot.rotate(pitch, yaw, roll); return this; }
	public Transform rotate(Tup3fR v, float angle) { this.newRot.rotate(v, angle); return this; }
	public Quat getRot() { return this.newRot.getRotation(); }
	public Quat getTransformedRot()
	{
		if(this.parent != null)
			return this.parent.getTransformedRot().mul(this.newRot.getRotation(), null);
		return this.newRot.getRotation();
	}	
	public Transform setPos(Tup3fR pos) { this.newPos.set(pos); return this; }
	public Vec3 getPos() { return this.newPos; }
	public Vec3 getTransformedPos()
	{
		if(this.parent != null)
			return this.parent.getTransformationMatrix().transform(this.newPos, new Vec3());
		return this.newPos;
	}

	public Mat4 getTransformationMatrix()
	{
		if(this.parent == null)
			return Mat4.modelMatrix(this.newPos, this.newRot.getRotation(), this.newScale);

		return this.parent.getTransformationMatrix().mul(Mat4.modelMatrix(this.newPos, this.newRot.getRotation(), this.newScale), null);
	}

	public Vec3 getPos(float alpha)
	{
		return getPos();
	}

	public Vec3 getTransformedPos(float alpha)
	{
		return getTransformedPos();
	}

	public Quat getRot(float alpha)
	{
		return getRot();
	}

	public Quat getTransformedRot(float alpha)
	{
		return getTransformedRot();
	}

	public Vec3 getScale(float alpha)
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