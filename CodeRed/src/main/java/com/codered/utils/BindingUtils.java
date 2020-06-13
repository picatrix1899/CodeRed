package com.codered.utils;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

import com.codered.managing.VAO;
import com.codered.rendering.fbo.Framebuffer;

public class BindingUtils
{
	public static void bindTexture2D(int texture)
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
	}
	
	public static void bindMSTexture2D(int texture)
	{
		GL11.glBindTexture(GL32.GL_TEXTURE_2D_MULTISAMPLE, texture);
	}
	
	public static void bindReadFramebuffer(Framebuffer fbo)
	{		
		GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, fbo.getId());

		GLUtils.glDrawBuffersAll();
	}
	
	public static void bindDrawFramebuffer(Framebuffer fbo)
	{		
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, fbo.getId());

		GLUtils.glDrawBuffersAll();
	}
	
	public static void bindFramebuffers(Framebuffer read, Framebuffer draw)
	{
		GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, read.getId());
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, draw.getId());

		GLUtils.glDrawBuffersAll();
	}
	
	public static void bindFramebuffer(Framebuffer fbo)
	{		
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, fbo.getId());

		GLUtils.glDrawBuffersAll();
	}
	
	public static void unbindFramebuffer()
	{
		bindDefaultFramebuffer();
	}
	
	public static void bindDefaultFramebuffer()
	{
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);

		GLUtils.glDrawBuffersAll();
	}
	
	public static void bindDefaultReadFramebuffer()
	{
		GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, 0);

		GLUtils.glDrawBuffersAll();
	}
	
	public static void bindDefaultDrawFramebuffer()
	{
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, 0);

		GLUtils.glDrawBuffersAll();
	}
	
	public static void bindVAO(VAO vao, int... attribs)
	{
		GL30.glBindVertexArray(vao.getID());
		
		for(int i = 0; i < attribs.length; i++)
			GL20.glEnableVertexAttribArray(attribs[i]);
	}
	
	public static void bindVAO(int vao, int...attribs)
	{
		GL30.glBindVertexArray(vao);
		
		for(int i = 0; i < attribs.length; i++)
			GL20.glEnableVertexAttribArray(attribs[i]);
	}
	
	public static void bindArrayBuffer(int bufferId)
	{
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferId);
	}
	
	public static void bindElementArrayBuffer(int bufferId)
	{
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, bufferId);
	}
	
	public static void bindRenderbuffer(int bufferId)
	{
		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, bufferId);
	}
	
	public static void unbindRenderbuffer()
	{
		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, 0);
	}
	
	public static void unbindTexture2D()
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	public static void unbindMSTexture2D()
	{
		GL11.glBindTexture(GL32.GL_TEXTURE_2D_MULTISAMPLE, 0);
	}
}
