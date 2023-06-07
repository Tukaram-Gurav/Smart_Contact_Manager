package com.helper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.Entities.Contact;

public class XMlHelper
{
	public static String[] HEADERS=
	{
		"Contact Name",
			"Email",
			"Contact Number"
	};
	public static String SHEET_NAME="contact-list";
	
	
	public static ByteArrayInputStream xldata(List<Contact> list) 
	{
		Workbook workbook=new XSSFWorkbook();
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		try
		{
	
		Sheet sheet=workbook.createSheet(SHEET_NAME);
		//HEADER ROW
		
		Row row=sheet.createRow(0);
		for(int i=0;i<HEADERS.length;i++)
		{
			Cell createCell = row.createCell(i);
			createCell.setCellValue(HEADERS[i]);
			
		}
		
		int rowIndex=1;
		for(Contact c:list)
		{
			Row row1=sheet.createRow(rowIndex);
			rowIndex++;
			row1.createCell(0).setCellValue(c.getName());
			row1.createCell(1).setCellValue(c.getEmail());
			row1.createCell(2).setCellValue(c.getPhone());
			
			
		}
		workbook.write(out);
		return new ByteArrayInputStream(out.toByteArray());
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.out.println("Error Creating xal file");
			return null;
			
		}
		finally
		{
			try {
				workbook.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}

}
