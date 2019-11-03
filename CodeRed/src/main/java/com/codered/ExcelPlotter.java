package com.codered;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelPlotter
{

	private static HashMap<String,List<Double>> values = new HashMap<>();
	
	public static void addValue(String id, double value)
	{
		if(!values.containsKey(id)) values.put(id, new ArrayList<>());
		
		values.get(id).add(value);
	}
	
	public static void plot(OutputStream out)
	{
		Workbook wb = new XSSFWorkbook();
		Sheet sh = wb.createSheet();
		
		Row row;
		Cell cell;
		
		int rowNumber = 0;
		for(String key : values.keySet())
		{
			row = sh.createRow(rowNumber);
			cell = row.createCell(0);
			cell.setCellValue(key);
			for(int i = 0; i < values.get(key).size(); i++)
			{
				cell = row.createCell(i + 1);
				cell.setCellValue(values.get(key).get(i) * 1000);
			}
			rowNumber++;
		}
		
		try
		{
			wb.write(out);
			out.flush();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		try
		{
			wb.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
