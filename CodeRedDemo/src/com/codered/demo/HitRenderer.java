package com.codered.demo;

import com.codered.engine.entities.Camera;
import com.codered.engine.entities.StaticEntity;
import com.codered.engine.managing.World;
import com.codered.engine.rendering.DefaultEntityRenderer;

import cmn.utilslib.math.geometry.OBB3f;
import cmn.utilslib.math.geometry.RayOBBResolver;
import cmn.utilslib.math.vector.Vector3f;

public class HitRenderer extends DefaultEntityRenderer
{

	public static HitRenderer INSTANCE = new HitRenderer();
	
	public void init(StaticEntity e, World w, Camera c)
	{
		super.init(e, w, c);
	}

	public void render(StaticEntity e)
	{
		
		OBB3f entityOBB = e.getModel().getPhysicalMesh().getOBBf(e.getTransformationMatrix(), e.getRotationMatrix());
		
		Player p = Session.get().getPlayer();
		
		Vector3f center = p.getEyePos();
		Vector3f dir = p.getCamera().getTotalRot().getForwardf();
		
		dir.negate();
		
		System.out.println(dir);
		
		((GUIIngameOverlay)p.window).label.text(RayOBBResolver.hit(center, dir, entityOBB) + "");
		
		super.render(e);
	}

}
