package com.codered.demo;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.codered.demo.GlobalSettings.Keys;
import com.codered.engine.Time;
import com.codered.engine.entities.BaseEntity;
import com.codered.engine.entities.Camera;
import com.codered.engine.entities.StaticEntity;
import com.codered.engine.gui.GUIWindow;
import com.codered.engine.input.Input.ButtonEventArgs;
import com.codered.engine.input.Input.KeyEventArgs;
import com.codered.engine.managing.Window;
import com.codered.engine.managing.World;

import cmn.utilslib.essentials.Auto;
import cmn.utilslib.math.matrix.Matrix4f;
import cmn.utilslib.math.Quaternion;
import cmn.utilslib.math.geometry.AABB3f;
import cmn.utilslib.math.geometry.OBB3f;
import cmn.utilslib.math.geometry.OBBOBBResolver;
import cmn.utilslib.math.geometry.Point3f;
import cmn.utilslib.math.geometry.RayOBBResolver;
import cmn.utilslib.math.vector.Vector3f;
import cmn.utilslib.math.vector.api.Vec3f;



public class Player extends BaseEntity
{
	
	public AABB3f aabb2;

	public Vec3f velocity = new Vector3f();
	
	public GUIWindow window;
	
	private Camera camera;
	
	private long selectedEntity = -1;
	
	
	
	public Player()
	{
		this.aabb2 = new AABB3f(new Point3f(0, 9, 0), new Vector3f(3, 9, 3));
		
		this.transform.setPos(Vector3f.TEMP.set(0.0f, 0.0f, 0.0f));
		//Transform t = Session.get().getWorld().getStaticEntity(0).getTransform();
		
		//t.setPos(Vector3f.TEMP.set(0.0f, 0.0f, 0.0f));
		//t.setParent(getTransform());
		
		this.camera = new Camera(0.0f, 18.0f, 0.0f, 0.0d, 0.0d, 0.0d);
		this.camera.getTransform().setParent(getTransform());
		
		this.camera.setYawSpeed(GlobalSettings.camSpeed_yaw);
		this.camera.setPitchSpeed(GlobalSettings.camSpeed_pitch);
		this.camera.limitPitch(-70.0f, 70.0f);
		
		Window.active.getInputManager().keyPress.addHandler((a, b) -> updateMovement(a));
		Window.active.getInputManager().buttonStroke.addHandler((a, b) -> buttonStroke(a));
		Window.active.getInputManager().buttonRelease.addHandler((a, b) -> buttonRelease(a));
		Window.active.getInputManager().buttonPress.addHandler((a, b) -> buttonPress(a));
	}
	
	private void buttonStroke(ButtonEventArgs args)
	{
		if(args.response.buttonPresent(Keys.b_moveCam))
		{
			Window.active.getInputManager().setMouseGrabbed(true);
		}
	}
	
	private void buttonPress(ButtonEventArgs args)
	{
		if(Window.active.getInputManager().isButtonHelt(Keys.b_moveCam))
		{
			updateOrientation();
		}
	}
	
	private void buttonRelease(ButtonEventArgs args)
	{
		if(args.response.buttonPresent(Keys.b_moveCam))
		{
			Window.active.getInputManager().setMouseGrabbed(false);
		}
	}
	
	
	
	private void updateMovement(KeyEventArgs args)
	{
		Vec3f dir = Vector3f.getInstance();
		Vec3f vel = Vector3f.getInstance();

		if(args.response.keyPresent(Keys.k_delete))
		{
			if(this.selectedEntity != -1)
			{
				Session.get().getWorld().removeStaticEntity(this.selectedEntity);
				
				this.selectedEntity = -1;
			}
		}
		
		if(args.response.keyPresent(Keys.k_forward))
		{
			dir.sub(this.camera.getYaw().getForwardf().normalize());
		}
		
		if(args.response.keyPresent(Keys.k_right))
		{
			dir.sub(this.camera.getYaw().getRightf().normalize());
		}
		
		if(args.response.keyPresent(Keys.k_left))
		{
			dir.sub(this.camera.getYaw().getLeftf().normalize());
		}
		
		if(args.response.keyPresent(Keys.k_back))
		{
			dir.sub(this.camera.getYaw().getBackf().normalize());
		}
		
		if(args.response.keyPresent(Keys.k_turnLeft))
		{
			this.transform.rotate(Vec3f.aY, GlobalSettings.camSpeed_yaw * Time.getDelta() * 1000.0f);
		}
		
		if(args.response.keyPresent(Keys.k_turnRight))
		{
			this.transform.rotate(Vec3f.aY, GlobalSettings.camSpeed_yaw * -Time.getDelta() * 1000.0f);
		}
		
		if(dir.length() == 0) { return; }
		
		dir.normalize();
		
		float time = (float)Time.getDelta();
		
		float acceleration = 20.0f * time;
		
		vel.set(dir).mul(acceleration);
		
		vel = checkCollisionStatic(vel);
		vel = checkCollisionDynamic(vel);
		
		this.transform.moveBy(vel);
		
		Vector3f.storeInstance((Vector3f) dir);
		Vector3f.storeInstance((Vector3f) vel);
	}
	
	public Camera getCamera() { return this.camera; }
	
