package com.codered.rendering.shader;

public interface Shader
{
	void recompile();
	
	void setUniformValue(int id, Object... obj);
	
	ShaderSession start();
	void stop();
	void load();
	
	void release();
}
