package com.codered.demo;

import java.util.ArrayList;

import org.barghos.math.matrix.Mat4f;
import org.barghos.math.vector.Quat;
import org.barghos.math.vector.Vec3f;
import org.barghos.math.vector.Vec3fAxis;

import com.codered.ConvUtils;
import com.codered.StaticEntityTreeImpl;
import com.codered.demo.GlobalSettings.Keys;
import com.codered.engine.EngineRegistry;
import com.codered.entities.BaseEntity;
import com.codered.entities.Camera;
import com.codered.entities.StaticEntity;
import com.codered.gui.GUIWindow;
import com.codered.input.InputConfiguration;
import com.codered.input.InputConfiguration.ButtonEventArgs;
import com.codered.input.InputConfiguration.KeyEventArgs;
import com.codered.window.WindowContext;

import cmn.utilslib.math.geometry.AABB3f;
import cmn.utilslib.math.geometry.OBB3f;
import cmn.utilslib.math.geometry.OBBOBBResolver;
import cmn.utilslib.math.geometry.Point3f;
import cmn.utilslib.math.vector.Vector3f;



public class Player extends BaseEntity
{
	
	public AABB3f aabb2;

	public Vec3f velocity = new Vec3f();
	
	public GUIWindow window;
	
	private Camera camera;
	
	private long selectedEntity = -1;
	
	private WindowContext context;
	
	private StaticEntityTreeImpl world;
	
	public Player(StaticEntityTreeImpl world)
	{
		this.context = EngineRegistry.getCurrentWindowContext();
		
		this.world = world;
		this.aabb2 = new AABB3f(new Point3f(0, 9, 0), new Vector3f(4, 9, 4));
		
		this.transform.setPos(new Vec3f(0.0f, 0.0f, 0.0f));
		//Transform t = Session.get().getWorld().getStaticEntity(0).getTransform();
		
		//t.setPos(Vector3f.TEMP.set(0.0f, 0.0f, 0.0f));
		//t.setParent(getTransform());
		
		this.camera = new Camera(0.0f, 18.0f, 0.0f, 0.0d, 0.0d, 0.0d);
		
		this.camera.getTransform().setParent(getTransform());
		
		this.camera.setYawSpeed(GlobalSettings.camSpeed_yaw);
		this.camera.setPitchSpeed(GlobalSettings.camSpeed_pitch);
		this.camera.limitPitch(-70.0f, 70.0f);
		
		InputConfiguration config = GlobalSettings.ingameInput;
		
		config.keyPress.addHandler((src) -> updateMovement(src));
		config.buttonStroke.addHandler((src) -> buttonStroke(src));
		config.buttonRelease.addHandler((src) -> buttonRelease(src));
		config.buttonPress.addHandler((src) -> buttonPress(src));
	}
	
	private void buttonStroke(ButtonEventArgs args)
	{
		if(args.response.buttonPresent(Keys.b_moveCam))
		{
			this.context.getInputManager().setMouseGrabbed(true);
		}
	}
	
	private void buttonPress(ButtonEventArgs args)
	{
		if(args.response.buttonPresent(Keys.b_moveCam))
		{
			updateOrientation();
		}
	}
	
	private void buttonRelease(ButtonEventArgs args)
	{
		if(args.response.buttonPresent(Keys.b_moveCam))
		{
			this.context.getInputManager().setMouseGrabbed(false);
		}
	}
	
	
	
