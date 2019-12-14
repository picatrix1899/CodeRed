package com.codered.assimp;

import java.util.ArrayList;
import java.util.List;

import org.barghos.math.geometry.Triangle3f;

import com.codered.resource.object.TriangleData;

public class MeshData
{
	public int materialIndex;
	
	public List<TriangleData>triangleData = new ArrayList<>();
	public List<Integer> indices = new ArrayList<>();
	public List<Triangle3f> triangles = new ArrayList<>();
}
