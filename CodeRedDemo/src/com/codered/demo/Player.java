package com.codered.demo;

import java.util.ArrayList;
import java.util.List;

import org.barghos.core.profiler.CascadingProfiler.ProfilingSession;
import org.barghos.math.geometry.AABB3f;
import org.barghos.math.geometry.OBB3f;
import org.barghos.math.matrix.Mat4f;
import org.barghos.math.point.Point3f;
import org.barghos.math.vector.Quat;
import org.barghos.math.vector.Vec3f;
import org.barghos.math.vector.Vec3fAxis;
import org.lwjgl.glfw.GLFW;

import com.codered.ConvUtils;
import com.codered.Profiling;
import com.codered.engine.EngineRegistry;
import com.codered.entities.BaseEntity;
import com.codered.entities.Camera;
import com.codered.entities.StaticEntity;
import com.codered.gui.GUIWindow;
import com.codered.window.WindowContext;

import cmn.utilslib.math.geometry.OBBOBBResolver;
import cmn.utilslib.math.vector.Vector3f;



public class Player extends BaseEntity
{
	
	public org.barghos.math.geometry.AABB3f aabb;
	public cmn.utilslib.math.geometry.AABB3f aabb2;
	
	public Vec3f velocity = new Vec3f();
	
	public GUIWindow window;
	
	private Camera camera;
	
	private WindowContext context;
	
	private StaticEntityTreeImpl world;
	
	public Player(StaticEntityTreeImpl world)
	{
		this.context = EngineRegistry.getCurrentWindowContext();
		
		this.world = world;

		this.aabb = new org.barghos.math.geometry.AABB3f(new Point3f(0, 9, 0), new Vec3f(4, 9, 4));
		this.aabb2 = new cmn.utilslib.math.geometry.AABB3f(new cmn.utilslib.math.geometry.Point3f(0, 9, 0), new Vector3f(4, 9, 4));
		
		this.transform.setPos(new Vec3f(0.0f, 0.0f, 0.0f));
		
		this.camera = new Camera(0.0f, 18.0f, 0.0f, 0.0d, 0.0d, 0.0d);
		
		this.camera.getTransform().setParent(getTransform());
		
		this.camera.setYawSpeed(GlobalSettings.camSpeed_yaw);
		this.camera.setPitchSpeed(GlobalSettings.camSpeed_pitch);
		this.camera.limitPitch(-70.0f, 70.0f);
	}
	
	public void update(double delta)
	{
		try(ProfilingSession session = Profiling.CPROFILER.startSession("PlayerUpdate"))
		{
			updateMovement(delta);
			updateOrientation(delta);
		}
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
		Vec3f dir = new Vec3f();
		Vec3f vel = new Vec3f();
		
		if(this.context.getInputManager().isKeyHold(GLFW.GLFW_KEY_W))
		{
			dir.sub(this.camera.getYaw().transform(Vec3fAxis.AXIS_Z, null).normal(), dir);
		}
		
		if(this.context.getInputManager().isKeyHold(GLFW.GLFW_KEY_D))
		{
			dir.sub(this.camera.getYaw().transform(Vec3fAxis.AXIS_NX, null).normal(), dir);
		}
		
		if(this.context.getInputManager().isKeyHold(GLFW.GLFW_KEY_A))
		{
			dir.sub(this.camera.getYaw().transform(Vec3fAxis.AXIS_X, null).normal(), dir);
		}
		
		if(this.context.getInputManager().isKeyHold(GLFW.GLFW_KEY_S))
		{
			dir.sub(this.camera.getYaw().transform(Vec3fAxis.AXIS_NZ, null).normal(), dir);
		}
		
		if(dir.squaredLength() == 0) { return; }
		
		dir.normal();
	
		float acceleration = 20.0f * (float)delta;

		dir.mul(acceleration, vel);
		
		vel = checkCollisionStatic(vel);
		
		this.transform.moveBy(vel);
	}
	
	public Camera getCamera() { return this.camera; }
	
	public Quat getPitch() { return this.transform.getRotation().getRotationPitch(); }
	public Quat getYaw() { return this.transform.getRotation().getRotationYaw(); }
	public Quat getRoll() { return this.transform.getRotation().getRotationRoll(); }
	
