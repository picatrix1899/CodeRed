package com.codered.demo;

import com.codered.engine.managing.World;

public class Session
{
	private Player p;
	private World w;
	
	private static Session session = new Session();
	
	public static Session get()
	{
		return session;
	}
	
	private Session()
	{
		session = this;
	}
	
	public void setPlayer(Player p)
	{
		this.p = p;
	}
	
	public void setWorld(World w)
	{
		this.w = w;
		this.w.load();
	}
	
	public Player getPlayer()
	{
		return this.p;
	}
	
	public World getWorld()
	{
		return this.w;
	}
}
