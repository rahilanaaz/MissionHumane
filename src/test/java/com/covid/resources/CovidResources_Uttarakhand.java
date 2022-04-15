package com.covid.resources;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import com.utilities.ExcelUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CovidResources_Uttarakhand {
	
	public static WebDriver driver;
	public static LinkedHashMap<String, ArrayList<String>> resourcesMap = new LinkedHashMap<String, ArrayList<String>>();

	public static void main(String[] args) throws IOException {
		
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://covid19.uk.gov.in/bedssummary.aspx");
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		
		WebElement districtDropDown = driver.findElement(By.xpath("//select[@id='ContentPlaceHolder1_ddndistView']"));
		
		Select distDropDown = new Select(districtDropDown);
		
		int dropDownSize = distDropDown.getOptions().size();
		
		for(int i=1; i<dropDownSize;i++) {
			
			try {
				
	        distDropDown.selectByIndex(i);
	        
			}
			catch(StaleElementReferenceException e) {
				
				districtDropDown = driver.findElement(By.xpath("//select[@id='ContentPlaceHolder1_ddndistView']"));
				Select distDropDown1 = new Select(districtDropDown);
				distDropDown1.selectByIndex(i);
				
			}
			
	        int tableRows = driver.findElements(By.xpath("//table//tbody/tr")).size();
	        
	        for(int j=1; j<=tableRows; j++){
	        	
	        String district = driver.findElement(By.xpath("//table//tbody/tr["+j+"]/td[1]/span[1]")).getText();
	        String hospital = driver.findElement(By.xpath("//table//tbody/tr["+j+"]/td[2]/span[1]")).getText();
	        String nodalOfficerName = driver.findElement(By.xpath("//table//tbody/tr["+j+"]/td[3]/span[1]")).getText();
	        String nodalOfficerContact = driver.findElement(By.xpath("//table//tbody/tr["+j+"]/td[3]/span[2]")).getText();
	        String bedsWithoutO2 = driver.findElement(By.xpath("//table//tbody/tr["+j+"]/td[4]/span[1]")).getText();
	        String bedsWithO2 = driver.findElement(By.xpath("//table//tbody/tr["+j+"]/td[5]/span[1]")).getText();
	        String ICUBeds = driver.findElement(By.xpath("//table//tbody/tr["+j+"]/td[6]/span[1]")).getText();
	        String ventilators = driver.findElement(By.xpath("//table//tbody/tr["+j+"]/td[7]/span[1]")).getText();
	        String lastUpdated = driver.findElement(By.xpath("//table//tbody/tr["+j+"]/td[8]/span[1]")).getText();
	        
	        ArrayList<String> list = new ArrayList<String>();
	        
	        list.add(district);
	        list.add(nodalOfficerName);
	        list.add(nodalOfficerContact);
	        list.add(bedsWithoutO2);
	        list.add(bedsWithO2);
	        list.add(ICUBeds);
	        list.add(ventilators);
	        list.add(lastUpdated);
	        
	        resourcesMap.put(hospital,list);
	    
	        }
	        
		}
		
			
		System.out.println(resourcesMap);
		
		String filePath = "C:\\Users\\R\\eclipse-workspace\\MissionHumane\\ExcelData\\coviddata.xlsx";
		ExcelUtil util = new ExcelUtil(filePath);
		util.setCellData("data", 0, 0, "Hospital Name");
		util.setCellData("data", 0, 1, "District");
		util.setCellData("data", 0, 2, "Officer Name");
		util.setCellData("data", 0, 3, "Contact");
		util.setCellData("data", 0, 4, "Beds Without O2");
		util.setCellData("data", 0, 5, "Beds With O2");
		util.setCellData("data", 0, 6, "ICU Beds");
		util.setCellData("data", 0, 7, "Ventilators");
		util.setCellData("data", 0, 8, "Last Updated");
		
		int i =1;
		
		for(Entry e: resourcesMap.entrySet()) {
			
			i++;
			util.setCellData("data", i, 0, (String)e.getKey());
			ArrayList<String> value = (ArrayList<String>)e.getValue();
			util.setCellData("data", i, 1, value.get(0));
			util.setCellData("data", i, 2, value.get(1));
			util.setCellData("data", i, 3, value.get(2));
			util.setCellData("data", i, 4, value.get(3));
			util.setCellData("data", i, 5, value.get(4));
			util.setCellData("data", i, 6, value.get(5));
			util.setCellData("data", i, 7, value.get(6));
			util.setCellData("data", i, 8, value.get(7));
			
		
		}
		
		driver.close();

		
		

	}

}
