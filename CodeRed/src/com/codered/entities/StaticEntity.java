package com.codered.entities;

import org.barghos.math.matrix.Mat4f;
import org.barghos.math.vector.Quat;
import org.barghos.math.vector.vec3.Vec3;
import org.barghos.math.vector.vec3.Vec3Axis;

import com.codered.managing.models.TexturedModel;

public class StaticEntity extends BaseEntity
{
	
	protected TexturedModel model;

	public StaticEntity(TexturedModel model, Vec3 pos, float rx, float ry, float rz)
	{
		this.model = model;
		
		setPos(pos);
		setRotX(rx);
		setRotY(ry);
		setRotZ(rz);
		this.transform.swap();
	}
	
	public StaticEntity setPos(Vec3 pos) { super.setPos(pos); return this; }

	public StaticEntity setRotX(float rx) { super.rotatePitch(rx); return this; }
	
	public StaticEntity setRotY(float ry) { super.rotateYaw(ry); return this; }
	
	public StaticEntity setRotZ(float rz) { super.rotateZ(rz); return this; }

	public TexturedModel getModel() { return this.model; }

	public Vec3 getPos() { return super.getPos(); }
	
	public Quat getRot() { return super.getRot(); }

	public Mat4f getTransformationMatrix()
	{
		Mat4f baseTransformation = Mat4f.modelMatrix(getTransform().getPos(), getTransform().getRot(), Vec3Axis.ONE);
		
		Mat4f modelTransformation = baseTransformation.mul(getModel().getModel().getMatrix(), (Mat4f)null);
		Mat4f ownTransformation = modelTransformation.mul(Mat4f.scaling(getTransform().getScale()), null);
		if(this.transform.getParent() != null)
		{
			Mat4f parentTransformation = this.transform.getParent().getTransformationMatrix();
			
			return parentTransformation.mul(ownTransformation, null);
		}
		
		return ownTransformation;
	}
	
	public Mat4f getTMatrix()
	{
		return Mat4f.translation(this.transform.getPos());
	}
	
	public Mat4f Test()
	{
	
		Mat4f baseTransformation = Mat4f.modelMatrix(getTransform().getPos(), getTransform().getRot(), Vec3Axis.ONE);
	
		Mat4f ownTransformation = baseTransformation.mul(Mat4f.scaling(getTransform().getScale()), null);
		if(this.transform.getParent() != null)
		{
			Mat4f parentTransformation = this.transform.getParent().getTransformationMatrix();
			
			return parentTransformation.mul(ownTransformation, null);
		}
		
		return baseTransformation;
		
	}
	
	public Mat4f getRotationMatrix()
	{
		return Mat4f.rotation(this.transform.getRot());
	}
	
	public Mat4f getTransformedScaleMatrix()
	{
		Mat4f modelTransformation = getModel().getModel().getMatrix();
		Mat4f ownTransformation = modelTransformation.mul(Mat4f.scaling(getTransform().getScale()), null);
		
		return ownTransformation;
	}
	
	public Mat4f getMatrix()
	{
		return getModel().getModel().getMatrix();
	}
}
