package com.codered.entities;

import org.barghos.core.tuple.tuple3.Tup3fR;
import org.barghos.math.matrix.Mat4f;
import org.barghos.math.vector.quat.Quat;
import org.barghos.math.vector.vec3.Vec3;
import org.barghos.math.vector.vec3.Vec3Axis;
import org.barghos.math.vector.vec3.Vec3Pool;

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
	
	public Camera(Vec3 pos, float pitch, float yaw, float roll)
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
		setPos(new Vec3(posX, posY, posZ));
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
	
	public Quat getRelativeRot() { return this.transform.getRot(); }
	public Quat getTotalRot() { return this.transform.getTransformedRot(); }
	
	public float getPitchSpeed() { return this.speedPitch; }
	public float getYawSpeed() { return this.speedYaw; }
	public float getRollSpeed() { return this.speedRoll; }
	
	public Quat getPitch() { return this.transform.getRotation().getRotationPitch(); }
	public Quat getYaw() { return this.transform.getRotation().getRotationYaw(); }
	public Quat getRoll() { return this.transform.getRotation().getRotationRoll(); }
	
	public Camera setPos(Vec3 pos) { this.transform.setPos(pos); return this; }
	public Camera moveBy(Vec3 velocity) { this.transform.setPos(this.transform.getPos().add(velocity, null)); return this; }
	public Vec3 getRelativePos() { return this.transform.getPos(); }
	public Vec3 getTotalPos() { return this.transform.getTransformedPos(); }
	
	public Mat4f getViewMatrix()
	{
		return Mat4f.viewMatrix(getTotalPos(), getTotalRot());
	}
	
	public Mat4f getLerpedViewMatrix(float alpha)
	{
		return Mat4f.viewMatrix(this.transform.getTransformedPos(alpha), this.transform.getRot(alpha));
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
					this.transform.rotate(Vec3Axis.AXIS_NX, this.speedPitch * amount);
			}
			else if(amount < 0.0f)
			{
				if(-this.transform.getRotation().getEulerPitch() + this.speedPitch * amount > this.minRotPitch)
					this.transform.rotate(Vec3Axis.AXIS_NX, this.speedPitch * amount);
			}
		}
		else
		{
			if(amount != 0.0f && amount != -0.0f)
				this.transform.rotate(Vec3Axis.AXIS_NX, this.speedPitch * amount);
		}

		return this;
	}
	
	public double getAngle(Tup3fR a, Tup3fR b)
	{
		Vec3 va = Vec3Pool.get(a);
		Vec3 vb = Vec3Pool.get(b);
		double combinedLength = va.lengthSafe() * vb.lengthSafe();
		Vec3 cross = va.cross(vb, null);

		double sin = cross.length() / combinedLength;
		double cos = va.dot(vb) / combinedLength;
		
		Vec3 n = cross.normal(null);
		
		double sign = n.dot(cross);
	
		double out = Math.atan2(sin, cos);
		
		if(sign < 0) out = -out;
		
		Vec3Pool.store(va, vb);
		
		return out;
	}
	
	public Camera rotateYaw(float amount)
	{
		if(this.limitRotYaw)
		{
			if(amount > 0.0f)
			{
				if(this.transform.getRotation().getEulerYaw() + this.speedYaw * amount < this.maxRotYaw)
					this.transform.rotate(Vec3Axis.AXIS_Y, this.speedYaw * amount);
			}
			else if(amount < 0.0f)
			{
				if(this.transform.getRotation().getEulerYaw() + this.speedYaw * amount > this.minRotYaw)
					this.transform.rotate(Vec3Axis.AXIS_Y, this.speedYaw * amount);
			}
		}
		else
		{
			if(amount != 0.0f && amount != -0.0f)
				this.transform.rotate(Vec3Axis.AXIS_Y, this.speedYaw * amount);
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
					this.transform.rotate(Vec3Axis.AXIS_Z, this.speedYaw * amount);
			}
			else if(amount < 0.0f)
			{
				if(this.transform.getRotation().getEulerRoll() + this.speedRoll * amount > this.minRotRoll)
					this.transform.rotate(Vec3Axis.AXIS_Z, this.speedRoll * amount);
			}
		}
		else
		{
			if(amount != 0.0f && amount != -0.0f)
				this.transform.rotate(Vec3Axis.AXIS_Z, this.speedRoll * amount);
		}

		return this;
	}
}
