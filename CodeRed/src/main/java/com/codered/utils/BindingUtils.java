package com.codered.utils;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.codered.managing.VAO;
import com.codered.rendering.fbo.Framebuffer;

public class BindingUtils
{
	public static void bindTexture2D(int texture) { GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture); }
	
	public static void bindFramebuffer(Framebuffer fbo)
	{		
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, fbo.getId());
		
		GLUtils.glDrawBuffersAll();
	}
	
	public static void bindFramebuffer(int framebuffer)
	{
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, framebuffer);
		
		GLUtils.glDrawBuffersAll();
	}
	
	public static void bindDefaultFramebuffer()
	{
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
		
		GLUtils.glDrawBuffersAll();
	}
	
	public static void bindVAO(VAO vao, int... attribs)
	{
		GL30.glBindVertexArray(vao.getID());
		
		int length = attribs.length;
		for(int i = 0; i < length; i++)
			GL20.glEnableVertexAttribArray(attribs[i]);
	}
	
	public static void bindVAO(int vao, int...attribs)
	{
		GL30.glBindVertexArray(vao);
		
		int length = attribs.length;
		for(int i = 0; i < length; i++)
			GL20.glEnableVertexAttribArray(attribs[i]);
	}
}
