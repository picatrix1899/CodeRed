package com.codered.entities;

import org.barghos.core.api.tuple.ITup3fR;
import org.barghos.math.matrix.Mat4f;
import org.barghos.math.vector.Quat;
import org.barghos.math.vector.Vec3f;
import org.barghos.math.vector.Vec3fAxis;

import com.codered.Transform;

public class Camera
{

	private Transform transform;
	
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
		this.transform = new Transform();
	}
	
	public Camera(Vec3f pos, double pitch, double yaw, double roll)
	{
		this();
		setPos(pos);
		rotatePitch(pitch);
		rotateYaw(yaw);
		rotateRoll(roll);
	}
	
	public Camera(float posX, float posY, float posZ, double pitch, double yaw, double roll)
	{
		this();
		setPos(new Vec3f(posX, posY, posZ));
		rotatePitch(pitch);
		rotateYaw(yaw);
		rotateRoll(roll);
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
	
	public Transform getTransform() { return this.transform; }
	
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
	
	public Camera setPos(Vec3f pos) { this.transform.setPos(pos); return this; }
	public Camera moveBy(Vec3f velocity) { this.transform.moveBy(velocity); return this; }
	public Vec3f getRelativePos() { return this.transform.getPos(); }
	public Vec3f getTotalPos() { return this.transform.getTransformedPos(); }
	
	public Mat4f getViewMatrix() { return Mat4f.viewMatrix(getTotalPos(), getTotalRot()); }
	
	
	
	public Camera rotatePitch(double amount)
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
	
	public double getAngle(ITup3fR a, ITup3fR b)
	{
		double combinedLength = Vec3f.length(a) * Vec3f.length(b);
		Vec3f cross = Vec3f.cross(a, b, null);

		double sin = cross.length() / combinedLength;
		double cos = Vec3f.dot(a, b) / combinedLength;
		
		Vec3f n = cross.normal(null);
		
		double sign = n.dot(cross);
	
		double out = Math.atan2(sin, cos);
		
		if(sign < 0) out = -out;
		
		return out;
	}
	
	public Camera rotateYaw(double amount)
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
	
	public Camera rotateRoll(double amount)
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