	private void updateMovement(KeyEventArgs args)
	{
		Vec3f dir = new Vec3f();
		Vec3f vel = new Vec3f();

		if(args.keyPresent(Keys.k_delete))
		{
			if(this.selectedEntity != -1)
			{
				this.selectedEntity = -1;
			}
		}
		
		if(args.keyPresent(Keys.k_forward))
		{
			dir.sub(this.camera.getYaw().transform(Vec3fAxis.AXIS_Z, null).normal(), dir);
		}
		
		if(args.keyPresent(Keys.k_right))
		{
			dir.sub(this.camera.getYaw().transform(Vec3fAxis.AXIS_NX, null).normal(), dir);
		}
		
		if(args.keyPresent(Keys.k_left))
		{
			dir.sub(this.camera.getYaw().transform(Vec3fAxis.AXIS_X, null).normal(), dir);
		}
		
		if(args.keyPresent(Keys.k_back))
		{
			dir.sub(this.camera.getYaw().transform(Vec3fAxis.AXIS_NZ, null).normal(), dir);
		}
		
		if(dir.length() == 0) { return; }
		
		dir.normal();
		
		float time = (float)args.delta;
		
		float acceleration = 20.0f * time;
		
		dir.mul(acceleration, vel);
		
		vel = checkCollisionStatic(vel);
		
		this.transform.moveBy(vel);
	}
	
	public Camera getCamera() { return this.camera; }
	
	public Quat getPitch() { return this.transform.getRotation().getRotationPitch(); }
	public Quat getYaw() { return this.transform.getRotation().getRotationYaw(); }
	public Quat getRoll() { return this.transform.getRotation().getRotationRoll(); }
	
	public AABB3f getTransformedAABB()
	{
		Mat4f translation = Mat4f.translation(this.transform.getTransformedPos());
		return this.aabb2.transform(ConvUtils.matToMatrix(translation));
	}
	
	private Vec3f checkCollisionStatic(Vec3f vel)
	{
		Vec3f sum = new Vec3f();
		Vec3f partial = new Vec3f();
		Vec3f tempPos = new Vec3f();

		Mat4f translation;
		
		this.transform.getTransformedPos().add(vel, tempPos);

		OBB3f tempOBB;
		
		OBB3f entityOBB;
		
		AABB3f sweptAABB;
		
		translation = Mat4f.translation(tempPos);
		
		sweptAABB = this.aabb2.transform(ConvUtils.matToMatrix(translation));
		
		ArrayList<StaticEntity> entities = this.world.walker.walk(sweptAABB);
		
		for(StaticEntity entity : entities)
		{
			entityOBB = entity.getModel().getPhysicalMesh().getOBBf(ConvUtils.matToMatrix(entity.getTransformationMatrix()), ConvUtils.matToMatrix(entity.getRotationMatrix()));
			
			translation = Mat4f.translation(tempPos);
			
			sweptAABB = this.aabb2.transform(ConvUtils.matToMatrix(translation));
			
			tempOBB = sweptAABB.getOBBf();

			if(OBBOBBResolver.iOBBOBB3f(tempOBB, entityOBB))
			{
				
				partial = ConvUtils.vector3fToVec3f(OBBOBBResolver.rOBBOBB3f(tempOBB, entityOBB));
				
				sum.add(partial, sum);
				tempPos.add(partial, tempPos);
			}
		}
		
		vel.add(sum, vel);
		
		return vel;		
	}
	
	private void updateOrientation()
	{
		this.camera.rotateYaw(-this.context.getInputManager().getDX());

		this.camera.rotatePitch(this.context.getInputManager().getDY());
		
//		World w = Session.get().getWorld();
//		
//		List<StaticEntity> statents = Auto.ArrayList(w.getStaticEntities());
		
//		double minlength = Double.MAX_VALUE;
//		StaticEntity ent = null;
		
//		for(StaticEntity e : statents)
//		{
//			OBB3f entityOBB = e.getModel().getPhysicalMesh().getOBBf(e.getTransformationMatrix(), e.getRotationMatrix());
//
//			Vector3f center = getCamera().getTotalPos();
//			Vector3f dir = getCamera().getTotalRot().getForwardf().negate();
//
//			if(RayOBBResolver.hit(center, dir, entityOBB))
//			{
//				Vector3f delta = getCamera().getTotalPos().subN(entityOBB.center);
//				
//				if(delta.length() <= minlength)
//				{
//					minlength = delta.length();
//					ent = e;
//				}
//			}
//			
//			e.highlighted = false;
//		}

	}
	
	public Vec3f getPos() { return this.transform.getPos(); }
	
	public Vec3f getEyePos() { return this.transform.getPos().add(0.0f, 18.0f, 0.0f, null); }

}
