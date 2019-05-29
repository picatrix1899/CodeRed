package com.codered.utils;

import java.util.ArrayList;

import org.barghos.core.StringUtils;

public class DebugInfo
{
	private int maxLengthTitle;
	private int maxLengthValue;
	private ArrayList<String> titles = new ArrayList<>();
	private ArrayList<String> values = new ArrayList<>();
	private String lead = "+  ";
	private String trail = "  +";
	private String gap = "        ";
	
	public void print()
	{
		
		System.out.println(StringUtils.padLeft("", "#", lead.length() + maxLengthTitle + gap.length() + maxLengthValue +  + trail.length()));
		
		for(int i = 0; i < titles.size(); i++)
		{
			System.out.println(lead + calcTitle(titles.get(i)) + gap + calcValue(values.get(i)) + trail);
		}
		
		System.out.println(StringUtils.padLeft("", "#", lead.length() + maxLengthTitle + gap.length() + maxLengthValue +  + trail.length()));
		System.out.println();
	}
	
	private String calcValue(String s)
	{
		int length = s.length();
		String r = s;
		if(length < maxLengthValue)
		{
			r = StringUtils.padRight(r, " ", maxLengthValue - length);
		}
		
		return r;
	}
	
	private String calcTitle(String s)
	{
		int length = s.length();
		String r = s;
		if(length < maxLengthTitle)
		{
			r = StringUtils.padLeft(r, " ", maxLengthTitle - length);
		}
		
		return r;
	}
	
	public void add(String title, String value)
	{
		if(title.length() > maxLengthTitle)
		{
			maxLengthTitle = title.length();
		}
		
		titles.add(title);
		
		if(value.length() > maxLengthValue)
		{
			maxLengthValue = value.length();
		}
		
		values.add(value);
	}
}
