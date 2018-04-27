package com.codered.engine.window;

import com.codered.engine.input.Input;
import com.codered.engine.resource.ResourceManager;
import com.codered.engine.shader.ShaderList;
import com.codered.engine.shader.ShaderParts;

import cmn.utilslib.math.vector.api.Vec2f;
import cmn.utilslib.math.vector.api.Vec2fBase;

public interface IWindowContext
{
	Window getWindow();

	ResourceManager getResourceManager();
	
	String getTitle();
	
	int getWidth();
	
	int getHeight();
	
	Vec2fBase getSize();
	
	void getSize(Vec2f v);
	
	ShaderParts getShaderParts();
	
	Input getInputManager();
	
	ShaderList getObjectShaders();
}
