package com.codered.resourcemanager.objects;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.barghos.math.geometry.Triangle3f;
import org.barghos.math.point.Point3f;
import org.barghos.math.vector.Vec2f;
import org.barghos.math.vector.Vec3f;

import com.codered.resourcemanager.utils.ResourceLoader;

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
					data.uvs.add(new Vec2f(Float.parseFloat(parts[1]),Float.parseFloat(parts[2])));
				}
				else if(line.startsWith("vn "))
				{
					parts = line.split(" ");
					data.normals.add(new Vec3f(Float.parseFloat(parts[1]),Float.parseFloat(parts[2]),Float.parseFloat(parts[3])));
				}
				else if(line.startsWith("f "))
				{
					parts = line.split(" ");
					
					
					Vertex vA = processVertex(data, parts[1]);
					Vertex vB = processVertex(data, parts[2]);
					Vertex vC = processVertex(data, parts[3]);

					calculateTangents(vA, vB, vC);	
					
					Triangle3f tr = new Triangle3f();
					tr.set(vA.pos, vB.pos, vC.pos);
					
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
	v.tangent = new Vec3f();

	data.indices.add(data.indices.size());
	
	return v;
}

private void calculateTangents(Vertex a, Vertex b, Vertex c)
{
	Vec3f deltaPos1 = Vec3f.sub(b.pos, a.pos, null);
	Vec3f deltaPos2 = Vec3f.sub(c.pos, a.pos, null);
	
	Vec2f uv0 = a.uv;
	Vec2f uv1 = b.uv;
	Vec2f uv2 = c.uv;
	
	Vec2f deltaUv1 = uv1.sub(uv0, null);
	Vec2f deltaUv2 = uv2.sub(uv0, null);

	float r = 1.0f / (deltaUv1.getX() * deltaUv2.getY() - deltaUv1.getY() * deltaUv2.getX());
	deltaPos1.mul(deltaUv2.getY(), deltaPos1);
	deltaPos2.mul(deltaUv1.getY(), deltaPos2);
	Vec3f tangent = deltaPos1.sub(deltaPos2, null);
	tangent.mul(r, tangent);
	
	a.tangent.add(tangent, a.tangent);
	b.tangent.add(tangent, b.tangent);
	c.tangent.add(tangent, c.tangent);
}
}
