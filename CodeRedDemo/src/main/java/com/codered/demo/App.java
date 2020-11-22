package com.codered.demo;

import org.barghos.core.debug.Debug;
import org.barghos.math.quat.pool.QuatfPool;
import org.barghos.math.vec3.pool.Vec3fPool;

import com.codered.engine.EngineBootstrap;
import com.codered.utils.GLCommon;

public class App
{

	public static void main(String[] args)
	{
		Debug.DEBUG_MODE = true;
		Debug.PRINT_STACK_ELEMENT = true;
		
		Vec3fPool.ensure(16);
		QuatfPool.ensure(16);
		
		GLCommon.isLeakDetectionEnabled(false);
		
		EngineBootstrap boot = new EngineBootstrap(new DemoGame());
		boot.boot();
	}
}
