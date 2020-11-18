package com.codered.entities;

import org.barghos.core.tuple3.api.Tup3fR;
import org.barghos.math.matrix.Mat4;
import org.barghos.math.vector.quat.Quatf;
import org.barghos.math.vector.vec3.Vec3f;
import org.barghos.math.vector.vec3.Vec3fAxis;
import org.barghos.math.vector.vec3.Vec3fPool;

import com.codered.SweptTransform;

public class Camera
{

	private SweptTransform transform;
	
	private boolean limitRotPitch = false;
	private boolean limitRotYaw = false;
	private boolean limitRotRoll = false;
	
	private float minRotPitch;
	private float maxRotPitch;
	private float minRotYaw;
	private float maxRotYaw;
	private float minRotRoll;
	private float maxRotRoll;
	
	private float speedPitch = 1.0f;
	private float speedYaw = 1.0f;
	private float speedRoll = 1.0f;
	
	public Camera()
	{
		this.transform = new SweptTransform();
	}
	
	public Camera(Vec3f pos, float pitch, float yaw, float roll)
	{
		this();
		setPos(pos);
		rotatePitch(pitch);
		rotateYaw(yaw);
		rotateRoll(roll);
		this.transform.swap();
	}
	
	public Camera(float posX, float posY, float posZ, float pitch, float yaw, float roll)
	{
		this();
		setPos(new Vec3f(posX, posY, posZ));
		rotatePitch(pitch);
		rotateYaw(yaw);
		rotateRoll(roll);
		this.transform.swap();
	}
	
	public Camera limitPitch(boolean limit) { this.limitRotPitch = limit; return this; }
	public Camera limitYaw(boolean limit) { this.limitRotYaw = limit; return this; }
	public Camera limitRoll(boolean limit) { this.limitRotRoll = limit; return this; }
	
	public Camera limit(boolean pitch, boolean yaw, boolean roll) { return limitPitch(pitch).limitYaw(yaw).limitRoll(roll); }
	
	public Camera limitPitch(float min, float max)
	{
		this.limitRotPitch = true;
		this.minRotPitch = min;
		this.maxRotPitch = max;
		
		return this;
	}
	
	public Camera limitYaw(float min, float max)
	{
		this.limitRotYaw = true;
		this.minRotYaw = min;
		this.maxRotYaw = max;
		
		return this;
	}
	
	public Camera limitRoll(float min, float max)
	{
		this.limitRotRoll = true;
		this.minRotRoll = min;
		this.maxRotRoll = max;
		
		return this;
	}
	
	public Camera setPitchSpeed(float speed) { this.speedPitch = speed; return this; }
	public Camera setYawSpeed(float speed) { this.speedYaw = speed; return this; }
	public Camera setRollSpeed(float speed) { this.speedRoll = speed; return this; }
	
	public SweptTransform getTransform() { return this.transform; }
	
	public boolean isPitchRotLimited() { return this.limitRotPitch; }
	public boolean isYawRotLimited() { return this.limitRotYaw; }
	public boolean isRollRotLimited() { return this.limitRotRoll; }
	
	public float getMinRotPitch() { return this.minRotPitch; }
	public float getMaxRotPitch() { return this.maxRotPitch; }
	public float getMinRotYaw() { return this.minRotYaw; }
	public float getMaxRotYaw() { return this.maxRotYaw; }
	public float getMinRotRoll() { return this.minRotRoll; }
	public float getMaxRotRoll() { return this.maxRotRoll; }
	
	public Quatf getRelativeRot() { return this.transform.getRot(); }
	public Quatf getTotalRot() { return this.transform.getTransformedRot(); }
	
	public float getPitchSpeed() { return this.speedPitch; }
	public float getYawSpeed() { return this.speedYaw; }
	public float getRollSpeed() { return this.speedRoll; }
	
	public Quatf getPitch() { return this.transform.getRotation().getRotationPitch(); }
	public Quatf getYaw() { return this.transform.getRotation().getRotationYaw(); }
	public Quatf getRoll() { return this.transform.getRotation().getRotationRoll(); }
	
