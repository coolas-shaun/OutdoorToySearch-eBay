package com.miniProject.Base;

import java.awt.desktop.AboutHandler;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import net.bytebuddy.asm.Advice.Return;

public class BaseUI {

	public WebDriver driver;
	public Properties properties;
	
	//*******Read the config.properties file first and then read the broswerName and invoke appropriate one************
	public void invokeBrowser() {
		if(properties==null) {
			properties = new Properties();
			try {
				FileInputStream fi = new FileInputStream(System.getProperty("user.dir")+"\\config.properties");
				properties.load(fi);
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			try {
				String browserString = properties.getProperty("browserName");
				if(browserString.equalsIgnoreCase("chrome")) {
					System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\Resources\\chromedriver.exe" );
					driver = new ChromeDriver();
				}
				else if(browserString.equalsIgnoreCase("edge")) {
					System.setProperty("webdriver.edge.driver",System.getProperty("user.dir")+"\\Resources\\msedgedriver.exe" );
					driver = new EdgeDriver();
				}
				if(browserString.equalsIgnoreCase("firefox")) {
					System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"\\Resources\\geckodriver.exe" );
					driver = new FirefoxDriver();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(20,TimeUnit.SECONDS);
		}
		
	}
	
	//**********Redirect to the URL***********************************
	public void getUrl(String urlKey) {
		try {
			String url = properties.getProperty(urlKey);
			driver.navigate().to(url);
			System.out.println("url - "+driver.getCurrentUrl()+" opened.");
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("URL not found");
			e.printStackTrace();
		}
	}
	
	public void quitDriver() {
		driver.quit();
	}
	
	public void tearDownBrowser() {
		driver.close();
	}
	
	public void waitFor(long time) {
		try {
			Thread.sleep(time*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//***********Method to find element dynamically****
	public WebElement locateElement(String locator) {
		WebElement element;
		if(locator.endsWith("_id")) {
			element = driver.findElement(By.id(properties.getProperty(locator)));
		}
		else if(locator.endsWith("_xpath")) {
			element = driver.findElement(By.xpath(properties.getProperty(locator)));
		}
		else if(locator.endsWith("_css")) {
			element = driver.findElement(By.cssSelector(properties.getProperty(locator)));
		}
		else if(locator.endsWith("_linkText")) {
			element = driver.findElement(By.linkText(properties.getProperty(locator)));
		}
		else if(locator.endsWith("_partialLinkText")) {
			element = driver.findElement(By.partialLinkText(properties.getProperty(locator)));
		}
		else if(locator.endsWith("_class")) {
			element = driver.findElement(By.className(properties.getProperty(locator)));
		}
		else if(locator.endsWith("_tag")) {
			element = driver.findElement(By.tagName(properties.getProperty(locator)));
		}
		else if(locator.endsWith("_name")) {
			element = driver.findElement(By.name(properties.getProperty(locator)));
		}
		else {
			element=null;
		}
		return element;
	}
	
	//********click the element*****************
	public void clickElement(String element) {
		locateElement(element).click();
	}
	
	//**********enter text in textBox**********
	public void enterText(String textbox, String text) {
		locateElement(textbox).sendKeys(text);
	}
	
	
	//********select option from dropdown by visible text******
	public void selectDropdown(String dropdown, String text) {
		Select select = new Select(locateElement(dropdown));
		select.selectByVisibleText(text);
	}
	//********select option from dropdown by index******
	public void selectDropdown(String dropdown, int index) {
			Select select = new Select(locateElement(dropdown));
			select.selectByIndex(index);
	}
	
	//*******return text of selected option from drop down************
	public String selectedOptionDropdown(String dropdown) {
		Select select = new Select(locateElement(dropdown));
		return select.getFirstSelectedOption().getText();
	}
	
	public List<WebElement> locateElements(String locator){
		List<WebElement> elements;
		if(locator.endsWith("_id")) {
			elements = driver.findElements(By.id(properties.getProperty(locator)));
		}
		else if(locator.endsWith("_xpath")) {
			elements = driver.findElements(By.xpath(properties.getProperty(locator)));
		}
		else if(locator.endsWith("_css")) {
			elements = driver.findElements(By.cssSelector(properties.getProperty(locator)));
		}
		else if(locator.endsWith("_linkText")) {
			elements = driver.findElements(By.linkText(properties.getProperty(locator)));
		}
		else if(locator.endsWith("_partialLinkText")) {
			elements = driver.findElements(By.partialLinkText(properties.getProperty(locator)));
		}
		else if(locator.endsWith("_class")) {
			elements = driver.findElements(By.className(properties.getProperty(locator)));
		}
		else if(locator.endsWith("_tag")) {
			elements = driver.findElements(By.tagName(properties.getProperty(locator)));
		}
		else if(locator.endsWith("_name")) {
			elements = driver.findElements(By.name(properties.getProperty(locator)));
		}
		else {
			elements=null;
		}
		return elements;
		
	}
	
	//***********print text of the elements*********
	public List<String> printElements(String locator) {
		List<WebElement> elements = locateElements(locator);
		List<String> strList = new ArrayList<String>();
		int i=0;
		Iterator<WebElement> itr = elements.iterator();
		while (itr.hasNext()) {
			WebElement webElement = (WebElement) itr.next();
			strList.add(i, webElement.getText());
		}
		return strList;
	}
	
	//*********print attribute values***********
	public List<String> printElementsAttributes(String locator,String attr) {
		List<WebElement> elements = locateElements(locator);
		List<String> attList = new ArrayList<String>();
		int i=0;
		Iterator<WebElement> itr = elements.iterator();
		while (itr.hasNext()) {
			WebElement webElement = (WebElement) itr.next();
			attList.add(i++,webElement.getAttribute(attr));
		}
		return attList;
	}
	
	public void printList(List<String> list) {
		Iterator<String> itr = list.iterator();
		int count=0;
		while(itr.hasNext()) {
			String str = (String)itr.next();
			System.out.println(""+(++count)+". "+str);
		}
	}
	public void printTitle() {
		System.out.println("current page title - "+driver.getTitle());
	}
	
	
	public void clickEachPage(String locator) {
		List<WebElement> elements = locateElements(locator);
		for(int i=0,count=0;i<elements.size();i++) {
			elements.get(i).click();
			waitFor(2);
			System.out.println(""+(++count)+"---> "+locateElement("Item_id").getText());
			driver.navigate().back();
			elements = locateElements(locator);
		}
	}
}
