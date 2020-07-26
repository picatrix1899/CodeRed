package com.codered.rendering.fbo;

import org.barghos.core.tuple2.Tup2i;
import org.barghos.core.tuple2.api.Tup2iR;

import com.codered.ResourceHolder;

public abstract class FBOAttachment implements ResourceHolder
{
	protected int id;

	protected final Tup2i size = new Tup2i();
	
	protected int internalformat;

	public FBOAttachment(int id, int internalformat, Tup2iR size)
	{
		this.id = id;
		this.internalformat = internalformat;
		this.size.set(size);
	}
	
	protected abstract void resize(Tup2iR size);
	public abstract void bindToFramebuffer(Framebuffer framebuffer, FBOTarget target);
	
	public int getId() { return this.id; }
	
	public Tup2iR getSize() { return this.size; }
	
	public int getInternalFormat() { return this.internalformat; }
}
