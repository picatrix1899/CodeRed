package com.codered.entities;

import org.barghos.core.tuple3.api.Tup3fR;
import org.barghos.math.matrix.Mat4f;
import org.barghos.math.quat.Quatf;
import org.barghos.math.vec3.Vec3f;
import org.barghos.math.vec3.Vec3fAxis;

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
	
	public Quatf getRot() { return super.getRot(); }

	public Mat4f getTransformationMatrix()
	{
		Mat4f baseTransformation = Mat4f.modelMatrix(getTransform().getPos(), getTransform().getRot(), Vec3fAxis.ONE);
		
		Mat4f ownTransformation = baseTransformation.mul(Mat4f.scaling3D(getTransform().getScale()), null);
		if(this.transform.getParent() != null)
		{
			Mat4f parentTransformation = this.transform.getParent().getTransformationMatrix();
			
			return parentTransformation.mul(ownTransformation, null);
		}
		
		return ownTransformation;
	}
	
	public Mat4f getTMatrix()
	{
		return Mat4f.translation3D(this.transform.getPos());
	}
	
	public Mat4f Test()
	{
	
		Mat4f baseTransformation = Mat4f.modelMatrix(getTransform().getPos(), getTransform().getRot(), Vec3fAxis.ONE);
	
		Mat4f ownTransformation = baseTransformation.mul(Mat4f.scaling3D(getTransform().getScale()), null);
		if(this.transform.getParent() != null)
		{
			Mat4f parentTransformation = this.transform.getParent().getTransformationMatrix();
			
			return parentTransformation.mul(ownTransformation, null);
		}
		
		return baseTransformation;
		
	}
	
	public Mat4f getRotationMatrix()
	{
		return Mat4f.rotation3D(this.transform.getRot());
	}
	
	public Mat4f getTransformedScaleMatrix()
	{
		Mat4f ownTransformation = Mat4f.scaling3D(getTransform().getScale());
		
		return ownTransformation;
	}
}
