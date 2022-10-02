package salesForceNew.test.pages.login;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import salesForceNew.test.pages.base.BasePage;

public class LoginPage extends BasePage {

	@FindBy(id = "username")
	WebElement username;
	@FindBy(id = "Login")
	WebElement loginButton;
	@FindBy(id = "password")
	WebElement password;
	@FindBy(xpath = "//input[@id=\"rememberUn\"]")
	WebElement rememberMe;
	@FindBy(id = "error")
	WebElement errorMsg;
	@FindBy(xpath = "//a[@id = 'forgot_password_link']")
	WebElement forgotPw;
	@FindBy(xpath = "//span[@id = 'idcard-identity']")
	WebElement rememberedUsrnm;

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	public void enterUserName(String usrname) {
		// waitUntilVisible(username,"user name field");
		enterText(username, usrname, "username field");

	}

	public void enterPassword(String passWord) {
		// waitUntilVisible(password,"user name field");
		enterText(password, passWord, "password field");

	}

	public void clickLoginButton() {

		clickElement(loginButton, "login button");

	}

	public void clickForgotPw() {
		clickElement(forgotPw, "forgot password");

	}

	public void selectRememberMe() {

		if (!rememberMe.isSelected()) {
			// clickElement(rememberMe,"Remember Me checkbox");
			rememberMe.click();
			System.out.println("Remember me checked.");
		} else
			System.out.println("Remember me already checked.");
	}

	public String readUsernmField() {
		waitUntilVisible(username, "user name field");
		String str = username.getText();
		return str;

	}

	public String readRememberedUsernmField() {
		waitUntilVisible(rememberedUsrnm, "Remembered user name field");
		String str = rememberedUsrnm.getText();
		return str;

	}

	public void waitUntillErrorVisible() {
		waitUntilVisible(errorMsg, "Error message wait");

	}

	public void validate_errormsg() {
		waitUntillErrorVisible();
		if (errorMsg.isDisplayed()) {
			System.out.println("Error : " + errorMsg.getText());
			String ScreenshotPath = captureWebElementScreenshot(errorMsg, "loginError");
			System.out.println("ScreenShot path : " + ScreenshotPath);
		} else {
			System.out.println("No error message displayed");
		}
	}

	public void login(String usrname, String password) {
		enterUserName(usrname);
		enterPassword(password);
		clickLoginButton();
	}

}
