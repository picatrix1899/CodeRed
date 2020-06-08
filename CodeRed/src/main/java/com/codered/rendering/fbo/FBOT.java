package com.codered.rendering.fbo;

import org.lwjgl.opengl.GL30;

import com.codered.CodeRed;

public class FBOT
{
	public static final FBOT DEPTH = new FBOT(-1, GL30.GL_DEPTH_ATTACHMENT) { public boolean isDepth() { return true; }};
	public static final FBOT COLOR0 = new FBOT(0, GL30.GL_COLOR_ATTACHMENT0);
	public static final FBOT COLOR1 = new FBOT(1, GL30.GL_COLOR_ATTACHMENT1);
	public static final FBOT COLOR2 = new FBOT(2, GL30.GL_COLOR_ATTACHMENT2);
	public static final FBOT COLOR3 = new FBOT(3, GL30.GL_COLOR_ATTACHMENT3);
	public static final FBOT COLOR4 = new FBOT(4, GL30.GL_COLOR_ATTACHMENT4);
	public static final FBOT COLOR5 = new FBOT(5, GL30.GL_COLOR_ATTACHMENT5);
	public static final FBOT COLOR6 = new FBOT(6, GL30.GL_COLOR_ATTACHMENT6);
	public static final FBOT COLOR7 = new FBOT(7, GL30.GL_COLOR_ATTACHMENT7);
	
	private int target;
	private int attachment;
	
	public FBOT(int attachment, int target)
	{
		if(attachment < -1 || attachment >= CodeRed.AVAILABLE_FBO_ATTACHMENTS) throw new IllegalArgumentException();
		this.attachment = attachment;
		this.target = target;
	}
	
	public int getTarget()
	{
		return this.target;
	}
	
	public int getAttachmentIndex()
	{
		return this.attachment;
	}
	
	public boolean isDepth()
	{
		return false;
	}
}
