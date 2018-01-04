package com.codered.engine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

import com.codered.engine.managing.FBO;
import com.codered.engine.managing.MSFBO;

public class GLUtils
{
	private GLUtils() { }
	
	
	private static boolean CURRENT_ENABLED_BLEND = false;
	private static boolean CURRENT_ENABLED_DEPTH_TEST = false;
	private static boolean CURRENT_ENABLED_MULTISAMPLE = false;
	
	private static int CURRENT_TEXTURE2D = 0;
	private static int CURRENT_FRAMEBUFFER = 0;
	
	public static void toggleBlend(boolean state)
	{
		if(state)
			GL11.glEnable(GL11.GL_BLEND);
		else
			GL11.glDisable(GL11.GL_BLEND);
		CURRENT_ENABLED_BLEND = state;
	}
	
	public static boolean getBlend() { return CURRENT_ENABLED_BLEND; }
	
	public static void toggleMultisample(boolean state)
	{
		if(state)
			GL11.glEnable(GL13.GL_MULTISAMPLE);
		else
			GL11.glDisable(GL13.GL_MULTISAMPLE);
		CURRENT_ENABLED_MULTISAMPLE = state;
	}
	
	public static boolean getMultisample() { return CURRENT_ENABLED_MULTISAMPLE; }
	
	public static void toggleDepthTest(boolean state)
	{
		if(state)
			GL11.glEnable(GL11.GL_DEPTH_TEST);
		else
			GL11.glDisable(GL11.GL_DEPTH_TEST);
		CURRENT_ENABLED_DEPTH_TEST = state;
	}
	
	public static boolean getDepthTest() { return CURRENT_ENABLED_DEPTH_TEST; }
	
	public static void bindTexture2D(int texture)
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		
		CURRENT_TEXTURE2D = texture;
	}
	
	public static int getTexture2D() { return CURRENT_TEXTURE2D; }
	
	public static void bindFramebuffer(FBO fbo)
	{
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, fbo.getFramebuffer());
		
		FBO.updateDraws();
		
		CURRENT_FRAMEBUFFER = fbo.getFramebuffer();
	}
	
	public static void bindFramebuffer(MSFBO fbo)
	{
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, fbo.getFramebuffer());
		
		MSFBO.updateDraws();
		
		CURRENT_FRAMEBUFFER = fbo.getFramebuffer();
	}
	
	public static void bindFramebuffer(int framebuffer)
	{
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, framebuffer);
		
		FBO.updateDraws();
		
		CURRENT_FRAMEBUFFER = framebuffer;
	}
	
	public static int getFramebuffer() { return CURRENT_FRAMEBUFFER; }
}
