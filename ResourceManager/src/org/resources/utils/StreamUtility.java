package org.resources.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class StreamUtility
{
	
	public static InputStream getStreamForResource(ResourcePath path) throws Exception
	{
		File f = new File(path.path);
		
		return new FileInputStream(f);
	}
	
}
