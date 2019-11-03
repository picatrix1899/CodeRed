package com.codered.resource.object;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.barghos.math.point.Point3;
import org.barghos.math.vector.vec2.Vec2;
import org.barghos.math.vector.vec3.Vec3;
import org.barghos.math.geometry.Triangle3f;

public class ObjectLoader
{

	public static ObjectData loadResource(InputStream stream) throws Exception
	{
		ObjectData data = new ObjectData();
		
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			String line = "";
			String[] parts;

			Point3 position;
			
			while((line = reader.readLine()) != null)
			{
				if(line.startsWith("v "))
				{
					parts = line.split(" ");
					
					position = new Point3(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]), Float.parseFloat(parts[3]));
					
					data.pos.add(position);
				}
				else if(line.startsWith("vt "))
				{
					parts = line.split(" ");
					data.uvs.add(new Vec2(Float.parseFloat(parts[1]),Float.parseFloat(parts[2])));
				}
				else if(line.startsWith("vn "))
				{
					parts = line.split(" ");
					data.normals.add(new Vec3(Float.parseFloat(parts[1]),Float.parseFloat(parts[2]),Float.parseFloat(parts[3])));
				}
				else if(line.startsWith("f "))
				{
					parts = line.split(" ");
					
					
					Vertex vA = processVertex(data, parts[1]);
					Vertex vB = processVertex(data, parts[2]);
					Vertex vC = processVertex(data, parts[3]);

					calculateTangents(vA, vB, vC);	
					
					Triangle3f tr = new Triangle3f(vA.pos, vB.pos, vC.pos);
					
					Triangle3f t = new Triangle3f(vA.p, vB.p, vC.p);
					
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

					data.tr.add(t);
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

private static Vertex processVertex(ObjectData data, String line)
{
	String[] vertex = line.split("/");
	
	int posIndex =	Integer.parseInt(vertex[0]) - 1;
	int textureIndex =	Integer.parseInt(vertex[1]) - 1;
	int normalIndex =	Integer.parseInt(vertex[2]) - 1;
	
	Vertex v;
	
	v = new Vertex();
	
	v.pos = data.pos.get(posIndex);
	v.p = new Point3(v.pos.getX(), v.pos.getY(), v.pos.getZ());
	v.uv = data.uvs.get(textureIndex);
	v.normal = data.normals.get(normalIndex);
	v.tangent = new Vec3();

	data.indices.add(data.indices.size());
	
	return v;
}

private static void calculateTangents(Vertex a, Vertex b, Vertex c)
{
	Vec3 deltaPos1 = b.pos.sub(a.pos, null);
	Vec3 deltaPos2 = c.pos.sub(a.pos, null);
	
	Vec2 uv0 = a.uv;
	Vec2 uv1 = b.uv;
	Vec2 uv2 = c.uv;
	
	Vec2 deltaUv1 = uv1.sub(uv0, null);
	Vec2 deltaUv2 = uv2.sub(uv0, null);

	float r = 1.0f / (deltaUv1.getX() * deltaUv2.getY() - deltaUv1.getY() * deltaUv2.getX());
	deltaPos1.mul(deltaUv2.getY(), deltaPos1);
	deltaPos2.mul(deltaUv1.getY(), deltaPos2);
	Vec3 tangent = deltaPos1.sub(deltaPos2, null);
	tangent.mul(r, tangent);
	
	a.tangent.add(tangent, a.tangent);
	b.tangent.add(tangent, b.tangent);
	c.tangent.add(tangent, c.tangent);
}
}
