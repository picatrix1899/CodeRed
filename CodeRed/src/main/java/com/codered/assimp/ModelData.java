package com.codered.assimp;

import java.util.ArrayList;
import java.util.List;

public class ModelData
{
	public List<MeshData> meshes = new ArrayList<>();
	public List<MaterialData> materials = new ArrayList<>();
	public ModelNodeData rootNode;
}
