package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Wrapper.PriceDetails;
import base.TestBase;
import util.BookList;
import util.CommonMethods;

public class BuyBooksIndiaPage extends TestBase {
	

	String booksPrice;
	boolean isAvailable = true;
	PriceDetails book = new PriceDetails(null, null, false);

	@FindBy(xpath = "//*[@Placeholder][1]")
	WebElement searchBar;
	
	@FindBy(xpath = "//span[@class='price']")
	WebElement bookPrice;
	

	// Initializing the Page Objects:
	public BuyBooksIndiaPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public void searchBook(BookList bookDetails, WebDriver driver) {
		try {
			searchBar.click();
			searchBar.clear();
			searchBar.sendKeys(bookDetails.getBookName());
			searchBar.sendKeys(Keys.ENTER);
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
	}

	public void clickOnSearchedResult(BookList bookDetails, WebDriver driver) {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(5000);
			List<WebElement> els = driver.findElements(By.xpath(".//a[@href!='']"));
			for (WebElement el : els) {
				if (el.getText().toLowerCase().equals(bookDetails.getBookName().toLowerCase()) ||
						el.getText().toLowerCase().contains(bookDetails.getBookName().toLowerCase()+":")) {
					el.click();
					break;
				}
			}
		} catch (Exception e) {

			isAvailable = false;
		}
	}

	public PriceDetails getBookPriceQuantityDetails(BookList bookDetails, WebDriver driver, String onlinePlatform) {
		List<String> arr;
		try {
			arr = getBookPriceDetails(bookDetails, driver);
			getBookQuantityDetails(bookDetails, arr, driver);
			book = getSummarizedOriceQuantity(bookDetails, onlinePlatform);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return book;
	}

	private void getBookQuantityDetails(BookList bookDetails, List<String> arr, WebDriver driver) throws InterruptedException {try {
		if(bookPrice.isDisplayed());
		isAvailable = true;
		System.out.println(isAvailable);
	} catch (Exception e) {
		isAvailable = false;
		System.out.println(isAvailable);
		e.printStackTrace();
	}
	}
		
	

	private PriceDetails getSummarizedOriceQuantity(BookList bookDetails, String onlinePlatform) {
		book = new PriceDetails(onlinePlatform, booksPrice, isAvailable);
		return book;
	}

	private List<String> getBookPriceDetails(BookList bookDetails, WebDriver driver) {
		List<String> arr = null;
		try {
			booksPrice = bookPrice.getText();
			bookPrice.click();
			System.out.println("Price : " + booksPrice);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("PaperBack Edition not found");
		}
		return arr;
	
	}
}
