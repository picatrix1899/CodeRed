package com.codered.engine.shaders.shader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;

import com.codered.engine.managing.Paths;
import com.google.common.collect.Maps;

import cmn.utilslib.color.colors.api.IColor3Base;
import cmn.utilslib.essentials.Auto;
import cmn.utilslib.math.matrix.Matrix4f;
import cmn.utilslib.math.vector.api.Vec2fBase;
import cmn.utilslib.math.vector.api.Vec3fBase;
import cmn.utilslib.math.vector.api.Vec4fBase;

public abstract class ShaderProgram
{
	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	
	private int programID;
	
	private List<Integer> geometryShaderIds = Auto.ArrayList();
	private List<Integer> vertexShaderIds = Auto.ArrayList();
	private List<Integer> fragmentShaderIds = Auto.ArrayList();
	
	private HashMap<String,Integer> uniforms = Maps.newHashMap();
	
	private HashMap<String,Object> inputs = Maps.newHashMap();
	
	protected ShaderProgram()
	{
		this.programID = GL20.glCreateProgram();
		
		Shader.VertexShader[] vertexShaders = getClass().getDeclaredAnnotationsByType(Shader.VertexShader.class);
		Shader.FragmentShader[] fragmentShaders = getClass().getDeclaredAnnotationsByType(Shader.FragmentShader.class);
		Shader.GeometryShader[] geometryShaders = getClass().getDeclaredAnnotationsByType(Shader.GeometryShader.class);
		
		if(vertexShaders.length > 0)
			for(Shader.VertexShader vs : vertexShaders)
				attachVertexShader(vs.value());
		
		if(fragmentShaders.length > 0)
			for(Shader.FragmentShader fs : fragmentShaders)
				attachFragmentShader(fs.value());
		
		if(geometryShaders.length > 0)
			for(Shader.GeometryShader gs : geometryShaders)
				attachGeometryShader(gs.value());
		
		compile();
	}
	
