package com.codered.engine.utils;

import java.util.List;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import cmn.utilslib.essentials.ListUtils;

public class GL
{
	
	
	public static void glDrawBuffers(int... buffers)
	{
		GL20.glDrawBuffers(buffers);
	}
	
	public static void glDrawBuffers(List<Integer> buffers)
	{
		GL20.glDrawBuffers(ListUtils.toIntArray(buffers));
	}
	
	public static void glDrawBuffersFirst()
	{
		GL20.glDrawBuffers(GL30.GL_COLOR_ATTACHMENT0);
	}
	
	public static void glDrawBuffersAll()
	{
		GL20.glDrawBuffers(new int[] {GL30.GL_COLOR_ATTACHMENT0, GL30.GL_COLOR_ATTACHMENT1, GL30.GL_COLOR_ATTACHMENT2, GL30.GL_COLOR_ATTACHMENT3,
				GL30.GL_COLOR_ATTACHMENT4, GL30.GL_COLOR_ATTACHMENT5, GL30.GL_COLOR_ATTACHMENT6, GL30.GL_COLOR_ATTACHMENT7});
	}
}
