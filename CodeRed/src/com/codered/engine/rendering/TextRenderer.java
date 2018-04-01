package com.codered.engine.rendering;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import com.codered.engine.GLUtils;
import com.codered.engine.managing.VAO;
import com.codered.engine.managing.loader.LambdaFont;
import com.codered.engine.shaders.GUIShaders;

import cmn.utilslib.math.vector.Vector2f;
import cmn.utilslib.math.vector.Vector4f;



public class TextRenderer
{
	
	public static LambdaFont f;
	
	private static VAO vao;
	
	public static void create()
	{
		vao = new VAO();
	}
	
	public static void renderText(String s, Vector2f pos, Vector2f size)
	{
		
		char[] c = s.toCharArray();

		float inc = size.x / s.length();
		
		float p = 0.0f;
		
		for(char c1 : c)
		{

			if((c1 + "").equals(" "))
			{
				p+=inc;
				continue;
			}
			Vector4f v = f.getUVs(c1 + "");			

			
			Vector2f[] vertices = new Vector2f[4];
			
			vertices[0] = new Vector2f(pos.x + p, pos.y);
			vertices[1] = new Vector2f(pos.x + p, pos.y + size.y);	
			vertices[2] = new Vector2f(pos.x + p + inc, pos.y + size.y);
			vertices[3] = new Vector2f(pos.x + p + inc, pos.y);
			

			int[] indices = new int[] {
			0,1,
			1,2,
			2,3,
			3,0
			};
			
			Vector2f[] uvs = new Vector2f[] {
			new Vector2f(v.x, v.y),					
			new Vector2f(v.x,v.a),
			new Vector2f(v.z,v.a),			
			new Vector2f(v.z,v.y),


			};

			vao.storeData(0, vertices, 0, 0, GL15.GL_STATIC_DRAW);
			vao.storeData(1, uvs, 0, 0, GL15.GL_STATIC_DRAW);
			vao.storeIndices(indices, GL15.GL_STATIC_DRAW);
			
			GLUtils.bindVAO(vao, 0, 1);
			
			GUIShaders.No.loadTextureMap(f.getTexture().getId());
			GUIShaders.No.use();
			
			GL11.glDrawElements(GL11.GL_QUADS, indices.length, GL11.GL_UNSIGNED_INT, 0);
			
			GUIShaders.No.stop();
			
			p += inc;
		}
	}
}
