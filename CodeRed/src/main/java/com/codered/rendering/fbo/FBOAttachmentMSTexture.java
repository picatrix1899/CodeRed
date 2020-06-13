package com.codered.rendering.fbo;

import org.barghos.core.tuple.tuple2.Tup2iR;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

import com.codered.utils.BindingUtils;
import com.codered.utils.GLCommon;

public class FBOAttachmentMSTexture extends FBOAttachment
{
	private int samples;
	
	public FBOAttachmentMSTexture(int samples, int internalformat, Tup2iR size)
	{
		super(GLCommon.genTexture(), internalformat, size);
;
		this.samples = samples;
		
		create();
	}

	private void create()
	{
		BindingUtils.bindMSTexture2D(this.id);

		GL11.glTexParameteri(GL32.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL32.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL32.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL32.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		
		GL32.glTexImage2DMultisample(GL32.GL_TEXTURE_2D_MULTISAMPLE, samples, internalformat, this.size.getX(), this.size.getY(), false);	

		BindingUtils.unbindMSTexture2D();
	}

	protected void resize(Tup2iR size)
	{
		this.size.set(size);
		
		BindingUtils.bindMSTexture2D(this.id);
		
		GL32.glTexImage2DMultisample(GL32.GL_TEXTURE_2D_MULTISAMPLE, samples, internalformat, this.size.getX(), this.size.getY(), false);	

		BindingUtils.unbindMSTexture2D();
	}

	public void bindToFramebuffer(Framebuffer framebuffer, FBOTarget target)
	{
		BindingUtils.bindFramebuffer(framebuffer);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, target.getTarget(), GL32.GL_TEXTURE_2D_MULTISAMPLE, this.id, 0);
		BindingUtils.unbindFramebuffer();
	}
	
	public void release(boolean forced)
	{
		GLCommon.deleteTexture(this.id);
	}
}
