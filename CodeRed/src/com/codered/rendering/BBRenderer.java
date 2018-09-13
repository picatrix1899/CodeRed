package com.codered.rendering;


import cmn.utilslib.color.colors.api.IColor3Base;
import cmn.utilslib.math.geometry.AABB3f;
import cmn.utilslib.math.geometry.OBB3f;
import cmn.utilslib.math.geometry.Point3f;
import cmn.utilslib.math.vector.Vector3f;
import cmn.utilslib.math.vector.api.Vec3f;

public class BBRenderer
{

	public static void drawAABB3f(AABB3f aabb, IColor3Base c)
	{
		
		drawAABBBox(aabb, c);
		
	}
	
	public static void drawOBB3f(OBB3f obb, IColor3Base c)
	{
		
		drawOBBBox(obb, c);
		
	}
	
	public static void drawOBBBox(OBB3f obb, IColor3Base c)
	{
		Point3f[] points = obb.getPoints();
		
		Vec3f[] vertices = new Vec3f[8];
		
		vertices[0] = points[0].asVector3f();
		vertices[1] = points[1].asVector3f();
		vertices[2] = points[2].asVector3f();
		vertices[3] = points[3].asVector3f();
		vertices[4] = points[4].asVector3f();
		vertices[5] = points[5].asVector3f();
		vertices[6] = points[6].asVector3f();
		vertices[7] = points[7].asVector3f();

		PrimitiveRenderer.drawBox(	vertices[0], vertices[1], vertices[2], vertices[3],
									vertices[4], vertices[5], vertices[6], vertices[7], c);
	}
	
	public static void drawAABBBox(AABB3f aabb, IColor3Base c)
	{
		Point3f[] points = aabb.getPoints();
		
		Vec3f[] vertices = new Vec3f[8];
		
		vertices[0] = points[0].asVector3f(new Vector3f());
		vertices[1] = points[1].asVector3f(new Vector3f());
		vertices[2] = points[2].asVector3f(new Vector3f());
		vertices[3] = points[3].asVector3f(new Vector3f());
		vertices[4] = points[4].asVector3f(new Vector3f());
		vertices[5] = points[5].asVector3f(new Vector3f());
		vertices[6] = points[6].asVector3f(new Vector3f());
		vertices[7] = points[7].asVector3f(new Vector3f());
		
		PrimitiveRenderer.drawBox(	vertices[0], vertices[1], vertices[2], vertices[3],
									vertices[4], vertices[5], vertices[6], vertices[7], c);
	}

}
