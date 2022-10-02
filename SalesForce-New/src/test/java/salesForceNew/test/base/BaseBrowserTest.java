package salesForceNew.test.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
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
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import salesForceNew.test.tests.ReadProperties;
import salesForceNew.test.utility.CommonUtilities;
import salesForceNew.test.utility.GenerateReports;

//import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseBrowserTest extends ReadProperties {

	public static GenerateReports report = null;
	public static Logger Logger = LogManager.getLogger(BaseBrowserTest.class);

	public static WebDriverWait wait = null;
	public static WebDriver driver = null;

	public static boolean first_time = true;

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

	public static void get_salesforce_page() throws Exception {

		String url2 = readPropertyData("applicationProperties", "url");

		driver.get(url2);
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(6));
		String url1 = driver.getCurrentUrl();
		System.out.println(url1);
		String title = driver.getTitle();
		System.out.println(title);
		driver.manage().window().maximize();
		if (!(title.isBlank())) {
			System.out.println("Sleasforce application page is displayed.");
			Logger.info("Sleasforce application page is displayed.");
			report.logTestInfo("Sleasforce application page is displayed.");
		}
	}

	public static void type_invalid_login(String txt) throws Exception {

		WebElement usrName = driver.findElement(By.id("username"));
		usrName.sendKeys(readPropertyData("applicationProperties", "invalid-usernm"));

		if (usrName.isDisplayed()) {
			System.out.println(txt + " is displayed in User name field");
			Logger.info(txt + " is displayed in User name field");
			report.logTestInfo(txt + " is displayed in User name field");
		}
	}

	public static void type_login(String txt) throws Exception {

		WebElement usrName = driver.findElement(By.id("username"));
		usrName.sendKeys(readPropertyData("applicationProperties", "valid-usernm"));
		if (usrName.isDisplayed()) {
			System.out.println(txt + " is displayed in User name field");
			Logger.info(txt + " is displayed in User name field");
			report.logTestInfo(txt + " is displayed in User name field");
		}

	}

	public static void click_LoginButton(String txt) throws Exception {

		WebElement buttonLogin = driver.findElement(By.id("Login"));
		waitUntilElementToBeClickable(buttonLogin);
		buttonLogin.click();
		System.out.println(txt + " button clicked");
		waitUntilPageLoads();
		report.logTestInfo(txt + " button clicked");
	}

	public static void validate_error_message(String msg) {

		waitUntilElementToBevisible(driver.findElement(By.id("error")));
		Assert.assertEquals(driver.findElement(By.id("error")).getText(), msg);

		if (driver.findElement(By.id("error")).getText().equals(msg))
			System.out.println("Error message same as expected");
		else
			System.out.println("error message is not same as expected");
	}

	public static void type_password(String txt) throws Exception {

		waitUntilElementToBeClickable(driver.findElement(By.id("password")));

		WebElement password = driver.findElement(By.id("password"));
		password.sendKeys(readPropertyData("applicationProperties", "valid-pw"));

		if (password.isDisplayed()) {
			System.out.println(txt + " is displayed in password field");
			report.logTestInfo(txt + " is displayed in password field");
		}
	}

	public static void type_invalid_password(String txt) throws Exception {

		WebElement password = driver.findElement(By.id("password"));
		password.sendKeys(readPropertyData("applicationProperties", "invalid-pw"));
		if (password.isDisplayed()) {
			System.out.println(txt + " is displayed in password field");
			report.logTestInfo(txt + " is displayed in password field");
		}
	}

	public static void check_homepage_title() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.titleContains("Home Page ~ Salesforce - Developer Edition"));
		Assert.assertEquals(driver.getTitle(), "Home Page ~ Salesforce - Developer Edition");

		if (driver.getTitle().equals("Home Page ~ Salesforce - Developer Edition")) {
			System.out.println("Home page displayed.");
			report.logTestpassed(" Homepage displayed");
		} else {
			System.out.println("Home page not displayed.");
			report.logTestFailed("Homepage not displayed");
		}
	}

	public static void check_rememberMe(String txt) {

		waitUntilElementToBeClickable(driver.findElement(By.id("rememberUn")));

		if (driver.findElement(By.id("rememberUn")).isSelected()) {
			System.out.println(txt + " is selected.");
			report.logTestInfo(txt + " is selected");
		}

		else {
			driver.findElement(By.id("rememberUn")).click();
			System.out.println(txt + " is not selected.");
			report.logTestInfo(txt + " is not selected");
		}
	}

	public static void logout_salesforce(String txt) throws Exception {

		click_usernav_button("UserID dropdown button");
		driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();
		System.out.println("Clicked " + txt + " button.");

		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait1.until(ExpectedConditions.titleContains("Login | Salesforce"));

		Assert.assertEquals(driver.getTitle(), "Login | Salesforce");

		if (driver.getTitle().equals("Login | Salesforce")) {
			System.out.println("Successfully loged out of Salesforce.");
			report.logTestpassed("Successfully logged out of salesforce");
		} else {
			System.out.println("Couldn't log out of Salesforce.");
			report.logTestFailed("Couldn't logout of the salesforce");
		}
	}

	public static void click_usernav_button(String txt) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"userNavLabel\"]")));

		driver.findElement(By.xpath("//*[@id=\"userNavLabel\"]")).click();
		if (driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).isDisplayed())
			System.out.println(txt + " has displayed");
		else
			System.out.println(txt + " has not displayed");
	}

	public static void click_usernav_myprofile_button(String txt) {
		waitUntilLocatorToBeClickable(By.xpath("//a[@title='My Profile']"));

		driver.findElement(By.xpath("//a[@title='My Profile']")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.titleContains("Salesforce - Developer Edition"));
		System.out.println("Profile page: " + driver.getTitle());
		System.out.println("My profile page has displayed");
		report.logTestInfo("My profile page has displayed");

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"userNavLabel\"]")));

		Assert.assertEquals(driver.getTitle(),
				"User: " + driver.findElement(By.xpath("//*[@id=\"userNavLabel\"]")).getText()
						+ " ~ Salesforce - Developer Edition");
		if (driver.getTitle().equals("User: " + driver.findElement(By.xpath("//*[@id=\"userNavLabel\"]")).getText()
				+ " ~ Salesforce - Developer Edition")) {
			System.out.println("My profile page has displayed");
			report.logTestInfo("My profile page has displayed");
		} else {
			System.out.println("My profile page has not displayed");
			report.logTestInfo("My profile page not displayed");
		}

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void click_myProfile_edit_button(String txt) {

		driver.findElement(By.xpath("//*[@id=\"chatterTab\"]/div[2]/div[2]/div[1]/h3/div/div/a/img")).click();
		System.out.println("My profile edit icon clicked");

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.switchTo().frame("contactInfoContentId");
		System.out.println("Switched to frame.");
	}

	public static void click_EditProfileForm_AboutTab(String lname) {

		System.out.println("Inside Edit my profile frame.");

		driver.findElement(By.xpath("//a[contains(text(),'About')]")).click();
		System.out.println("About link clicked");

		WebElement lastnm = driver.findElement(By.xpath("//input[@id='lastName']"));
		lastnm.clear();

		lastnm.sendKeys(lname);
		System.out.println("new Last name  entered");
		driver.findElement(By.xpath("//input[@value='Save All']")).click();
		System.out.println("Saveall button clicked");
		driver.switchTo().defaultContent();
		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait1.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//a[@class='contactInfoLaunch editLink']//img[@title='Edit Profile']")));

		String name = driver.findElement(By.xpath("//span[@id='tailBreadcrumbNode']")).getText();

		String[] str = name.split(" ");
		System.out.println("Profile name : " + name);
		assertEquals(lname, str[1]);

		report.logTestInfo("edit profile shows same name as entered.");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public static void type_myprofile_post(String txt) throws Exception {

		waitUntilLocatorToBeClickable(By.xpath("//*[@id=\"publisherAttachTextPost\"]/span[1]"));

		System.out.println("My profile post explicit wait is over.");
		driver.findElement(By.id("publisherAttachTextPost")).click();
		System.out.println("My profile post clicked.");

		WebElement iframe = driver.findElement(By.xpath(
				"/html/body/div[1]/div[2]/table/tbody/tr/td/div/div[3]/div[1]/div/div[1]/div/div[2]/ul/li[1]/div/div/div[1]/div[1]/div[1]/div[1]/div/div[2]/div[2]/div/div/iframe"));
		driver.switchTo().frame(iframe);
		// System.out.println(readPropertyData("applicationProperties","post-profile-txt"));
		driver.findElement(By.xpath("/html/body")).click();
		// driver.findElement(By.xpath("/html/body")).sendKeys(readPropertyData("applicationProperties","post-profile-txt"));
		driver.switchTo().parentFrame();

		driver.findElement(By.xpath("//input[@id='publishersharebutton']")).click();
	}

	public static void upload_myprofile_file(String txt) throws Exception {
		waitUntilLocatorToBeClickable(By.id("publisherAttachTextPost"));

		System.out.println("My profile file wait is over!!");
		driver.findElement(By.xpath("//*[@id=\"publisherAttachContentPost\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"publisherAttachContentPost\"]")).click();
		System.out.println("My profile file clicked.");

		waitUntilLocatorToBeClickable(By.xpath("//*[@id=\"chatterUploadFileAction\"]"));

		System.out.println("upload from computer button enable wait is over.");
		driver.findElement(By.xpath("//*[@id=\"chatterUploadFileAction\"]")).click();
		System.out.println(txt + " is successful.");

		waitUntilLocatorToBeClickable(By.id("chatterFile"));
		System.out.println("Choose file wait is over.");

		WebElement choose_file = driver.findElement(By.id("chatterFile"));

		File file = new File(
				"C:\\Users\\kanup\\eclipse-workspace\\com.Maven.Salesforce\\src\\test\\resources\\logo.jpg");
		choose_file.sendKeys(file.getAbsolutePath());
		System.out.println("Choose file button got the file link.");

		driver.findElement(By.id("publishersharebutton")).click();
		System.out.println("File share button got clicked and file got shared.");

	}

	public static void upload_profile_image(String txt) throws Exception {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.xpath("//*[@id=\"photoSection\"]/span[2]/img[1]"))));
		WebElement myprofilephoto = driver.findElement(By.xpath("//*[@id=\"photoSection\"]/span[2]/img[1]"));
		Actions a = new Actions(driver);

		a.moveToElement(myprofilephoto).build().perform();
		waitUntilElementToBeClickable(driver.findElement(By.id("uploadLink")));
		WebElement uploadlink = driver.findElement(By.id("uploadLink"));
		uploadlink.click();
		System.out.println("Profile photo upload link clicked.");

		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(30));
		WebElement frame1 = driver.findElement(By.xpath("//*[@id=\"uploadPhotoContentId\"]"));
		wait2.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame1));
		System.out.println("Frame wait is over.");

		WebDriverWait wait4 = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait4.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(frame1)));
		System.out.println("Staleness of frame wait is over.");

		Thread.sleep(8);

		// driver.switchTo().frame(frame1);

		/*
		 * for(int i=0; i<=5;i++){ try{ driver.switchTo().frame(frame1); break; }
		 * catch(Exception e){ System.out.println(e.getMessage()); } }
		 * driver.switchTo().frame(frame1); System.out.println("Switched to frame");
		 */

		WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(30));
		WebElement choose_photo = driver.findElement(By.id("j_id0:uploadFileForm:uploadInputFile"));
		// WebElement choose_photo =
		// driver.findElement(By.xpath("//input[@id=\"j_id0:uploadFileForm:uploadInputFile\"]"));
		wait3.until(ExpectedConditions.elementToBeClickable(choose_photo));
		System.out.println("Choose photo button wait is over.");
		Thread.sleep(30);
		File file = new File("C:\\Users\\kanup\\eclipse-workspace\\com.Maven.Salesforce\\src\\test\\resources\\my.png");
		choose_photo.sendKeys(file.getAbsolutePath());
		System.out.println("Choose photo button got the file link.");

		driver.findElement(By.xpath("//*[@id=\"j_id0:uploadFileForm:uploadBtn\"]")).click();

		WebDriverWait wait6 = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait6.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"j_id0:j_id7:save\"")));
		driver.findElement(By.xpath("//*[@id=\"j_id0:j_id7:save\"")).click();

		System.out.println("Profile image has changed.");
	}

	public static void click_mysettings_button() {
		waitUntilLocatorToBeClickable(By.xpath("//*[@id=\"userNav-menuItems\"]/a[2]"));
		System.out.println("My settings button wait over");
		driver.findElement(By.xpath("//*[@id=\"userNav-menuItems\"]/a[2]")).click();
		System.out.println("My settings button clicked.");
		waitUntilLocatorToBeClickable(By.xpath("//*[@id=\"PersonalSetup_font\"]"));
		if (driver.findElement(By.xpath("//*[@id=\"PersonalSetup_font\"]")).isDisplayed())
			System.out.println("My settings page has displayed.");
		else
			System.out.println("My settings page has not displayed");
	}

	public static void download_loginhistory_csv_file() {

		waitUntilLocatorToBeClickable(By.xpath("//*[@id=\"PersonalInfo\"]/a"));
		System.out.println("Personal link wait is over");
		driver.findElement(By.xpath("//*[@id=\"PersonalInfo\"]/a")).click();
		System.out.println("Personal link clicked.");

		waitUntilLocatorToBeClickable(By.xpath("//span[@id='LoginHistory_font']"));
		System.out.println("Login history link wait is over");

		driver.findElement(By.xpath("//span[@id='LoginHistory_font']")).click();
		System.out.println("Login history link clicked");

		waitUntilLocatorToBeClickable(By.xpath("//*[@id='RelatedUserLoginHistoryList_body']/div/a"));
		System.out.println("Download history wait is over.");

		driver.findElement(By.xpath("//*[@id='RelatedUserLoginHistoryList_body']/div/a")).click();
		System.out.println("Download history link clicked.");

		String downloadPath = "C:\\Users\\kanup\\Downloads\\";
		Assert.assertTrue(isFileDownloaded_Ext(downloadPath, ".csv"),
				"Failed to download document which has extension .xls");
		System.out.println("File found in the download folder");
		report.logTestpassed("File found in the download folder");
	}

	public static boolean isFileDownloaded_Ext(String dirPath, String ext) {
		boolean flag = false;
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			flag = false;
		}
		for (int i = 1; i < files.length; i++) {
			if (files[i].getName().contains(ext)) {
				flag = true;
			}
		}
		return flag;
	}

	public static void click_usernav_developerconsole_link() {

		waitUntilLocatorToBeClickable(By.xpath("//*[@id=\"userNav-menuItems\"]/a[3]"));
		System.out.println("Developer console link wait is over.");

		String parentWindow_handle = driver.getWindowHandle();
		driver.findElement(By.xpath("//*[@id=\"userNav-menuItems\"]/a[3]")).click();
		System.out.println("Developer console link clicked.");

		waitUntilPageLoads();

		Set<String> allWindow_handle = driver.getWindowHandles();

		for (String handle : allWindow_handle) {

			if (!handle.equals(parentWindow_handle)) {
				driver.switchTo().window(handle);
			}
		}
		System.out.println("New window title is " + driver.getTitle());
		if (driver.getTitle().equals("Developer Console"))
			System.out.println("New window " + driver.getTitle() + " got opened.");
		else
			System.out.println("New window has not opened.");
		driver.close();
		System.out.println("Closed the child window");
		driver.switchTo().window(parentWindow_handle);
		System.out.println("Switched back to parent window.");

	}

	public static void chk_userMenu_drpdwn_items() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"userNavLabel\"]")));
		driver.findElement(By.xpath("//*[@id=\"userNavLabel\"]")).click();

		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait1.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@title='My Profile']")));

		System.out.println("First menu Itm = " + driver.findElement(By.xpath("//a[@title='My Profile']")).getText());
		System.out.println("First menu Itm = " + driver.findElement(By.xpath("//a[@title='My Settings']")).getText());
		System.out.println("First menu Itm = "
				+ driver.findElement(By.xpath("//a[@title='Developer Console (New Window)']")).getText());
		System.out.println("First menu Itm = "
				+ driver.findElement(By.xpath("//a[@title='Switch to Lightning Experience']")).getText());
		System.out.println("First menu Itm = " + driver.findElement(By.xpath("//a[@title='Logout']")).getText());

		if (driver.findElement(By.xpath("//a[@title='My Profile']")).getText().equals("My Profile")
				&& driver.findElement(By.xpath("//a[@title='My Settings']")).getText().equals("My Settings")
				&& driver.findElement(By.xpath("//a[@title='Developer Console (New Window)']")).getText()
						.equals("Developer Console")
				&& driver.findElement(By.xpath("//a[@title='Switch to Lightning Experience']")).getText()
						.equals("Switch to Lightning Experience")
				&& driver.findElement(By.xpath("//a[@title='Logout']")).getText().equals("Logout")) {
			System.out.println(" All five menu items are displayed correctly");
			// return true;
		} else {
			System.out.println("Five menu items are not displayed correctly.");
			// return false;
		}
	}

	public static boolean chk_usrnmField_remember() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("idcard-identity"))));

		if (driver.findElement(By.id("idcard-identity")).getText().equals("kanuojm20@gmail.com"))
			return true;
		else
			return false;
	}

	public static void click_forgot_password(String txt) {

		waitUntilLocatorToBeClickable(By.id("forgot_password_link"));

		driver.findElement(By.id("forgot_password_link")).click();
		System.out.println(txt + " link clicked");

	}

	public static void chk_forgotPassword_title(String txt) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.titleContains("Forgot Your Password | Salesforce"));
		if (driver.getTitle().equals("Forgot Your Password | Salesforce"))
			System.out.println(txt + " page is displayed.");
		else
			System.out.println(txt + " page is not displayed.");
	}

	public static void send_forgotPassword_usernm() throws Exception {

		waitUntilLocatorToBeClickable(By.id("un"));
		driver.findElement(By.id("un")).sendKeys(readPropertyData("applicationProperties", "valid-usernm"));

		driver.findElement(By.id("continue")).click();
		System.out.println("Typed username in forgot password page and clicked continue button.");
		waitUntilPageLoads();
	}

	public static boolean chk_verificationEmail_page() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.titleContains("Check Your Email | Salesforce"));
		if (driver.getTitle().equals("Check Your Email | Salesforce"))
			return true;
		else
			return false;
	}

	public static void click_homepage_accounts_link() throws Exception {

		waitUntilLocatorToBeClickable(By.xpath("//*[@id=\"Account_Tab\"]/a"));
		driver.findElement(By.xpath("//*[@id=\"Account_Tab\"]/a")).click();

		/*
		 * WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(30));
		 * wait1.until( ExpectedConditions.presenceOfElementLocated(By.xpath(
		 * "//*[@id=\"bodyCell\"]/div[1]/div[1]/div[1]/h1"))); String account =
		 * driver.findElement(By.xpath("//*[@id=\"bodyCell\"]/div[1]/div[1]/div[1]/h1"))
		 * .getText(); // handle_alert(driver); if (account.equals("Accounts"))
		 * System.out.println("Accounts page is displayed."); else
		 * System.out.println("Accounts page not displayed.");
		 */

		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait2.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"tryLexDialogX\"]")));

		driver.findElement(By.xpath("//*[@id=\"tryLexDialogX\"]")).click();

		// ######################### have to check if correct username displayed on
		// accounts page #########################
	}

	public static void verify_accounts_page() {

		waitUntilPageLoads();
		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait1.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"bodyCell\"]/div[1]/div[1]/div[1]/h1")));
		String account = driver.findElement(By.xpath("//*[@id=\"bodyCell\"]/div[1]/div[1]/div[1]/h1")).getText();
		if (account.equals("Accounts"))
			System.out.println("Accounts page is displayed.");
		else
			System.out.println("Accounts page not displayed.");

	}

	public static void click_CreateNew_accounts_link() {

		waitUntilLocatorToBeClickable(By.xpath("//*[@id=\"hotlist\"]/table/tbody/tr/td[2]/input"));
		System.out.println("New button wait is over");
		driver.findElement(By.xpath("//*[@id=\"hotlist\"]/table/tbody/tr/td[2]/input")).click();
		System.out.println("New button has clicked");

		waitUntilLocatorToBeClickable(By.xpath("//*[@id=\"acc2\"]"));
		System.out.println("Wait to send keys in 'Account name' is over.");
		driver.findElement(By.xpath("//*[@id=\"acc2\"]")).sendKeys("XYZ");
		System.out.println("'Account name' has xyz value.");

		Select type_drpdwn = new Select(driver.findElement(By.xpath("//*[@id=\"acc6\"]")));

		type_drpdwn.selectByVisibleText("Technology Partner");
		System.out.println("Technology partnet selected from type drop down list.");

		Select cus_priority_drpdwn = new Select(driver.findElement(By.xpath("//*[@id=\"00N4x00000WJSTe\"]")));
		cus_priority_drpdwn.selectByVisibleText("High");
		System.out.println("Customer priority selected as High.");

		driver.findElement(By.xpath("//*[@id=\"bottomButtonRow\"]/input[1]")).click();

		if (driver
				.findElement(
						By.xpath("/html/body/div[1]/div[2]/table/tbody/tr/td[2]/div[1]/div[1]/div[1]/div[1]/div[2]/h2"))
				.getText().equals("XYZ"))
			System.out.println("New added account is shown.");
		else
			System.out.println("New account is not shown in the display.");
	}

	public static void click_createView_Accounts_link() {
		int count = 0;
		waitUntilLocatorToBeClickable(By.linkText("Create New View"));
		System.out.println("Create new view button wait is over");
		driver.findElement(By.linkText("Create New View")).click();
		System.out.println("Create new view button clicked");

		waitUntilLocatorToBeClickable(By.xpath("//input[@id = 'fname'] "));
		System.out.println("new view name text field wait is over");
		String view_name = "Kalyani_view" + generate_random_int();
		driver.findElement(By.xpath("//input[@id = 'fname'] ")).sendKeys(view_name);
		System.out.println("View name = " + view_name);
		System.out.println("new view name text field keys sent.");
		// ############### no need to send new view name it can take automatically have
		// to check.########
		String unique_view_name = "My" + generate_random_int() + "View";
		driver.findElement(By.xpath("//input[@id = 'devname']")).sendKeys(unique_view_name);
		System.out.println("Unique view name = " + unique_view_name);

		driver.findElement(By.xpath("//input[@name = 'save' and @class = 'btn primary' and @data-uidsfdc = '4']"))
				.click();

		waitUntilLocatorToBeClickable(By.xpath("//select[@name = 'fcf' and @title = 'View:'] "));
		System.out.println("####View_name : " + view_name);
		System.out.println("Drop down list items:");

		// System.out.println("View displaying on the drop down "+
		// driver.findElement(By.xpath("//*[@id=\"00B4x00000HQL6z_listSelect\"]")).getText());

		List<WebElement> ls = driver.findElements(By.xpath("//select[@name = 'fcf' and @title = 'View:']//option "));
		for (WebElement l : ls) {
			// System.out.println(l.getText());

			if ((l.getText()).equals(view_name)) {
				count = count + 1;
				System.out.println("New created view is showing in the view list.");
				break;
			}
		}
		if (count == 0)
			System.out.println("New created view is not showing in the view list.");

	}

	public static void Click_edit_accounts_view() {

		int count = 0;
		WebElement view = driver.findElement(By.xpath("//select[@name = 'fcf' and @title = 'View:']//option[10] "));
		System.out.println(view.getText());
		view.click();
		driver.findElement(By.xpath("//a[text() = 'Edit']")).click();
		if (driver.findElement(By.xpath("//h2[@class = 'pageDescription']")).getText().equals("Edit View"))
			System.out.println("Edit view page has displayed");
		else
			System.out.println("Edit view page has not displayed");

		WebElement view_name = driver.findElement(By.xpath("//input[@id = 'fname']"));
		view_name.clear();
		String change_view_name = "Changed_view_name" + generate_random_int();
		view_name.sendKeys(change_view_name);
		System.out.println("New changed name typed in the view name text box.");

		Select field_drpdwn = new Select(driver.findElement(By.xpath("//select[@id = 'fcol1']")));
		field_drpdwn.selectByVisibleText("Account Name");
		System.out.println("Filter field Account Name has selected.");

		Select operator_drpdwn = new Select(driver.findElement(By.xpath("//select[@id = 'fop1']")));
		operator_drpdwn.selectByVisibleText("contains");
		System.out.println("Operator 'Contains' has selected.");

		driver.findElement(By.xpath("//input[@id = 'fval1']")).sendKeys("a");
		System.out.println("In value filed 'a' has typed.");

		driver.findElement(By.xpath("//input[@data-uidsfdc = '5' and @title = 'Save']")).click();
		System.out.println("Save view Edit has clicked.");

		List<WebElement> ls = driver.findElements(By.xpath("//select[@name = 'fcf' and @title = 'View:']//option "));
		for (WebElement l : ls) {
			// System.out.println(l.getText());

			if ((l.getText()).equals(change_view_name)) {
				count = count + 1;
				System.out.println("New changed view is showing in the view list.");
				break;
			}
		}
		if (count == 0)
			System.out.println("New changed view is not showing in the view list.");
	}

	public static void merge_accounts_accounts_link() throws Exception {

		waitUntilLocatorToBeClickable(By.xpath("//a[text() = 'Merge Accounts']"));
		driver.findElement(By.xpath("//a[text() = 'Merge Accounts']")).click();
		System.out.println("Merge accounts on account page clicked");

		waitUntilLocatorToBeClickable(By.xpath("//input[@id = 'srch']"));
		driver.findElement(By.xpath("//input[@id = 'srch']")).sendKeys("uni");
		System.out.println("Search field has been send \"uni\" keys");

		driver.findElement(By.xpath("//input[@value = 'Find Accounts']")).click();
		System.out.println("Find account button clicked");

		// ######### ToDo: select first two accounts and deselect other if present
		/*
		 * WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(30));
		 * wait2.until(ExpectedConditions.elementToBeSelected(By.xpath(
		 * "//input[@id=\"cid0\"]")));
		 * 
		 * //WebElement table =
		 * //driver.findElement(By.xpath("\\table[@id = 'bodytable']\\tbody"));
		 * List<WebElement> rows =
		 * driver.findElements(By.xpath("//table[@class = 'list']//tbody/tr"));
		 * System.out.println("Table selected.");
		 * 
		 * //List<WebElement> heads = driver.findElements(By.xpath("//th")); int i = 4;
		 * for(WebElement row : rows) { String str =
		 * "//table[@class = 'list']//tbody/tr["+String.valueOf(i)+"]th"; WebElement h =
		 * driver.findElement(By.xpath(str)); if(h.isSelected()) h.click();
		 * 
		 * i++; }
		 * System.out.println("Other than first two all other fields are deselected");
		 */

		driver.findElement(By.xpath("//div[@class = 'pbBottomButtons']//input[1]")).click();
		System.out.println("Click merge on first page of merge page");

		waitUntilLocatorToBeClickable(
				By.xpath("/html/body/div/div[2]/table/tbody/tr/td[2]/form/div/div[2]/div[1]/div/input[2]"));
		driver.findElement(By.xpath("/html/body/div/div[2]/table/tbody/tr/td[2]/form/div/div[2]/div[1]/div/input[2]"))
				.click();
		System.out.println("Second merge page merge button clicked.");

		handle_alert(true);
		System.out.println("Accept alert.");

		verify_accounts_page();
	}

	public static void click_accountspage_lastactivity_greaterthan_30days() throws Exception {

		waitUntilLocatorToBeClickable(By.xpath("//a[text() = 'Accounts with last activity > 30 days']"));
		driver.findElement(By.xpath("//a[text() = 'Accounts with last activity > 30 days'] ")).click();
		System.out.println("Accounts with last activity > 30 days link clicked.");

		waitUntilPageLoads();

		if (driver.findElement(By.xpath("//button[@id = 'ext-gen189']")).isDisplayed()) {
			driver.findElement(By.xpath("//button[@id = 'ext-gen189']")).click();
			System.out.println("Popup window 'Biuld reports interactively' closed.");
		}
		// ######################### herafter test steps are unclear.//
		// #########################################
	}

	public static void click_opportunities_link() {

		waitUntilLocatorToBeClickable(By.xpath("//a[@title = 'Opportunities Tab']"));
		driver.findElement(By.xpath("//a[@title = 'Opportunities Tab']")).click();
		System.out.println("Opportunities Tab clicked.");

		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(8));

		waitUntilLocatorToBeClickable(By.xpath("//h1[@class = 'pageType']"));
		String str = driver.findElement(By.xpath("//h1[@class = 'pageType']")).getText();
		System.out.println("new page has heading " + str);

		switch_to_lightning_popup();
	}

	public static void Check_opportunities_drpdwn() {

		// click_opportunities_link(driver);

		driver.findElement(By.xpath("//select[@id = 'fcf']")).click();

		List<WebElement> opportunity_drpdwn = driver.findElements(By.xpath("//select[@id = 'fcf']//option"));
		int count = opportunity_drpdwn.size();
		if (count != 0)
			System.out.println("Opportunities drop down is present.");
		else
			System.out.println("Opportunities drop down is not present.");

		for (WebElement opportunity : opportunity_drpdwn) {

			System.out.println(opportunity.getText());
		}

	}

	public static void create_new_opportunity() {

		waitUntilLocatorToBeClickable(By.xpath(
				"/html/body/div[1]/div[2]/table/tbody/tr/td[2]/div[3]/div[1]/div/div[1]/form/table/tbody/tr/td[2]/input"));
		driver.findElement(By.xpath(
				"/html/body/div[1]/div[2]/table/tbody/tr/td[2]/div[3]/div[1]/div/div[1]/form/table/tbody/tr/td[2]/input"))
				.click();
		System.out.println("opportunities page new button clicked.");

		waitUntilLocatorToBeClickable(By.xpath("//h2[@class = 'pageDescription']"));
		System.out.println("New Edit opportunity page heading : "
				+ driver.findElement(By.xpath("//h2[@class = 'pageDescription']")).getText());

		String oppo_name = generate_random_int();
		driver.findElement(By.xpath("//table[@class = 'detailList']//tbody/tr[3]/td[2]/div/input")).sendKeys(oppo_name);

		// Select acc_nm = new Select(driver.findElement(By.xpath("//input[@id =
		// 'opp4']")));
		// acc_nm.selectByIndex(2);
		// driver.findElement(By.xpath("//input[@id =
		// 'opp4']")).sendKeys("my"+generate_random_int());
		driver.findElement(By.xpath("//span[@class = 'dateFormat']")).click(); // Click the date beside date field

		Select stage = new Select(driver.findElement(By.xpath("//select[@id = 'opp11']")));
		stage.selectByVisibleText("Prospecting");

		Select lead_source = new Select(driver.findElement(By.xpath("//select[@id = 'opp6']")));
		lead_source.selectByVisibleText("Web");

		// driver.findElement(By.xpath("//input[@id = 'opp17']")).sendKeys("Green
		// Earth");

		driver.findElement(
				By.xpath("/html/body/div[1]/div[2]/table/tbody/tr/td[2]/form/div/div[3]/table/tbody/tr/td[2]/input[1]"))
				.click();

		if (driver.findElement(By.xpath("//h2[@class = 'pageDescription']")).getText().equals(oppo_name))
			System.out.println("new opportunity created");
		else
			System.out.println("new opportunity not created");
	}

	public static void click_oppo_pipeline() {
		waitUntilLocatorToBeClickable(By.xpath("//a[text() = 'Opportunity Pipeline']"));
		driver.findElement(By.xpath("//a[text() = 'Opportunity Pipeline']")).click();
		System.out.println("Opportunity pipeline clicked");

		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(8));

		waitUntilLocatorToBeClickable(By.xpath("//h1[text() = 'Opportunity Pipeline']"));

		if (driver.findElement(By.xpath("//h1[text() = 'Opportunity Pipeline']")).isDisplayed())
			System.out.println("Opportunity Pipeline page displayed");
		else
			System.out.println("Opportunity Pipeline page not displayed");
	}

	public static void click_stuck_opportunity() {

		waitUntilLocatorToBeClickable(By.xpath("//a[text() = 'Stuck Opportunities']"));
		driver.findElement(By.xpath("//a[text() = 'Stuck Opportunities']")).click();
		System.out.println("Stuck opportunities link clicked");

		waitUntilLocatorToBeClickable(By.xpath("//h1[text() = 'Stuck Opportunities']"));

		if (driver.findElement(By.xpath("//h1[text() = 'Stuck Opportunities']")).getText()
				.equals("Stuck Opportunities"))
			System.out.println("Stuck opportunities page displayed");
		else
			System.out.println("Stuck opportunities page not displayed");

	}

	public static void click_quarterly_summary_link() {

		waitUntilLocatorToBeClickable(By.xpath("//select[@id = 'quarter_q']"));
		Select interval = new Select(driver.findElement(By.xpath("//select[@id = 'quarter_q']")));
		interval.selectByVisibleText("Current FQ");
		System.out.println("Current FQ selected as an interval");

		Select include = new Select(driver.findElement(By.xpath("//select[@id = 'open']")));
		include.selectByVisibleText("All Opportunities");

		driver.findElement(By.xpath("//input[@value = 'Run Report']")).click();
		System.out.println("Run report clicked");

		if (driver.findElement(By.xpath("//h1[text() = 'Opportunity Report']")).getText().equals("Opportunity Report"))
			System.out.println("Quarterly summary page displayed");
		else
			System.out.println("Quarterly summary page not displayed");
	}

	public static void click_leads_tab_link() {
		waitUntilLocatorToBeClickable(By.xpath("//a[@title = 'Leads Tab']"));
		driver.findElement(By.xpath("//a[@title = 'Leads Tab']")).click();
		System.out.println("Leads tab clicked");

		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(8));

		waitUntilLocatorToBeClickable(By.xpath("//h1[text() = 'Leads']"));
		// System.out.println("My leads page view title :
		// "+driver.findElement(By.xpath("//h1[text() = 'Leads']")).getText());
		if (driver.findElement(By.xpath("//h1[text() = 'Leads']")).getText().equals("Leads"))
			System.out.println("Leads page displayed");
		else
			System.out.println("Leads page not displayed");
		if (first_time == true)
			switch_to_lightning_popup();
	}

	public static void check_leads_drpdwn() {

		driver.findElement(By.xpath("//select[@id = 'fcf']")).click();

		List<WebElement> leads_drpdwn = driver.findElements(By.xpath("//select[@id = 'fcf']//option"));
		int count = leads_drpdwn.size();
		if (count != 0)
			System.out.println("Leads drop down is present.");
		else
			System.out.println("Leads drop down is not present.");

		for (WebElement lead : leads_drpdwn) {

			System.out.println(lead.getText());
		}
	}

	public static void chk_defaultView_leads_goButton() throws Exception {

		Select leads_drpdwn = new Select(driver.findElement(By.xpath("//select[@id = 'fcf']")));
		leads_drpdwn.selectByVisibleText("My Unread Leads");
		System.out.println("in Leads drop down 'My Unread Leads' selected");

		logout_salesforce("SalesForce Logout");

		get_salesforce_page();
		type_login("User name");
		type_password("password");
		click_LoginButton("Login");
		check_homepage_title();
		click_leads_tab_link();

		waitUntilLocatorToBeClickable(By.xpath("//input[@value = ' Go! ']"));
		driver.findElement(By.xpath("//input[@value = ' Go! ']")).click();
		System.out.println("LEads go button clicked");

		System.out.println("Stored view page heading: "
				+ driver.findElement(By.xpath("//select[@id = '00B4x00000Gaz0p_listSelect']")).getText());

		if (driver.findElement(By.xpath("//select[@id = '00B4x00000Gaz0p_listSelect']")).getText()
				.equals("My Unread Leads"))
			System.out.println("My unread leads page displayed");
		else
			System.out.println("My unread leads page not displayed");
	}

	public static void select_todays_leads_drpdwn() {

		Select leads_drpdwn = new Select(driver.findElement(By.xpath("//select[@id = 'fcf']")));

		leads_drpdwn.selectByVisibleText("Today's Leads");
		System.out.println("Todays leads field selected from leads drop down");

		driver.findElement(By.xpath("//input[@value = ' Go! ']")).click();
		System.out.println("LEad select view go button clicked");

		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(8));

		System.out.println("Leads drop down default selection: "
				+ driver.findElement(By.xpath("//select[@id = '00B4x00000Gaz13_listSelect']")).getText());

		if (driver.findElement(By.xpath("//select[@id = '00B4x00000Gaz13_listSelect']//option[4]")).getText()
				.equals("Today's Leads"))
			System.out.println("Todays leads page is displayed");
		else
			System.out.println("Todays leads page not displayed");
	}

	public static void chk_newbutton_leadshome() {

		driver.findElement(By.xpath("//table/tbody/tr/td[2]/input")).click();
		driver.findElement(By.xpath("//input[@id = 'name_lastlea2']")).sendKeys("ABCD");
		driver.findElement(By.xpath("//input[@id = 'lea3']")).sendKeys("ABCD");
		System.out.println("new button clicked and last name company name entered");

		driver.findElement(By.xpath("//*[@id=\"bottomButtonRow\"]/input[1]")).click();
		System.out.println("New leads save button clicked");

		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(8));

		if (driver.findElement(By.xpath("//h2[@class = 'topName']")).getText().equals("ABCD"))
			System.out.println("New lead is displaying");
		else
			System.out.println("New lead is not displaying");
	}

	public static void click_contacts_tab() {

		waitUntilLocatorToBeClickable(By.xpath("//a[@title = 'Contacts Tab']"));
		driver.findElement(By.xpath("//a[@title = 'Contacts Tab']")).click();
		System.out.println("Contacts tab clicked");

		if (driver.findElement(By.xpath("//h1[text()= 'Contacts']")).getText().equals("Contacts"))
			System.out.println("Contacts page is displaying");
		else
			System.out.println("Contacts page is not displaying");
		switch_to_lightning_popup();

	}

	public static void clk_newBtn_create_contacts() {

		waitUntilLocatorToBeClickable(By.xpath("//table/tbody/tr/td[2]/input"));
		driver.findElement(By.xpath("//table/tbody/tr/td[2]/input")).click();
		System.out.println("Contacts page new button clicked");
		waitUntilPageLoads();
	}

	public static void click_newButton_contactsTab() {

		clk_newBtn_create_contacts();
		String lastnm = "Kauli";
		String company = "my86362426";
		driver.findElement(By.xpath("//input[@id = 'name_lastcon2']")).sendKeys(lastnm);
		driver.findElement(By.xpath("//input[@id = 'con4']")).sendKeys(company);
		System.out.println("Last name and account name displaying in the respective fields.");

		driver.findElement(
				By.xpath("/html/body/div[1]/div[2]/table/tbody/tr/td[2]/form/div/div[3]/table/tbody/tr/td[2]/input[1]"))
				.click();
		System.out.println("Contacts save button clicked");

		if (driver.findElement(By.xpath("//h2[@class = 'topName']")).getText().equals(lastnm))
			System.out.println("New contact is displayed");
		else
			System.out.println("New contact is not displayed");
	}

	public static void clk_createNewView_contacts_tab() {

		waitUntilLocatorToBeClickable(By.xpath("//a[text() = 'Create New View']"));

		driver.findElement(By.xpath("//a[text() = 'Create New View']")).click();
		System.out.println("create New view link clicked");
	}

	public static void createNewView_contacts_tab() {

		clk_createNewView_contacts_tab();

		String view_name = "Kalyani_view" + generate_random_int();
		// String unique_view_name = "My_unique"+ generate_random_int();

		driver.findElement(By.xpath("//input[@id = 'fname']")).sendKeys(view_name);
		System.out.println("View name has send to the view name field");
		driver.findElement(By.xpath("//input[@id = 'devname']")).click();
		System.out.println("unique view name field clicked to show autosuggestion.");

		driver.findElement(By.xpath("//*[@id=\"editPage\"]/div[1]/table/tbody/tr/td[2]/input[1]")).click();
		System.out.println("Save button clicked");

		waitUntilLocatorToBeClickable(By.xpath("//select[@name= 'fcf']//option"));
		List<WebElement> views = driver.findElements(By.xpath("//select[@name= 'fcf']//option"));
		int cnt = 0;
		for (WebElement view : views) {
			if (view.getText().equals(view_name)) {
				System.out.println("New created view is displaying in the view drop down");
				cnt++;
				break;
			}
		}
		if (cnt == 0)
			System.out.println("New view has not created.");
	}

	public static void chk_recentlyCreated_contacts() {

		driver.findElement(By.xpath("//select[@id = 'hotlist_mode']")).click();
		int cnt = 0;
		List<WebElement> contacts = driver.findElements(By.xpath("//select[@id = 'hotlist_mode']//option"));

		for (WebElement contact : contacts) {

			if (contact.getText().equals("Recently Created")) {
				System.out.println("Recently created contact found in drop down");
				contact.click();
				cnt++;
				break;
			}
		}
		if (cnt == 0)
			System.out.println("Recently created contact option is not available in drop down");

		if (driver.findElement(By.xpath("//h3[text() = 'Recent Contacts']")).getText().equals("Recent Contacts"))
			System.out.println("Recently created contacts is displaying");
		else
			System.out.println("Recently created contacts not displayed");
	}

	public static void select_MyContacts_contactsdrpdwn() {

		driver.findElement(By.xpath("//select[@name = 'fcf'] ")).click();

		List<WebElement> contacts = driver.findElements(By.xpath("//select[@name = 'fcf']//option "));
		int cnt = 0;
		for (WebElement contact : contacts) {
			if (contact.getText().equals("My Contacts")) {
				contact.click();
				cnt++;
				break;
			}
		}
		if (cnt == 0)
			System.out.println("My contacts is not found in drop down");

		List<WebElement> contacts1 = driver.findElements(By.xpath("//select[@name = 'fcf']//option "));
		cnt = 0;
		for (WebElement contact : contacts1) {
			if (contact.isSelected()) {
				if (contact.getText().equals("My Contacts")) {
					System.out.println("My contacts is displaying");
					cnt++;
					break;
				}
			}
		}
		if (cnt == 0)
			System.out.println("My contacts is not displaying");
	}

	public static void clk_recentContacts_name() {

		String str = driver.findElement(By.xpath("//table/tbody/tr[2]/th/a")).getText();
		driver.findElement(By.xpath("//table/tbody/tr[2]/th/a")).click();
		System.out.println("First name in recent contacts list clicked");

		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(100));
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[@class = 'topName']")));
		if (driver.findElement(By.xpath("//h2[@class = 'topName']")).getText().equals(str))
			System.out.println("Contact details of particular name is displying");
		else
			System.out.println("Contact details of particular name is not displying");
	}

	public static void errorMsg_createNewView_contacts_tab() {

		clk_createNewView_contacts_tab();
		driver.findElement(By.xpath("//input[@id = 'devname']")).sendKeys("EFGH");
		System.out.println("unique view name field has EFGH.");

		driver.findElement(By.xpath("//*[@id=\"editPage\"]/div[1]/table/tbody/tr/td[2]/input[1]")).click();
		System.out.println("Save button clicked");

		if (driver.findElement(By.xpath(
				"/html/body/div/div[2]/table/tbody/tr/td[2]/div[2]/form/div[2]/div[1]/div[2]/table/tbody/tr[1]/td[2]/div/div[2]"))
				.isDisplayed()) {
			System.out.println("Error message : " + driver.findElement(By.xpath(
					"/html/body/div/div[2]/table/tbody/tr/td[2]/div[2]/form/div[2]/div[1]/div[2]/table/tbody/tr[1]/td[2]/div/div[2]"))
					.getText());
			System.out.println("Successfully displayed error message.");
		} else
			System.out.println("Error message not displayed");
	}

	public static void chk_cancelButton_createNewView_contactsTab() {

		clk_createNewView_contacts_tab();

		driver.findElement(By.xpath("//input[@id = 'fname']")).sendKeys("ABCD");
		System.out.println("ABCD has send to the view name field");

		driver.findElement(By.xpath("//input[@id = 'devname']")).clear();
		driver.findElement(By.xpath("//input[@id = 'devname']")).sendKeys("EFGH");
		System.out.println("unique view name field has EFGH.");

		driver.findElement(By.xpath("//*[@id=\"editPage\"]/div[1]/table/tbody/tr/td[2]/input[2]")).click();
		System.out.println("Cancel button clicked");

		if (driver.findElement(By.xpath("//h1[text() = 'Contacts']")).getText().equals("Contacts"))
			System.out.println("Contacts page displayed");
		else
			System.out.println("Contacts has not displayed");

		int cnt = 0;
		List<WebElement> contacts = driver.findElements(By.xpath("//select[@id = 'fcf']//option"));
		for (WebElement contact : contacts) {
			if (contact.getText().equals("ABCD")) {
				cnt++;
				break;
			}
		}
		if (cnt == 0)
			System.out.println("Cancelled contact has not created successfully");
		else
			System.out.println("Cancelled contact created so test case failed");

	}

	public static void chk_saveNnewBtn_new_contactsTab() {

		clk_newBtn_create_contacts();

		driver.findElement(By.xpath("//input[@id = 'name_lastcon2']")).sendKeys("Indian");
		System.out.println("Entered Indian as last name");

		driver.findElement(By.xpath("//input[@id = 'con4']")).sendKeys("Global Media");
		System.out.println("Global Media entered as account name");

		driver.findElement(
				By.xpath("/html/body/div[1]/div[2]/table/tbody/tr/td[2]/form/div/div[1]/table/tbody/tr/td[2]/input[2]"))
				.click();
		System.out.println("Save and new button clicked");

		if (driver.findElement(By.xpath("//h2[@class = 'pageDescription']")).getText().equals("New Contact"))
			System.out.println("New contacts page successfully displayed");
		else
			System.out.println("New contacts page not displayed");

		if (driver.findElement(By.xpath("//*[@id=\"mru0034x00001Hs8lL\"]/a/span")).getText().equals("Indian"))
			System.out.println("Newly created contact is displaying in recent items");
		else
			System.out.println("Newly created contact is not displaying in recent items");
	}

	public static void chk_Fnm_n_Lnm_loginUser() {
		waitUntilPageLoads();
		waitUntilLocatorToBeClickable(By.xpath("//a[@title = 'Home Tab']"));
		driver.findElement(By.xpath("//a[@title = 'Home Tab']")).click();
		System.out.println("Home tab button clicked.");
		switch_to_lightning_popup();

		waitUntilLocatorToBeClickable(By.xpath("//h1[@class = 'currentStatusUserName']//a"));
		driver.findElement(By.xpath("//h1[@class = 'currentStatusUserName']//a")).click();
		System.out.println("Clicked login name link on home page");

		waitUntilPageLoads();

		System.out.println("New page title: " + driver.getTitle());

		if (driver.getTitle().equals("User: Anu ABCD ~ Salesforce - Developer Edition"))
			System.out.println("My profile page is displayed");
		else
			System.out.println("My profile page is not displayed");
	}

	public static void clk_plus_onHomePage() {
		String str = null;
		driver.findElement(By.xpath("//img[@alt = 'All Tabs']")).click();
		System.out.println("+ on home page clicked.");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text() = 'All Tabs']")));

		if (driver.findElement(By.xpath("//h1[text() = 'All Tabs']")).isDisplayed())
			System.out.println("All tabs page displayed");
		else
			System.out.println("All tabs page not displayed");

		driver.findElement(By.xpath("//input[@value = 'Customize My Tabs']")).click();
		System.out.println("Customize my tabs button clicked");

		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text() = 'Customize My Tabs']")));

		if (driver.findElement(By.xpath("//h1[text() = 'Customize My Tabs']")).isDisplayed())
			System.out.println("Customize my Tabs page displayed");
		else
			System.out.println("Customize my Tabs page not displayed");

		List<WebElement> select_Tabs = driver.findElements(By.xpath("//select[@id = 'duel_select_1']//option"));
		List<WebElement> avail_Tabs = driver.findElements(By.xpath("//select[@id = 'duel_select_0']//option"));
		int cnt = 0;
		for (WebElement tab : select_Tabs) {

			if (cnt == 5)
				str = tab.getText();

			cnt++;
		}

		Select selectTab = new Select(driver.findElement(By.id("duel_select_1")));

		selectTab.selectByIndex(5);
		System.out.println("Selected 5th tab from available tabs area");

		driver.findElement(By.xpath("//a[@id = 'duel_select_0_left']//img")).click();
		System.out.println("Clicked remove button.");

		driver.findElement(By.xpath("//input[@value = ' Save ']")).click();
		System.out.println("Save button clicked");

		try {
			logout_salesforce("Logout salesforce");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			get_salesforce_page();
			type_login("User name");
			type_password("password");
			click_LoginButton("Login");
			check_homepage_title();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<WebElement> home_tabs = driver.findElements(By.xpath("//ul[@id = 'tabBar']//li/a"));
		cnt = 0;
		for (WebElement tab : home_tabs) {
			if (tab.getText().equals(str))
				cnt++;
		}
		if (cnt == 0)
			System.out.println("Successfully removed tab from home tab");
		else
			System.out.println("Successfully not removed tab from home tab");

	}

	public static void get_usr_calender() {

		driver.findElement(By.xpath("//a[@title = 'Home Tab']")).click();
		System.out.println("Home tab clicked");

		waitUntilLocatorToBeClickable(By.xpath("//*[@id=\"ptBody\"]/div/div[2]/span[2]/a"));
		driver.findElement(By.xpath("//*[@id=\"ptBody\"]/div/div[2]/span[2]/a")).click();
		System.out.println("Date link on home tab clicked.");

		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait1.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//h1[text() = 'Calendar for Anu Abcd - Day View']")));

		if (driver.findElement(By.xpath("//h1[text() = 'Calendar for Anu Abcd - Day View']")).isDisplayed())
			System.out.println("user Calender displayed");
		else
			System.out.println("user Calender not displayed");
	}

	public static void clkAndHandle_subjectCombo_window() {
		String childwindowhandle = null;

		String mainwindowhandle = driver.getWindowHandle();

		driver.findElement(By.xpath("//img[@alt = 'Subject Combo (New Window)']")).click();
		System.out.println("Clicked on subject combo icon");

		Set<String> allwindowhandles = driver.getWindowHandles();
		for (String s : allwindowhandles) {
			if (mainwindowhandle != s) {
				childwindowhandle = s;
			}
		}

		driver.switchTo().window(childwindowhandle);
		System.out.println("Switched to child window.");

		waitUntilLocatorToBeClickable(By.xpath("/html/body/div[2]/ul/li[5]/a"));
		driver.findElement(By.xpath("/html/body/div[2]/ul/li[5]/a")).click();
		System.out.println("Clicked other option in new window");
		driver.switchTo().window(mainwindowhandle);
		System.out.println("Switched back to main window");

	}

	public static void blk_event_inCalender() {

		driver.findElement(By.xpath("//*[@id=\"p:f:j_id25:j_id61:28:j_id64\"]/a")).click();
		System.out.println("Clicked on 8PM link");

		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text() = ' New Event']")));
		if (driver.findElement(By.xpath("//h2[text() = ' New Event']")).isDisplayed())
			System.out.println("New Event page displayed");
		else
			System.out.println("New Event page not displayed");

		switch_to_lightning_popup();

		clkAndHandle_subjectCombo_window();

		driver.findElement(By.xpath("//input[@id = 'EndDateTime_time']")).click();
		System.out.println("Clicked end time field");

		driver.findElement(By.xpath("//*[@id=\"timePickerItem_42\"]")).click();
		System.out.println("Clicked 9PM from endtime drop down");

		driver.findElement(By.xpath("//*[@id=\"topButtonRow\"]/input[1]")).click();
		System.out.println("Save button clicked.");

		waitUntilLocatorToBeClickable(
				By.xpath("//*[@id=\"p:f:j_id25:j_id69:28:j_id71:0:j_id72:calendarEvent:i\"]/div/div"));
		if (driver.findElement(By.xpath("//*[@id=\"p:f:j_id25:j_id69:28:j_id71:0:j_id72:calendarEvent:i\"]/div/div"))
				.isDisplayed())
			System.out.println("An event has created");
		else
			System.out.println("An event has not created");
	}

	public static void blk_weeklyRecurrence_event_inCalender() {

		driver.findElement(By.xpath("//*[@id=\"p:f:j_id25:j_id61:20:j_id64\"]/a")).click();
		System.out.println("4PM link in calender clicked.");

		waitUntilLocatorToBeClickable(By.xpath("//*[@id=\"bodyCell\"]/div[1]/div[1]/div[1]/h2"));
		if (driver.findElement(By.xpath("//*[@id=\"bodyCell\"]/div[1]/div[1]/div[1]/h2")).isDisplayed())
			System.out.println("New Event page displayed");
		else
			System.out.println("New Event page not displayed");

		switch_to_lightning_popup();

		clkAndHandle_subjectCombo_window();

		driver.findElement(By.xpath("//input[@id = 'EndDateTime_time']")).click();
		System.out.println("Clicked end time field");

		driver.findElement(By.xpath("//*[@id=\"timePickerItem_38\"]")).click();
		System.out.println("Click on 7PM in end time drop down");

		if (driver.findElement(By.xpath("//input[@id = 'IsRecurrence']")).isSelected()) {
			System.out.println("Recurrence checkbox already selected");
		} else {
			driver.findElement(By.xpath("//input[@id = 'IsRecurrence']")).click();
			System.out.println("Recurrence checkbox got selected");
		}

		driver.findElement(By.xpath("//input[@id = 'rectypeftw']")).click();
		System.out.println("Weekly radiobutton selected");

		String date = get_2weekAhead_date();
		driver.findElement(By.xpath("//input[@id = 'RecurrenceEndDateOnly']")).sendKeys(date);
		System.out.println("2 Week ahead from today date printed in end event date field");

		driver.findElement(By.xpath("//*[@id=\"topButtonRow\"]/input[1]")).click();
		System.out.println("Save button clicked");

		// ##################### TODO: verification of the event in user monthly
		// calendar ##############

	}

	public static String get_2weekAhead_date() {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 14);
		Date d = cal.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String date = sdf.format(d);
		System.out.println("Date : " + date);
		return date;
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

	public static void waitUntilLocatorToBeClickable(By locator) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public static void waitUntilElementToBeClickable(WebElement element) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public static void waitUntilElementToBevisible(WebElement element) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public static void waitUntilPageLoads() {
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(8));
	}

	public static void close_browser() {

		driver.close();
		System.out.println("Closed the browser.");
	}

	public static void close_all_browsers() {

		driver.quit();
	}
}
