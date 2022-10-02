package salesForceNew.test.tests;

import java.lang.reflect.Method;
import java.time.Duration;
/*import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;*/
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
//import org.testng.annotations.*;
import org.testng.annotations.Test;

import salesForceNew.test.base.BaseBrowserTest;
import salesForceNew.test.utility.GenerateReports;

public class AutomationScripts extends BaseBrowserTest {

	@BeforeTest

	public static void setupBeforeTest() {
		report = GenerateReports.getInstance();
		report.startExtentReport();
	}

	@Parameters({ "browsername" })
	@BeforeMethod
	public static void setUp(String browsername, Method m) throws Exception {
		System.out.println("before method execution started");
		report.startSingleTestReport(m.getName());
		get_driver(browsername);

		get_salesforce_page();

		waitUntilPageLoads();
	}

	@AfterMethod

	public static void tearDown() { //
		System.out.println("After method started");
		// logger.info("after method execution is started");
		close_browser();
	}

	@AfterTest
	public static void teardownAfterTest() {
		System.out.println("After test started");
		report.endReport();
	}

	@Test(groups = "Regression")
	public static void salesforceTest1() throws Exception {
		get_salesforce_page();
		no_password_login("Login with no password");
	}

	@Test
	public static void salesforceTest2() throws Exception {
		get_salesforce_page();
		valid_login("Login with valid credentials");
	}

	@Test
	public static void salesforceTest3() throws Exception {
		get_salesforce_page();
		rememberMe_login("Remember Me on login page");
	}

	@Test
	public static void salesforceTest4A() throws Exception {
		get_salesforce_page();
		forgotPw_loginPage("Forgot password of login page");

	}

	@Test

	public static void salesforceTest4B() throws Exception {
		get_salesforce_page();
		invalidLoginPw("Invalid login password");
	}
}