package org.resources.objects;

import java.util.ArrayList;

import cmn.utilslib.essentials.Auto;
import cmn.utilslib.math.geometry.Point3f;
import cmn.utilslib.math.geometry.Triangle3f;
import cmn.utilslib.math.vector.Vector2f;
import cmn.utilslib.math.vector.Vector3f;

public class ObjectData
{
	public ArrayList<Triangle3f> triangles = Auto.ArrayList();
	public ArrayList<TriangleData> data = Auto.ArrayList();

	public ArrayList<Vector2f> uvs = Auto.ArrayList();
	public ArrayList<Vector3f> normals =Auto.ArrayList();
	public ArrayList<Point3f> pos = Auto.ArrayList();
	public ArrayList<Integer> indices = Auto.ArrayList();
}
