package com.codered;

import org.barghos.core.tuple3.api.Tup3fR;
import org.barghos.math.matrix.Mat4;
import org.barghos.math.vector.VectorInterpolation;
import org.barghos.math.vector.quat.Quat;
import org.barghos.math.vector.vec3.Vec3;

public class SweptTransform implements ITransform
{
	private Transform oldTransform = new Transform();
	private Transform newTransform = new Transform();
	
	private ITransform parent;
	
	public void swap()
	{
		this.oldTransform.setPos(this.newTransform.getPos());
		this.oldTransform.setRotation(this.newTransform.getRotation());
		this.oldTransform.setScale(this.newTransform.getScale());
	}
	
	public SweptTransform setParent(ITransform parent)
	{
		this.parent = parent;
		return this;
	}
	
	public ITransform getParent()
	{
		return this.parent;
	}

	public EulerRotation getRotation()
	{
		return this.newTransform.getRotation();
	}
	
	public Vec3 getPos(float alpha)
	{
		return VectorInterpolation.lerp(this.oldTransform.getPos(), this.newTransform.getPos(), alpha, null);
	}

	public Vec3 getPos()
	{
		return this.newTransform.getPos();
	}

	public Vec3 getTransformedPos(float alpha)
	{
		if(this.parent != null)
			return this.parent.getTransformationMatrix(alpha).transform(getPos(alpha), new Vec3());
		return getPos(alpha);
	}

	public Vec3 getTransformedPos()
	{
		if(this.parent != null)
			return this.parent.getTransformationMatrix().transform(getPos(), new Vec3());
		return getPos();
	}

	public Quat getRot(float alpha)
	{
		return VectorInterpolation.slerp(this.oldTransform.getRot(), this.newTransform.getRot(), alpha, null);
	}

	public Quat getRot()
	{
		return this.newTransform.getRot();
	}

	public Quat getTransformedRot(float alpha)
	{
		if(this.parent != null)
			return this.parent.getTransformedRot(alpha).mul(getRot(alpha), null);
		return getRot(alpha);
	}

	public Quat getTransformedRot()
	{
		if(this.parent != null)
			return this.parent.getTransformedRot().mul(getRot(), null);
		return getRot();
	}

	public Vec3 getScale(float alpha)
	{
		return VectorInterpolation.lerp(this.oldTransform.getScale(), this.newTransform.getScale(), alpha, null);
	}

	public Vec3 getScale()
	{
		return this.newTransform.getScale();
	}

	public Mat4 getTransformationMatrix(float alpha)
	{
		if(this.parent != null)
			return this.parent.getTransformationMatrix(alpha).mul(Mat4.modelMatrix(getPos(alpha), getRot(alpha),getScale(alpha)), null);
		return Mat4.modelMatrix(getPos(alpha), getRot(alpha), getScale(alpha));
	}

	public Mat4 getTransformationMatrix()
	{
		if(this.parent != null)
			return this.parent.getTransformationMatrix().mul(Mat4.modelMatrix(getPos(), getRot(),getScale()), null);
		return Mat4.modelMatrix(getPos(), getRot(), getScale());
	}

	public SweptTransform setPos(Tup3fR pos)
	{
		this.newTransform.setPos(pos); return this;
	}

	public SweptTransform rotate(float pitch, float yaw, float roll)
	{
		this.newTransform.rotate(pitch,  yaw, roll); return this;
	}

	public SweptTransform rotate(Tup3fR v, float angle)
	{
		this.newTransform.rotate(v, angle); return this;
	}

	public SweptTransform setScale(Tup3fR scale)
	{
		this.newTransform.setScale(scale); return this;
	}

	public ITransform setRotation(EulerRotation rot)
	{
		return this.newTransform.setRotation(rot);
	}


	
	
}
