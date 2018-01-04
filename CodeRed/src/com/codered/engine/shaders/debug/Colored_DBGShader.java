package com.codered.engine.shaders.debug;

import com.codered.engine.shaders.shader.Shader.Attrib;
import com.codered.engine.shaders.shader.Shader.FragmentShader;
import com.codered.engine.shaders.shader.Shader.VertexShader;

import cmn.utilslib.color.colors.api.IColor3Base;
import cmn.utilslib.math.matrix.Matrix4f;

@VertexShader("debug/dbg_colored")
@FragmentShader("debug/dbg_colored")
@Attrib(pos=0, var="vertexPos")
public class Colored_DBGShader extends DebugShader
{
	protected void getAllUniformLocations()
	{
		addUniform("T_model");
		addUniform("T_view");
		addUniform("T_projection");
		
		addUniform("color");
	}

	public void loadViewMatrix(Matrix4f m) { setInput("T_view", m); }
	
	public void loadModelMatrix(Matrix4f m) { setInput("T_model", m); }
	
	public void loadProjectionMatrix(Matrix4f m) { setInput("T_projection", m); }
	
	public void loadColor(IColor3Base c) { setInput("color", c); }
	
	
	private void loadMatrices0(Matrix4f view, Matrix4f model, Matrix4f proj)
	{
		loadMatrix("T_model", model != null ? model : Matrix4f.identity());
		loadMatrix("T_view", view);
		loadMatrix("T_projection", proj);
	}
	
	public void use()
	{
		start();
		loadMatrices0(getInput("T_view"), getInput("T_model"), getInput("T_projection"));
		loadColor3("color", getInput("color"));
	}
	
}
