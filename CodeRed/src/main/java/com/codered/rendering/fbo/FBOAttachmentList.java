package com.codered.rendering.fbo;

import org.barghos.core.exception.ArgumentNullException;

import com.codered.CodeRed;

public class FBOAttachmentList
{
	private FBOAttachment[] attachments = new FBOAttachment[CodeRed.AVAILABLE_FBO_ATTACHMENTS];
	
	private FBOAttachment depth;
	
	public void resize(int width, int height)
	{
		for(int i = 0; i < CodeRed.AVAILABLE_FBO_ATTACHMENTS; i++)
			if(this.attachments[i] != null) this.attachments[i].resize(width, height);

		if(this.depth != null) this.depth.resize(width, height);
	}

	public void set(FBOT target, FBOAttachment att)
	{
		if(target == null) throw new ArgumentNullException("target");
		if(target.getAttachmentIndex() < -1 || target.getAttachmentIndex() >= CodeRed.AVAILABLE_FBO_ATTACHMENTS) throw new IllegalArgumentException();
		if(att == null) throw new ArgumentNullException("att");
		
		if(target.isDepth())
			this.depth = att;
		else
			this.attachments[target.getAttachmentIndex()] = att;
	}
	
	public FBOAttachment get(FBOT target)
	{
		if(target == null) throw new ArgumentNullException("target");
		
		return target.isDepth() ? this.depth : this.attachments[target.getAttachmentIndex()];
	}
	
	public boolean isAvailable(FBOT target)
	{
		if(target == null) throw new ArgumentNullException("target");
		
		if(target.isDepth())
			return this.depth != null;
		else
			return this.attachments[target.getAttachmentIndex()] != null;
	}
	
	public int getId(FBOT target)
	{
		if(target == null) throw new ArgumentNullException("target");
		
		if(target.isDepth())
			return this.depth != null ? this.depth.getId() : 0;
		else
			return this.attachments[target.getAttachmentIndex()] != null ? this.attachments[target.getAttachmentIndex()].getId() : 0;
	}
	
	public void release()
	{
		for(int i = 0; i < CodeRed.AVAILABLE_FBO_ATTACHMENTS; i++)
		{
			FBOAttachment att = this.attachments[i];
			if(att != null) att.release();
		}
	}
}
