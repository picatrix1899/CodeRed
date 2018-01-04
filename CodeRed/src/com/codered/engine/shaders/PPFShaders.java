package com.codered.engine.shaders;

import com.codered.engine.shaders.postprocess.filter.Blend_PPFilter;
import com.codered.engine.shaders.postprocess.filter.BlurH_PPFilter;
import com.codered.engine.shaders.postprocess.filter.BlurV_PPFilter;
import com.codered.engine.shaders.postprocess.filter.Brightness_PPFilter;
import com.codered.engine.shaders.postprocess.filter.Contrast_PPFilter;
import com.codered.engine.shaders.postprocess.filter.DepthMap_PPFilter;
import com.codered.engine.shaders.postprocess.filter.DepthTest_PPFilter;
import com.codered.engine.shaders.postprocess.filter.HDR_PPFilter;
import com.codered.engine.shaders.postprocess.filter.Invert_PPFilter;
import com.codered.engine.shaders.postprocess.filter.No_PPFilter;
import com.codered.engine.shaders.postprocess.filter.RadialBlur_PPFilter;

public class PPFShaders
{
	public static final Contrast_PPFilter Contrast = new Contrast_PPFilter();
	public static final Blend_PPFilter Blend = new Blend_PPFilter();
	public static final BlurH_PPFilter BlurH = new BlurH_PPFilter();
	public static final BlurV_PPFilter BlurV = new BlurV_PPFilter();
	public static final Brightness_PPFilter Brightness = new Brightness_PPFilter();
	public static final DepthTest_PPFilter DepthTest = new DepthTest_PPFilter();
	public static final DepthMap_PPFilter DepthMap = new DepthMap_PPFilter();
	public static final HDR_PPFilter HDR = new HDR_PPFilter();
	public static final Invert_PPFilter Invert = new Invert_PPFilter();
	public static final No_PPFilter No = new No_PPFilter();
	public static final RadialBlur_PPFilter RadialBlur = new RadialBlur_PPFilter();
}
