package com.resources;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.utilities.ExcelUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Misc {
	
	public static WebDriver driver;
	
	public static LinkedHashMap<String, ArrayList<String>> hashmap = new LinkedHashMap<String, ArrayList<String>>();
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://pune.covidsafe.in/");
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		
		int rowCount = driver.findElements(By.xpath("//tbody/tr")).size();
		
		WebElement loadBtn = driver.findElement(By.xpath("//*[@id=\"root\"]//div/div[2]/div[3]/div/button"));
		
		for(int i=1; i<=rowCount; i+=2) {
			
			//System.out.println("The value of i :"+i);
			int j=i+1;
            String hospital = driver.findElement(By.xpath("//tbody/tr["+i+"]/td/div//strong")).getText();
            String bedsWithoutOxygen = driver.findElement(By.xpath("//tbody/tr["+i+"]/td[2]/span")).getText();
		    String bedsWithOxygen = driver.findElement(By.xpath("//tbody/tr["+i+"]/td[3]/span")).getText();
		    String icuWithoutVentilator = driver.findElement(By.xpath("//tbody/tr["+i+"]/td[4]/span")).getText();
		    String icuWithVentilator = driver.findElement(By.xpath("//tbody/tr["+i+"]/td[5]/span")).getText();
		    WebElement expandBtn = driver.findElement(By.xpath("//tbody/tr["+i+"]/td/div//strong"));
		
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click()", expandBtn);
		
		rowCount = rowCount+1;
		
		String phone = driver.findElement(By.xpath("//table/tbody/tr["+j+"]/td/p[2]/span")).getText();
		String contct = phone.substring(5);
		String contact = contct.replace(":", "");
				
		ArrayList<String> list = new ArrayList<String>();
		
		list.add(bedsWithoutOxygen);
		list.add(bedsWithOxygen);
		list.add(icuWithoutVentilator);
		list.add(icuWithVentilator);
		list.add(contact);
		
		hashmap.put(hospital, list);
		System.out.println(hashmap);
	    List<WebElement> Btn = driver.findElements(By.xpath("//*[@id=\"root\"]//div/div[2]/div[3]/div/button"));
		if(i==rowCount-1 && Btn.size()>0) {
			
			try {
				
				
			    loadBtn.click();
			
			}
			
			catch(Exception e) {
			
			JavascriptExecutor js1 = (JavascriptExecutor)driver;
			js1.executeScript("arguments[0].click();", driver.findElement(By.xpath("//*[@id=\"root\"]//div/div[2]/div[3]/div/button")));
			}
			rowCount = driver.findElements(By.xpath("//tbody/tr")).size();
		}
		
		}
		
		/*
		String filePath = "C:\\Users\\R\\eclipse-workspace\\MissionHumane\\ExcelData\\punedata.xlsx";
		ExcelUtil util = new ExcelUtil(filePath);
		util.setCellData("Pune_Data", 0, 0, "Hospital Name");
		util.setCellData("Pune_Data", 0, 1, "Beds Without Oxygen");
		util.setCellData("Pune_Data", 0, 2, "Beds With Oxygen");
		util.setCellData("Pune_Data", 0, 3, "ICU Without Ventilator");
		util.setCellData("Pune_Data", 0, 4, "ICU With Ventilator");
		util.setCellData("Pune_Data", 0, 5, "Contact");
		
        int i =1;
		
		for(Entry e: hashmap.entrySet()) {
			
			i++;
			util.setCellData("Pune_data", i, 0, (String)e.getKey());
			ArrayList<String> value = (ArrayList<String>)e.getValue();
			util.setCellData("Pune_data", i, 1, value.get(0));
			util.setCellData("Pune_data", i, 2, value.get(1));
			util.setCellData("Pune_data", i, 3, value.get(2));
			util.setCellData("Pune_data", i, 4, value.get(3));
			util.setCellData("Pune_data", i, 5, value.get(4));
		*/	
		
		}
		
		
		
		
	}


