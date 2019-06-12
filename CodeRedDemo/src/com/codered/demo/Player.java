package com.codered.demo;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;

import org.barghos.core.profiler.CascadingProfiler.ProfilingSession;

import org.barghos.math.geometry.AABB3f;
import org.barghos.math.geometry.OBB3f;
import org.barghos.math.geometry.OBBOBBResolver;
import org.barghos.math.matrix.Mat4f;
import org.barghos.math.point.Point3f;
import org.barghos.math.vector.Quat;
import org.barghos.math.vector.Vec3f;
import org.barghos.math.vector.Vec3fAxis;
import org.barghos.math.vector.Vec3fPool;

import com.codered.Profiling;
import com.codered.engine.EngineRegistry;
import com.codered.entities.BaseEntity;
import com.codered.entities.Camera;
import com.codered.entities.StaticEntity;
import com.codered.gui.GUIWindow;
import com.codered.window.WindowContext;




public class Player extends BaseEntity
{
	public AABB3f aabb;

	public GUIWindow window;
	
	private Camera camera;
	
	private WindowContext context;
	
	private StaticEntityTreeImpl world;
	
	public Player(StaticEntityTreeImpl world)
	{
		this.context = EngineRegistry.getCurrentWindowContext();
		
		this.world = world;

		this.aabb = new AABB3f(new Point3f(0, 9, 0), new Vec3f(4, 9, 4));
		
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

		Vec3f dir = Vec3fPool.get();
		Vec3f vel = Vec3fPool.get();
		Vec3f t = Vec3fPool.get();
		
		if(this.context.getInputManager().isKeyHold(GLFW.GLFW_KEY_W))
		{
			dir.sub(this.camera.getYaw().transform(Vec3fAxis.AXIS_Z, t).normal(), dir);
		}
		
		if(this.context.getInputManager().isKeyHold(GLFW.GLFW_KEY_D))
		{
			dir.sub(this.camera.getYaw().transform(Vec3fAxis.AXIS_NX, t).normal(), dir);
		}
		
		if(this.context.getInputManager().isKeyHold(GLFW.GLFW_KEY_A))
		{
			dir.sub(this.camera.getYaw().transform(Vec3fAxis.AXIS_X, t).normal(), dir);
		}
		
		if(this.context.getInputManager().isKeyHold(GLFW.GLFW_KEY_S))
		{
			dir.sub(this.camera.getYaw().transform(Vec3fAxis.AXIS_NZ, t).normal(), dir);
		}

		if(!Vec3f.isZero(dir))
		{
			dir.normal();
			
			float acceleration = 25.0f * (float)delta;

			dir.mul(acceleration, vel);
			
			vel = checkCollisionStatic(vel);
			
			this.transform.moveBy(vel);
		}


		
		Vec3fPool.store(dir, vel, t);

	}
	
	public Camera getCamera() { return this.camera; }
	
	public Quat getPitch() { return this.transform.getRotation().getRotationPitch(); }
	public Quat getYaw() { return this.transform.getRotation().getRotationYaw(); }
	public Quat getRoll() { return this.transform.getRotation().getRotationRoll(); }
	
	private Vec3f checkCollisionStatic(Vec3f vel)
	{
		
		Vec3f sum = Vec3fPool.get();
		Vec3f partial = Vec3fPool.get();
		Vec3f tempPos = Vec3fPool.get();

		Mat4f translation;
		
		this.transform.getTransformedPos().add(vel, tempPos);

		OBB3f tempOBB;

		OBB3f entityOBB;

		AABB3f sweptAABB;
		translation = Mat4f.translation(tempPos);
		
		sweptAABB = this.aabb.transform(translation, null);

		ArrayList<StaticEntity> entities = this.world.walker.walk(sweptAABB);
		
		for(StaticEntity entity : entities)
		{
			entityOBB = entity.getModel().getPhysicalMesh().getOBBf(entity.getTransformationMatrix(), entity.getRotationMatrix());

			translation = Mat4f.translation(tempPos);
			
			sweptAABB = this.aabb.transform(translation, sweptAABB);
			
			tempOBB = sweptAABB.getOBB();

			if(OBBOBBResolver.iOBBOBB3f(tempOBB, entityOBB))
			{
				partial = OBBOBBResolver.rOBBOBB3f(tempOBB, entityOBB);
				
				sum.add(partial, sum);
				tempPos.add(partial, tempPos);
			}
		}
		
		vel.add(sum, vel);
		
		Vec3fPool.store(sum, partial, tempPos);
		
		return vel;	
	}
	
	private void updateOrientation()
	{
		this.camera.rotateYaw(-this.context.getMouse().getDeltaPos().x);

		this.camera.rotatePitch(-this.context.getMouse().getDeltaPos().y);

	}
	
	public Vec3f getPos() { return this.transform.getPos(); }
	
	public Vec3f getEyePos() { return this.transform.getPos().add(0.0f, 18.0f, 0.0f, null); }

}
