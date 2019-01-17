package com.codered.utils;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import com.codered.managing.VAO;
import com.codered.material.Material;
import com.codered.shaders.object.SimpleObjectShader;
import com.codered.shaders.object.simple.Colored_OShader;

import cmn.utilslib.color.colors.api.IColor3Base;
import cmn.utilslib.math.matrix.Matrix4f;
import cmn.utilslib.math.Quaternion;
import cmn.utilslib.math.vector.Vector2f;
import cmn.utilslib.math.vector.Vector3f;
import cmn.utilslib.math.vector.api.Vec3fBase;
import cmn.utilslib.math.vector.api.Vec2fBase;
import cmn.utilslib.math.vector.api.Vec3f;

public class PrimitiveRenderer
{
	private static VAO vao;
	
	public static void create()
	{
		vao = new VAO();
	}
	
	public static void drawPoint(Vec3fBase point, IColor3Base c, float size)
	{
		Vec3fBase[] vertices = new Vec3fBase[1];
		
		
		vertices[0] = point;
		
		int[] indices = new int[]
		{
			0
		};
		
		vao.storeData(0, vertices, 0, 0, GL15.GL_STATIC_DRAW);
		vao.storeIndices(indices, GL15.GL_STATIC_DRAW);
		
		BindingUtils.bindVAO(vao, 0);
		Colored_OShader shader = WindowContextHelper.getCurrentContext().getShader(Colored_OShader.class);
		shader.u_color.set(c);
		
		shader.start();
		
		GL11.glPointSize(size);
		GL11.glDrawElements(GL11.GL_POINTS, indices.length, GL11.GL_UNSIGNED_INT, 0);
		
		shader.stop();
	}
	
	public static void drawLine(Vec3fBase p1, Vec3fBase p2, IColor3Base c)
	{
		Vec3fBase[] vertices = new Vec3fBase[2];
		
		vertices[0] = p1;
		vertices[1] = p2;
		
		
		vao.storeData(0, vertices, 0, 0, GL15.GL_STATIC_DRAW);
		
		BindingUtils.bindVAO(vao, 0);
		
		Colored_OShader shader = WindowContextHelper.getCurrentContext().getShader(Colored_OShader.class);
		shader.u_color.set(c);
		
		shader.start();
		
		GL11.glLineWidth(3);
		GL11.glDrawArrays(GL11.GL_LINES,  0, 2);
		
		shader.stop();
	}
	
