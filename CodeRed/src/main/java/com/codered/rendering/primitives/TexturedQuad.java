package com.codered.rendering.primitives;

import org.barghos.math.matrix.Mat4;
import org.barghos.math.vector.vec2.Vec2f;
import org.barghos.math.vector.vec3.Vec3f;
import org.barghos.math.vector.vec3.Vec3fAxis;


import org.lwjgl.opengl.GL15;

import com.codered.Transform;
import com.codered.managing.VAO;
import com.codered.rendering.material.Material;

public class TexturedQuad
{
	private Material mat;
	
	private Transform transform = new Transform();
	
	private Vec3f newX = new Vec3f();
	private Vec3f newZ = new Vec3f();
	
	private VAO vao;
	
	public TexturedQuad(Vec3f newX, Vec3f newZ, Material mat)
	{
		this.newX.set(newX);
		this.newZ.set(newZ);
		this.mat = mat;

		Vec3f[] v = new Vec3f[4];
		v[0] = new Vec3f().add(newZ);
		v[1] = new Vec3f();
		v[2] = new Vec3f().add(newX);
		v[3] = new Vec3f().add(newX).add(newZ);
		
		Vec3f normal = newX.cross(newZ, null).normal();
		Vec3f tangent = calculateTangents();
		
		this.vao = new VAO();
		this.vao.storeData(0, v, 0, 0, GL15.GL_STATIC_DRAW);
		this.vao.storeData(1, new Vec2f[] { new Vec2f(0,0), new Vec2f(0,1), new Vec2f(1,1), new Vec2f(1,0) }, 0, 0, GL15.GL_STATIC_DRAW);
		this.vao.storeData(2, new Vec3f[] {normal, normal, normal, normal}, 0, 0, GL15.GL_STATIC_DRAW);
		this.vao.storeData(3, new Vec3f[] {tangent, tangent, tangent, tangent}, 0, 0, GL15.GL_STATIC_DRAW);
		this.vao.storeIndices(new int[] {0, 1, 2, 2, 3, 0}, GL15.GL_STATIC_DRAW);
	}
	
	private Vec3f calculateTangents()
	{
		Vec3f deltaPos1 = new Vec3f(newX);
		Vec3f deltaPos2 = new Vec3f(newZ);
		
		Vec2f uv0 = new Vec2f(0,1);
		Vec2f uv1 = new Vec2f(1,1);
		Vec2f uv2 = new Vec2f(0,0);
		
		Vec2f deltaUv1 = uv1.sub(uv0, null);
		Vec2f deltaUv2 = uv2.sub(uv0, null);

		float r = 1.0f / (deltaUv1.getX() * deltaUv2.getY() - deltaUv1.getY() * deltaUv2.getX());
	
		deltaPos1.mul(deltaUv2.getY(), deltaPos1);
		deltaPos2.mul(deltaUv1.getY(), deltaPos2);
		
		Vec3f tangent = deltaPos1.subN(deltaPos2);
		
		tangent.mul(r, tangent);
		
		return tangent.normal();
	}
	
	public Mat4 getTransformationMatrix()
	{
		
		Mat4 baseTransformation = Mat4.modelMatrix(getTransform().getPos(), getTransform().getRot(), Vec3fAxis.ONE);
		
		Mat4 ownTransformation = baseTransformation.mul(Mat4.scaling(getTransform().getScale()), null);
		if(this.transform.getParent() != null)
		{
			Mat4 parentTransformation = this.transform.getParent().getTransformationMatrix();
			
			return parentTransformation.mul(ownTransformation, null);
		}
		
		return ownTransformation;
	}
	
	
	public Material getMaterial() { return this.mat; }
	public VAO getVao() { return this.vao; }
	public Transform getTransform() { return this.transform; }
}
