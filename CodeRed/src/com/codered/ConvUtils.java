package com.codered;

public class ConvUtils
{
	public static org.barghos.core.api.tuple.ITup3R ITup3R_new_old(org.barghos.core.tuple.tuple3.api.ITup3R in)
	{
		return new org.barghos.core.tuple.Tup3f(in.getUniX(), in.getUniY(), in.getUniZ());
	}
}
