package com.codered.entities;

import org.barghos.core.tuple3.api.Tup3fR;
import org.barghos.math.matrix.Mat4;
import org.barghos.math.vector.quat.Quat;
import org.barghos.math.vector.vec3.Vec3f;
import org.barghos.math.vector.vec3.Vec3Axis;

import com.codered.model.TexturedModel;

public class StaticEntity extends BaseEntity
{
	
	protected TexturedModel model;

	public StaticEntity(TexturedModel model, Vec3f pos, float rx, float ry, float rz, Tup3fR scale)
	{
		this.model = model;
		
		setPos(pos);
		setRotX(rx);
		setRotY(ry);
		setRotZ(rz);
		this.transform.setScale(scale);
		this.transform.swap();
	}
	
	public StaticEntity(TexturedModel model, Vec3f pos, float rx, float ry, float rz)
	{
		this.model = model;
		
		setPos(pos);
		setRotX(rx);
		setRotY(ry);
		setRotZ(rz);
		this.transform.swap();
	}
	
	public StaticEntity setPos(Vec3f pos) { super.setPos(pos); return this; }

	public StaticEntity setRotX(float rx) { super.rotatePitch(rx); return this; }
	
	public StaticEntity setRotY(float ry) { super.rotateYaw(ry); return this; }
	
	public StaticEntity setRotZ(float rz) { super.rotateZ(rz); return this; }

	public TexturedModel getModel() { return this.model; }

	public Vec3f getPos() { return super.getPos(); }
	
	public Quat getRot() { return super.getRot(); }

	public Mat4 getTransformationMatrix()
	{
		Mat4 baseTransformation = Mat4.modelMatrix(getTransform().getPos(), getTransform().getRot(), Vec3Axis.ONE);
		
		Mat4 ownTransformation = baseTransformation.mul(Mat4.scaling(getTransform().getScale()), null);
		if(this.transform.getParent() != null)
		{
			Mat4 parentTransformation = this.transform.getParent().getTransformationMatrix();
			
			return parentTransformation.mul(ownTransformation, null);
		}
		
		return ownTransformation;
	}
	
	public Mat4 getTMatrix()
	{
		return Mat4.translation(this.transform.getPos());
	}
	
	public Mat4 Test()
	{
	
		Mat4 baseTransformation = Mat4.modelMatrix(getTransform().getPos(), getTransform().getRot(), Vec3Axis.ONE);
	
		Mat4 ownTransformation = baseTransformation.mul(Mat4.scaling(getTransform().getScale()), null);
		if(this.transform.getParent() != null)
		{
			Mat4 parentTransformation = this.transform.getParent().getTransformationMatrix();
			
			return parentTransformation.mul(ownTransformation, null);
		}
		
		return baseTransformation;
		
	}
	
	public Mat4 getRotationMatrix()
	{
		return Mat4.rotation(this.transform.getRot());
	}
	
	public Mat4 getTransformedScaleMatrix()
	{
		Mat4 ownTransformation = Mat4.scaling(getTransform().getScale());
		
		return ownTransformation;
	}
}
