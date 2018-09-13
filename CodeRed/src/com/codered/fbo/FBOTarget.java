package com.codered.fbo;

import org.lwjgl.opengl.GL30;

public enum FBOTarget
{
	
	COLOR0(0, 0, GL30.GL_COLOR_ATTACHMENT0),
	COLOR1(1, 0, GL30.GL_COLOR_ATTACHMENT1),
	COLOR2(2, 0, GL30.GL_COLOR_ATTACHMENT2),
	COLOR3(3, 0, GL30.GL_COLOR_ATTACHMENT3),
	COLOR4(4, 0, GL30.GL_COLOR_ATTACHMENT4),
	COLOR5(5, 0, GL30.GL_COLOR_ATTACHMENT5),
	COLOR6(6, 0, GL30.GL_COLOR_ATTACHMENT6),
	COLOR7(7, 0, GL30.GL_COLOR_ATTACHMENT7),
	DEPTH(-1, 1, GL30.GL_DEPTH_ATTACHMENT)
	;
	private final int type;
	private final int target;
	private final int index;

	public static final int DST_COLOR = 0;
	public static final int DST_DEPTH = 1;
	
	private FBOTarget(int index, int type, int target)
	{
		this.index = index;
		this.type = type;
		this.target = target;
	}
	
	public int getIndex() { return this.index; }
	public int getType() { return this.type; }
	public int getTarget() { return this.target; }
	
	
	public static FBOTarget getByIndex(int id)
	{
		if(id < -1 || id > 7) return null;
		
		if(id >= 0)
		{
			return values()[id];
		}
		else
		{
			if(id == -1) return FBOTarget.DEPTH;
		}
		
		return null;
	}
}
