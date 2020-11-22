package com.codered.demo;

import java.util.List;

import org.barghos.core.tuple3.api.Tup3fR;
import org.barghos.math.matrix.Mat4f;
import org.barghos.math.quat.Quatf;
import org.barghos.math.utils.VectorInterpolation;
import org.barghos.math.vec3.Vec3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import com.codered.entities.Camera;
import com.codered.entities.StaticModelEntity;
import com.codered.managing.VAO;
import com.codered.model.Mesh;
import com.codered.model.Model;
import com.codered.rendering.light.AmbientLight;
import com.codered.rendering.light.DirectionalLight;
import com.codered.rendering.shader.ShaderProgram;
import com.codered.rendering.shader.ShaderSession;
import com.codered.utils.BindingUtils;



public class RenderHelper
{
	public static ShaderProgram directionalLightShader;
	public static ShaderProgram ambientLightShader;
	public static ShaderProgram deferredShader;
	public static ShaderProgram shadowShader;
	public static ShaderProgram coloredShader;
	
	public static void renderAmbientLight(Model model, List<StaticModelEntity> entities, Camera cam, Mat4f projection,
			AmbientLight light, double alpha)
	{
		try(ShaderSession ss = ambientLightShader.start())
		{
			ambientLightShader.setUniformValue(4, light.base.color);
			ambientLightShader.setUniformValue(5, light.base.intensity);
			ambientLightShader.setUniformValue(1, projection);
			ambientLightShader.setUniformValue(2, cam, alpha);

			for(Mesh mesh : model.getMeshes())
			{	
				if(mesh.getMaterial().isPresent())
				{
					ambientLightShader.setUniformValue(3, mesh.getMaterial().get());
					
					BindingUtils.bindVAO(mesh.getVao(), 0, 1, 2, 3);
					
					for(StaticModelEntity entity : entities)
					{
						ambientLightShader.setUniformValue(0, entity.getTransformationMatrix());
						
						GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
					}
				}
			}
		}
	}
	
	public static void renderDirectionalLight(Model model, List<StaticModelEntity> entities, Camera cam, Mat4f projection,
			DirectionalLight light, double alpha, Mat4f lightSpace, int shadowMap)
	{
		try(ShaderSession ss = directionalLightShader.start())
		{
			directionalLightShader.setUniformValue(4, light.base.color);
			directionalLightShader.setUniformValue(5, light.base.intensity);
			directionalLightShader.setUniformValue(6, light.direction);
			directionalLightShader.setUniformValue(1, projection);
			directionalLightShader.setUniformValue(2, cam, alpha);
			directionalLightShader.setUniformValue(7, lightSpace);
			directionalLightShader.setUniformValue(8, shadowMap);
			for(Mesh mesh : model.getMeshes())
			{	
				if(mesh.getMaterial().isPresent())
				{
					directionalLightShader.setUniformValue(3, mesh.getMaterial().get());
					
					BindingUtils.bindVAO(mesh.getVao(), 0, 1, 2, 3);
					
					for(StaticModelEntity entity : entities)
					{
						directionalLightShader.setUniformValue(0, entity.getTransformationMatrix());
						
						GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
					}
				}
			}
		}
	}
	
	public static void renderDeferred(Model model, List<StaticModelEntity> entities, Camera cam, Mat4f projection, double alpha)
	{
		try(ShaderSession ss = deferredShader.start())
		{
			deferredShader.setUniformValue(1, projection);
			deferredShader.setUniformValue(2, cam, alpha);

			for(Mesh mesh : model.getMeshes())
			{	
				if(mesh.getMaterial().isPresent())
				{
					deferredShader.setUniformValue(3, mesh.getMaterial().get());
					
					BindingUtils.bindVAO(mesh.getVao(), 0, 1, 2, 3);
					
					for(StaticModelEntity entity : entities)
					{
						deferredShader.setUniformValue(0, entity.getTransformationMatrix());
						
						GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
					}
				}
			}
		}
	}
	
	
	public static void renderShadowMap(Model model, List<StaticModelEntity> entities, Mat4f projection, double alpha)
	{
		Mat4f mvp = projection.clone();
		
		try(ShaderSession ss = shadowShader.start())
		{
			for(Mesh mesh : model.getMeshes())
			{	
				if(mesh.getMaterial().isPresent())
				{
					BindingUtils.bindVAO(mesh.getVao(), 0, 1, 2, 3);
					
					for(StaticModelEntity entity : entities)
					{
						shadowShader.setUniformValue(0, mvp.mul(entity.getTransformationMatrix(), new Mat4f()));
						
						GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
					}
				}
			}
		}
	}
	
	private static VAO vao = new VAO();
	
