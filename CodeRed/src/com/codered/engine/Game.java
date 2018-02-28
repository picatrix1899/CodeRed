package com.codered.engine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Consumer;

import com.codered.engine.managing.ResourcePath;
import com.codered.engine.managing.ResourcePath.ResourceDestination;
import com.codered.engine.shader.MalformedShaderException;
import com.codered.engine.shader.ShaderNotFoundException;
import com.codered.engine.shader.ShaderPartList;
import com.codered.engine.shader.ShaderParts;

import cmn.utilslib.essentials.Auto;

public abstract class Game
{
	
	private GameRoutine routine;
	private ArgumentInterpreter argInterpreter;
	
	public Game()
	{
		this.routine = new GameRoutine(this);
		this.argInterpreter = new ArgumentInterpreter();
	}
	
	public void setArguments(String[] args)
	{
		this.argInterpreter.interpret(args);
	}
	
	public ArgumentInterpreter getArgInterpreter()
	{
		return this.argInterpreter;
	}
	
	public void start()
	{
		this.routine.start();
	}
	
	public void stop()
	{
		this.routine.stop();
	}
	
	public abstract void release();
	
	public abstract void preInit();
	
	public abstract void init();
	
	public abstract void update();
	
	public abstract void render();
	
	public void initShaderParts()
	{
		try
		{
			ResourcePath path = new ResourcePath();
			path.dest(ResourceDestination.EMBEDED);
			path.src(Game.class);
			
			path.base("/resources/shaders/object/simple/");
			loadDefaultEmbededShader(path, "o_ambientLight");
			loadDefaultEmbededShader(path, "o_colored");
			loadDefaultEmbededShader(path, "o_directionalLight");
			loadDefaultEmbededShader(path, "o_directionalLight_N");
			loadDefaultEmbededShader(path, "o_glow");
			loadDefaultEmbededShader(path, "o_no");
			loadDefaultEmbededShader(path, "o_pointLight_N");
			
			path.base("/resources/shaders/terrain/simple/");
			loadDefaultEmbededShader(path, "t_ambientLight");
			loadDefaultEmbededShader(path, "t_directionalLight");
			loadDefaultEmbededShader(path, "t_directionalLight_N");
			loadDefaultEmbededShader(path, "t_no");
			loadDefaultEmbededShader(path, "t_pointLight_N");
			
			path.base("/resources/shaders/ppf/");
			loadDefaultEmbededShader(path, "ppf_blend");
			
			ShaderParts.builtIn().loadFragmentShader("ppf_blur", path.file("ppf_blur").extension(".fs"));
			ShaderParts.builtIn().loadVertexShader("ppf_blurHorizontal", path.file("ppf_blurHorizontal").extension(".vs"));
			ShaderParts.builtIn().loadVertexShader("ppf_blurVertical", path.file("ppf_blurVertical").extension(".vs"));
			
			loadDefaultEmbededShader(path, "ppf_brightness");
			loadDefaultEmbededShader(path, "ppf_contrast");
			loadDefaultEmbededShader(path, "ppf_contrastMS");
			loadDefaultEmbededShader(path, "ppf_depthMap");
			loadDefaultEmbededShader(path, "ppf_depthTest");
			loadDefaultEmbededShader(path, "ppf_depthTestMS");
			loadDefaultEmbededShader(path, "ppf_hdr");
			loadDefaultEmbededShader(path, "ppf_invert");
			loadDefaultEmbededShader(path, "ppf_no");
			loadDefaultEmbededShader(path, "ppf_radialBlur");
			
			path.base("/resources/shaders/gui/");
			loadDefaultEmbededShader(path, "gui_color");
			loadDefaultEmbededShader(path, "gui_no");
			loadDefaultEmbededShader(path, "gui_texture");
			
		}
		catch(MalformedShaderException | ShaderNotFoundException e)
		{
			e.printStackTrace();
		}

	}
	
	private void loadDefaultEmbededShader(ResourcePath res, String vfs) throws ShaderNotFoundException, MalformedShaderException 
	{
		res.file(vfs);
		
		ShaderPartList sh = ShaderParts.builtIn();
		
		sh.loadVertexShader(vfs, res.extension(".vs"));
		sh.loadFragmentShader(vfs, res.extension(".fs"));
	}
	
	public final class ArgumentInterpreter
	{
		
		private HashMap<String, Argument> interpreter = Auto.HashMap();
		
		public void addArgument(String args, Argument destination)
		{
			this.interpreter.put(args.toLowerCase(), destination);
		}
		
		protected void interpret(String[] args)
		{
			int index = 0;
			
			String currentArg = "";
			String[] values = new String[0];
			
			while(index < args.length)
			{
				if(args[index].startsWith("-"))
				{	
					currentArg = args[index].substring(1, args[index].length()).toLowerCase();
					
					if(this.interpreter.containsKey(currentArg))
					{
						for(int i = index + 1; i < args.length; i++)
						{
							if(args[i].startsWith("-"))
							{
								if(i > index + 1)
								{
									values = Arrays.copyOfRange(args, index + 1, i);
									index = i - 1;
								}
								else
								{
									values = new String[0];
								}
								break;
							}
							else if(i == args.length - 1)
							{
								values = Arrays.copyOfRange(args, index + 1, i + 1);
								index = i;
							}
						}
						
						this.interpreter.get(currentArg).accept(values);
						values = new String[0];
					}
				}
				
				index++;
			}
		}
	}
	
	public interface Argument extends Consumer<String[]> { }
}
