package com.codered.entities;

import org.barghos.math.matrix.Mat4f;
import org.barghos.math.vector.vec3.Vec3;
import org.barghos.math.vector.vec3.Vec3Axis;

import com.codered.model.Model;

public class StaticModelEntity extends StaticEntity
{

	private Model model;
	
	public StaticModelEntity(Model model, Vec3 pos, float rx, float ry, float rz)
	{
		super(null, pos, rx, ry, rz);
		this.model = model;
	}

	public Model getNewModel()
	{
		return this.model;
	}
	
	public Mat4f getTransformationMatrix()
	{
		Mat4f baseTransformation = Mat4f.modelMatrix(getTransform().getPos(), getTransform().getRot(), Vec3Axis.ONE);
		
		Mat4f ownTransformation = baseTransformation.mul(Mat4f.scaling(getTransform().getScale()), null);
		if(this.transform.getParent() != null)
		{
			Mat4f parentTransformation = this.transform.getParent().getTransformationMatrix();
			
			return parentTransformation.mul(ownTransformation, null);
		}
		
		return ownTransformation;
	}
}
