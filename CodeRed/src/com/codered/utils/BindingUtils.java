package com.codered.utils;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.codered.fbo.Framebuffer;
import com.codered.managing.VAO;

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
		
		for(int i : attribs)
			GL20.glEnableVertexAttribArray(i);
	}
	
	public static void bindVAO(int vao, int...attribs)
	{
		GL30.glBindVertexArray(vao);
		
		for(int i : attribs)
			GL20.glEnableVertexAttribArray(i);
	}
}
