package com.codered.sh;

import java.util.Set;

public interface Shader
{
	void recompile();
	
	Set<String> getVariants();
	void applyVariant(String name);
	
	Set<String> getUniforms();
	void setUniformValue(String name, Object obj);
	
	ShaderSession start();
	void stop();
	void load();
	
	void release();
}
