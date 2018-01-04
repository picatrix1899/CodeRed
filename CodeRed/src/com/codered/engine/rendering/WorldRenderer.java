package com.codered.engine.rendering;

import org.lwjgl.opengl.GL11;

import com.codered.engine.GLUtils;
import com.codered.engine.entities.Camera;
import com.codered.engine.entities.DynamicEntity;
import com.codered.engine.entities.StaticEntity;
import com.codered.engine.managing.FBO;
import com.codered.engine.managing.MSFBO;
import com.codered.engine.managing.Window;
import com.codered.engine.managing.World;
import com.codered.engine.terrain.Terrain;

public class WorldRenderer
{
	private static MSFBO main = new MSFBO(Window.active.WIDTH, Window.active.HEIGHT, 6);
	
	static
	{
		main.applyColorAttachment(FBO.Target.COLOR0, true);
		main.applyDepthBufferAttachment();
	}
	
	public static void render(World w, Camera c)
	{
		GLUtils.bindFramebuffer(main);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		GLUtils.toggleMultisample(true);
		
		for(StaticEntity e : w.getStaticEntities())
		{
			e.getRenderer().init(e, w, c);
			e.getRenderer().render(e);
		}
		
		for(DynamicEntity e : w.getDynamicEntities())
		{
			e.getRenderer().init(e, w, c);
			e.getRenderer().render(e);
		}
		
		for(Terrain t : w.getStaticTerrains())
		{
			t.getRenderer().init(t, w, c);
			t.getRenderer().render(t);
		}
		
		GLUtils.toggleMultisample(false);
		
		main.resolveAttachmentToScreen(FBO.Target.COLOR0);
	}
}
