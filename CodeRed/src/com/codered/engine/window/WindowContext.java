package com.codered.engine.window;

import com.codered.engine.input.Input;
import com.codered.engine.ppf.PPF;
import com.codered.engine.resource.ResourceManager;
import com.codered.engine.shader.ShaderList;
import com.codered.engine.shader.ShaderParts;
import com.codered.engine.shader.ShaderProgram;

import cmn.utilslib.math.vector.api.Vec2f;
import cmn.utilslib.math.vector.api.Vec2fBase;

public interface WindowContext
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
