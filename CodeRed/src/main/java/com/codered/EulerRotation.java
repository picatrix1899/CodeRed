package com.codered;

import org.barghos.core.tuple3.api.Tup3fR;
import org.barghos.math.vector.quat.Quatf;
import org.barghos.math.vector.vec3.Vec3fAxis;

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
	
	public Quatf getRotation()
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
	
	public Quatf getRotationPitch()
	{
		return Quatf.getFromAxis(Vec3fAxis.AXIS_NX, getEulerPitch());
	}

	public Quatf getRotationYaw()
	{
		return Quatf.getFromAxis(Vec3fAxis.AXIS_Y, getEulerYaw());
	}

	public Quatf getRotationRoll()
	{
		return Quatf.getFromAxis(Vec3fAxis.AXIS_Z, getEulerRoll());
	}
	
}