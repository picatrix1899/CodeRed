package com.codered.utils;

import cmn.utilslib.math.Maths;
import cmn.utilslib.math.matrix.Matrix4f;
import cmn.utilslib.math.vector.Vector2f;
import cmn.utilslib.math.vector.Vector3f;
import cmn.utilslib.math.vector.api.Vec2fBase;

public class MathUtils
{
	public static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos) {
		float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
		float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
		float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
		float l3 = 1.0f - l1 - l2;
		return l1 * p1.y + l2 * p2.y + l3 * p3.y;
	}
	
	public static Matrix4f createProjectionMatrix(Vec2fBase size, float fovX, float fovY, float near, float far)
	{
		Matrix4f projectionMatrix = Matrix4f.identity();
		
		float tanHalfFOVX = (float)Math.tan(Maths.DEG_TO_RAD * (fovX / 2f));
		float tanHalfFOVY = (float)Math.tan(Maths.DEG_TO_RAD * (fovY / 2f));
		float aspectRatio = size.getX() / size.getY();
		float y_scale = 1.0f / (tanHalfFOVY * aspectRatio);
		float x_scale = 1.0f / tanHalfFOVX;//y_scale / aspectRatio;
		float range = far - near;
		
		projectionMatrix = new Matrix4f();
		
		projectionMatrix.m0.x = x_scale;
		projectionMatrix.m1.y = y_scale;
		projectionMatrix.m2.z = -((far + near) / range); //-((FAR + NEAR) / frustrum_length);
		
		projectionMatrix.m2.a = -((2 * near * far) / range);
		projectionMatrix.m3.a = 0;
		projectionMatrix.m3.z = -1;
		
		return projectionMatrix;
	}
}
