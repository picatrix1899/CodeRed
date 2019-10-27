package com.codered.resource.object;

import java.util.ArrayList;
import java.util.List;

import org.barghos.math.geometry.Triangle3f;
import org.barghos.math.point.Point3f;
import org.barghos.math.vector.Vec2f;
import org.barghos.math.vector.Vec3f;

public class ObjectData
{
	public List<org.barghos.math.experimental.geometry.Triangle3f> tr = new ArrayList<>();
	public ArrayList<Triangle3f> triangles = new ArrayList<>();
	public ArrayList<TriangleData> data = new ArrayList<>();

	public ArrayList<Vec2f> uvs = new ArrayList<>();
	public ArrayList<Vec3f> normals = new ArrayList<>();
	public ArrayList<Point3f> pos = new ArrayList<>();
	public ArrayList<Integer> indices = new ArrayList<>();
}
