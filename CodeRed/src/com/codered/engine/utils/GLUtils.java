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
	
	public static void blend(boolean state)
	{
		if(state)
			GL11.glEnable(GL11.GL_BLEND);
		else
			GL11.glDisable(GL11.GL_BLEND);
	}
	
	public static void multisample(boolean state)
	{
		if(state)
			GL11.glEnable(GL13.GL_MULTISAMPLE);
		else
			GL11.glDisable(GL13.GL_MULTISAMPLE);
	}
	
	public static void depthTest(boolean state)
	{
		if(state)
			GL11.glEnable(GL11.GL_DEPTH_TEST);
		else
			GL11.glDisable(GL11.GL_DEPTH_TEST);
	}
	
	public static void bindTexture2D(int texture)
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
	}
	
	public static void bindFramebuffer(Framebuffer fbo)
	{		
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, fbo.getFramebuffer());
		
		GL.glDrawBuffersAll();
	}
	
	public static void bindFramebuffer(int framebuffer)
	{
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, framebuffer);
		
		GL.glDrawBuffersAll();
	}
	
	public static void bindVAO(VAO vao, int... attribs)
	{
		GL30.glBindVertexArray(vao.getID());
		
		for(int i : attribs)
		{
			GL20.glEnableVertexAttribArray(i);
		}
	}
	
	public static void bindVAO(int vao, int...attribs)
	{
		GL30.glBindVertexArray(vao);
		
		for(int i : attribs)
		{
			GL20.glEnableVertexAttribArray(i);
		}
	}
}
