package com.codered.demo;

import java.util.ArrayList;
import java.util.List;


import com.codered.entities.StaticEntity;

public class OctTree
{
	private List<StaticEntity> entity = new ArrayList<>();
	private OctTree[] subTrees = new OctTree[8];
	
	private float width;
	private float height;
	
}
