package com.codered.window;

import com.codered.input.Input;
import com.codered.ppf.PPF;
import com.codered.resource.ResourceManager;
import com.codered.shader.ShaderList;
import com.codered.shader.ShaderParts;
import com.codered.shader.ShaderProgram;

import cmn.utilslib.math.vector.Vector2f;
import cmn.utilslib.math.vector.api.Vec2f;

public interface WindowContext
{
	Window getWindow();

	ResourceManager getResourceManager();
	
	String getTitle();
	
	int getWidth();
	
	int getHeight();
	
	Vec2f getSize();
	
	void getSize(Vector2f v);
	
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
	
	<T extends PPF> T getPPF(Class<T> clazz);
	void addPPF(Class<? extends PPF> clazz);
}
