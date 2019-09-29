package com.codered;

import org.barghos.core.tuple.tuple3.api.ITup3R;
import org.barghos.math.vector.Quat;
import org.barghos.math.vector.Vec3fAxis;

public class EulerRotation
{
	public double rotPitch;
	public double rotYaw;
	public double rotRoll;
	
	public EulerRotation()
	{
		this.rotPitch = 0.0d;
		this.rotYaw = 0.0d;
		this.rotRoll = 0.0d;
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
	
	public void rotate(double pitch, double yaw, double roll)
	{
		this.rotPitch += pitch;
		this.rotYaw += yaw;
		this.rotRoll += roll;
	}
	
	public void rotate(ITup3R v, double angle)
	{
		
		rotate(angle * v.getUniX(), angle * v.getUniY(), angle * v.getUniZ());
	}

	public double getEulerPitch()
	{
		return this.rotPitch;
	}

	public double getEulerYaw()
	{
		return this.rotYaw;
	}

	public double getEulerRoll()
	{
		return this.rotRoll;
	}
	
	public Quat getRotationPitch()
	{
		return Quat.getFromAxis(Vec3fAxis.AXIS_NX, getEulerPitch());
	}

	public Quat getRotationYaw()
	{
		return Quat.getFromAxis(Vec3fAxis.AXIS_Y, getEulerYaw());
	}

	public Quat getRotationRoll()
	{
		return Quat.getFromAxis(Vec3fAxis.AXIS_Z, getEulerRoll());
	}
	
}