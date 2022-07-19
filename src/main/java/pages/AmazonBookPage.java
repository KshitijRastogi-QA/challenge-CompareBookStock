package pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v102.browser.Browser;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import Wrapper.PriceDetails;
import Wrapper.BookDetails;
import base.TestBase;
import util.BookList;
import util.CommonMethods;

public class AmazonBookPage extends TestBase {

	WebDriver driver;
	Browser browser;
	Actions action;
	String booksPrice;
	String booksISBN;
	String booksAuthor;
	boolean isAvailable;
	String booksPublisher;
	BookDetails isbn;
	PriceDetails book = new PriceDetails(null, null, false);

	@FindBy(xpath = "//select[@id='searchDropdownBox']")
	WebElement selectCategory;

	@FindBy(xpath = "//*[@Placeholder][1]")
	WebElement searchBar;

	@FindBy(xpath = "//*[contains(text(),'ISBN')]/parent::span/parent::li/following-sibling::li[1]/span/span[2]")
	WebElement bookISBN;

	@FindBy(xpath = "//*[contains(text(),'ASIN')]/following-sibling::span")
	WebElement bookASIN;

	@FindBy(xpath = "//li//span[text()='Paperback']/following-sibling::span")
	WebElement bookPrice;

	@FindBy(xpath = "//*[text()='Quantity:']/following-sibling::select")
	WebElement bookQuantity;

	@FindBy(xpath = "//*[text()='Quantity:']")
	WebElement quantity;

	@FindBy(xpath = "//*[@id='availability']/span']")
	WebElement availability;

	@FindBy(xpath = "//*[text()='Follow the Author']/following-sibling::div/div[2]/a")
	WebElement authorName;
	

	@FindBy(xpath = "(//*[contains(text(),'Publisher')])[2]/following-sibling::span")
	WebElement publisherName;

	// Initializing the Page Objects:
	public AmazonBookPage(WebDriver driver) {
		try {
			this.driver = driver;
			PageFactory.initElements(driver, this);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void selectShopCategory(WebDriver driver) {
		CommonMethods.selectByDropDownValues(driver, selectCategory, "search-alias=stripbooks");
	}

	public void searchBookOnHomePage(BookList bookDetails, WebDriver driver) {
		try {
			action = new Actions(driver);
			searchBar.click();
			searchBar.clear();
			searchBar.sendKeys(bookDetails.getBookName());
			searchBar.sendKeys(Keys.ENTER);
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
		}
	}

	public List<String> getBookPriceDetails(BookList bookDetails, WebDriver driver) {
		List<String> arr = null;
		try {
			arr = CommonMethods.switchToTabByIndex(driver, 1);
			action.moveToElement(bookPrice).build().perform();
			booksPrice = bookPrice.getText();
			bookPrice.click();
			System.out.println("Price : " + booksPrice);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("PaperBack Edition not found");
		}
		return arr;
	}

	public void getBookQuantityDetails(BookList bookDetails, List<String> arr, WebDriver driver)
			throws InterruptedException {
		try {

			String availabilityText = quantity.getText().trim();
			if (availabilityText.equals("Quantity:")) {
				Select select = new Select(bookQuantity);
				List<WebElement> availableQuantityList = select.getOptions();
				if (availableQuantityList.size() < bookDetails.getQuantity()) {
					isAvailable = false;
					System.out.println("Is Available : " + isAvailable);
				} else {
					isAvailable = true;
					System.out.println("Is Available : " + isAvailable);
				}
			} else {
				String stockAvailability = availability.getText();
				if (CommonMethods.getIntegerfromString(stockAvailability) < bookDetails.getQuantity()) {
					isAvailable = false;
					System.out.println("Is Available : " + isAvailable);
				} else {
					isAvailable = true;
					System.out.println("Is Available : " + isAvailable);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			CommonMethods.switchToParentTab(driver, arr);
		}
	}

	public BookDetails getBookDetails(BookList bookDetails, List<PriceDetails> priceList, WebDriver driver,
			String onlinePlatform) {

		try {
			System.out.println(bookDetails.getBookName());
			List<String> arr = getBookPriceDetails(bookDetails, driver);
			System.out.println("List<String> arr = getBookPriceDetails(bookDetails, driver);");
			getBookISBNDetail(bookDetails, driver);
			System.out.println("getBookISBNDetail(bookDetails, driver);");
			getBookAuthorDetails(bookDetails, driver);
			getBookPublisherDetails(bookDetails, driver);
			CommonMethods.switchToParentTab(driver, arr);
			isbn = getSumarizedDetails(bookDetails, priceList, onlinePlatform);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isbn;
	}

	private void getBookISBNDetail(BookList bookDetails, WebDriver driver) {
		try {
			action.moveToElement(bookISBN).build().perform();
			booksISBN = bookISBN.getText();
			System.out.println("Book ISBN: " + booksISBN);
		} catch (Exception e) {
			action.moveToElement(bookASIN).build().perform();
			booksISBN = bookASIN.getText();
			System.out.println("Book ISBN: " + booksISBN);
		}
	}

	private void getBookPublisherDetails(BookList bookDetails, WebDriver driver) {
		try{
		action.moveToElement(publisherName).build().perform();
		booksPublisher = publisherName.getText();
		System.out.println("Book Publisher: " + booksAuthor);
		}catch(Exception e){
			WebElement alternatePublisherName = driver.findElement(By.xpath("(//*[contains(text(),'Publisher')])[3]/following-sibling::span"));
			action.moveToElement(alternatePublisherName).build().perform();
			booksPublisher = alternatePublisherName.getText();
			System.out.println("Book Publisher: " + alternatePublisherName);
		}
		
		
		

	}

	private void getBookAuthorDetails(BookList bookDetails, WebDriver driver) {
		try {
		action.moveToElement(authorName).build().perform();
		booksAuthor = authorName.getText();
		System.out.println("Book Author: " + booksAuthor);
		}catch(Exception e) {
			WebElement alternateAuthorName = driver.findElement(By.xpath("//*[contains(@class,'author')]//a"));
			action.moveToElement(alternateAuthorName).build().perform();
			booksAuthor = alternateAuthorName.getText();
			System.out.println("Book Author: " + alternateAuthorName);
		}

	}

	public BookDetails getSumarizedDetails(BookList bookList, List<PriceDetails> priceList, String onlinePlatform) {
		isbn = new BookDetails(booksISBN, bookList.getBookName(), booksAuthor, booksPublisher, bookList.getQuantity(),
				priceList);
		return isbn;
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
		book.setPrice(onlinePlatform);
		book = new PriceDetails(onlinePlatform, booksPrice, isAvailable);
		return book;

	}
}
