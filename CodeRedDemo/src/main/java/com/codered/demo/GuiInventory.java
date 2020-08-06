package com.codered.demo;

import org.lwjgl.glfw.GLFW;

import com.codered.engine.EngineRegistry;
import com.codered.gui.GUIElement;
import com.codered.gui.GUIWindow;
import com.codered.gui.elements.GUIEButton;
import com.codered.gui.font.FontType;
import com.codered.input.InputConfiguration;
import com.codered.rendering.texture.Texture;
import com.codered.utils.IGuiRenderer;

public class GuiInventory extends GUIWindow
{

	public GUIEButton button;
	
	public InputConfiguration inventoryInput;
	
	public Routine1 main;
	
	public FontType font;

	public boolean entermode;
	
	public int pic;
	
	public GuiInventory(Routine1 parent, IGuiRenderer renderer, FontType font)
	{
		super(renderer);
		this.main = parent;
		this.font = font;
		inventoryInput = new InputConfiguration();
		inventoryInput.registerKey(GLFW.GLFW_KEY_TAB);
		inventoryInput.registerKey(GLFW.GLFW_KEY_ESCAPE);
		inventoryInput.registerMouseButton(0);
		
		this.button = new GUIEButton(1, this, 0, 0, 60, 82, EngineRegistry.getResourceRegistry().get("res/materials/gray_rsquare.png", Texture.class), this.renderer);
		this.button.setText(GLFW.glfwGetKeyName(KeyBindings.forward, 0), 20, this.font, true, true);
		addElement(this.button);
	}
	
	public void setPic(int id)
	{
		this.pic = id;
	}
	
	public void update()
	{
		//if(this.context.getInputManager().isKeyPressed(GLFW.GLFW_KEY_TAB)) close();
		//if(this.context.getInputManager().isMouseButtonPressed(0)) this.button.onClick();
		
//		if(entermode)
//		{
//			int lastKey = this.context.getInputManager().lastFreeKey;
//			
//			if(lastKey != 0)
//			{
//				KeyBindings.main.unregisterKey(KeyBindings.forward);
//				KeyBindings.forward = lastKey;
//				KeyBindings.main.registerKey(KeyBindings.forward);
//				this.button.setText(GLFW.glfwGetKeyName(KeyBindings.forward, 0), 20, this.font, true, true);
//				this.context.getInputManager().setFreemode(false);
//				this.entermode = false;
//			}
//		}

		
		super.update();
	}
	
	public void render()
	{
		//drawTexturedRect(EngineRegistry.getResourceRegistry().get("res/materials/inventory-background.png", Texture.class), 0, 0, this.context.getWindow().getWidth(), this.context.getWindow().getHeight());
		
		//drawTexturedRect(this.pic, 0, 0, this.context.getWindow().getWidth(), this.context.getWindow().getHeight());
		
		super.render();
	}
	
	public void open()
	{
		//this.context.getInputManager().pushInputConfiguration(inventoryInput);
	}
	
	public void close()
	{
		DemoGame.getInstance().showInventory = false;
		//this.context.getInputManager().popInputConfiguration();
	}
	
	public boolean allowWorldProcessing()
	{
		return true;
	}

	public void response(GUIElement e)
	{
		if(e.getID() == 1)
		{
			this.button.setText("-", 20, this.font, true, true);
			this.context.getInputManager().setFreemode(true);
			this.entermode = true;
		}
	}

	public int getMinX()
	{
		return 0;
	}

	public int getMaxX()
	{
		return this.context.getWindow().getWidth();
	}

	public int getMinY()
	{
		return 0;
	}

	public int getMaxY()
	{
		return this.context.getWindow().getHeight();
	}

}
