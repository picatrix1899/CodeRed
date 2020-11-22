package com.codered.entities;

import org.barghos.core.tuple3.api.Tup3fR;

import org.barghos.math.matrix.Mat4f;
import org.barghos.math.vec3.Vec3f;

import com.codered.model.Model;

public class StaticModelEntity extends BaseEntity
{

	private Model model;

	public StaticModelEntity(Model model, Vec3f pos, float rx, float ry, float rz, Tup3fR scale)
	{
		this.model = model;
		
		this.transform.setRelativePosition(pos);
		this.transform.setRelativeOrientation(rx, ry, rz);
		this.transform.setRelativeScale(scale);
	}

	
	public StaticModelEntity(Model model, Vec3f pos, float rx, float ry, float rz)
	{
		this.model = model;
		
		this.transform.setRelativePosition(pos);
		this.transform.setRelativeOrientation(rx, ry, rz);
	}

	public Model getModel()
	{
		return this.model;
	}
	
	public Mat4f getTransformationMatrix()
	{
		return this.transform.getAbsoluteTransformationMatrix4f();
	}
}
