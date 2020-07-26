package com.codered.rendering.fbo;

import org.barghos.core.tuple2.api.Tup2iR;
import org.lwjgl.opengl.GL30;

import com.codered.utils.BindingUtils;
import com.codered.utils.GLCommon;

public class FBOAttachmentBuffer extends FBOAttachment
{
	public FBOAttachmentBuffer(int internalformat, Tup2iR size)
	{
		super(GLCommon.genRenderbuffer(), internalformat, size);
		
		create();
	}
	
	private void create()
	{
		BindingUtils.bindRenderbuffer(this.id);
		GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, this.internalformat, this.size.getX(), this.size.getY());
		BindingUtils.unbindRenderbuffer();
	}
	
	public void resize(Tup2iR size)
	{
		this.size.set(size);

		BindingUtils.bindRenderbuffer(this.id);
		GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, this.internalformat, this.size.getX(), this.size.getY());
		BindingUtils.unbindRenderbuffer();
	}

	public void release(boolean forced)
	{
		GLCommon.deleteRenderbuffer(this.id);
	}

	public void bindToFramebuffer(Framebuffer framebuffer, FBOTarget target)
	{
		BindingUtils.bindFramebuffer(framebuffer);
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, target.getTarget(), GL30.GL_RENDERBUFFER, this.id);
		BindingUtils.unbindFramebuffer();
	}
	
}
