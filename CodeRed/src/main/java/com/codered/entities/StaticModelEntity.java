package com.codered.entities;

import java.util.List;

import org.barghos.core.tuple3.api.Tup3fR;
import org.barghos.math.geometry.AABB3f;
import org.barghos.math.helper.AABB3fHelper;
import org.barghos.math.matrix.Mat4;
import org.barghos.math.vector.vec3.Vec3;
import org.barghos.math.vector.vec3.Vec3Axis;

import com.codered.model.Mesh;
import com.codered.model.Model;

public class StaticModelEntity extends StaticEntity
{

	private Model model;
	
	private AABB3f basicAABB;
	
	public StaticModelEntity(Model model, Vec3 pos, float rx, float ry, float rz, Tup3fR scale)
	{
		super(null, pos, rx, ry, rz, scale);
		this.model = model;
	}

	
	public StaticModelEntity(Model model, Vec3 pos, float rx, float ry, float rz)
	{
		super(null, pos, rx, ry, rz);
		this.model = model;
	}

	public Model getNewModel()
	{
		return this.model;
	}
	
	public AABB3f getAABB()
	{
		if(basicAABB != null) return this.basicAABB;

		List<Mesh> meshes = this.model.getMeshes();
		
		AABB3f out = new AABB3f(meshes.get(0).getCollisionMesh().get().getAABBf());
		
		for(int i = 1; i < meshes.size(); i++)
		{
			AABB3fHelper.merge(out, meshes.get(i).getCollisionMesh().get().getAABBf(), out);
		}
		
		return out;
	}
	
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
}
