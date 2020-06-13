package com.codered.rendering.fbo;

import org.lwjgl.opengl.GL30;

public enum FBOTarget
{
	DEPTH(GL30.GL_DEPTH_ATTACHMENT),
	DEPTH_STENCIL(GL30.GL_DEPTH_STENCIL_ATTACHMENT),
	COLOR0(GL30.GL_COLOR_ATTACHMENT0),
	COLOR1(GL30.GL_COLOR_ATTACHMENT1),
	COLOR2(GL30.GL_COLOR_ATTACHMENT2),
	COLOR3(GL30.GL_COLOR_ATTACHMENT3),
	COLOR4(GL30.GL_COLOR_ATTACHMENT4),
	COLOR5(GL30.GL_COLOR_ATTACHMENT5),
	COLOR6(GL30.GL_COLOR_ATTACHMENT6),
	COLOR7(GL30.GL_COLOR_ATTACHMENT7);
	
	private int target;
	
	private FBOTarget(int target)
	{
		this.target = target;
	}

	public int getTarget()
	{
		return this.target;
	}
}
