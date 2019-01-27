package org.resources.objects;

import cmn.utilslib.math.vector.Vector3f;
import cmn.utilslib.math.geometry.Point3f;
import cmn.utilslib.math.vector.Vector2f;

public class Vertex
{
	public Point3f pos;
	public Vector2f uv;
	public Vector3f normal;
	public Vector3f tangent = new Vector3f();
	public int index;
}