	public Quaternion getPitch() { return this.transform.getRotation().getRotationPitch(); }
	public Quaternion getYaw() { return this.transform.getRotation().getRotationYaw(); }
	public Quaternion getRoll() { return this.transform.getRotation().getRotationRoll(); }
	
	public AABB3f getTransformedAABB()
	{
		Matrix4f translation = Matrix4f.translation(this.transform.getTransformedPos());
		return this.aabb2.transform(translation);
	}
	
	private Vec3f checkCollisionStatic(Vec3f vel)
	{
		Vector3f sum = Vector3f.getInstance();
		Vector3f partial = Vector3f.getInstance();
		Vector3f tempPos = Vector3f.getInstance();
		
		World w = Session.get().getWorld();
		List<StaticEntity> statents = Auto.ArrayList(w.getStaticEntities());
		
		Matrix4f translation;
		
		tempPos.set(this.transform.getTransformedPos()).add(vel);

		OBB3f tempOBB;
		
		OBB3f entityOBB;
		
		if(statents != null)
		{
			
			Collections.sort(statents, new Comparator<StaticEntity>()
			{

				public int compare(StaticEntity o1, StaticEntity o2)
				{
					return Double.compare(Vector3f.TEMP.set(o1.getPos()).sub(tempPos).length(), Vector3f.TEMP0.set(o2.getPos()).sub(tempPos).length());
				}
				
			});
			
			for(StaticEntity e : statents)
			{
				
				entityOBB = e.getModel().getPhysicalMesh().getOBBf(e.getTransformationMatrix(), e.getRotationMatrix());
				
				translation = Matrix4f.translation(tempPos);
				
				tempOBB = this.aabb2.transform(translation).getOBBf();

				if(OBBOBBResolver.iOBBOBB3f(tempOBB, entityOBB))
				{
					
					partial = OBBOBBResolver.rOBBOBB3f(tempOBB, entityOBB);
					
					sum.add(partial);
					tempPos.add(partial);
				}
			}
			
			vel.add(sum);

		}
		
		Vector3f.storeInstance(sum);
		Vector3f.storeInstance(partial);
		Vector3f.storeInstance(tempPos);
		
		return vel;		
	}
	
	public Vec3f checkCollisionDynamic(Vec3f vel)
	{
	Vector3f sum = Vector3f.getInstance();
	Vector3f partial = Vector3f.getInstance();
	Vector3f tempPos = Vector3f.getInstance();
	
	World w = Session.get().getWorld();
	List<StaticEntity> statents = Auto.ArrayList(w.getDynamicEntities());
	
	Matrix4f translation;
	
	tempPos.set(this.transform.getTransformedPos()).add(vel);

	OBB3f tempOBB;
	
	OBB3f entityOBB;
	
	if(statents != null)
	{
		
		Collections.sort(statents, new Comparator<StaticEntity>()
		{

			public int compare(StaticEntity o1, StaticEntity o2)
			{
				return Double.compare(Vector3f.TEMP.set(o1.getPos()).sub(tempPos).length(), Vector3f.TEMP0.set(o2.getPos()).sub(tempPos).length());
			}
			
		});
		
		for(StaticEntity e : statents)
		{
			
			entityOBB = e.getModel().getPhysicalMesh().getOBBf(e.getTransformationMatrix(), e.getRotationMatrix());
			
			translation = Matrix4f.translation(tempPos);
			
			tempOBB = this.aabb2.transform(translation).getOBBf();

				if(OBBOBBResolver.iOBBOBB3f(tempOBB, entityOBB))
				{
					
					partial = OBBOBBResolver.rOBBOBB3f(tempOBB, entityOBB);
					
					sum.add(partial);
					tempPos.add(partial);
				}

		}
		
		vel.add(sum);

	}
	
	Vector3f.storeInstance(sum);
	Vector3f.storeInstance(partial);
	Vector3f.storeInstance(tempPos);
	
	return vel;	
	}
	
	private void updateOrientation()
	{
		this.camera.rotateYaw(-Window.active.getInputManager().getDX());

		this.camera.rotatePitch(Window.active.getInputManager().getDY());
		
		World w = Session.get().getWorld();
		
		List<StaticEntity> statents = Auto.ArrayList(w.getStaticEntities());
		
		double minlength = Double.MAX_VALUE;
		StaticEntity ent = null;
		
		for(StaticEntity e : statents)
		{
			OBB3f entityOBB = e.getModel().getPhysicalMesh().getOBBf(e.getTransformationMatrix(), e.getRotationMatrix());

			Vector3f center = getCamera().getTotalPos();
			Vector3f dir = getCamera().getTotalRot().getForwardf().negate();

			if(RayOBBResolver.hit(center, dir, entityOBB))
			{
				Vector3f delta = getCamera().getTotalPos().subN(entityOBB.center);
				
				if(delta.length() <= minlength)
				{
					minlength = delta.length();
					ent = e;
				}
			}
			
			e.highlighted = false;
		}
		
		if(ent != null)
		{
			ent.highlighted = true;
			this.selectedEntity = ent.id;
		}
		else
		{
			this.selectedEntity = -1;
		}
	}
	
	public Vector3f getPos() { return this.transform.getPos(); }
	
	public Vector3f getEyePos() { return (Vector3f) this.transform.getPos().addN(0.0f, 18.0f, 0.0f); }

}
