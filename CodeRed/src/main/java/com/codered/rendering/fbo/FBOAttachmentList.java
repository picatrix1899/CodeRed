package com.codered.rendering.fbo;

import java.util.HashMap;
import java.util.Map;

import org.barghos.core.exception.ArgumentNullException;
import org.barghos.core.tuple.tuple2.Tup2iR;

import com.codered.ResourceHolder;

public class FBOAttachmentList implements ResourceHolder
{
	private Map<FBOTarget, FBOAttachment> attachments = new HashMap<>();
	
	public void resize(Tup2iR size)
	{
		for(FBOTarget key : this.attachments.keySet())
		{
			this.attachments.get(key).resize(size);
		}
	}

	public void set(FBOTarget target, FBOAttachment att)
	{
		if(target == null) throw new ArgumentNullException("target");
		if(att == null) throw new ArgumentNullException("att");
		
		this.attachments.put(target, att);
	}
	
	public FBOAttachment get(FBOTarget target)
	{
		if(target == null) throw new ArgumentNullException("target");
		
		return this.attachments.get(target);
	}
	
	public boolean isAvailable(FBOTarget target)
	{
		if(target == null) throw new ArgumentNullException("target");
		
		return this.attachments.containsKey(target);
	}
	
	public int getId(FBOTarget target)
	{
		if(target == null) throw new ArgumentNullException("target");
		
		if(this.attachments.containsKey(target))
			return this.attachments.get(target).getId();
		else
			return 0;
	}

	public void release(boolean forced)
	{
		for(FBOTarget target : this.attachments.keySet())
			this.attachments.get(target).release(forced);
		
		this.attachments.clear();
	}
}
