package com.codered.managing.models;

import java.util.ArrayList;
import java.util.List;

import org.barghos.math.geometry.ConvexTriangleMesh3f;
import org.barghos.math.geometry.Triangle3f;

import com.codered.ConvUtils;
import com.codered.engine.EngineRegistry;
import com.codered.material.Material;

import cmn.utilslib.math.geometry.ConcaveTriangleMesh3f;

public class TexturedModel
{
	private ConvexTriangleMesh3f pysModel;
	private ConcaveTriangleMesh3f pModel;
	private String model;
	private String mat;
	
	public TexturedModel(String model, String texture)
	{
		this.model = model;
		this.mat = texture;
		
		Mesh mesh = EngineRegistry.getCurrentWindowContext().getDRM().getStaticMesh(this.model);
		
		this.pysModel = new ConvexTriangleMesh3f(mesh.triangles);
		
		List<cmn.utilslib.math.geometry.Triangle3f> tr = new ArrayList<>();
		
		for(Triangle3f t : mesh.triangles)
		{
			tr.add(new cmn.utilslib.math.geometry.Triangle3f(ConvUtils.bpoint3fToUPoint3f(t.getP1(null)), ConvUtils.bpoint3fToUPoint3f(t.getP2(null)), ConvUtils.bpoint3fToUPoint3f(t.getP3(null))));
		}
		
		this.pModel = new ConcaveTriangleMesh3f(tr);
	}
	
	public ConvexTriangleMesh3f getPhysicalMesh() { return this.pysModel; }
	public ConcaveTriangleMesh3f getPhysMesh() {return this.pModel; }
	
	public Mesh getModel() { return EngineRegistry.getCurrentWindowContext().getDRM().getStaticMesh(this.model); }

	public Material getMaterial() { return EngineRegistry.getCurrentWindowContext().getDRM().getMaterial(this.mat); }
	
}