	public static void renderPoint(Mat4f projection, Camera cam, Tup3fR p, Tup3fR color, float size, double alpha)
	{
		vao.storeData(0, new Tup3fR[] {p}, 0, 0, GL20.GL_STATIC_DRAW);
		vao.storeIndices(new int[] {0}, GL20.GL_STATIC_DRAW);
		
		try(ShaderSession ss = coloredShader.start())
		{
			coloredShader.setUniformValue(0, Mat4f.identity());
			coloredShader.setUniformValue(1, projection);
			coloredShader.setUniformValue(2, cam.getLerpedViewMatrix((float)alpha));
			coloredShader.setUniformValue(3, color);
			
			BindingUtils.bindVAO(vao, 0);
			
			GL11.glPointSize(size);
			GL11.glDrawElements(GL11.GL_POINTS, 1, GL11.GL_UNSIGNED_INT, 0);
		}
	}

	public static void renderLine(Mat4f projection, Camera cam, Tup3fR start, Tup3fR end, Tup3fR color, double alpha)
	{
		vao.storeData(0, new Tup3fR[] {start, end}, 0, 0, GL20.GL_STATIC_DRAW);
		vao.storeIndices(new int[] {0, 1}, GL20.GL_STATIC_DRAW);
		
		try(ShaderSession ss = coloredShader.start())
		{
			coloredShader.setUniformValue(0, Mat4f.identity());
			coloredShader.setUniformValue(1, projection);
			coloredShader.setUniformValue(2, cam.getLerpedViewMatrix((float)alpha));
			coloredShader.setUniformValue(3, color);
			
			BindingUtils.bindVAO(vao, 0);
			
			//GL11.glPointSize(10f);
			GL11.glDrawElements(GL11.GL_LINES, 2, GL11.GL_UNSIGNED_INT, 0);
		}
	}

	public static void renderArrow(Mat4f projection, Camera cam, Tup3fR start, Tup3fR end, Tup3fR color, double alpha)
	{
		try(ShaderSession ss = coloredShader.start())
		{
			coloredShader.setUniformValue(0, Mat4f.identity());
			coloredShader.setUniformValue(1, projection);
			coloredShader.setUniformValue(2, cam.getLerpedViewMatrix((float)alpha));
			coloredShader.setUniformValue(3, color);
			
			vao.storeData(0, new Tup3fR[] {start, end}, 0, 0, GL20.GL_STATIC_DRAW);
			vao.storeIndices(new int[] {0, 1}, GL20.GL_STATIC_DRAW);
			
			BindingUtils.bindVAO(vao, 0);
			
			GL11.glDrawElements(GL11.GL_LINES, 2, GL11.GL_UNSIGNED_INT, 0);
			
			vao.storeData(0, new Tup3fR[] {end}, 0, 0, GL20.GL_STATIC_DRAW);
			vao.storeIndices(new int[] {0}, GL20.GL_STATIC_DRAW);

			BindingUtils.bindVAO(vao, 0);

			GL11.glPointSize(10f);
			GL11.glDrawElements(GL11.GL_POINTS, 1, GL11.GL_UNSIGNED_INT, 0);
		}
	}
	
	public static void renderArc(Mat4f projection, Camera cam, Tup3fR center, Tup3fR start, Tup3fR end, int segments, Tup3fR color, double alpha)
	{
		Vec3f vStart = new Vec3f(start);
		Vec3f vEnd = new Vec3f(end);
		Vec3f vCenter = new Vec3f(center);
		
		Quatf identity = new Quatf();
		Quatf q = Quatf.getFromVectors(vStart, vEnd).normal();
		
		float thetaFactor = 1.0f / segments;
		
		Tup3fR[] points = new Tup3fR[segments + 1];
		int[] indices = new int[segments + 1];
		
		points[0] = start;
		indices[0] = 0;
		
		for(int i = 1; i < segments; i++)
		{
			Quatf rot = VectorInterpolation.slerp(identity, q, thetaFactor * i, new Quatf());

			Vec3f v = rot.transform(vStart, new Vec3f());
			
			points[i] = vCenter.addN(v);
			indices[i] = i;
		}
			
		points[points.length -1] = end;
		indices[indices.length -1] = indices.length -1;
		
		try(ShaderSession ss = coloredShader.start())
		{
			coloredShader.setUniformValue(0, Mat4f.identity());
			coloredShader.setUniformValue(1, projection);
			coloredShader.setUniformValue(2, cam.getLerpedViewMatrix((float)alpha));
			coloredShader.setUniformValue(3, color);
			
			vao.storeData(0, points, 0, 0, GL20.GL_STATIC_DRAW);
			vao.storeIndices(indices, GL20.GL_STATIC_DRAW);
			
			BindingUtils.bindVAO(vao, 0);
			
			GL11.glDrawElements(GL11.GL_LINE_STRIP, points.length, GL11.GL_UNSIGNED_INT, 0);
		}
	}
}
