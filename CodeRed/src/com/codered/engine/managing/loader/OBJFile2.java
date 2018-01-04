 package com.codered.engine.managing.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import com.codered.engine.managing.Vertex;

import cmn.utilslib.essentials.Auto;
import cmn.utilslib.math.geometry.Point3f;
import cmn.utilslib.math.geometry.Triangle3f;
import cmn.utilslib.math.vector.Vector2f;
import cmn.utilslib.math.vector.Vector3f;
import cmn.utilslib.math.vector.api.Vec2f;
import cmn.utilslib.math.vector.api.Vec3f;

public class OBJFile2
{
	
	public ArrayList<Triangle3f> triangles = Auto.ArrayList();
	public ArrayList<TriangleData> data = Auto.ArrayList();

	private ArrayList<Vector2f> uvs = Auto.ArrayList();
	private ArrayList<Vector3f> normals =Auto.ArrayList();
	private ArrayList<Point3f> pos = Auto.ArrayList();
	public ArrayList<Integer> indices = Auto.ArrayList();
	
	public void load(File file)
	{
		
		try
		{
			
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = "";
			String[] parts;

			Point3f position;
			
			while((line = reader.readLine()) != null)
			{
				if(line.startsWith("v "))
				{
					parts = line.split(" ");
					
					position = new Point3f(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]), Float.parseFloat(parts[3]));
					
					this.pos.add(position);
				}
				else if(line.startsWith("vt "))
				{
					parts = line.split(" ");
					uvs.add(new Vector2f(Float.parseFloat(parts[1]),Float.parseFloat(parts[2])));
				}
				else if(line.startsWith("vn "))
				{
					parts = line.split(" ");
					normals.add(new Vector3f(Float.parseFloat(parts[1]),Float.parseFloat(parts[2]),Float.parseFloat(parts[3])));
				}
				else if(line.startsWith("f "))
				{
					parts = line.split(" ");
					
					
					Vertex vA = processVertex(parts[1]);
					Vertex vB = processVertex(parts[2]);
					Vertex vC = processVertex(parts[3]);

					calculateTangents(vA, vB, vC);	
					
					Triangle3f tr = new Triangle3f();
					tr.a = vA.pos;
					tr.b = vB.pos;
					tr.c = vC.pos;
					
					TriangleData d = new TriangleData();
					d.normalA = vA.normal;
					d.normalB = vB.normal;
					d.normalC = vC.normal;
					
					d.uvA = vA.uv;
					d.uvB = vB.uv;
					d.uvC = vC.uv;
					
					d.tangentA = vA.tangent;
					d.tangentB = vB.tangent;
					d.tangentC = vC.tangent;

					this.triangles.add(tr);
					this.data.add(d);
				}
			}
			
			reader.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private Vertex processVertex(String line)
	{
		String[] vertex = line.split("/");
		
		int posIndex =	Integer.parseInt(vertex[0]) - 1;
		int textureIndex =	Integer.parseInt(vertex[1]) - 1;
		int normalIndex =	Integer.parseInt(vertex[2]) - 1;
		
		Vertex v;
		
		v = new Vertex();
		
		v.pos = this.pos.get(posIndex);
		v.uv = this.uvs.get(textureIndex);
		v.normal = this.normals.get(normalIndex);
		v.tangent = new Vector3f();

		this.indices.add(this.indices.size());
		
		return v;
	}
	
	private void calculateTangents(Vertex a, Vertex b, Vertex c)
	{
		Vec3f deltaPos1 = a.pos.vectorTof(b.pos, new Vector3f());
		Vec3f deltaPos2 = a.pos.vectorTof(c.pos, new Vector3f());
		
		Vec2f uv0 = a.uv;
		Vec2f uv1 = b.uv;
		Vec2f uv2 = c.uv;
		
		Vec2f deltaUv1 = uv1.subN(uv0);
		Vec2f deltaUv2 = uv2.subN(uv0);

		float r = 1.0f / (deltaUv1.getX() * deltaUv2.getY() - deltaUv1.getY() * deltaUv2.getX());
		deltaPos1.mul(deltaUv2.getY());
		deltaPos2.mul(deltaUv1.getY());
		Vec3f tangent = deltaPos1.subN(deltaPos2);
		tangent.mul(r);
		
		a.tangent.add(tangent);
		b.tangent.add(tangent);
		c.tangent.add(tangent);
	}
	
}
