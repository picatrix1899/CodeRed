package com.codered.demo;

import org.barghos.math.vector.QuatPool;
import org.barghos.math.vector.Vec3fPool;

import com.codered.engine.EngineBootstrap;
import com.codered.utils.GLCommon;

public class Main
{

	public static void main(String[] args)
	{
		Vec3fPool.ensure(16);
		QuatPool.ensure(16);
		
		GLCommon.isLeakDetectionEnabled(true);
		
		EngineBootstrap boot = new EngineBootstrap(new DemoGame());
		boot.boot();
	}
}
