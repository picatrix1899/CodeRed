package com.codered.managing;

import com.codered.light.AmbientLight;
import com.codered.light.DirectionalLight;

import cmn.utilslib.color.colors.LDRColor3;
import cmn.utilslib.math.vector.Vector3f;

public class Enviroment
{
	public DirectionalLight sun = new DirectionalLight(new LDRColor3(128, 102, 0), 0.5f, new Vector3f(-1.0f, -1.0f, 0.0f));
	public AmbientLight ambient = new AmbientLight(new LDRColor3(255,76,76), 0.5f);
}