	private Vec3f checkCollisionStatic(Vec3f vel)
	{
		Vec3f sum = new Vec3f();
		Vec3f partial = new Vec3f();
		Vec3f tempPos = new Vec3f();

		Mat4f translation;
		
		this.transform.getTransformedPos().add(vel, tempPos);

		OBB3f tempOBB;
		cmn.utilslib.math.geometry.OBB3f tempOBB2;
		
		OBB3f entityOBB;
		cmn.utilslib.math.geometry.OBB3f entityOBB2;
		AABB3f sweptAABB;
		translation = Mat4f.translation(tempPos);
		
		sweptAABB = this.aabb.transform(translation, null);
		cmn.utilslib.math.geometry.AABB3f sweptAABB2 = this.aabb2.transform(ConvUtils.matToMatrix(translation));
		
		ArrayList<StaticEntity> entities = this.world.walker.walk(sweptAABB);
		
		for(StaticEntity entity : entities)
		{
			entityOBB = entity.getModel().getPhysicalMesh().getOBBf(entity.getTransformationMatrix(), entity.getRotationMatrix());
			entityOBB2 = entity.getModel().getPhysMesh().getOBBf(ConvUtils.matToMatrix(entity.getTransformationMatrix()), ConvUtils.matToMatrix(entity.getRotationMatrix()));
			
			System.out.println(entity.getTransformationMatrix());
			System.out.println(ConvUtils.matToMatrix(entity.getTransformationMatrix()));
			System.out.println();
			
			translation = Mat4f.translation(tempPos);
			
			sweptAABB = this.aabb.transform(translation, null);
			
			tempOBB = sweptAABB.getOBB();
			tempOBB2 = sweptAABB2.getOBBf();
			
			cmn.utilslib.math.geometry.OBB3f a = ConvUtils.bOBB3fToUOBB3f(tempOBB);
			cmn.utilslib.math.geometry.OBB3f b = ConvUtils.bOBB3fToUOBB3f(entityOBB);
			
			if(OBBOBBResolver.iOBBOBB3f(tempOBB2, entityOBB2))
			{
				
				partial = ConvUtils.vector3fToVec3f(OBBOBBResolver.rOBBOBB3f(tempOBB2, entityOBB2));
				
				sum.add(partial, sum);
				tempPos.add(partial, tempPos);
			}
		}
		
		vel.add(sum, vel);
		
		return vel;	
	}
	
//	private Vec3f checkCollisionStatic(Vec3f vel)
//	{
//		Vec3f sum = new Vec3f();
//		Vec3f partial = new Vec3f();
//		Vec3f tempPos = new Vec3f();
//
//		Mat4f translation;
//		
//		this.transform.getTransformedPos().add(vel, tempPos);
//
//		OBB3f tempOBB;
//		
//		OBB3f entityOBB;
//		
//		AABB3f sweptAABB;
//		
//		translation = Mat4f.translation(tempPos);
//		
//		sweptAABB = this.aabb2.transform(ConvUtils.matToMatrix(translation));
//		
//		List<StaticEntity> entities = this.world.walker.walk(sweptAABB);
//		
//		for(StaticEntity entity : entities)
//		{
//			entityOBB = entity.getModel().getPhysicalMesh().getOBBf(ConvUtils.matToMatrix(entity.getTransformationMatrix()), ConvUtils.matToMatrix(entity.getRotationMatrix()));
//			
//			translation = Mat4f.translation(tempPos);
//			
//			sweptAABB = this.aabb2.transform(ConvUtils.matToMatrix(translation));
//			
//			tempOBB = sweptAABB.getOBBf();
//
//			if(OBBOBBResolver.iOBBOBB3f(tempOBB, entityOBB))
//			{
//				
//				partial = ConvUtils.vector3fToVec3f(OBBOBBResolver.rOBBOBB3f(tempOBB, entityOBB));
//				
//				sum.add(partial, sum);
//				tempPos.add(partial, tempPos);
//			}
//		}
//		
//		vel.add(sum, vel);
//		
//		return vel;		
//	}
	
	private void updateOrientation()
	{
		this.camera.rotateYaw(-this.context.getMouse().getDeltaPos().x);

		this.camera.rotatePitch(-this.context.getMouse().getDeltaPos().y);

	}
	
	public Vec3f getPos() { return this.transform.getPos(); }
	
	public Vec3f getEyePos() { return this.transform.getPos().add(0.0f, 18.0f, 0.0f, null); }

}
