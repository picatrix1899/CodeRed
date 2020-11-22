package com.codered;

import org.barghos.core.tuple3.api.Tup3fR;
import org.barghos.math.matrix.Mat4f;
import org.barghos.math.quat.Quatf;
import org.barghos.math.utils.VectorInterpolation;
import org.barghos.math.vec3.Vec3f;

public class SweptTransform implements ITransform
{
	public Transform oldTransform = new Transform();
	public Transform newTransform = new Transform();
	
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
	
	public Vec3f getPos(float alpha)
	{
		return VectorInterpolation.lerp(this.oldTransform.getPos(), this.newTransform.getPos(), alpha, null);
	}

	public Vec3f getPos()
	{
		return this.newTransform.getPos();
	}

	public Vec3f getTransformedPos(float alpha)
	{
		if(this.parent != null)
			return this.parent.getTransformationMatrix(alpha).transform(getPos(alpha), true, new Vec3f());
		return getPos(alpha);
	}

	public Vec3f getTransformedPos()
	{
		if(this.parent != null)
			return this.parent.getTransformationMatrix().transform(getPos(), true, new Vec3f());
		return getPos();
	}

	public Quatf getRot(float alpha)
	{
		return VectorInterpolation.slerp(this.oldTransform.getRot(), this.newTransform.getRot(), alpha, null);
	}

	public Quatf getRot()
	{
		return this.newTransform.getRot();
	}

	public Quatf getTransformedRot(float alpha)
	{
		if(this.parent != null)
			return this.parent.getTransformedRot(alpha).mul(getRot(alpha), null);
		return getRot(alpha);
	}

	public Quatf getTransformedRot()
	{
		if(this.parent != null)
			return this.parent.getTransformedRot().mul(getRot(), null);
		return getRot();
	}

	public Vec3f getScale(float alpha)
	{
		return VectorInterpolation.lerp(this.oldTransform.getScale(), this.newTransform.getScale(), alpha, null);
	}

	public Vec3f getScale()
	{
		return this.newTransform.getScale();
	}

	public Mat4f getTransformationMatrix(float alpha)
	{
		if(this.parent != null)
			return this.parent.getTransformationMatrix(alpha).mul(Mat4f.modelMatrix(getPos(alpha), getRot(alpha),getScale(alpha)), new Mat4f());
		return Mat4f.modelMatrix(getPos(alpha), getRot(alpha), getScale(alpha));
	}

	public Mat4f getTransformationMatrix()
	{
		if(this.parent != null)
			return this.parent.getTransformationMatrix().mul(Mat4f.modelMatrix(getPos(), getRot(),getScale()), new Mat4f());
		return Mat4f.modelMatrix(getPos(), getRot(), getScale());
	}
	
	public Mat4f getModelMatrix()
	{
		return Mat4f.modelMatrix(getPos(), getRot(), getScale());
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
