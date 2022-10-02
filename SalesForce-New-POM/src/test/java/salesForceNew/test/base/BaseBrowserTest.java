package salesForceNew.test.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.Random;
//import java.util.concurrent.TimeUnit;
import java.util.Set;

//import org.apache.log4j.LogManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import salesForceNew.test.pages.home.HomePage;
import salesForceNew.test.pages.login.ForgotPasswordPage1;
import salesForceNew.test.pages.login.ForgotPasswordPage2;
import salesForceNew.test.pages.login.LoginPage;
import salesForceNew.test.tests.ReadProperties;
import salesForceNew.test.utility.CommonUtilities;
import salesForceNew.test.utility.GenerateReports;

//import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseBrowserTest extends ReadProperties {

	public static Logger Logger = LogManager.getLogger(BaseBrowserTest.class);

	public static WebDriverWait wait = null;
	public static WebDriver driver = null;

	public static boolean first_time = true;
	protected static GenerateReports report = null;

	public static WebDriver get_driver(String browser) throws Exception {

		switch (browser) {
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;

		case "chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;

		default:
			break;
		}
		return driver;
	}

	public static void goto_url(String url) {
		driver.get(url);
	}

	public static void waitUntillPageLoads() {
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(8));
	}

	public static void get_salesforce_page() throws Exception {

		String url2 = readPropertyData("applicationProperties", "url");

		driver.get(url2);
		waitUntillPageLoads();
		String url1 = driver.getCurrentUrl();
		System.out.println(url1);
		String title = driver.getTitle();
		System.out.println(title);
		driver.manage().window().maximize();
		waitUntilPageLoads();
		System.out.println("Title of the page : " + get_title());
		if (!(title.isBlank())) {
			System.out.println("Salesforce application page is displayed.");
			// Logger.info("Sleasforce application page is displayed.");
		}
	}

	public static void no_password_login(String txt) throws Exception {
		LoginPage login = new LoginPage(driver);

		login.enterUserName(readPropertyData("applicationProperties", "invalid-usernm"));
		login.clickLoginButton();

		login.waitUntillErrorVisible();

		login.validate_errormsg();
	}

	public static void valid_login(String txt) throws InterruptedException {
		waitUntillPageLoads();
		LoginPage login = new LoginPage(driver);
		try {
			login.enterUserName(readPropertyData("applicationProperties", "valid-usernm"));
			login.enterPassword(readPropertyData("applicationProperties", "valid-pw"));
		} catch (IOException exception) {
			report.logTestFailedWithException(exception);
		}

		login.clickLoginButton();

		HomePage homepage = new HomePage(driver);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		homepage.waitUtillHomeTabVisible();

		System.out.println("Home page Title: " + homepage.get_homepage_title());
		if (get_title().equals("Home Page ~ Salesforce - Developer Edition")) {
			System.out.println("Home page displayed.");
			report.logTestpassed("Home page displayed.");
		} else {
			System.out.println("Home page not displayed.");
			report.logTestpassed("Home page not displayed.");
		}
	}

	public static void rememberMe_login(String str) {
		waitUntillPageLoads();
		LoginPage login = new LoginPage(driver);
		try {
			login.enterUserName(readPropertyData("applicationProperties", "valid-usernm"));
			login.enterPassword(readPropertyData("applicationProperties", "valid-pw"));
		} catch (IOException exception) {
			report.logTestFailedWithException(exception);
		}
		login.selectRememberMe();

		login.clickLoginButton();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		HomePage homepage = new HomePage(driver);
		homepage.waitUtillHomeTabVisible();

		if (get_title().equals("Home Page ~ Salesforce - Developer Edition")) {
			System.out.println("Home page displayed.");
			report.logTestpassed("Home page displayed.");
		} else {
			System.out.println("Home page not displayed.");
			report.logTestFailed("Home page not displayed.");
		}

		homepage.logout(); // logging out of the home page.

		System.out
				.println("User name field after logging out of the salesforce: \n" + login.readRememberedUsernmField());
		try {
			if (login.readRememberedUsernmField().equals(readPropertyData("applicationProperties", "valid-usernm"))) {
				report.logTestpassed("User name field is showing expected text.");
				System.out.println("User name field is showing expected text.");
			} else {
				report.logTestFailed("User name field is not showing expected text.");
				System.out.println("User name field is not showing expected text.");
			}
		} catch (IOException exception) {
			report.logTestFailedWithException(exception);
		}

	}

	public static void forgotPw_loginPage(String str) {
		waitUntillPageLoads();
		LoginPage login = new LoginPage(driver);
		login.clickForgotPw();
		waitUntilPageLoads();
		ForgotPasswordPage1 forgotPw1 = new ForgotPasswordPage1(driver);
		// forgotPw1.enterUserName_clickContinue();
		try {
			forgotPw1.enterUserName_forgotPwPage(readPropertyData("applicationProperties", "valid-usernm"));
			System.out.println("USername entered: " + readPropertyData("applicationProperties", "valid-usernm"));
		} catch (IOException exception) {
			report.logTestFailedWithException(exception);
		}
		forgotPw1.clickContinueButton();
		waitUntilPageLoads();
		ForgotPasswordPage2 forgotPw2 = new ForgotPasswordPage2(driver);
		forgotPw2.Waituntill_chkEmailMsg_visible();

		if (forgotPw2.validate_SendEmail("Check email to recover password")) {
			report.logTestpassed("Email has send to recover password .");
			System.out.println("Email has send to recover password .");
		} else {
			report.logTestFailed("Email has not send to recover password .");
			System.out.println("Email has not send to recover password .");
		}
	}

	public static void invalidLoginPw(String str) {
		LoginPage login = new LoginPage(driver);

		try {
			login.enterUserName(readPropertyData("applicationProperties", "invalid-usernm"));
			login.enterPassword(readPropertyData("applicationProperties", "invalid-pw"));
		} catch (IOException exception) {
			report.logTestFailedWithException(exception);
		}
		login.clickLoginButton();

		login.validate_errormsg();
	}

	public static void switch_to_lightning_popup() {

		if (driver.findElement(By.xpath("//a[@id = 'tryLexDialogX']")).isDisplayed()) {
			driver.findElement(By.xpath("//a[@id = 'tryLexDialogX']")).click();
			first_time = false;
		}

	}

	public static String generate_random_int() {
		Random rd = new Random(); // creating Random object
		String str = String.valueOf(Math.abs(rd.nextInt()));
		return str;
	}

	public static void handle_alert(Boolean cmd) throws Exception {
		Alert alert = driver.switchTo().alert();
		System.out.println("Alert text : " + alert.getText());
		if (cmd == false)
			alert.dismiss();
		else
			alert.accept();
	}

	public static void waitUntilElementToBeClickable(By locator, String objName) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public static void waitUntilPageLoads() {
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
	}

	public static String get_title() {
		String str = driver.getTitle();
		return str;
	}

	public static void close_browser() {

		driver.close();
		System.out.println("Closed the browser.");
	}

	public static void close_all_browsers() {

		driver.quit();
	}
}
