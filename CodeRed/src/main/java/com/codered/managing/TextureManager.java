package com.codered.managing;

import java.util.ArrayList;
import java.util.List;

import org.barghos.core.util.ListUtils;

import com.codered.utils.GLCommon;

public class TextureManager
{
	private List<Integer> textureIds = new ArrayList<>();
	
	public void unload(int id)
	{
		GLCommon.deleteTextures(id);
		this.textureIds.remove((Integer)id);
	}
	
	public void unload(int...ids)
	{
		GLCommon.deleteTextures(ids);
		for(int id : ids)
			this.textureIds.remove((Integer)id);
	}
	
	public int create()
	{
		int id = GLCommon.genTextures();
		this.textureIds.add(id);
		return id;
	}
	
	public void cleanup()
	{
		GLCommon.deleteTextures(ListUtils.toIntArray(this.textureIds));
	}
}
