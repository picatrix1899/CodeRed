package com.codered.utils;

import org.lwjgl.opengl.GL11;

public enum EvalFunc
{
	NEVER(GL11.GL_NEVER),
	LESS(GL11.GL_LESS),
	GREATER(GL11.GL_GREATER),
	EQUAL(GL11.GL_EQUAL),
	LEQUAL(GL11.GL_LEQUAL),
	GEQUAL(GL11.GL_GEQUAL),
	NOTEQUAL(GL11.GL_NOTEQUAL),
	ALWAYS(GL11.GL_ALWAYS)
	;
	private int function;
	
	private EvalFunc(int function)
	{
		this.function = function;
	}
	
	public int getFunction()
	{
		return this.function;
	}
}
