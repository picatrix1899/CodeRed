package com.codered.resource.manager;

import com.codered.resource.ResourceRequestBlock;

public interface ResourceManager
{
	void update();
	
	void load(ResourceRequestBlock block);
	
	void cleanup();
}
