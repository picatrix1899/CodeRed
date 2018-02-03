package com.codered.engine.rendering;

import org.lwjgl.opengl.GL11;

import com.codered.engine.GLUtils;
import com.codered.engine.entities.Camera;
import com.codered.engine.entities.DynamicEntity;
import com.codered.engine.entities.StaticEntity;
import com.codered.engine.managing.World;
import com.codered.engine.fbo.FBO;
import com.codered.engine.fbo.FBOTarget;
import com.codered.engine.fbo.MSFBO;
import com.codered.engine.terrain.Terrain;

public class WorldRenderer
{
	public static MSFBO main = new MSFBO(4);
	
	public static FBO test = new FBO();
	
	static
	{
		main.applyColorTextureAttachment(FBOTarget.COLOR0, true);
		main.applyDepthTextureAttachment();
		
		test.applyColorTextureAttachment(FBOTarget.COLOR0, true);
		test.applyDepthBufferAttachment();
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
		
		main.blitAttachment(test, FBOTarget.COLOR0, FBOTarget.COLOR0, true);
		
		test.resolveAttachmentToScreen(FBOTarget.COLOR0);
	}
}
