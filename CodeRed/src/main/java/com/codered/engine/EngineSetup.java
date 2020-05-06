package com.codered.engine;

import com.codered.resource.loader.DefaultResourceLoadingProcessFactory;
import com.codered.resource.loader.HazeLoader;
import com.codered.resource.loader.IResourceLoader;
import com.codered.resource.loader.IResourceLoadingProcessFactory;

public class EngineSetup
{
	public TickRoutine mainTickRoutine = new FixedTimestepTickRoutine();
	public TickRoutine resourceTickRoutine = new FixedTimestepTickRoutine();
	public int fpscap = 30;
	
	public IResourceLoader resourceLoader = new HazeLoader();
	public IResourceLoadingProcessFactory resourceLoadingProcessFactory = new DefaultResourceLoadingProcessFactory();
}
