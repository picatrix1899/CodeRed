package com.codered.window;

import com.codered.input.Input;
import com.codered.resource.ResourceManager;
import com.codered.shader.ShaderList;
import com.codered.shader.ShaderParts;
import com.codered.shader.ShaderProgram;

public interface WindowContext
{
	Window getWindow();

	ResourceManager getResourceManager();
	
	ShaderParts getShaderParts();
	
	Input getInputManager();
	
	ShaderList getShaders();
	
	
	/*
	 * #####################
	 * #  DIRECT-ACCESSES  #
	 * #####################
	 */
	
	default long getWindowId() { return getWindow().getWindowId(); }
	
	default <A extends ShaderProgram> A getShader(Class<A> clazz) { return getShaders().getShader(clazz); }
	
	default void addShader(Class<? extends ShaderProgram> clazz) { getShaders().addShader(clazz); }
}
