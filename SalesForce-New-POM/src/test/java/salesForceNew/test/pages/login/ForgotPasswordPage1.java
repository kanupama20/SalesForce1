package salesForceNew.test.pages.login;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import salesForceNew.test.pages.base.BasePage;

public class ForgotPasswordPage1 extends BasePage {

	@FindBy(xpath = "//input[@id = 'un']")
	WebElement forgotPWpageUsrnm;
	@FindBy(xpath = "//input[@id = 'continue']")
	WebElement ContinueBtn;

	public ForgotPasswordPage1(WebDriver driver) {
		super(driver);
	}

	public void enterUserName_forgotPwPage(String usrname) {
		waitUntilVisible(forgotPWpageUsrnm, "Forgot Password user name field");
		enterText(forgotPWpageUsrnm, usrname, "Forgot Password username field");

	}

	public void clickContinueButton() {
		clickElement(ContinueBtn, "continue button");

	}

	/*
	 * public void enterUserName_clickContinue() {
	 * enterUserName_forgotPwPage("Forgot password page user name field");
	 * clickContinueButton(); }
	 */

}
