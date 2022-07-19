package util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CommonMethods {

	public static List<BookList> getBookListData() throws FileNotFoundException {

		FileReader reader = new FileReader(CommonConstants.BOOKLIST_TESTDATA_PATH);
		CsvToBean<BookList> csvToBean = new CsvToBeanBuilder<BookList>(reader).withType(BookList.class)
				.withSeparator(',').withIgnoreEmptyLine(true).build();

		return csvToBean.parse();
	}

	public static List<OnlinePlatform> getOnlInePlatformUrl() throws FileNotFoundException {

		FileReader reader = new FileReader(CommonConstants.ONLINEPLATFORM_TESTDATA_PATH);
		CsvToBean<OnlinePlatform> csvToBean = new CsvToBeanBuilder<OnlinePlatform>(reader)
				.withType(OnlinePlatform.class).withSeparator(',').withIgnoreEmptyLine(true).build();

		return csvToBean.parse();
	}

	public static List<String> switchToTabByIndex(WebDriver driver, int tabIndex) throws InterruptedException {
		Set<String> handles = driver.getWindowHandles();
		List<String> arr = new ArrayList<>(handles);
		driver.switchTo().window(arr.get(1));
		Thread.sleep(5000);
		return arr;

	}

	public static void switchToParentTab(WebDriver driver, List<String> arr) throws InterruptedException {
		driver.close();
		Thread.sleep(5000);
		driver.switchTo().window(arr.get(0));

	}

	public static int getIntegerfromString(String inputString) {
		char[] chars = inputString.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (char c : chars) {
			if (Character.isDigit(c)) {
				sb.append(c);
			}
		}
		return Integer.parseInt(sb.toString());
	}
	
	public static void selectByDropDownValues(WebDriver driver,WebElement element,String value) {
		Select selectDropDownValue = new Select(element);
		selectDropDownValue.selectByValue(value);
	}

}
