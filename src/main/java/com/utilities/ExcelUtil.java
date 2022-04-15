package com.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	
	public FileInputStream fi;
    public FileOutputStream fo;
    public XSSFWorkbook workbook;
    public XSSFSheet sheet;
    public XSSFRow row;
    public XSSFCell cell;
    String path;
	
	public ExcelUtil(String path){
		
		this.path=path;
	}
		
		public void setCellData(String sheetName,int rownum,int colnum,String data) throws IOException {
			
			File xfile = new File(path);
			if(! xfile.exists())
			{
				workbook = new XSSFWorkbook();
				fo = new FileOutputStream(path);
				workbook.write(fo);
			}
			
			fi = new FileInputStream(path);
			workbook = new XSSFWorkbook(fi);
			if(workbook.getSheetIndex(sheetName)==-1)
				workbook.createSheet(sheetName);
			sheet=workbook.getSheet(sheetName);
			
			if(sheet.getRow(rownum)==null)
				sheet.createRow(rownum);
			row=sheet.getRow(rownum);
			
			cell=row.createCell(colnum);
			cell.setCellValue(data);
			fo=new FileOutputStream(path);
			workbook.write(fo);
			workbook.close();
			fi.close();
			fo.close();
		
			
	}
}
	


