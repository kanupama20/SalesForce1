package salesForceNew.test.pages.login;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import salesForceNew.test.pages.base.BasePage;

public class ForgotPasswordPage2 extends BasePage {

	@FindBy(xpath = "//h1[text() = 'Check Your Email']")
	WebElement chkEmailMsg;

	public ForgotPasswordPage2(WebDriver driver) {
		super(driver);
	}

	public boolean validate_SendEmail(String str) {

		waitUntilVisible(chkEmailMsg, "Forgot Password page email sent msg.");
		if (chkEmailMsg.isDisplayed()) {
			String ScreenshotPath = captureWebElementScreenshot(chkEmailMsg, "chkEamilMsg");
			System.out.println("ScreenShot path : " + ScreenshotPath);
			return true;
		} else {
			return false;
		}
	}

	public void Waituntill_chkEmailMsg_visible() {
		waitUntilVisible(chkEmailMsg, "Forgot Password page email sent msg.");
	}

}
