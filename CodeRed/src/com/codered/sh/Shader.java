package com.codered.sh;

import java.util.Set;

public interface Shader
{
	void recompile();
	
	Set<String> getVariants();
	void applyVariant(String name);
	
	Set<String> getUniforms();
	void setUniformValue(String name, Object obj);
	
	void start();
	void stop();
	
	void release();
}
