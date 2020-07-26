package com.codered;

import org.barghos.core.tuple3.api.Tup3fR;
import org.barghos.math.matrix.Mat4;
import org.barghos.math.vector.quat.Quat;
import org.barghos.math.vector.vec3.Vec3;

public interface ITransform
{
	void swap();
	
	ITransform setParent(ITransform parent);
	ITransform getParent();

	ITransform setRotation(EulerRotation rot);
	EulerRotation getRotation();
	
	ITransform setPos(Tup3fR pos);
	Vec3 getPos(float alpha);
	Vec3 getPos();
	
	Vec3 getTransformedPos(float alpha);
	Vec3 getTransformedPos();
	
	ITransform rotate(float pitch, float yaw, float roll);
	ITransform rotate(Tup3fR v, float angle);
	Quat getRot(float alpha);
	Quat getRot();
	
	Quat getTransformedRot(float alpha);
	Quat getTransformedRot();
	
	ITransform setScale(Tup3fR scale);
	Vec3 getScale(float alpha);
	Vec3 getScale();
	
	Mat4 getTransformationMatrix(float alpha);
	Mat4 getTransformationMatrix();
}
