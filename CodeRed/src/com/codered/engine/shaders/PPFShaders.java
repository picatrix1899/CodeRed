package com.codered.engine.shaders;

import com.codered.engine.shaders.postprocess.filter.Blend_PPFilter;
import com.codered.engine.shaders.postprocess.filter.BlurH_PPFilter;
import com.codered.engine.shaders.postprocess.filter.BlurV_PPFilter;
import com.codered.engine.shaders.postprocess.filter.Brightness_PPFilter;
import com.codered.engine.shaders.postprocess.filter.ContrastMS_PPFilter;
import com.codered.engine.shaders.postprocess.filter.Contrast_PPFilter;
import com.codered.engine.shaders.postprocess.filter.DepthMap_PPFilter;
import com.codered.engine.shaders.postprocess.filter.DepthTestMS_PPFilter;
import com.codered.engine.shaders.postprocess.filter.DepthTest_PPFilter;
import com.codered.engine.shaders.postprocess.filter.HDR_PPFilter;
import com.codered.engine.shaders.postprocess.filter.Invert_PPFilter;
import com.codered.engine.shaders.postprocess.filter.No_PPFilter;
import com.codered.engine.shaders.postprocess.filter.RadialBlur_PPFilter;

public class PPFShaders
{
	public Contrast_PPFilter Contrast = new Contrast_PPFilter();
	public ContrastMS_PPFilter ContrastMS = new ContrastMS_PPFilter();
	public Blend_PPFilter Blend = new Blend_PPFilter();
	public BlurH_PPFilter BlurH = new BlurH_PPFilter();
	public BlurV_PPFilter BlurV = new BlurV_PPFilter();
	public Brightness_PPFilter Brightness = new Brightness_PPFilter();
	public DepthTest_PPFilter DepthTest = new DepthTest_PPFilter();
	public DepthTestMS_PPFilter DepthTestMS = new DepthTestMS_PPFilter();
	public DepthMap_PPFilter DepthMap = new DepthMap_PPFilter();
	public HDR_PPFilter HDR = new HDR_PPFilter();
	public Invert_PPFilter Invert = new Invert_PPFilter();
	public No_PPFilter No = new No_PPFilter();
	public RadialBlur_PPFilter RadialBlur = new RadialBlur_PPFilter();
}
