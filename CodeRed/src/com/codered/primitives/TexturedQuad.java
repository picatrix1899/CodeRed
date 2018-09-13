package com.codered.primitives;

import org.lwjgl.opengl.GL15;

import com.codered.managing.VAO;
import com.codered.material.Material;

import cmn.utilslib.math.Transform;
import cmn.utilslib.math.matrix.Matrix4f;
import cmn.utilslib.math.vector.Vector2f;
import cmn.utilslib.math.vector.Vector3f;
import cmn.utilslib.math.vector.api.Vec3f;
import cmn.utilslib.math.vector.api.Vec3fBase;

public class TexturedQuad
{
	private Material mat;
	
	private Transform transform = new Transform();
	
	private Vector3f newX = new Vector3f();
	private Vector3f newZ = new Vector3f();
	
	private VAO vao;
	
	public TexturedQuad(Vec3fBase newX, Vec3fBase newZ, Material mat)
	{
		this.newX.set(newX);
		this.newZ.set(newZ);
		this.mat = mat;

		Vector3f[] v = new Vector3f[4];
		v[0] = new Vector3f().add(newZ);
		v[1] = new Vector3f();
		v[2] = new Vector3f().add(newX);
		v[3] = new Vector3f().add(newX).add(newZ);
		
		Vector3f normal = (Vector3f)newX.crossN(newZ).normalize().negate();
		Vector3f tangent = calculateTangents();
		
		this.vao = new VAO();
		this.vao.storeData(0, v, 0, 0, GL15.GL_STATIC_DRAW);
		this.vao.storeData(1, new Vector2f[] { new Vector2f(0,0), new Vector2f(0,1), new Vector2f(1,1), new Vector2f(1,0) }, 0, 0, GL15.GL_STATIC_DRAW);
		this.vao.storeData(2, new Vector3f[] {normal, normal, normal, normal}, 0, 0, GL15.GL_STATIC_DRAW);
		this.vao.storeData(3, new Vector3f[] {tangent, tangent, tangent, tangent}, 0, 0, GL15.GL_STATIC_DRAW);
		this.vao.storeIndices(new int[] {0, 1, 2, 2, 3, 0}, GL15.GL_STATIC_DRAW);
	}
	
	private Vector3f calculateTangents()
	{
		Vector3f deltaPos1 = new Vector3f(newX);
		Vector3f deltaPos2 = new Vector3f(newZ);
		
		Vector2f uv0 = new Vector2f(0,1);
		Vector2f uv1 = new Vector2f(1,1);
		Vector2f uv2 = new Vector2f(0,0);
		
		Vector2f deltaUv1 = uv1.subN(uv0);
		Vector2f deltaUv2 = uv2.subN(uv0);

		float r = 1.0f / (deltaUv1.getX() * deltaUv2.getY() - deltaUv1.getY() * deltaUv2.getX());
	
		deltaPos1.mul(deltaUv2.getY());
		deltaPos2.mul(deltaUv1.getY());
		
		Vector3f tangent = deltaPos1.subN(deltaPos2);
		
		tangent.mul(r);
		
		return tangent.normalize();
	}
	
	public Matrix4f getTransformationMatrix()
	{
		
		Matrix4f baseTransformation = Matrix4f.modelMatrix(getTransform().getPos(), getTransform().getRot(), Vec3f.ONE);
		
		Matrix4f ownTransformation = baseTransformation.mul(Matrix4f.scaling(getTransform().getScale()));;
		if(this.transform.getParent() != null)
		{
			Matrix4f parentTransformation = this.transform.getParent().getTransformationMatrix();
			
			return parentTransformation.mul(ownTransformation);
		}
		
		return ownTransformation;
	}
	
	
	public Material getMaterial() { return this.mat; }
	public VAO getVao() { return this.vao; }
	public Transform getTransform() { return this.transform; }
}
