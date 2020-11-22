package com.codered;

import org.barghos.core.tuple3.api.Tup3fR;
import org.barghos.math.matrix.Mat4f;
import org.barghos.math.quat.Quatf;
import org.barghos.math.vec3.Vec3f;

public interface ITransform
{
	void swap();
	
	ITransform setParent(ITransform parent);
	ITransform getParent();

	ITransform setRotation(EulerRotation rot);
	EulerRotation getRotation();
	
	ITransform setPos(Tup3fR pos);
	Vec3f getPos(float alpha);
	Vec3f getPos();
	
	Vec3f getTransformedPos(float alpha);
	Vec3f getTransformedPos();
	
	ITransform rotate(float pitch, float yaw, float roll);
	ITransform rotate(Tup3fR v, float angle);
	Quatf getRot(float alpha);
	Quatf getRot();
	
	Quatf getTransformedRot(float alpha);
	Quatf getTransformedRot();
	
	ITransform setScale(Tup3fR scale);
	Vec3f getScale(float alpha);
	Vec3f getScale();
	
	Mat4f getTransformationMatrix(float alpha);
	Mat4f getTransformationMatrix();
}
