package com.codered.engine.managing;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

import com.codered.engine.GLUtils;
import com.codered.engine.managing.Window;
import com.google.common.collect.Lists;

import cmn.utilslib.essentials.ListUtils;


public class MSFBO
{
	private int[] attachments = new int[8];
	private boolean[] hdr = new boolean[8];
	
	private int framebuffer;
	
	private int depthBuffer;
	private int depthTexture;
	
	private int samples;
	
	private int width;
	private int height;
	
	public MSFBO(int width, int height, int samples)
	{
		this.width = width;
		this.height = height;
		this.samples = samples;
		
		this.framebuffer = GL30.glGenFramebuffers();
	}
	
	public void clearDraws()
	{
		GL20.glDrawBuffers(0);
	}
	
	public static void updateDraws()
	{
		ArrayList<Integer> a = Lists.newArrayList();
		
		for(int i = 0; i < 8; i++)
		{
				a.add(GL30.GL_COLOR_ATTACHMENT0 + i);
		}

		GL20.glDrawBuffers(ListUtils.toIntArray(a));
	}
	
	public void clearAllAttachments()
	{
		GLUtils.bindFramebuffer(this.framebuffer);
		updateDraws();
		GL11.glClearColor(0,0,0,1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public void clearAttachment(FBO.Target t)
	{
		GLUtils.bindFramebuffer(this.framebuffer);
		GL11.glDrawBuffer(t.getTarget());
		GL11.glClearColor(0,0,0,1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		updateDraws();
	}
	
	public void resolveAttachmentToScreen(FBO.Target t)
	{
		GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, this.framebuffer);
		GL11.glReadBuffer(t.getTarget());
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, 0);
		GL11.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT0);
		
		GL30.glBlitFramebuffer(0, 0, this.width, this.height, 0, 0, Window.active.WIDTH, Window.active.HEIGHT, GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT, GL11.GL_NEAREST);
	}
	
	public void blitAttachment(FBO dstFBO, FBO.Target tSrc, FBO.Target tDst)
	{
		GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, this.framebuffer);
		GL11.glReadBuffer(tSrc.getTarget());
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, dstFBO.getFramebuffer());
		GL11.glDrawBuffer(tDst.getTarget());
		GL30.glBlitFramebuffer(0, 0, this.width, this.height, 0, 0, dstFBO.getWidth(), dstFBO.getHeight(), GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT, GL11.GL_NEAREST);
	}
	
	public void applyColorAttachment(FBO.Target t, boolean hdr)
	{
		if(!(this.attachments[t.getIndex()] > 0))
		{	
			GLUtils.bindFramebuffer(this.framebuffer);
			
			int buffer = GL30.glGenRenderbuffers();
			
			GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, buffer);
			
			if(hdr)
				GL30.glRenderbufferStorageMultisample(GL30.GL_RENDERBUFFER, this.samples, GL30.GL_RGBA16F, this.width, this.height);
			else
				GL30.glRenderbufferStorageMultisample(GL30.GL_RENDERBUFFER, this.samples, GL11.GL_RGBA8, this.width, this.height);
			
			GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, t.getTarget(), GL30.GL_RENDERBUFFER, buffer);
				
			this.attachments[t.getIndex()] = buffer;
			this.hdr[t.getIndex()] = hdr;		
		}
	}

	public void applyDepthColorAttachment()
	{
		GLUtils.bindFramebuffer(this.framebuffer);
		
		this.depthTexture = GL11.glGenTextures();
		
		GLUtils.bindTexture2D(this.depthTexture);
		
		GL32.glTexImage2DMultisample(GL11.GL_TEXTURE_2D, this.samples, GL14.GL_DEPTH_COMPONENT24, this.width, this.height, true);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);	
		
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL11.GL_TEXTURE_2D, this.depthTexture, 0);
	}

	public void applyDepthBufferAttachment()
	{
		
		GLUtils.bindFramebuffer(this.framebuffer);
		
		this.depthBuffer = GL30.glGenRenderbuffers();
		
		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, this.depthBuffer);
		GL30.glRenderbufferStorageMultisample(GL30.GL_RENDERBUFFER, this.samples, GL14.GL_DEPTH_COMPONENT32, this.width, this.height);
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER, this.depthBuffer);
	}
	
	public void cleanup()
	{
		
	}
	
	public int getWidth() { return this.width; }
	
	public int getHeight() { return this.height; }
	
	public int getFramebuffer() { return this.framebuffer; }

	public int getBufferTexture(FBO.Target t)
	{
		if(t.getType() == 0)
		{
			return this.attachments[t.ordinal()];
		}
		
		return 0;
	}
	
	public int getDepthBuffer() { return this.depthBuffer; }
	
	public int getDepthTexture() { return this.depthTexture; }
}
