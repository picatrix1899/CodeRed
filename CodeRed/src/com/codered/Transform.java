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
	
	private Transform parent;
	
	public Transform()
	{
		this.pos = new Vec3f();
		this.rot = new EulerRotation();
		this.scale = new Vec3f(1);
	}
	
	public Transform setScale(ITup3R scale) { this.scale.set(scale); return this; }
	public Vec3f getScale() { return this.scale; }

	
	public Transform setParent(Transform parent) { this.parent = parent; return this; }
	public Transform getParent() { return this.parent; }
	
	
	public Transform rotate(double pitch, double yaw, double roll) { this.rot.rotate(pitch, yaw, roll); return this; }
	public Transform rotate(ITup3R v, double angle) { this.rot.rotate(v, angle); return this; }
	public Quat getRot() { return this.rot.getRotation(); }
	public Quat getTransformedRot()
	{
		if(this.parent != null)
			return this.parent.getTransformedRot().mul(this.rot.getRotation(), null);
		return this.rot.getRotation();
	}	
	
	
	public EulerRotation getRotation() { return this.rot; }
	
	public Transform moveBy(ITup3R velocity) { this.pos.add(velocity, this.pos); return this; }
	public Transform setPos(ITup3R pos) { this.pos.set(pos); return this; }
	public Vec3f getPos() { return this.pos; }
	public Vec3f getTransformedPos()
	{
		if(this.parent != null)
			return this.parent.getTransformationMatrix().transform(this.pos, (Vec3f)null);
		return this.pos;
	}
	
	

	
	
	public Mat4f getTransformationMatrix()
	{
		if(this.parent == null)
			return Mat4f.modelMatrix(this.pos, this.rot.getRotation(), this.scale);

		return this.parent.getTransformationMatrix().mul(Mat4f.modelMatrix(this.pos, this.rot.getRotation(), this.scale), null);
	}
	
}