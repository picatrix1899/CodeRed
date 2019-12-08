package com.codered.rendering.primitives;

import org.barghos.math.matrix.Mat4f;
import org.barghos.math.vector.vec2.Vec2;
import org.barghos.math.vector.vec3.Vec3;
import org.barghos.math.vector.vec3.Vec3Axis;


import org.lwjgl.opengl.GL15;

import com.codered.Transform;
import com.codered.engine.EngineRegistry;
import com.codered.managing.VAO;
import com.codered.rendering.material.Material;

public class TexturedQuad
{
	private Material mat;
	
	private Transform transform = new Transform();
	
	private Vec3 newX = new Vec3();
	private Vec3 newZ = new Vec3();
	
	private VAO vao;
	
	public TexturedQuad(Vec3 newX, Vec3 newZ, Material mat)
	{
		this.newX.set(newX);
		this.newZ.set(newZ);
		this.mat = mat;

		Vec3[] v = new Vec3[4];
		v[0] = new Vec3().add(newZ, null);
		v[1] = new Vec3();
		v[2] = new Vec3().add(newX, null);
		v[3] = new Vec3().add(newX, null).add(newZ, null);
		
		Vec3 normal = newX.cross(newZ, null).normal();
		Vec3 tangent = calculateTangents();
		
		this.vao = EngineRegistry.getVAOManager().getNewVAO();
		this.vao.storeData(0, v, 0, 0, GL15.GL_STATIC_DRAW);
		this.vao.storeData(1, new Vec2[] { new Vec2(0,0), new Vec2(0,1), new Vec2(1,1), new Vec2(1,0) }, 0, 0, GL15.GL_STATIC_DRAW);
		this.vao.storeData(2, new Vec3[] {normal, normal, normal, normal}, 0, 0, GL15.GL_STATIC_DRAW);
		this.vao.storeData(3, new Vec3[] {tangent, tangent, tangent, tangent}, 0, 0, GL15.GL_STATIC_DRAW);
		this.vao.storeIndices(new int[] {0, 1, 2, 2, 3, 0}, GL15.GL_STATIC_DRAW);
	}
	
	private Vec3 calculateTangents()
	{
		Vec3 deltaPos1 = new Vec3(newX);
		Vec3 deltaPos2 = new Vec3(newZ);
		
		Vec2 uv0 = new Vec2(0,1);
		Vec2 uv1 = new Vec2(1,1);
		Vec2 uv2 = new Vec2(0,0);
		
		Vec2 deltaUv1 = uv1.sub(uv0, null);
		Vec2 deltaUv2 = uv2.sub(uv0, null);

		float r = 1.0f / (deltaUv1.getX() * deltaUv2.getY() - deltaUv1.getY() * deltaUv2.getX());
	
		deltaPos1.mul(deltaUv2.getY(), deltaPos1);
		deltaPos2.mul(deltaUv1.getY(), deltaPos2);
		
		Vec3 tangent = deltaPos1.sub(deltaPos2, null);
		
		tangent.mul(r, tangent);
		
		return tangent.normal();
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
	
	
	public Material getMaterial() { return this.mat; }
	public VAO getVao() { return this.vao; }
	public Transform getTransform() { return this.transform; }
}
