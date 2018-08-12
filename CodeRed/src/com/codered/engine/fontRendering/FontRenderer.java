package com.codered.engine.fontRendering;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import com.codered.engine.fontMeshCreator.FontType;
import com.codered.engine.fontMeshCreator.GUIText;
import com.codered.engine.shaders.gui.Font_GUIShader;
import com.codered.engine.utils.BindingUtils;
import com.codered.engine.utils.GLUtils;
import com.codered.engine.utils.WindowContextHelper;
import com.codered.engine.window.WindowContext;

public class FontRenderer
{

private WindowContext context;
	
	public FontRenderer()
	{
		this.context = WindowContextHelper.getCurrentContext();
	}

	public void render(Map<FontType, List<GUIText>> texts)
	{
		GLUtils.blend(true);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GLUtils.depthTest(false);
		
		Font_GUIShader shader = context.getShader(Font_GUIShader.class);
		
		for(FontType font : texts.keySet())
		{
			shader.loadAtlas(font.getTexture().getId());
			
			for(GUIText text : texts.get(font))
			{
				renderText(text, shader);
			}
		}
		
		GLUtils.blend(false);
		GLUtils.depthTest(true);
	}
	
	private void renderText(GUIText text, Font_GUIShader shader)
	{
		BindingUtils.bindVAO(text.getVAO(), 0, 1);
		
		shader.loadColor(text.getColor());
		shader.use();
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, text.getVertexCount());
		shader.stop();
	}

}
