package com.codered.shaders.object.simple;

import java.util.List;

import com.codered.shader.ShaderProgram;
import com.codered.shader.UniformMatrix4x4;
import com.codered.window.WindowContext;

import cmn.utilslib.color.colors.api.IColor3Base;
import cmn.utilslib.dmap.dmaps.DMap2;
import cmn.utilslib.math.matrix.Matrix4f;

public class Colored_OShader extends ShaderProgram
{

	public UniformMatrix4x4 u_T_view;
	
	public Colored_OShader(WindowContext context)
	{
		super(context);
	}

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

	public void attachShaderParts()
	{
		attachVertexShader(this.context.getShaderParts().builtIn().getVertexShader("o_colored"));
		attachFragmentShader(this.context.getShaderParts().builtIn().getFragmentShader("o_colored"));
	}

	public void getAttribs(List<DMap2<Integer,String>> attribs)
	{
		attribs.add(new DMap2<Integer,String>(0, "vertexPos"));
	}
	
}
