package com.codered.engine.shader;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import com.codered.engine.utils.WindowContextHelper;
import com.codered.engine.window.WindowContext;

public class ShaderList
{
	private HashMap<Class<? extends ShaderProgram>, ShaderProgram> shaders = new HashMap<Class<? extends ShaderProgram>, ShaderProgram>();

	
	private WindowContext context;
	
	public ShaderList()
	{
		this.context = WindowContextHelper.getCurrentContext();
	}
	
	public void addShader(Class<? extends ShaderProgram> clazz)
	{
		try
		{
			Constructor<? extends ShaderProgram> constructor = clazz.getDeclaredConstructor(WindowContext.class);
			
			try
			{
				this.shaders.put(clazz, constructor.newInstance(context));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public <A extends ShaderProgram> A getShader(Class<A> clazz)
	{
		return (A)this.shaders.get(clazz);
	}
}
