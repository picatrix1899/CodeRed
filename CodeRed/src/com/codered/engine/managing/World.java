package com.codered.engine.managing;


import java.util.List;

import com.codered.engine.entities.DynamicEntity;
import com.codered.engine.entities.StaticEntity;
import com.codered.engine.light.Glow;
import com.codered.engine.light.PointLight;
import com.codered.engine.managing.models.TexturedModel;
import com.codered.engine.terrain.Terrain;

import cmn.utilslib.color.colors.api.IColor3Base;
import cmn.utilslib.dmap.dmappings.DMapping2;
import cmn.utilslib.essentials.Auto;
import cmn.utilslib.math.vector.Vector3f;

public abstract class World
{
	private DMapping2<Long, Terrain> staticTerrain = Auto.DMapping2();
	
	private DMapping2<Long, StaticEntity> staticEntities = Auto.DMapping2();
	private DMapping2<Long, DynamicEntity> dynamicEntities = Auto.DMapping2();
	
	private DMapping2<Long, PointLight> staticPointLights = Auto.DMapping2();
	
	private Enviroment enviroment = new Enviroment();
	
	
	
	public abstract void load();
	
	public List<StaticEntity> getStaticEntities()
	{
		return this.staticEntities.b();
	}
	
	public List<DynamicEntity> getDynamicEntities()
	{
		return this.dynamicEntities.b();
	}
	
	public List<Terrain> getStaticTerrains()
	{
		return this.staticTerrain.b();
	}
	
	public List<PointLight> getStaticPointLights()
	{
		return this.staticPointLights.b();
	}
	
	
	public StaticEntity getStaticEntity(long id)
	{
		return this.staticEntities.getFirstBByA(id);
	}
	
	public DynamicEntity getDynamicEntity(long id)
	{
		return this.dynamicEntities.getFirstBByA(id);
	}
	
	public Terrain getStaticTerrain(long id)
	{
		return this.staticTerrain.getFirstBByA(id);
	}
	
	public PointLight getStaticPointLight(long id)
	{
		return this.staticPointLights.getFirstBByA(id);
	}
	
	public Enviroment getEnviroment()
	{
		return this.enviroment;
	}
	
	
	protected StaticEntity addStaticEntity(long id, TexturedModel tmodel, Vector3f pos, float rx, float ry ,float rz, Glow g)
	{
		
		StaticEntity e = new StaticEntity(tmodel, pos, rx, ry, rz);
		e.setGlow(g);
		e.id = id;

		this.staticEntities.add(e.id, e);
		return e;
	}
	
	protected StaticEntity addStaticEntity(long id, String name, Vector3f pos, float rx, float ry ,float rz, Glow g)
	{
		return addStaticEntity(id, ResourceManager.getTexturedModel(name),pos, rx, ry, rz, g);
	}
	
	protected StaticEntity addStaticEntity(long id, TexturedModel tmodel, Vector3f pos, float rx, float ry ,float rz)
	{
		return addStaticEntity(id, tmodel, pos,  rx,  ry , rz, Glow.DEFAULT);
	}
	
	protected StaticEntity addStaticEntity(long id, String name, Vector3f pos, float rx, float ry ,float rz)
	{
		return addStaticEntity(id, ResourceManager.getTexturedModel(name),pos, rx, ry, rz);
	}
	
	protected DynamicEntity addDynamicEntity(long id, TexturedModel tmodel, Vector3f pos, float rx, float ry ,float rz)
	{
		DynamicEntity e = new DynamicEntity(tmodel, pos, rx, ry, rz);
		e.id = id;

		this.dynamicEntities.add(e.id, e);
		return e;
	}
	
	protected DynamicEntity addDynamicEntity(long id, DynamicEntity e)
	{
		e.id = id;

		this.dynamicEntities.add(e.id, e);
		return e;
	}
	
	protected void addStaticTerrain(long id, Terrain t)
	{
		t.id = id;
		
		this.staticTerrain.add(t.id, t);
	}
	
	protected PointLight addStaticPointLight(long id, Vector3f pos, IColor3Base color, float intensity, float constant, float linear, float exponent)
	{
		PointLight light = new PointLight(pos, color, intensity, constant, linear, exponent);
		
		return addStaticPointLight(id, light);
	}
	
	protected PointLight addStaticPointLight(long id, PointLight light)
	{
		light.id = id;
		
		this.staticPointLights.add(light.id, light);
		
		return light;
	}
}
