package com.miniProject.OutdoorToySearch;
import org.openqa.selenium.WebElement;

import com.miniProject.Base.*;

public class EbayExecution extends BaseUI {
	public static void main(String[] args) {
		BaseUI base = new BaseUI();
		base.invokeBrowser();
		base.getUrl("url");
		base.waitFor(3);
		base.clickElement("registerLink_xpath");
		base.printTitle();
//		base.enterText("keywordTextbox_id", "outdoor toys");
//		base.selectDropdown("keywordSelect_id", "Any words, any order");
//		base.clickElement("conditionCheckbox_xpath");
//		base.clickElement("prefLocationRadio_xpath");
//		base.selectDropdown("prefLocationSelect_id", 1);
//		base.clickElement("searchButton_id");
		base.printTitle();
		
		//verifying the search result
		if(base.selectedOptionDropdown("categorySelect_id").equals("Outdoor Toys & Structures")) {
			System.out.println("Result Verified. PASSED");
			System.out.println("Category: Outdoor Toys & Structures");
		}
		else {
			System.out.println("FAILED");
			System.out.println("closing browser");
			base.tearDownBrowser();
		}
		System.out.println("***************************************************");

		// printing all the product names and links 
		System.out.println("All products in the page");
		base.printList(base.printElements("productList_xpath"));
//		System.out.println("***************************************************");
//		System.out.println("list of links");
//		base.printList(base.printElementsAttributes("productListLink_xpath", "href"));
		
		System.out.println("List of products having keyword 'Portable' in title \n ");
		//base.printList(base.printElements("portableProducts_xpath"));
		System.out.println("Links:");
		base.printList(base.printElementsAttributes("portableProducts_xpath", "href"));
		System.out.println("************************************************");
		base.clickEachPage("portableProducts_xpath");
		base.waitFor(3);
		base.quitDriver();
	}
}
