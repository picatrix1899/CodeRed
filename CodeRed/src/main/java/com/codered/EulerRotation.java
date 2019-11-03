package com.codered;

import org.barghos.core.tuple.tuple3.Tup3fR;
import org.barghos.math.vector.Quat;
import org.barghos.math.vector.vec3.Vec3Axis;

public class EulerRotation
{
	public float rotPitch;
	public float rotYaw;
	public float rotRoll;
	
	public EulerRotation()
	{
		this.rotPitch = 0.0f;
		this.rotYaw = 0.0f;
		this.rotRoll = 0.0f;
	}
	
	public EulerRotation set(EulerRotation rot)
	{
		this.rotPitch = rot.rotPitch;
		this.rotRoll = rot.rotRoll;
		this.rotYaw = rot.rotYaw;
		
		return this;
	}
	
	public Quat getRotation()
	{	
		return getRotationRoll().mul(getRotationYaw().mul(getRotationPitch()));
	}
	
	public void rotate(float pitch, float yaw, float roll)
	{
		this.rotPitch += pitch;
		this.rotYaw += yaw;
		this.rotRoll += roll;
	}
	
	public void rotate(Tup3fR v, float angle)
	{
		
		rotate(angle * v.getX(), angle * v.getY(), angle * v.getZ());
	}

	public float getEulerPitch()
	{
		return this.rotPitch;
	}

	public float getEulerYaw()
	{
		return this.rotYaw;
	}

	public float getEulerRoll()
	{
		return this.rotRoll;
	}
	
	public Quat getRotationPitch()
	{
		return Quat.getFromAxis(Vec3Axis.AXIS_NX, getEulerPitch());
	}

	public Quat getRotationYaw()
	{
		return Quat.getFromAxis(Vec3Axis.AXIS_Y, getEulerYaw());
	}

	public Quat getRotationRoll()
	{
		return Quat.getFromAxis(Vec3Axis.AXIS_Z, getEulerRoll());
	}
	
}