package com.codered.demo;

import com.codered.engine.EngineBootstrap;

public class Main
{

	public static void main(String[] args)
	{
		EngineBootstrap boot = new EngineBootstrap(new DemoGame());
		boot.boot();
	}
}
