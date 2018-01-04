package com.codered.engine.managing;

import java.nio.ByteBuffer;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.codered.engine.GLUtils;
import com.codered.engine.managing.Window;
import com.google.common.collect.Lists;

import cmn.utilslib.essentials.ListUtils;


public class FBO
{
	
	public enum Target
	{
		COLOR0(0, 0 ,GL30.GL_COLOR_ATTACHMENT0),
		COLOR1(1, 0 ,GL30.GL_COLOR_ATTACHMENT1),
		COLOR2(2, 0 ,GL30.GL_COLOR_ATTACHMENT2),
		COLOR3(3, 0 ,GL30.GL_COLOR_ATTACHMENT3),
		COLOR4(4, 0 ,GL30.GL_COLOR_ATTACHMENT4),
		COLOR5(5, 0 ,GL30.GL_COLOR_ATTACHMENT5),
		COLOR6(6, 0 ,GL30.GL_COLOR_ATTACHMENT6),
		COLOR7(7, 0 ,GL30.GL_COLOR_ATTACHMENT7),
		DEPTH(-1, 1 ,GL30.GL_DEPTH_ATTACHMENT)
		;
		private final int type;
		private final int target;
		private final int index;
		
		private Target(int index, int type, int target)
		{
			this.index = index;
			this.type = type;
			this.target = target;
		}
		
		public int getIndex() { return this.index; }
		public int getType() { return this.type; }
		public int getTarget() { return this.target; }
	}
	private int[] attachments = new int[8];
	private boolean[] hdr = new boolean[8];
	
	private int framebuffer;
	
	private int depthBuffer;
	private int depthTexture;
	
	
	private int width;
	private int height;
	
	public FBO(int width, int height)
	{
		this.width = width;
		this.height = height;
		
		this.framebuffer = GL30.glGenFramebuffers();		
	}
	
	public void clearDraws()
	{
		GL20.glDrawBuffers(GL30.GL_COLOR_ATTACHMENT0);
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
	
	public void blitAttachment(FBO dstFBO, FBO.Target tSrc, FBO.Target tDst, boolean depth)
	{
		GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, this.framebuffer);
		GL11.glReadBuffer(tSrc.getTarget());
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, dstFBO.getFramebuffer());
		GL11.glDrawBuffer(tDst.getTarget());
		if(depth)
			GL30.glBlitFramebuffer(0, 0, this.width, this.height, 0, 0, dstFBO.getWidth(), dstFBO.getHeight(), GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT, GL11.GL_NEAREST);
		else
			GL30.glBlitFramebuffer(0, 0, this.width, this.height, 0, 0, dstFBO.getWidth(), dstFBO.getHeight(), GL11.GL_COLOR_BUFFER_BIT, GL11.GL_NEAREST);
	}
	
	public void applyColorAttachment(FBO.Target t, boolean hdr)
	{
			if(!(this.attachments[t.getIndex()] > 0))
			{	
				GLUtils.bindFramebuffer(this.framebuffer);
					
				int texture = GL11.glGenTextures();
					
				GLUtils.bindTexture2D(texture);
					
				if(hdr)
					GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0,  GL30.GL_RGBA16F, this.width, this.height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
				else
					GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0,  GL11.GL_RGBA8, this.width, this.height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
						
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
				
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);	
					
				GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, t.getTarget(), GL11.GL_TEXTURE_2D, texture, 0);
				
				this.attachments[t.getIndex()] = texture;
				this.hdr[t.getIndex()] = hdr;
			}
	}
	
	public void applyColorAttachment(FBO.Target t, int id, boolean hdr)
	{
			if(!(this.attachments[t.getIndex()] > 0))
			{	
				GLUtils.bindFramebuffer(this.framebuffer);
					
				int texture = id;
					
				GLUtils.bindTexture2D(texture);
				
				GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, t.getTarget(), GL11.GL_TEXTURE_2D, texture, 0);
				
				this.attachments[t.getIndex()] = texture;
				this.hdr[t.getIndex()] = hdr;
			}
	}
	
	public void applyDepthColorAttachment(int id)
	{
		GLUtils.bindFramebuffer(this.framebuffer);
			
		this.depthTexture = id;
		
		GLUtils.bindTexture2D(this.depthTexture);

		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL11.GL_TEXTURE_2D, this.depthTexture, 0);
	}
	
	public void applyDepthColorAttachment()
	{
		GLUtils.bindFramebuffer(this.framebuffer);
			
		this.depthTexture = GL11.glGenTextures();
		
		GLUtils.bindTexture2D(this.depthTexture);
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL14.GL_DEPTH_COMPONENT24, this.width, this.height, 0, GL11.GL_DEPTH_COMPONENT, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
		
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
		GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL14.GL_DEPTH_COMPONENT24, this.width, this.height);
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER, this.depthBuffer);
		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, 0);
	}
	
	public int getBufferTexture(FBO.Target t)
	{
		if(t.getType() == 0)
		{
			return this.attachments[t.ordinal()];
		}
		
		return 0;
	}
	
	public int getDepthTexture() { return this.depthTexture; }
	
	public int getDepthBuffer() { return this.depthBuffer; }
	
	public int getFramebuffer() { return this.framebuffer; }
	
	public int getWidth() { return this.width; }
	
	public int getHeight() { return this.height; }
}
