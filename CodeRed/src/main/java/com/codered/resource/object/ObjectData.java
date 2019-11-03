package com.codered.resource.object;

import java.util.ArrayList;
import java.util.List;

import org.barghos.math.geometry.Triangle3f;
import org.barghos.math.point.Point3;
import org.barghos.math.vector.vec2.Vec2;
import org.barghos.math.vector.vec3.Vec3;

public class ObjectData
{
	public List<Triangle3f> tr = new ArrayList<>();
	public ArrayList<Triangle3f> triangles = new ArrayList<>();
	public ArrayList<TriangleData> data = new ArrayList<>();

	public ArrayList<Vec2> uvs = new ArrayList<>();
	public ArrayList<Vec3> normals = new ArrayList<>();
	public ArrayList<Point3> pos = new ArrayList<>();
	public ArrayList<Integer> indices = new ArrayList<>();
}
