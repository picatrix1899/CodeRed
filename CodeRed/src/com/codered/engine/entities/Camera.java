package com.codered.engine.entities;

import cmn.utilslib.math.matrix.Matrix4f;
import cmn.utilslib.math.Quaternion;
import cmn.utilslib.math.RotationType;
import cmn.utilslib.math.Transform;
import cmn.utilslib.math.vector.Vector3f;
import cmn.utilslib.math.vector.api.Vec3f;
import cmn.utilslib.math.vector.api.Vec3fBase;

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
		this.transform = new Transform(RotationType.EULER);
	}
	
	public Camera(Vec3fBase pos, double pitch, double yaw, double roll)
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
		setPos(new Vector3f(posX, posY, posZ));
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
	
	public Quaternion getRelativeRot() { return this.transform.getRot(); }
	public Quaternion getTotalRot() { return this.transform.getTransformedRot(); }
	
	public float getPitchSpeed() { return this.speedPitch; }
	public float getYawSpeed() { return this.speedYaw; }
	public float getRollSpeed() { return this.speedRoll; }
	
	public Quaternion getPitch() { return this.transform.getRotation().getRotationPitch(); }
	public Quaternion getYaw() { return this.transform.getRotation().getRotationYaw(); }
	public Quaternion getRoll() { return this.transform.getRotation().getRotationRoll(); }
	
	public Camera setPos(Vec3fBase pos) { this.transform.setPos(pos); return this; }
	public Camera moveBy(Vec3fBase velocity) { this.transform.moveBy(velocity); return this; }
	public Vector3f getRelativePos() { return this.transform.getPos(); }
	public Vector3f getTotalPos() { return this.transform.getTransformedPos(); }
	
	public Matrix4f getViewMatrix() { return Matrix4f.viewMatrix(getTotalPos(), getTotalRot()); }
	
	
	
	public Camera rotatePitch(double amount)
	{
		// +amount => down
		// -amount => up
		

		if(this.limitRotPitch)
		{
			if(amount > 0.0f)
			{
				if(-this.transform.getRotation().getEulerPitch() + this.speedPitch * amount < this.maxRotPitch)
					this.transform.rotate(Vec3f.aNX, this.speedPitch * amount);
			}
			else if(amount < 0.0f)
			{
				if(-this.transform.getRotation().getEulerPitch() + this.speedPitch * amount > this.minRotPitch)
					this.transform.rotate(Vec3f.aNX, this.speedPitch * amount);
			}
		}
		else
		{
			if(amount != 0.0f && amount != -0.0f)
				this.transform.rotate(Vec3f.aNX, this.speedPitch * amount);
		}

		return this;
	}
	
	public Camera rotateYaw(double amount)
	{
		if(this.limitRotYaw)
		{
			if(amount > 0.0f)
			{
				if(this.transform.getRotation().getEulerYaw() + this.speedYaw * amount < this.maxRotYaw)
					this.transform.rotate(Vec3f.aY, this.speedYaw * amount);
			}
			else if(amount < 0.0f)
			{
				if(this.transform.getRotation().getEulerYaw() + this.speedYaw * amount > this.minRotYaw)
					this.transform.rotate(Vec3f.aY, this.speedYaw * amount);
			}
		}
		else
		{
			if(amount != 0.0f && amount != -0.0f)
				this.transform.rotate(Vec3f.aY, this.speedYaw * amount);
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
					this.transform.rotate(Vec3f.aZ, this.speedYaw * amount);
			}
			else if(amount < 0.0f)
			{
				if(this.transform.getRotation().getEulerRoll() + this.speedRoll * amount > this.minRotRoll)
					this.transform.rotate(Vec3f.aZ, this.speedRoll * amount);
			}
		}
		else
		{
			if(amount != 0.0f && amount != -0.0f)
				this.transform.rotate(Vec3f.aZ, this.speedRoll * amount);
		}

		return this;
	}
}
