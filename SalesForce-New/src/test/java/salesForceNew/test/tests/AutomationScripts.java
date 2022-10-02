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

	// public static GenerateReports report = null;

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
	public static void tearDown() {
		// logger.info("after method execution is started");
		System.out.println("after method execution is started");
		close_browser();
	}

	@AfterTest
	public static void teardownAfterTest() {
		report.endReport();
	}

	@Test
	public static void salesforceTC1() throws Exception {
		type_login("User name");
		click_LoginButton("Login");
		validate_error_message("Please enter your password.");
	}

	@Test
	public static void salesforceTC2() throws Exception {
		get_salesforce_page();
		type_login("User name");
		type_password("password");
		click_LoginButton("Login");
		check_homepage_title();			
	}

	@Test
	public static void salesforceTC3() throws Exception {
		get_salesforce_page();
		type_login("Login");
		type_password("password");
		check_rememberMe("Remember me checkbox");
		click_LoginButton("Login");
		check_homepage_title();
		logout_salesforce("Logout");

		if (chk_usrnmField_remember())
			System.out.println("Username field displaying username so test case 3 passed.");
		else
			System.out.println("Username field not displaying username so test case 3 failed.");
	}

	@Test
	public static void salesforceTC4A() throws Exception {
		get_salesforce_page();
		click_forgot_password("Forgot Password ");
		chk_forgotPassword_title("Forgot Passwordn");
		send_forgotPassword_usernm();
		if (chk_verificationEmail_page()) {
			System.out.println("Email verification link is sent and email verification"
					+ " page is displayed.\n so test case 4A is passed. ");
		} else
			System.out.println("Email verification page has not displayed.\n" + "so test case 4A is failed.");
	}

	@Test
	public static void salesforceTC4B() throws Exception {
		get_salesforce_page();
		type_invalid_login("Invalid user name");
		type_invalid_password("Invalid password");
		click_LoginButton("Login button");

		validate_error_message("Please check your username and password. "
				+ "If you still can't log in, contact your Salesforce administrator.");
	}

	@Test
	public static void salesforceTC05() throws Exception {
		get_salesforce_page();
		type_login("User name");
		type_password("password");
		click_LoginButton("Login");
		check_homepage_title();
		chk_userMenu_drpdwn_items();
	}

	@Test
	public static void salesforceTC06() throws Exception {

		get_salesforce_page();
		type_login("User name");
		type_password("password");
		click_LoginButton("Login");
		check_homepage_title();
		click_usernav_button("UserID dropdown button");
		click_usernav_myprofile_button("User navigation My profile ");
		click_myProfile_edit_button("Edit My profile ");
		click_EditProfileForm_AboutTab(readPropertyData("applicationProperties", "last-name-tc06"));
		type_myprofile_post("My profile post ");
		// upload_myprofile_file(driver, "Upload file to my profile ");
		// ??upload_profile_image(driver, "My profile photo");
	}

	@Test
	public static void salesforceTC07() throws Exception {

		get_salesforce_page();
		type_login("User name");
		type_password("password");
		click_LoginButton("Login");
		check_homepage_title();
		chk_userMenu_drpdwn_items();
		click_mysettings_button();
		download_loginhistory_csv_file();
	}

	@Test
	public static void salesforceTC08() throws Exception {

		get_salesforce_page();
		type_login("User name");
		type_password("password");
		click_LoginButton("Login");
		check_homepage_title();
		chk_userMenu_drpdwn_items();
		click_usernav_developerconsole_link();
	}

	@Test
	public static void salesforceTC09() throws Exception {

		get_salesforce_page();
		type_login("User name");
		type_password("password");
		click_LoginButton("Login");
		check_homepage_title();
		chk_userMenu_drpdwn_items();
		click_usernav_button("UserID dropdown button");
		logout_salesforce("SalesForce logout");
	}

	@Test
	public static void salesforceTC10() throws Exception {

		get_salesforce_page();
		type_login("User name");
		type_password("password");
		click_LoginButton("Login");
		check_homepage_title();
		click_homepage_accounts_link();
		click_CreateNew_accounts_link();
	}

	@Test
	public static void salesforceTC11() throws Exception {

		get_salesforce_page();
		type_login("User name");
		type_password("password");
		click_LoginButton("Login");
		check_homepage_title();
		click_homepage_accounts_link();
		click_createView_Accounts_link();
	}

	@Test
	public static void salesforceTC12() throws Exception {

		get_salesforce_page();
		type_login("User name");
		type_password("password");
		click_LoginButton("Login");
		check_homepage_title();
		click_homepage_accounts_link();
		Click_edit_accounts_view();
	}

	@Test
	public static void salesforceTC13() throws Exception {

		get_salesforce_page();
		type_login("User name");
		type_password("password");
		click_LoginButton("Login");
		check_homepage_title();
		click_homepage_accounts_link();
		merge_accounts_accounts_link();
	}

	@Test
	public static void salesforceTC14() throws Exception {

		get_salesforce_page();
		type_login("User name");
		type_password("password");
		click_LoginButton("Login");
		check_homepage_title();
		//click_homepage_accounts_link();
		//click_accountspage_lastactivity_greaterthan_30days();
		// merge_accounts_accounts_link(driver);
	}

	@Test
	public static void salesforceTC15() throws Exception {

		get_salesforce_page();
		type_login("User name");
		type_password("password");
		click_LoginButton("Login");
		check_homepage_title();
		click_opportunities_link();
		Check_opportunities_drpdwn();
	}

	@Test
	public static void salesforceTC16() throws Exception {

		get_salesforce_page();
		type_login("User name");
		type_password("password");
		click_LoginButton("Login");
		check_homepage_title();
		click_opportunities_link();
		create_new_opportunity();
	}

	@Test
	public static void salesforceTC17() throws Exception {

		get_salesforce_page();
		type_login("User name");
		type_password("password");
		click_LoginButton("Login");
		check_homepage_title();
		click_opportunities_link();
		click_oppo_pipeline();
	}

	@Test
	public static void salesforceTC18() throws Exception {

		get_salesforce_page();
		type_login("User name");
		type_password("password");
		click_LoginButton("Login");
		check_homepage_title();
		click_opportunities_link();
		click_stuck_opportunity();
	}

	@Test
	public static void salesforceTC19() throws Exception {

		get_salesforce_page();
		type_login("User name");
		type_password("password");
		click_LoginButton("Login");
		check_homepage_title();
		click_opportunities_link();
		click_quarterly_summary_link();
	}

	@Test
	public static void salesforceTC20() throws Exception {

		get_salesforce_page();
		type_login("User name");
		type_password("password");
		click_LoginButton("Login");
		check_homepage_title();
		click_leads_tab_link();
	}

	@Test
	public static void salesforceTC21() throws Exception {

		get_salesforce_page();
		type_login("User name");
		type_password("password");
		click_LoginButton("Login");
		check_homepage_title();
		click_leads_tab_link();
		check_leads_drpdwn();
	}

	@Test
	public static void salesforceTC22() throws Exception {

		get_salesforce_page();
		type_login("User name");
		type_password("password");
		click_LoginButton("Login");
		check_homepage_title();
		click_leads_tab_link();
		chk_defaultView_leads_goButton();
	}

	@Test
	public static void salesforceTC23() throws Exception {

		get_salesforce_page();
		type_login("User name");
		type_password("password");
		click_LoginButton("Login");
		check_homepage_title();
		click_leads_tab_link();
		select_todays_leads_drpdwn();
	}

	@Test
	public static void salesforceTC24() throws Exception {

		get_salesforce_page();
		type_login("User name");
		type_password("password");
		click_LoginButton("Login");
		check_homepage_title();
		click_leads_tab_link();
		chk_newbutton_leadshome();
		// select_todays_leads_drpdwn(driver);
	}

	@Test
	public static void salesforceTC25() throws Exception {

		get_salesforce_page();
		type_login("User name");
		type_password("password");
		click_LoginButton("Login");
		check_homepage_title();
		click_contacts_tab();
		click_newButton_contactsTab();
	}

	@Test
	public static void salesforceTC26() throws Exception {

		get_salesforce_page();
		type_login("User name");
		type_password("password");
		click_LoginButton("Login");
		check_homepage_title();
		click_contacts_tab();
		createNewView_contacts_tab();
	}

	@Test
	public static void salesforceTC27() throws Exception {

		get_salesforce_page();
		type_login("User name");
		type_password("password");
		click_LoginButton("Login");
		check_homepage_title();
		click_contacts_tab();
		chk_recentlyCreated_contacts();
	}

	@Test
	public static void salesforceTC28() throws Exception {

		get_salesforce_page();
		type_login("User name");
		type_password("password");
		click_LoginButton("Login");
		check_homepage_title();
		click_contacts_tab();
		select_MyContacts_contactsdrpdwn();
	}

	@Test
	public static void salesforceTC29() throws Exception {

		get_salesforce_page();
		type_login("User name");
		type_password("password");
		click_LoginButton("Login");
		check_homepage_title();
		click_contacts_tab();
		clk_recentContacts_name();
	}

	@Test
	public static void salesforceTC30() throws Exception {

		get_salesforce_page();
		type_login("User name");
		type_password("password");
		click_LoginButton("Login");
		check_homepage_title();
		click_contacts_tab();
		errorMsg_createNewView_contacts_tab();
	}

	@Test
	public static void salesforceTC31() throws Exception {

		get_salesforce_page();
		type_login("User name");
		type_password("password");
		click_LoginButton("Login");
		check_homepage_title();
		click_contacts_tab();
		chk_cancelButton_createNewView_contactsTab();
	}

	@Test
	public static void salesforceTC32() throws Exception {

		get_salesforce_page();
		type_login("User name");
		type_password("password");
		click_LoginButton("Login");
		check_homepage_title();
		click_contacts_tab();
		chk_saveNnewBtn_new_contactsTab();
	}

	@Test
	public static void salesforceTC33() throws Exception {

		get_salesforce_page();
		type_login("User name");
		type_password("password");
		click_LoginButton("Login");
		check_homepage_title();
		chk_Fnm_n_Lnm_loginUser();
	}

	@Test
	public static void salesforceTC34() throws Exception {

		get_salesforce_page();
		type_login("User name");
		type_password("password");
		click_LoginButton("Login");
		check_homepage_title();
		click_usernav_button("UserID dropdown button");
		click_usernav_myprofile_button("User navigation My profile ");
		click_myProfile_edit_button("Edit My profile ");
		click_EditProfileForm_AboutTab(readPropertyData("applicationProperties", "last-name-tc34"));
	}

	@Test
	public static void salesforceTC35() throws Exception {

		get_salesforce_page();
		type_login("User name");
		type_password("password");
		click_LoginButton("Login");
		check_homepage_title();
		clk_plus_onHomePage();

	}

	@Test
	public static void salesforceTC36() throws Exception {

		get_salesforce_page();
		type_login("User name");
		type_password("password");
		click_LoginButton("Login");
		check_homepage_title();
		get_usr_calender();
		blk_event_inCalender();
	}

	@Test
	public static void salesforceTC37() throws Exception {

		get_salesforce_page();
		type_login("User name");
		type_password("password");
		click_LoginButton("Login");
		check_homepage_title();
		get_usr_calender();
		blk_weeklyRecurrence_event_inCalender();
	}
}
