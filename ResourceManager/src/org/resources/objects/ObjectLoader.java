package org.resources.objects;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.resources.utils.ResourceLoader;

import cmn.utilslib.math.geometry.Point3f;
import cmn.utilslib.math.geometry.Triangle3f;
import cmn.utilslib.math.vector.Vector2f;
import cmn.utilslib.math.vector.Vector3f;

public class ObjectLoader extends ResourceLoader<ObjectData>
{

	protected ObjectData loadResource(InputStream stream) throws Exception
	{
		ObjectData data = new ObjectData();
		
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			String line = "";
			String[] parts;

			Point3f position;
			
			while((line = reader.readLine()) != null)
			{
				if(line.startsWith("v "))
				{
					parts = line.split(" ");
					
					position = new Point3f(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]), Float.parseFloat(parts[3]));
					
					data.pos.add(position);
				}
				else if(line.startsWith("vt "))
				{
					parts = line.split(" ");
					data.uvs.add(new Vector2f(Float.parseFloat(parts[1]),Float.parseFloat(parts[2])));
				}
				else if(line.startsWith("vn "))
				{
					parts = line.split(" ");
					data.normals.add(new Vector3f(Float.parseFloat(parts[1]),Float.parseFloat(parts[2]),Float.parseFloat(parts[3])));
				}
				else if(line.startsWith("f "))
				{
					parts = line.split(" ");
					
					
					Vertex vA = processVertex(data, parts[1]);
					Vertex vB = processVertex(data, parts[2]);
					Vertex vC = processVertex(data, parts[3]);

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

					data.triangles.add(tr);
					data.data.add(d);
				}
			}
			
			reader.close();
			
			return data;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}

private Vertex processVertex(ObjectData data, String line)
{
	String[] vertex = line.split("/");
	
	int posIndex =	Integer.parseInt(vertex[0]) - 1;
	int textureIndex =	Integer.parseInt(vertex[1]) - 1;
	int normalIndex =	Integer.parseInt(vertex[2]) - 1;
	
	Vertex v;
	
	v = new Vertex();
	
	v.pos = data.pos.get(posIndex);
	v.uv = data.uvs.get(textureIndex);
	v.normal = data.normals.get(normalIndex);
	v.tangent = new Vector3f();

	data.indices.add(data.indices.size());
	
	return v;
}

private void calculateTangents(Vertex a, Vertex b, Vertex c)
{
	Vector3f deltaPos1 = a.pos.vectorTof(b.pos, new Vector3f());
	Vector3f deltaPos2 = a.pos.vectorTof(c.pos, new Vector3f());
	
	Vector2f uv0 = a.uv;
	Vector2f uv1 = b.uv;
	Vector2f uv2 = c.uv;
	
	Vector2f deltaUv1 = uv1.subN(uv0);
	Vector2f deltaUv2 = uv2.subN(uv0);

	float r = 1.0f / (deltaUv1.getX() * deltaUv2.getY() - deltaUv1.getY() * deltaUv2.getX());
	deltaPos1.mul(deltaUv2.getY());
	deltaPos2.mul(deltaUv1.getY());
	Vector3f tangent = deltaPos1.subN(deltaPos2);
	tangent.mul(r);
	
	a.tangent.add(tangent);
	b.tangent.add(tangent);
	c.tangent.add(tangent);
}
}
