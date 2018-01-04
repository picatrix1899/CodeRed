package com.codered.engine.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import com.codered.engine.managing.VAO;
import com.codered.engine.managing.Window;
import com.codered.engine.managing.loader.LambdaFont;
import com.codered.engine.shaders.gui.GUIShader;

import cmn.utilslib.color.colors.api.IColor3Base;
import cmn.utilslib.math.vector.Vector2f;
import cmn.utilslib.math.vector.Vector4f;
import cmn.utilslib.math.vector.api.Vec2f;

public abstract class GUI
{
	
	protected VAO vao;
	
	public abstract void render();
	public abstract void update();
	
	public GUI()
	{
		vao = new VAO();
	}
	
	protected void setMousePos(float x, float y)
	{
		Window.active.getInputManager().setMousePos(x, Window.active.HEIGHT - y);
	}
	
	protected void drawTexturedRect(int t, float posX, float posY, float sizeX, float sizeY)
	{
		Vector2f[] vertices = new Vector2f[4];
		
		vertices[0] = new Vector2f(posX + sizeX, posY);		
		vertices[1] = new Vector2f(posX, posY);
		vertices[2] = new Vector2f(posX + sizeX, posY +sizeY);		
		vertices[3] = new Vector2f(posX, posY + sizeY);
		
		Vector2f[] uvs = new Vector2f[] {
		
		new Vector2f(1,0),				
		new Vector2f(0,0),		
		new Vector2f(1,1),		
		new Vector2f(0,1),		

		};

		vao.storeData(0, vertices, GL15.GL_STATIC_DRAW);
		vao.storeData(1, uvs, GL15.GL_STATIC_DRAW);
		
		vao.bind(0, 1);
		
		GUIShader.No().loadTexture(t);
		GUIShader.No().use();
		
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		
		GUIShader.No().stop();
	}
	
	protected void drawLine(IColor3Base c, float width, Vector2f start, Vector2f... p)
	{
		Vector2f[] vertices = new Vector2f[1 + p.length];
		vertices[0] = start;
		
		for(int i = 0; i < p.length; i++)
		{
			vertices[i + 1] = p[i];
		}
		
		vao.storeData(0, vertices, GL15.GL_STATIC_DRAW);
		
		vao.bind(0);
		
		GUIShader.Color().setInput("color", c);
		GUIShader.Color().use();
		
		GL11.glLineWidth(width);
		GL11.glDrawArrays(GL11.GL_LINE_STRIP, 0, vertices.length);
		
		GUIShader.Color().stop();
	}
	
	protected void drawPoints(IColor3Base c, float size, Vector2f start, Vector2f... p)
	{
		Vector2f[] vertices = new Vector2f[1 + p.length];
		vertices[0] = start;
		for(int i = 0; i < p.length; i++)
		{
			vertices[i + 1] = p[i];
		}
		
		vao.storeData(0, vertices, GL15.GL_STATIC_DRAW);
		
		vao.bind(0);
		
		GUIShader.Color().setInput("color", c);
		GUIShader.Color().use();
		
		GL11.glPointSize(size);
		GL11.glDrawArrays(GL11.GL_POINTS, 0, vertices.length);
		
		GUIShader.Color().stop();
	}
	
	protected void drawColoredRect(IColor3Base c, float posX, float posY, float sizeX, float sizeY)
	{
		Vec2f[] vertices = new Vec2f[4];
		
		vertices[0] = new Vector2f(posX + sizeX, posY);		
		vertices[1] = new Vector2f(posX, posY);
		vertices[2] = new Vector2f(posX + sizeX, posY +sizeY);		
		vertices[3] = new Vector2f(posX, posY + sizeY);

		vao.storeData(0, vertices, GL15.GL_STATIC_DRAW);
		
		vao.bind(0);
		
		GUIShader.Color().setInput("color", c);
		GUIShader.Color().use();
		
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		
		GUIShader.Color().stop();
	}
	
	protected void drawText(String text, float posX, float posY, float sizeX, float sizeY, LambdaFont f)
	{
		char[] c = text.toCharArray();

		float inc = sizeX / text.length();
		
		float p = 0.0f;
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		for(char c1 : c)
		{

			if((c1 + "").equals(" "))
			{
				p+=inc;
				continue;
			}
			
			Vector4f v = f.getUVs(c1 + "");
			
			Vector2f[] vertices = new Vector2f[4];
			
			vertices[0] = new Vector2f(posX + p + inc, posY);
			vertices[1] = new Vector2f(posX + p, posY);
			vertices[2] = new Vector2f(posX + p + inc, posY + sizeY);
			vertices[3] = new Vector2f(posX + p, posY + sizeY);	
			
			Vector2f[] uvs = new Vector2f[] {
			new Vector2f(v.z, v.y),
			new Vector2f(v.x, v.y),	
			new Vector2f(v.z, v.a),
			new Vector2f(v.x, v.a),
			};


			
			vao.storeData(0, vertices, GL15.GL_STATIC_DRAW);
			vao.storeData(1, uvs, GL15.GL_STATIC_DRAW);
			
			vao.bind(0, 1);
			
			GUIShader.No().loadTexture(f.getTexture());
			GUIShader.No().use();
			
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
			
			GUIShader.No().stop();
			
			p += inc;
		}
		
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	public boolean mouseIsInsideInclusive(float minX, float minY, float maxX, float maxY)
	{
		float mX = Window.active.getInputManager().getMouseX();
		float mY = Window.active.getInputManager().getMouseY();

		boolean bX = mX >= minX && mX <= maxX;
		boolean bY = mY >= minY && mY <= maxY;
		
		return bX && bY;
	}

	public boolean mouseIsInsideExclusive(float minX, float minY, float maxX, float maxY)
	{
		float mX = Window.active.getInputManager().getMouseX();
		float mY = Window.active.getInputManager().getMouseY();

		boolean bX = mX > minX && mX < maxX;
		boolean bY = mY > minY && mY < maxY;
		
		return bX && bY;
	}
}
