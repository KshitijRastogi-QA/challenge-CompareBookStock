package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import Wrapper.PriceDetails;
import base.TestBase;
import util.BookList;
import util.CommonMethods;

public class FlipkartBookPage extends TestBase {
	
	Actions action;
	String booksPrice;
	boolean isAvailable = true;
	PriceDetails book = new PriceDetails(null, null, false);
	
	@FindBy(xpath = "(//button)[2]")
	WebElement closeLoginLayout;

	@FindBy(xpath = "//*[@Placeholder][1]")
	WebElement searchBar;

	@FindBy(xpath = "//*[text()='Request OTP']")
	WebElement requestOTP;
	
	@FindBy(xpath = "//*[contains(text(),'₹')][1]")
	WebElement bookPrice;
	
	@FindBy(xpath = "//*[contains(text(),'Hurry')][1]")
	WebElement quantity;
	
	
	

	// Initializing the Page Objects:
	public FlipkartBookPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public void searchBook(BookList bookDetails, WebDriver driver) {

		try {
			searchBar.click();
			searchBar.clear();
			searchBar.sendKeys(bookDetails.getBookName()+" Book");
			searchBar.sendKeys(Keys.ENTER);
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void selectShopCategory(WebDriver driver) {
		try {
			if (requestOTP.isDisplayed()) {
				Thread.sleep(5000);
				closeLoginLayout.click();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickOnSearchedResult(BookList bookDetails, WebDriver driver) {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(5000);
			List<WebElement> els = driver.findElements(By.xpath(".//a[@href!='']"));
			for (WebElement el : els) {
				if (el.getText().toLowerCase().equals(bookDetails.getBookName().toLowerCase())) {
					el.click();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			isAvailable = false;
		}
	}
	
	
	public List<String> getBookPriceDetails(BookList bookDetails, WebDriver driver) {
		List<String> arr = null;
		try {
			arr = CommonMethods.switchToTabByIndex(driver, 1);
			booksPrice = bookPrice.getText();
			bookPrice.click();
			System.out.println("Price : " + booksPrice);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("PaperBack Edition not found");
		}
		return arr;
	}
	
	public void getBookQuantityDetails(BookList bookDetails, List<String> arr, WebDriver driver) throws InterruptedException {
		
		try {
			quantity.getText().trim();
			CommonMethods.switchToParentTab(driver, arr);
			isAvailable = false;
			System.out.println(isAvailable);
		} catch (Exception e) {
			CommonMethods.switchToParentTab(driver, arr);
			isAvailable = true;
			System.out.println(isAvailable);
			e.printStackTrace();
		}
		}
	
	public PriceDetails getBookPriceQuantityDetails(BookList bookDetails, WebDriver driver, String onlinePlatform) {
		List<String> arr;
		try {
			arr = getBookPriceDetails(bookDetails, driver);
			getBookQuantityDetails(bookDetails, arr, driver);
			book = getSummarizedOriceQuantity(bookDetails, onlinePlatform);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return book;

	}

	private PriceDetails getSummarizedOriceQuantity(BookList bookDetails, String onlinePlatform) {
		book = new PriceDetails(onlinePlatform, booksPrice, isAvailable);
		return book;
	}
	
}
