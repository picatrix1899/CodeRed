package com.codered.window;

import com.codered.window.WindowHint.GLProfile;

public class WindowHints
{
	private int glVersion_major = 4;
	private int glVersion_minor = 5;
	private boolean glVersionChanged = false;
	
	private GLProfile glProfile = GLProfile.CORE;
	private boolean glProfileChanged = false;
	
	private int samples = 0;
	private boolean samplesChanged = false;
	
	private boolean hasDoubleBuffering = false;
	private boolean doubleBufferingChanged = false;
	
	private boolean isResizable = false;
	private boolean resizableChanged = false;
	
	private boolean isAutoShowWindow = false;
	private boolean autoShowWindowChanged = false;
	
	public WindowHints glVersion(int major, int minor)
	{
		this.glVersion_major = major;
		this.glVersion_minor = minor;
		this.glVersionChanged = true;
		
		return this;
	}
	
	public WindowHints glVersion(String version)
	{
		String[] parts = version.split("\\.");
		
		this.glVersion_major = Integer.parseInt(parts[0]);
		this.glVersion_minor = Integer.parseInt(parts[1]);
		this.glVersionChanged = true;
		
		return this;
	}
	
	public int glVersionMajor()
	{
		return this.glVersion_major;
	}
	
	public int glVersionMinor()
	{
		return this.glVersion_minor;
	}
	
	public boolean hasGlVersionChanged()
	{
		return this.glVersionChanged;
	}
	
	public WindowHints glProfile(GLProfile profile)
	{
		this.glProfile = profile;
		this.glProfileChanged = true;
		
		return this;
	}
	
	public GLProfile glProfile()
	{
		return this.glProfile;
	}
	
	public boolean hasGlProfileChanged()
	{
		return this.glProfileChanged;
	}
	
	public WindowHints samples(int samples)
	{
		this.samples = samples;
		this.samplesChanged = true;
		
		return this;
	}
	
	public int samples()
	{
		return this.samples;
	}
	
	public boolean hasSamplesChanged()
	{
		return this.samplesChanged;
	}
	
	public WindowHints doubleBuffering(boolean doublebuffering)
	{
		this.hasDoubleBuffering = doublebuffering;
		this.doubleBufferingChanged = true;
		
		return this;
	}
	
	public boolean doubleBuffering()
	{
		return this.hasDoubleBuffering;
	}
	
	public boolean hasDoubleBufferingChanged()
	{
		return this.doubleBufferingChanged;
	}
	
	public WindowHints autoShowWindow(boolean auto)
	{
		this.isAutoShowWindow = auto;
		this.autoShowWindowChanged = true;
		
		return this;
	}
	
	public boolean autoShowWindow()
	{
		return this.isAutoShowWindow;
	}
	
	public boolean hasAutoShowWindowChanged()
	{
		return this.autoShowWindowChanged;
	}
	
	public WindowHints resizable(boolean resizable)
	{
		this.isResizable = resizable;
		this.resizableChanged = true;
		
		return this;
	}
	
	public boolean resizable()
	{
		return this.isResizable;
	}
	
	public boolean hasResizableChanged()
	{
		return this.resizableChanged;
	}
}
