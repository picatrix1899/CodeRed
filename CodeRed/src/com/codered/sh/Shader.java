package com.codered.sh;

import java.util.Set;

public interface Shader
{
	void recompile();
	
	Set<String> getVariants();
	void applyVariant(String name);
	
	void setUniformValue(int id, Object obj);
	
	ShaderSession start();
	void stop();
	void load();
	
	void release();
}
