package com.codered.rendering.fbo;

import java.nio.ByteBuffer;

import org.barghos.core.tuple2.api.Tup2iR;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL30;

import com.codered.utils.BindingUtils;
import com.codered.utils.GLCommon;

public class FBOAttachmentTexture extends FBOAttachment
{

	private boolean isDepth;
	
	public FBOAttachmentTexture(int internalformat, Tup2iR size, boolean depth)
	{
		super(GLCommon.genTexture(), internalformat, size);
		
		this.isDepth = depth;
		
		create();
	}

	private void create()
	{
		BindingUtils.bindTexture2D(this.id);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, internalformat, this.size.getX(), this.size.getY(), 0, this.isDepth ? GL11.GL_DEPTH_COMPONENT : GL11.GL_RGBA, GL11.GL_FLOAT, (ByteBuffer) null);
	
		BindingUtils.unbindTexture2D();
	}

	protected void resize(Tup2iR size)
	{
		this.size.set(size);
		
		BindingUtils.bindTexture2D(this.id);
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, internalformat, this.size.getX(), this.size.getY(), 0, this.isDepth ? GL11.GL_DEPTH_COMPONENT : GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
	
		BindingUtils.unbindTexture2D();
	}

	public void bindToFramebuffer(Framebuffer framebuffer, FBOTarget target)
	{
		BindingUtils.bindFramebuffer(framebuffer);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, target.getTarget(), GL11.GL_TEXTURE_2D, this.id, 0);
		BindingUtils.unbindFramebuffer();
	}
	
	public void release(boolean forced)
	{
		GLCommon.deleteTexture(this.id);
	}
}