	public Camera setPos(Vec3f pos) { this.transform.setPos(pos); return this; }
	public Camera moveBy(Vec3f velocity) { this.transform.setPos(this.transform.getPos().addN(velocity)); return this; }
	public Vec3f getRelativePos() { return this.transform.getPos(); }
	public Vec3f getTotalPos() { return this.transform.getTransformedPos(); }
	
	public Mat4 getViewMatrix()
	{
		return Mat4.viewMatrix(getTotalPos(), getTotalRot());
	}
	
	public Mat4 getLerpedViewMatrix(float alpha)
	{
		return Mat4.viewMatrix(this.transform.getTransformedPos(alpha), this.transform.getRot(alpha));
	}
	
	public Camera rotatePitch(float amount)
	{
		// +amount => down
		// -amount => up
		

		if(this.limitRotPitch)
		{
			if(amount > 0.0f)
			{
				if(-this.transform.getRotation().getEulerPitch() + this.speedPitch * amount < this.maxRotPitch)
					this.transform.rotate(Vec3fAxis.AXIS_NX, this.speedPitch * amount);
			}
			else if(amount < 0.0f)
			{
				if(-this.transform.getRotation().getEulerPitch() + this.speedPitch * amount > this.minRotPitch)
					this.transform.rotate(Vec3fAxis.AXIS_NX, this.speedPitch * amount);
			}
		}
		else
		{
			if(amount != 0.0f && amount != -0.0f)
				this.transform.rotate(Vec3fAxis.AXIS_NX, this.speedPitch * amount);
		}

		return this;
	}
	
	public double getAngle(Tup3fR a, Tup3fR b)
	{
		Vec3f va = Vec3fPool.get(a);
		Vec3f vb = Vec3fPool.get(b);
		double combinedLength = va.lengthSafe() * vb.lengthSafe();
		Vec3f cross = va.cross(vb, null);

		double sin = cross.length() / combinedLength;
		double cos = va.dot(vb) / combinedLength;
		
		Vec3f n = cross.normal(null);
		
		double sign = n.dot(cross);
	
		double out = Math.atan2(sin, cos);
		
		if(sign < 0) out = -out;
		
		Vec3fPool.store(va, vb);
		
		return out;
	}
	
	public Camera rotateYaw(float amount)
	{
		if(this.limitRotYaw)
		{
			if(amount > 0.0f)
			{
				if(this.transform.getRotation().getEulerYaw() + this.speedYaw * amount < this.maxRotYaw)
					this.transform.rotate(Vec3fAxis.AXIS_Y, this.speedYaw * amount);
			}
			else if(amount < 0.0f)
			{
				if(this.transform.getRotation().getEulerYaw() + this.speedYaw * amount > this.minRotYaw)
					this.transform.rotate(Vec3fAxis.AXIS_Y, this.speedYaw * amount);
			}
		}
		else
		{
			if(amount != 0.0f && amount != -0.0f)
				this.transform.rotate(Vec3fAxis.AXIS_Y, this.speedYaw * amount);
		}

		return this;
	}
	
	public Camera rotateRoll(float amount)
	{
		
		if(this.limitRotRoll)
		{
			if(amount > 0.0f)
			{
				if(this.transform.getRotation().getEulerRoll() + this.speedRoll * amount < this.maxRotRoll)
					this.transform.rotate(Vec3fAxis.AXIS_Z, this.speedYaw * amount);
			}
			else if(amount < 0.0f)
			{
				if(this.transform.getRotation().getEulerRoll() + this.speedRoll * amount > this.minRotRoll)
					this.transform.rotate(Vec3fAxis.AXIS_Z, this.speedRoll * amount);
			}
		}
		else
		{
			if(amount != 0.0f && amount != -0.0f)
				this.transform.rotate(Vec3fAxis.AXIS_Z, this.speedRoll * amount);
		}

		return this;
	}
}
