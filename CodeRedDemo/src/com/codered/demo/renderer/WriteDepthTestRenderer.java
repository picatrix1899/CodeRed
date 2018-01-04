package com.codered.demo.renderer;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.codered.demo.Session;
import com.codered.engine.GLUtils;
import com.codered.engine.entities.Camera;
import com.codered.engine.entities.StaticEntity;
import com.codered.engine.managing.FBO;
import com.codered.engine.managing.MSFBO;
import com.codered.engine.managing.UnityScreen;
import com.codered.engine.managing.Window;
import com.codered.engine.managing.World;
import com.codered.engine.rendering.CustomRenderer;
import com.codered.engine.rendering.MasterRenderer;
import com.codered.engine.rendering.ppf.PPFilter;
import com.codered.engine.shaders.DBGShaders;
import com.codered.engine.shaders.SOShaders;
import com.codered.engine.shaders.debug.DebugShader;
import com.codered.engine.shaders.object.SimpleObjectShader;
import com.codered.engine.shaders.terrain.SimpleTerrainShader;
import com.codered.engine.terrain.Terrain;

import cmn.utilslib.math.matrix.Matrix4f;
import cmn.utilslib.math.vector.api.Vec3fBase;

public class WriteDepthTestRenderer implements CustomRenderer
{
	public static WriteDepthTestRenderer instance = new WriteDepthTestRenderer();

	
	private MasterRenderer master;	
	private Camera camera;
	private World world;	
	private Matrix4f T_projection;
	
	private int objFBOId;
	private int objFBO_ColorAtt0;
	private int objFBO_DepthAtt;
	private int resFBOId;
	private int resFBO_ColorAtt0;
	private int resFBO_DepthAtt;
	
	private int width;
	private int height;
	
	private FBO fbo1;
	private FBO fbo2;
	
	public void init(MasterRenderer master)
	{
		this.master = master;
		
		this.width = Window.active.WIDTH;
		this.height = Window.active.HEIGHT;
		
		// First FBO for Object Rendering
		objFBOId = GL30.glGenFramebuffers();
		objFBO_ColorAtt0 = GL11.glGenTextures();
		objFBO_DepthAtt = GL11.glGenTextures();
		
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, objFBOId);
		GL20.glDrawBuffers(GL30.GL_COLOR_ATTACHMENT0);
		
		// Color Attachment 0
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, objFBO_ColorAtt0);

		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0,  GL30.GL_RGBA16F, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);	
			
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL11.GL_TEXTURE_2D, objFBO_ColorAtt0, 0);
		
		// Depth Texture
		objFBO_DepthAtt = GL11.glGenTextures();
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, objFBO_DepthAtt);
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL14.GL_DEPTH_COMPONENT24,  width, height, 0, GL11.GL_DEPTH_COMPONENT, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);	
		
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL11.GL_TEXTURE_2D, objFBO_DepthAtt, 0);
		
		
		
		// Second FBO for Resulting Depth
		resFBOId = GL30.glGenFramebuffers();
		resFBO_ColorAtt0 = GL11.glGenTextures();
		resFBO_DepthAtt = GL11.glGenTextures();
		
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, resFBOId);
		GL20.glDrawBuffers(GL30.GL_COLOR_ATTACHMENT0);
		
		// Color Attachment 0
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, resFBO_ColorAtt0);

		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0,  GL30.GL_RGBA16F, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);	
			
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL11.GL_TEXTURE_2D, resFBO_ColorAtt0, 0);
		
		// Depth Texture
		resFBO_DepthAtt = GL11.glGenTextures();
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, resFBO_DepthAtt);
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL14.GL_DEPTH_COMPONENT24, width, height, 0, GL11.GL_DEPTH_COMPONENT, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);	
		
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL11.GL_TEXTURE_2D, resFBO_DepthAtt, 0);
		
		
	}
	
	public void prepare()
	{
		this.T_projection = Window.active.projectionMatrix;
		this.camera = Session.get().getPlayer().getCamera();
		this.world = Session.get().getWorld();

		StaticEntity e = this.world.getStaticEntity(0);
		
		e.rotateYaw(0.2f);
		
		

		
	}
	
	
	public void render()
	{
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, this.objFBOId);
		GL20.glDrawBuffers(GL30.GL_COLOR_ATTACHMENT0);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		//GL11.glEnable(GL11.GL_CULL_FACE);
		//GL11.glCullFace(GL11.GL_BACK);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		renderObject(camera, world.getStaticEntity(0), SOShaders.No);
		
		
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, this.resFBOId);
		GL20.glDrawBuffers(GL30.GL_COLOR_ATTACHMENT0);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		DBGShaders.DepthWrite.setInput("frame", this.objFBO_DepthAtt);
		DBGShaders.DepthWrite.use();
		{
			UnityScreen.quad.getVAO().bind(0);
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
			
		}
		DBGShaders.DepthWrite.stop();
		
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
		GL20.glDrawBuffers(GL30.GL_COLOR_ATTACHMENT0);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		DBGShaders.DepthWrite.setInput("frame", this.resFBO_DepthAtt);
		DBGShaders.DepthWrite.use();
		{
			UnityScreen.quad.getVAO().bind(0);
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
			
		}
		DBGShaders.DepthWrite.stop();
	}
	
	
	
	public void renderObject(Camera c, StaticEntity e, SimpleObjectShader oShader)
	{
			e.getModel().getModel().getVAO().bind(0, 1, 2, 3);
			
			oShader.setInput("material", e.getModel().getTexture());
			
			oShader.loadModelMatrix(e.getTransformationMatrix());
			oShader.loadProjectionMatrix(this.T_projection);
			oShader.loadCamera(c);
			
			oShader.use();			
			{
				GL11.glDrawElements(GL11.GL_TRIANGLES, e.getModel().getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			oShader.stop();	
	}
}
