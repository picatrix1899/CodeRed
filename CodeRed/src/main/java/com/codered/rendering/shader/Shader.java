package com.codered.rendering.shader;

import com.codered.ResourceHolder;

public interface Shader extends ResourceHolder
{
	void recompile();
	
	void setUniformValue(int id, Object... obj);
	
	ShaderSession start();
	void stop();
}
