package stock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.v102.browser.Browser;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Wrapper.PriceDetails;
import Wrapper.BookDetails;
import Wrapper.Details;
import base.TestBase;
import util.BookList;
import util.CommonConstants;
import util.CommonMethods;
import util.TestImplementation;
import util.OnlinePlatform;

public class BookStockCompareSpec extends TestBase {

	CommonMethods testUtil;
	TestImplementation testImplementation = new TestImplementation();
	WebDriver driver;
	PriceDetails book;
	BookDetails isbn;
	List<PriceDetails> shortList;
	List<PriceDetails> priceList = new ArrayList<PriceDetails>();
	List<BookDetails> bookLists = new ArrayList<BookDetails>();

	@BeforeMethod
	public void setUp() {
		initialization();
		testUtil = new CommonMethods();
	}

	@Test(priority = 1)
	public void testMethod() throws IOException {

		List<BookList> bookList = CommonMethods.getBookListData();
		List<OnlinePlatform> onlinePlatform = CommonMethods.getOnlInePlatformUrl();

		for (BookList bookDetails : bookList) {
			List<PriceDetails> priceList = new ArrayList<PriceDetails>();
			for (OnlinePlatform onlinePlatformUrl : onlinePlatform) {
				driver = testImplementation.launchBrowser(onlinePlatformUrl.getOnlinePlatformURL());
				book = testImplementation.getBookPriceQuantityDetails(bookDetails, onlinePlatformUrl.toString(),
						driver);
				priceList.add(book);
			}
			isbn = testImplementation.getBookDetails(bookDetails, CommonConstants.GET_BOOKS_DETAIL_URL.toString(),
					priceList, driver);
			bookLists.add(isbn);
		}
		testImplementation.createJson(bookLists);

		testImplementation.getComparedOutput(bookList, onlinePlatform);

	}

	@AfterMethod
	public void tearDown() {
		// driver.quit();
	}
}
