package util;

import base.TestBase;

public class CommonConstants extends TestBase {
	
	public static long PAGE_LOAD_TIMEOUT = 20;
	public static long IMPLICIT_WAIT = 20;
	public static String GET_BOOKS_DETAIL_URL = "https://www.amazon.in/";
	public static String userDir = System.getProperty("user.dir");
	public static String BOOKLIST_TESTDATA_PATH = userDir+ "/src/main/resources/testdata/BookList.csv";
	public static String ONLINEPLATFORM_TESTDATA_PATH = userDir+ "/src/main/resources/testdata/onlinePlatform.csv";

}