	public void setInput(String name, Object val)
	{
		this.inputs.put(name, val);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getInput(String name)
	{
		return (T) this.inputs.get(name);
	}
	
	public void compile()
	{
		for(int i : this.geometryShaderIds)
			GL20.glAttachShader(programID, i);
		
		for(int i : this.vertexShaderIds)
			GL20.glAttachShader(programID, i);
		
		for(int i : this.fragmentShaderIds)
			GL20.glAttachShader(programID, i);

		Shader.Attrib[] attribs = getClass().getDeclaredAnnotationsByType(Shader.Attrib.class);
		
		for(Shader.Attrib attrib : attribs)
		{
			bindAttribute(attrib.pos(), attrib.var());
		}
		
		GL20.glLinkProgram(this.programID);
		
		GL20.glValidateProgram(this.programID);
		getAllUniformLocations();
	}
	
	protected void attachGeometryShader(String file)
	{
		this.geometryShaderIds.add(loadShader(file + Paths.e_geometryShader, GL32.GL_GEOMETRY_SHADER));
	}
	
	protected void attachFragmentShader(String file)
	{
		this.fragmentShaderIds.add(ShaderParts.builtIn().getFragmentShader(file).getId());
		//this.fragmentShaderIds.add(loadShader(file + Paths.e_fragmentShader, GL20.GL_FRAGMENT_SHADER));
	}
	
	protected void attachVertexShader(String file)
	{
		this.vertexShaderIds.add(ShaderParts.builtIn().getVertexShader(file).getId());
		//this.vertexShaderIds.add(loadShader(file + Paths.e_vertexShader, GL20.GL_VERTEX_SHADER));
	}

	
	public abstract void use();
	
	public void start()
	{
		GL20.glUseProgram(this.programID);
	}
	
	public void stop()
	{
		GL20.glUseProgram(0);
	}
	
	public void cleanup()
	{
		GL20.glUseProgram(0);
		
		for(int i : this.geometryShaderIds)
			GL20.glDetachShader(this.programID, i);
		
		for(int i : this.vertexShaderIds)
			GL20.glDetachShader(this.programID, i);
		
		for(int i : this.fragmentShaderIds)
			GL20.glDetachShader(this.programID, i);
		
		GL20.glDeleteProgram(this.programID);
	}
	
	protected void bindAttribute(int attrib, String var)
	{
		GL20.glBindAttribLocation(this.programID, attrib, var);
	}
	
	protected int getUniformLocation(String uniform)
	{
		return GL20.glGetUniformLocation(this.programID, uniform);
	}
	
	protected void addUniform(String uniform)
	{
		this.uniforms.put(uniform, GL20.glGetUniformLocation(this.programID, uniform));
	}
	
	private int loadShader(String file, int type)
	{
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, readShader(file));
		GL20.glCompileShader(shaderID);
		
		if(GL20.glGetShaderi(shaderID,GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
		{
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.out.println("Could not compile shader. " + file);
			System.exit(-1);
		}
		
		return shaderID;
		
	}
	
	private String readShader(String file)
	{
		file = Paths.p_shaders + file;
		
		file = file.replaceAll("\\\\","\\\\").replaceAll("/", "\\\\");
		
		StringBuilder shaderSource = new StringBuilder();
		
		String line;
		
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			while((line = reader.readLine()) != null)
			{
				
				if(line.startsWith("#include"))
				{
					shaderSource.append(readShader(line.substring("#include".length() + 2, line.length() - 1)));
				}
				else
				{
					shaderSource.append(line).append("\n");					
				}

			}
			
			reader.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return shaderSource.toString();
	}
	
	protected abstract void getAllUniformLocations();
	
	
	
	protected void loadFloat(int location, float val)
	{
		GL20.glUniform1f(location, val);
	}
	
	protected void loadColor3(int location, IColor3Base val)
	{
		GL20.glUniform3f(location, val.getUnityR(), val.getUnityG(), val.getUnityB());
	}
	
	protected void loadVector2(int location, Vec2fBase val)
	{
		GL20.glUniform2f(location,val.getX(), val.getY());
	}
	
	protected void loadVector3(int location, Vec3fBase val)
	{
		GL20.glUniform3f(location, val.getX(), val.getY(), val.getZ());
	}
	
	protected void loadBoolean(int location, boolean val)
	{
		loadFloat(location, val ? 1 : 0);
	}
	
	protected void loadInt(int location, int val)
	{
		GL20.glUniform1i(location, val);
	}
	
	protected void loadMatrix(int location, Matrix4f val)
	{
		matrixBuffer.put(val.getColMajor());
		
		matrixBuffer.flip();
		
		GL20.glUniformMatrix4fv(location, false, matrixBuffer);
		
		matrixBuffer = BufferUtils.createFloatBuffer(16);
	}
	
	protected void loadFloat(String uniform, float val)
	{
		GL20.glUniform1f(this.uniforms.get(uniform), val);
	}
	
	protected void loadVector2(String uniform, Vec2fBase val)
	{
		GL20.glUniform2f(this.uniforms.get(uniform),val.getX(), val.getY());
	}
	
	protected void loadColor3(String uniform, IColor3Base val)
	{
		GL20.glUniform3f(this.uniforms.get(uniform), val.getUnityR(), val.getUnityG(), val.getUnityB());
	}
	
	protected void loadVector3(String uniform, Vec3fBase val)
	{
		GL20.glUniform3f(this.uniforms.get(uniform), val.getX(), val.getY(), val.getZ());
	}
	
	protected void loadVector4(String uniform, Vec4fBase val)
	{
		GL20.glUniform4f(this.uniforms.get(uniform), val.getX(), val.getY(), val.getZ(), val.getA());
	}
	
	protected void loadBoolean(String uniform, boolean val)
	{
		loadFloat(this.uniforms.get(uniform), val ? 1 : 0);
	}
	
	protected void loadInt(String uniform, int val)
	{
		GL20.glUniform1i(this.uniforms.get(uniform), val);
	}
	
	protected void loadMatrix(String uniform, Matrix4f val)
	{
		matrixBuffer.put(val.getColMajor());
		
		matrixBuffer.flip();
		
		GL20.glUniformMatrix4fv(this.uniforms.get(uniform), false, matrixBuffer);
		
		matrixBuffer = BufferUtils.createFloatBuffer(16);
	}
	
	protected void loadTexture(String uniform, int attrib, int texture)
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + attrib);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		loadInt(uniform, attrib);
	}
}
