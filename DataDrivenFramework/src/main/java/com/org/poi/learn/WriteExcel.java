package com.org.poi.learn;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// WorkBook --> Sheet --> Rows --> Cells

public class WriteExcel {

	public static void main(String[] args) throws IOException {
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("EmpInfo");
		
		Object empdata[][] = {
				{"EmpID","Name","Job"},
				{"101","Saravanan","Tester"},
				{"102","Sam","Doctor"},
				{"103","David","Teacher"}
		};

		int rows = empdata.length;
		int cols = empdata[0].length;
		
		System.out.println("Number of Rows: " +rows);
		System.out.println("Number of Rows: " +cols);
		
		for(int r= 0; r<rows; r++) {
			XSSFRow row = sheet.createRow(r);
			
			for(int c=0; c<cols; c++) {
				XSSFCell cell = row.createCell(c);
				Object value = empdata[r][c];
				
				if (value instanceof String)
					cell.setCellValue((String)value);
				if (value instanceof Integer)
					cell.setCellValue((Integer)value);
				if (value instanceof Boolean)
					cell.setCellValue((Boolean)value);		
			}
		}
		
		String writepath = ".\\datafiles\\employeeInfo.xlsx";
		FileOutputStream fo = new FileOutputStream(writepath);
		workbook.write(fo);		
		fo.close();
		
		System.out.println("Employee.xls file written successfully");
	}

}
