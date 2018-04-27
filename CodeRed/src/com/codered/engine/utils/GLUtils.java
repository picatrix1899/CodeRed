package com.codered.engine.utils;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.codered.engine.managing.VAO;
import com.codered.engine.fbo.Framebuffer;

public class GLUtils
{
	private GLUtils() { }
	
	
	private static boolean CURRENT_ENABLED_BLEND = false;
	private static boolean CURRENT_ENABLED_DEPTH_TEST = false;
	private static boolean CURRENT_ENABLED_MULTISAMPLE = false;
	
	private static int CURRENT_TEXTURE2D = 0;
	private static int CURRENT_FRAMEBUFFER = 0;
	private static int CURRENT_VAO = 0;
	
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
	
	public static void bindFramebuffer(Framebuffer fbo)
	{
		if(fbo.getFramebuffer() == CURRENT_FRAMEBUFFER) return;
		
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, fbo.getFramebuffer());
		
		GL.glDrawBuffersAll();
		
		CURRENT_FRAMEBUFFER = fbo.getFramebuffer();
	}
	
	public static void bindFramebuffer(int framebuffer)
	{
		if(framebuffer == CURRENT_FRAMEBUFFER) return;
		
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, framebuffer);
		
		GL.glDrawBuffersAll();
		
		CURRENT_FRAMEBUFFER = framebuffer;
	}
	
	public static int getFramebuffer() { return CURRENT_FRAMEBUFFER; }
	
	
	public static void bindVAO(VAO vao, int... attribs)
	{
		GL30.glBindVertexArray(vao.getID());
		
		for(int i : attribs)
		{
			GL20.glEnableVertexAttribArray(i);
		}
		
		CURRENT_VAO = vao.getID();
	}
	
	public static void bindVAO(int vao, int...attribs)
	{
		GL30.glBindVertexArray(vao);
		
		for(int i : attribs)
		{
			GL20.glEnableVertexAttribArray(i);
		}
		
		CURRENT_VAO = vao;
	}
	
	public static int getVAO() { return CURRENT_VAO; }
}