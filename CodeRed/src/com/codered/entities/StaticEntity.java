package com.codered.entities;

import cmn.utilslib.math.matrix.Matrix4f;

import com.codered.managing.models.TexturedModel;
import com.codered.utils.WindowContextHelper;

import cmn.utilslib.math.Quaternion;
import cmn.utilslib.math.vector.Vector3f;

public class StaticEntity extends BaseEntity
{
	
	protected TexturedModel model;
	
	public StaticEntity(TexturedModel model, Vector3f pos, float rx, float ry, float rz)
	{
		this.model = model;
		
		setPos(pos);
		setRotX(rx);
		setRotY(ry);
		setRotZ(rz);
	}

	public StaticEntity(String model, Vector3f pos, float rx, float ry, float rz)
	{
		this.model = WindowContextHelper.getCurrentContext().getResourceManager().getTexturedModel(model);
		
		setPos(pos);
		setRotX(rx);
		setRotY(ry);
		setRotZ(rz);
	}
	
	public StaticEntity setPos(Vector3f pos) { super.setPos(pos); return this; }

	public StaticEntity setModel(TexturedModel model) { this.model = model; return this; }
	
	public StaticEntity setRotX(float rx) { super.rotatePitch(rx); return this; }
	
	public StaticEntity setRotY(float ry) { super.rotateYaw(ry); return this; }
	
	public StaticEntity setRotZ(float rz) { super.rotateZ(rz); return this; }

	public TexturedModel getModel() { return model; }

	public Vector3f getPos() { return super.getPos(); }
	
	public Quaternion getRot() { return super.getRot(); }

	public Matrix4f getTransformationMatrix()
	{
		
		Matrix4f baseTransformation = Matrix4f.modelMatrix(getTransform().getPos(), getTransform().getRot(), Vector3f.ONE);
		
		Matrix4f modelTransformation = baseTransformation.mul(this.model.getModel().getMatrix());
		Matrix4f ownTransformation = modelTransformation.mul(Matrix4f.scaling(getTransform().getScale()));;
		if(this.transform.getParent() != null)
		{
			Matrix4f parentTransformation = this.transform.getParent().getTransformationMatrix();
			
			return parentTransformation.mul(ownTransformation);
		}
		
		return ownTransformation;
	}
	
	public Matrix4f getTMatrix()
	{
		

		return Matrix4f.translation(this.transform.getPos());
	}
	
	public Matrix4f Test()
	{
	
		Matrix4f baseTransformation = Matrix4f.modelMatrix(getTransform().getPos(), getTransform().getRot(), Vector3f.ONE);
	
		Matrix4f ownTransformation = baseTransformation.mul(Matrix4f.scaling(getTransform().getScale()));;
		if(this.transform.getParent() != null)
		{
			Matrix4f parentTransformation = this.transform.getParent().getTransformationMatrix();
			
			return parentTransformation.mul(ownTransformation);
		}
		
		return baseTransformation;
		
	}
	
	public Matrix4f getRotationMatrix()
	{
		return Matrix4f.rotation(this.transform.getRot());
	}
	
	public Matrix4f getTransformedScaleMatrix()
	{
		Matrix4f modelTransformation = this.model.getModel().getMatrix();
		Matrix4f ownTransformation = modelTransformation.mul(Matrix4f.scaling(getTransform().getScale()));;
		
		return ownTransformation;
	}
	
	public Matrix4f getMatrix()
	{
		return this.model.getModel().getMatrix();
	}
}
