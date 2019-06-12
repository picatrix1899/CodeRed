package com.codered;

import org.barghos.core.api.tuple.ITup3R;
import org.barghos.math.matrix.Mat4f;
import org.barghos.math.vector.Quat;
import org.barghos.math.vector.Vec3f;

public class Transform
{
	private Vec3f pos;
	private EulerRotation rot;
	private Vec3f scale;
	
	private Vec3f newPos;
	private EulerRotation newRot;
	private Vec3f newScale;
	
	private Transform parent;
	
	public Transform()
	{
		this.pos = new Vec3f();
		this.rot = new EulerRotation();
		this.scale = new Vec3f(1);
		this.newPos = new Vec3f();
		this.newRot = new EulerRotation();
		this.newScale = new Vec3f(1);
	}
	
	public void swap()
	{
		this.pos.set(this.newPos);
		this.rot.set(this.newRot);
		this.scale.set(this.newScale);
	}
	
	public Transform setScale(ITup3R scale) { this.newScale.set(scale); return this; }
	public Vec3f getScale() { return this.newScale; }

	
	public Transform setParent(Transform parent) { this.parent = parent; return this; }
	public Transform getParent() { return this.parent; }
	
	
	public Transform rotate(double pitch, double yaw, double roll) { this.newRot.rotate(pitch, yaw, roll); return this; }
	public Transform rotate(ITup3R v, double angle) { this.newRot.rotate(v, angle); return this; }
	public Quat getRot() { return this.newRot.getRotation(); }
	public Quat getTransformedRot()
	{
		if(this.parent != null)
			return this.parent.getTransformedRot().mul(this.newRot.getRotation(), null);
		return this.newRot.getRotation();
	}	
	
	
	public EulerRotation getRotation() { return this.rot; }
	
	public Transform moveBy(ITup3R velocity) { this.newPos.add(velocity, this.newPos); return this; }
	public Transform setPos(ITup3R pos) { this.newPos.set(pos); return this; }
	public Vec3f getPos() { return this.newPos; }
	public Vec3f getTransformedPos()
	{
		if(this.parent != null)
			return this.parent.getTransformationMatrix().transform(this.newPos, (Vec3f)null);
		return this.newPos;
	}

	public Mat4f getTransformationMatrix()
	{
		if(this.parent == null)
			return Mat4f.modelMatrix(this.newPos, this.newRot.getRotation(), this.newScale);

		return this.parent.getTransformationMatrix().mul(Mat4f.modelMatrix(this.newPos, this.newRot.getRotation(), this.newScale), null);
	}
	
	public Mat4f getLerpedTransformationMatrix(double alpha)
	{
		Vec3f pos = getLerpedPos(alpha);
		Quat rot = getLerpedRot(alpha);
		
		if(this.parent == null)
			return Mat4f.modelMatrix(pos, rot, this.newScale);

		return this.parent.getLerpedTransformationMatrix(alpha).mul(Mat4f.modelMatrix(pos, rot, this.newScale), null);
	}
	
	public Vec3f getLerpedPos(double alpha)
	{
		double x = this.pos.x * (1 - alpha) + this.newPos.x * alpha;
		double y = this.pos.y * (1 - alpha) + this.newPos.y * alpha;
		double z = this.pos.z * (1 - alpha) + this.newPos.z * alpha;
		
		return new Vec3f(x,y,z);
	}
	
	public Quat getLerpedRot(double alpha)
	{
		Quat q1 = this.rot.getRotation();
		Quat q2 = this.newRot.getRotation();
		
		double x = q1.x * (1 - alpha) + q2.x * alpha;
		double y = q1.y * (1 - alpha) + q2.y * alpha;
		double z = q1.z * (1 - alpha) + q2.z * alpha;
		double w = q1.w * (1 - alpha) + q2.w * alpha;
		
		return new Quat(w,x,y,z);
	}
	
	public Vec3f getLerpedTransformedPos(double alpha)
	{
		Vec3f pos = getLerpedPos(alpha);
		
		if(this.parent != null)
			return this.parent.getLerpedTransformationMatrix(alpha).transform(pos, (Vec3f)null);
		return pos;
	}
	
	public Quat getLerpedTransformedRot(double alpha)
	{
		Quat rot = getLerpedRot(alpha);
		
		if(this.parent != null)
			return this.parent.getLerpedTransformedRot(alpha).mul(rot, null);
		return rot;
	}	
}