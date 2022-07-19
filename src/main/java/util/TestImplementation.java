package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Wrapper.BookDetails;
import Wrapper.Details;
import Wrapper.PriceDetails;
import Wrapper.TotalDetails;
import base.TestBase;
import pages.AmazonBookPage;
import pages.BuyBooksIndiaPage;
import pages.FlipkartBookPage;

public class TestImplementation extends TestBase {

	BookDetails isbn;
	PriceDetails book = new PriceDetails(null, null, false);
	String pathOutputJson = CommonConstants.userDir + "/src/json/output.json";
	String pathOutputText = CommonConstants.userDir + "/src/json/output.txt";

	public WebDriver launchBrowser(String onlinePlatformUrl) {
		try {
			driver.get(onlinePlatformUrl);
			System.out.println("Launching URL : " + onlinePlatformUrl);
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(CommonConstants.PAGE_LOAD_TIMEOUT));
		} catch (Exception e) {
		}
		return driver;
	}

	public BookDetails getBookDetails(BookList bookDetails, String onlinePlatformUrl, List<PriceDetails> priceList,
			WebDriver driver) {
		String onlinePlatform = onlinePlatformUrl.substring(onlinePlatformUrl.indexOf(".") + 1,
				onlinePlatformUrl.lastIndexOf("."));
		switch (onlinePlatform) {
		case "amazon":
			isbn = searchBookDetailsOnAmamzon(driver, bookDetails, priceList, onlinePlatformUrl);
			break;
		default:
			break;
		}

		return isbn;

	}

	public BookDetails searchBookDetailsOnAmamzon(WebDriver driver, BookList bookDetails, List<PriceDetails> priceList,
			String onlinePlatformUrl) {
		try {
			String onlinePlatform = onlinePlatformUrl.substring(onlinePlatformUrl.indexOf(".") + 1,
					onlinePlatformUrl.lastIndexOf("."));
			AmazonBookPage amazonBookPage = new AmazonBookPage(driver);
			System.out.println("In TestImplementation:" + onlinePlatformUrl);
			driver.get(onlinePlatformUrl);
			amazonBookPage.selectShopCategory(driver);
			amazonBookPage.searchBookOnHomePage(bookDetails, driver);
			amazonBookPage.clickOnSearchedResult(bookDetails, driver);
			isbn = amazonBookPage.getBookDetails(bookDetails, priceList, driver, onlinePlatform);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isbn;

	}

	public PriceDetails getBookPriceQuantityDetails(BookList bookDetails, String onlinePlatformUrl, WebDriver driver) {
		try {
			String onlinePlatform = onlinePlatformUrl.substring(onlinePlatformUrl.indexOf(".") + 1,
					onlinePlatformUrl.lastIndexOf("."));
			switch (onlinePlatform.toLowerCase()) {
			case "flipkart":
				book = searchBookPriceQuantityOnFlipkart(driver, bookDetails, onlinePlatform);
				break;
			case "amazon":
				book = searchBookPriceQuantityOnAmamzon(driver, bookDetails, onlinePlatform);
				break;

			case "buybooksindia":
				book = searchBookPriceQuantityOnBBI(driver, bookDetails, onlinePlatform);
				break;

			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return book;

	}

	private PriceDetails searchBookPriceQuantityOnBBI(WebDriver driver, BookList bookDetails, String onlinePlatform) {
		BuyBooksIndiaPage booksIndiaPage = new BuyBooksIndiaPage(driver);
		try {
			Thread.sleep(5000);
			booksIndiaPage.searchBook(bookDetails, driver);
			booksIndiaPage.clickOnSearchedResult(bookDetails, driver);
			book = booksIndiaPage.getBookPriceQuantityDetails(bookDetails, driver, onlinePlatform);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return book;
		// TODO Auto-generated method stub

	}

	private PriceDetails searchBookPriceQuantityOnFlipkart(WebDriver driver, BookList bookDetails,
			String onlinePlatform) {
		FlipkartBookPage flipkartBookPage = new FlipkartBookPage(driver);
		flipkartBookPage.selectShopCategory(driver);
		flipkartBookPage.searchBook(bookDetails, driver);
		flipkartBookPage.clickOnSearchedResult(bookDetails, driver);
		book = flipkartBookPage.getBookPriceQuantityDetails(bookDetails, driver, onlinePlatform);
		return book;
		// TODO Auto-generated method stub

	}

	private PriceDetails searchBookPriceQuantityOnAmamzon(WebDriver driver, BookList bookDetails,
			String onlinePlatform) {
		AmazonBookPage amazonBookPage = new AmazonBookPage(driver);
		amazonBookPage.selectShopCategory(driver);
		amazonBookPage.searchBookOnHomePage(bookDetails, driver);
		amazonBookPage.clickOnSearchedResult(bookDetails, driver);
		book = amazonBookPage.getBookPriceQuantityDetails(bookDetails, driver, onlinePlatform);
		return book;
	}

	public void createJson(List<BookDetails> bookLists) {
		Details details = new Details(bookLists);

		try (PrintWriter out = new PrintWriter(new FileWriter(pathOutputJson))) {
			Gson gson = new Gson();
			String jsonString = gson.toJson(details);
			out.write(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Map<String, Integer> readJson(List<BookList> bookList, List<OnlinePlatform> onlinePlatform) {
		BufferedReader reader = null;
		Map<String,Integer> platformTotalCost = new HashMap<>();
		try {
			reader = new BufferedReader(new FileReader(pathOutputJson));
			Gson gson = new GsonBuilder().create();
			Details results = gson.fromJson(reader, Details.class);
			int totalQuantityPrice=0;
			
			if (results != null) {
				for(OnlinePlatform platformUrl:onlinePlatform) {
					String onlinePlatformUrl = platformUrl.getOnlinePlatformURL();
					String platformName = onlinePlatformUrl.substring(onlinePlatformUrl.indexOf(".") + 1,
							onlinePlatformUrl.lastIndexOf("."));
				for (BookDetails h : results.getIsbn()) {
					String platform;
					int price;
					
					int qty = h.getQty();
					for (PriceDetails cl : h.getBook()) {
						if (cl.getPrice() == null) {
							price = 0;
						} else {
							price = CommonMethods.getIntegerfromString(cl.getPrice());
						}
						platform = cl.getOnlinePlatform();
						int quantityPrice = price * qty;
						
						if(platform.equals(platformName)) {
							totalQuantityPrice = totalQuantityPrice+quantityPrice;
						}
					}
				}
				platformTotalCost.put(platformUrl.getOnlinePlatformURL(),totalQuantityPrice);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}
		return platformTotalCost;
	}

	public void getComparedOutput(List<BookList> bookList, List<OnlinePlatform> onlinePlatform) throws IOException {
		Map<String,Integer> map = readJson(bookList,onlinePlatform);
		compareOutput(map);
	}

	private void compareOutput(Map<String, Integer> map) throws IOException {
		
	
		try {
			PrintWriter output = new PrintWriter(pathOutputText);
			String lowestBidPlatform =null ; 
			Integer min = Collections.min(map.values());
			double lowestBid = min/100;
			for (Map.Entry<String,Integer> entry : map.entrySet()) {
				if(min == entry.getValue()) {
					lowestBidPlatform = entry.getKey();
				}
				double totalPrice = entry.getValue()/100;
				String temp= "Key = " + entry.getKey() +
	            ", Value = " + totalPrice ;
				output.println(temp);
				System.out.println(temp);
			}
			String temper = "Lowest Bid Platform: " +lowestBidPlatform+" Lowest Bid : "+lowestBid; 
			System.out.println(temper);
			output.println(temper);
			output.flush();
			output.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
