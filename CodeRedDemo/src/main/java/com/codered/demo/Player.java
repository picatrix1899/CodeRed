package com.codered.demo;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;
import org.barghos.core.tuple3.api.Tup3fR;
import org.barghos.math.geometry.AABB3;
import org.barghos.math.geometry.OBB3;
import org.barghos.math.geometry.OBBOBBResolver;
import org.barghos.math.matrix.Mat4;
import org.barghos.math.point.Point3;
import org.barghos.math.vector.vec3.Vec3;
import org.barghos.math.vector.vec3.Vec3Axis;
import org.barghos.math.vector.vec3.Vec3Pool;

import com.codered.SweptTransform;
import com.codered.engine.EngineRegistry;
import com.codered.entities.Camera;
import com.codered.entities.StaticEntity;
import com.codered.entities.StaticModelEntity;
import com.codered.gui.GUIWindow;
import com.codered.window.WindowContext;




public class Player
{
	public AABB3 aabb;

	public GUIWindow window;
	
	private Camera camera;
	
	private WindowContext context;

	private SweptTransform transform = new SweptTransform();

	public Player(Tup3fR pos)
	{
		this.context = EngineRegistry.getCurrentWindowContext();

		this.aabb = new AABB3(new Point3(0f, 0.9f, 0f), new Vec3(0.4f, 0.9f, 0.4f));
		
		this.transform.setPos(pos);
		
		this.camera = new Camera(0.0f, pos.getY() + 1.7f, 0.0f, 0.0f, 0.0f, 0.0f);
		
		this.camera.getTransform().setParent(this.transform);
		
		this.camera.setYawSpeed(GlobalSettings.camSpeed_yaw);
		this.camera.setPitchSpeed(GlobalSettings.camSpeed_pitch);
		this.camera.limitPitch(-70.0f, 70.0f);
	}
	
	public void preUpdate()
	{
		this.transform.swap();
		this.camera.getTransform().swap();
	}
	
	public void update(double delta)
	{
		updateMovement(delta);
		
		updateOrientation(delta);
	}
	
	public void updateOrientation(double delta)
	{
		if(this.context.getInputManager().isMouseButtonPressed(2))
		{
			this.context.getMouse().grab(true);
		}
		
		if(this.context.getInputManager().isMouseButtonHold(2))
		{
			updateOrientation();
		}
		
		if(this.context.getInputManager().isMouseButtonReleased(2))
		{
			this.context.getMouse().grab(false);
		}
		
	}
	
	public void updateMovement(double delta)
	{
		Vec3 dir = Vec3Pool.get();
		Vec3 t = Vec3Pool.get();
		
		if(this.context.getInputManager().isKeyHold(KeyBindings.forward))
		{
			dir.sub(this.camera.getYaw().transform(Vec3Axis.AXIS_Z, t).normal());
		}
		
		if(this.context.getInputManager().isKeyHold(GLFW.GLFW_KEY_D))
		{
			dir.sub(this.camera.getYaw().transform(Vec3Axis.AXIS_NX, t).normal());
		}
		
		if(this.context.getInputManager().isKeyHold(GLFW.GLFW_KEY_A))
		{
			dir.sub(this.camera.getYaw().transform(Vec3Axis.AXIS_X, t).normal());
		}
		
		if(this.context.getInputManager().isKeyHold(GLFW.GLFW_KEY_S))
		{
			dir.sub(this.camera.getYaw().transform(Vec3Axis.AXIS_NZ, t).normal());
		}

		if(!dir.isZero(0.0f))
		{
			dir.normal();
			
			float acceleration = 2.5f * (float)delta;

			Vec3 vel = Vec3Pool.get();
			
			dir.mul(acceleration, vel);

			//vel.set(checkCollisionStatic(vel));
			
			this.transform.setPos(this.transform.getPos().addN(vel));
			
			Vec3Pool.store(vel);
		}
		
		Vec3Pool.store(dir, t);
	}
	
	public Camera getCamera() { return this.camera; }

	
	
//	private Vec3 checkCollisionStatic(Vec3 vel)
//	{
//		Vec3 sum = Vec3Pool.get();
//		Vec3 partial = Vec3Pool.get();
//		Vec3 tempPos = Vec3Pool.get();
//
//		Mat4 translation;
//		
//		this.transform.getTransformedPos().add(vel, tempPos);
//
//		OBB3 tempOBB;
//
//		OBB3 entityOBB;
//
//		AABB3 sweptAABB;
//		translation = Mat4.translation(tempPos);
//		
//		sweptAABB = this.aabb.transform(translation, null);
//
//		ArrayList<StaticEntity> entities = this.world.walker.walk(sweptAABB);
//		
//		for(StaticEntity entity : entities)
//		{
//			if(entity instanceof StaticModelEntity)
//			{
//				for(com.codered.model.Mesh mesh : ((StaticModelEntity)entity).getNewModel().getMeshes())
//				{
//					entityOBB = mesh.getCollisionMesh().get().getOBBf(entity.getTransformationMatrix(), entity.getRotationMatrix());
//					
//					translation = Mat4.translation(tempPos);
//					
//					sweptAABB = this.aabb.transform(translation, sweptAABB);
//					
//					tempOBB = sweptAABB.getOBB();
//
//					if(OBBOBBResolver.iOBBOBB3f(tempOBB, entityOBB))
//					{
//						partial = OBBOBBResolver.rOBBOBB3f(tempOBB, entityOBB);
//						
//						sum.add(partial, sum);
//						tempPos.add(partial, tempPos);
//					}
//				}
//				
//			}
//			else
//			{
//				entityOBB = entity.getModel().getModel().getCollisionMesh().get().getOBBf(entity.getTransformationMatrix(), entity.getRotationMatrix());
//				
//				translation = Mat4.translation(tempPos);
//				
//				sweptAABB = this.aabb.transform(translation, sweptAABB);
//				
//				tempOBB = sweptAABB.getOBB();
//
//				if(OBBOBBResolver.iOBBOBB3f(tempOBB, entityOBB))
//				{
//					partial = OBBOBBResolver.rOBBOBB3f(tempOBB, entityOBB);
//					
//					sum.add(partial, sum);
//					tempPos.add(partial, tempPos);
//				}
//			}
//			
//		}
//	
//		vel.add(sum, vel);
//		
//		Vec3Pool.store(sum, partial, tempPos);
//		
//		return vel;
//	}
	
	private void updateOrientation()
	{
		this.camera.rotateYaw(-this.context.getMouse().getDeltaPos().getX());

		this.camera.rotatePitch(-this.context.getMouse().getDeltaPos().getY());

	}
	
	public Vec3 getPos() { return this.transform.getPos(); }
	
	public Vec3 getEyePos() { return this.transform.getPos().addN(0.0f, 18.0f, 0.0f); }

}