	public static void drawTexturedQuad(Vec3f p0, Vec3f p1, Vec3f p2, Vec3f p3, Material m, SimpleObjectShader shader)
	{
		Matrix4f model = Matrix4f.modelMatrix(Vector3f.ZERO, new Quaternion(), Vector3f.ONE);
		
		shader.u_T_model.set(model);
		
		Vec3fBase[] vertices = new Vec3fBase[4];
		
		vertices[0] = p0;
		vertices[1] = p1;
		vertices[2] = p2;
		vertices[3] = p3;
		

		int[] indices = new int[] {
		0,1,
		1,2,
		2,3,
		3,0
		};
		
		Vec2fBase[] uvs = new Vec2fBase[] {
		new Vector2f(0,0),
		new Vector2f(1,0),
		new Vector2f(1,1),
		new Vector2f(0,1)
		};

		vao.storeData(0, vertices, 0, 0, GL15.GL_STATIC_DRAW);
		vao.storeData(1, uvs, 0, 0, GL15.GL_STATIC_DRAW);
		vao.storeIndices(indices, GL15.GL_STATIC_DRAW);
		
		BindingUtils.bindVAO(vao, 0, 1);
		
		//shader.setInput("material", m);
		
		shader.start();
		
		GL11.glDrawElements(GL11.GL_QUADS, indices.length, GL11.GL_UNSIGNED_INT, 0);
		
		shader.stop();
	}
	
//	public static void drawTexturedQuad2D(Vector2f p0, Vector2f p1, Vector2f p2, Vector2f p3, int texture, Base_GUIShader shader)
//	{
//		shader.setInput("ambientLight", Enviroment.globalAmbientLight);
//		
//		Vector2f[] vertices = new Vector2f[4];
//		
//		vertices[0] = p0;
//		vertices[1] = p1;
//		vertices[2] = p2;
//		vertices[3] = p3;
//		
//
//		int[] indices = new int[] {
//		0,1,
//		1,2,
//		2,3,
//		3,0
//		};
//		
//		Vector2f[] uvs = new Vector2f[] {
//		new Vector2f(0,0),
//		new Vector2f(1,0),
//		new Vector2f(1,1),
//		new Vector2f(0,1)
//		};
//
//		vao.storeData(0, vertices, GL15.GL_STATIC_DRAW);
//		vao.storeData(1, uvs, GL15.GL_STATIC_DRAW);
//		vao.storeIndices(indices, GL15.GL_STATIC_DRAW);
//		
//		vao.bind(0, 1);
//		
//		shader.loadTexture(texture);
//		shader.use();
//		
//		GL11.glDrawElements(GL11.GL_QUADS, indices.length, GL11.GL_UNSIGNED_INT, 0);
//		
//		shader.stop();
//		
//		vao.unbind(0, 1);
//	}
//	
//	public static void drawTest(Vector2f p0, Vector2f p1, Vector2f p2, Vector2f p3, int texture, Base_GUIShader shader)
//	{
//		shader.setInput("ambientLight", Enviroment.globalAmbientLight);
//		
//		Vector2f[] vertices = new Vector2f[4];
//		
//		vertices[0] = p0;
//		vertices[1] = p1;
//		vertices[2] = p2;
//		vertices[3] = p3;
//		
//
//		int[] indices = new int[] {
//		0,1,
//		1,2,
//		2,3,
//		3,0
//		};
//		
//		Vector2f[] uvs = new Vector2f[] {
//		new Vector2f(0,0),
//		new Vector2f(1,0),
//		new Vector2f(1,1),
//		new Vector2f(0,1)
//		};
//
//		vao.storeData(0, vertices, GL15.GL_STATIC_DRAW);
//		vao.storeData(1, uvs, GL15.GL_STATIC_DRAW);
//		vao.storeIndices(indices, GL15.GL_STATIC_DRAW);
//		
//		vao.bind(0, 1);
//		
//		shader.loadTexture(texture);
//		shader.use();
//		
//		GL11.glDrawElements(GL11.GL_QUADS, indices.length, GL11.GL_UNSIGNED_INT, 0);
//		
//		shader.stop();
//		
//		vao.unbind(0, 1);
//	}
	
	
	public static void drawBox(Vec3fBase p0, Vec3fBase p1, Vec3fBase p2, Vec3fBase p3,
							   Vec3fBase p4, Vec3fBase p5, Vec3fBase p6, Vec3fBase p7, IColor3Base c)
	{

		Vec3fBase[] vertices = new Vec3fBase[8];
		
		vertices[0] = p0;
		vertices[1] = p1;
		vertices[2] = p2;
		vertices[3] = p3;
		vertices[4] = p4;
		vertices[5] = p5;
		vertices[6] = p6;
		vertices[7] = p7;
		
		int[] indices = new int[] {
		0,1,
		1,2,
		2,3,
		3,0,
		4,5,
		5,6,
		6,7,
		7,4,
		0,6,
		1,7,
		2,4,
		3,5
		};

		vao.storeData(0, vertices, 0, 0, GL15.GL_STATIC_DRAW);
		vao.storeIndices(indices, GL15.GL_STATIC_DRAW);
		
		BindingUtils.bindVAO(vao, 0);
		
		Colored_OShader shader = WindowContextHelper.getCurrentContext().getShader(Colored_OShader.class);
		shader.u_color.set(c);
		shader.start();
		
		GL11.glLineWidth(10);
		GL11.glDrawElements(GL11.GL_LINES, indices.length, GL11.GL_UNSIGNED_INT, 0);
		
		shader.stop();
	}
}
