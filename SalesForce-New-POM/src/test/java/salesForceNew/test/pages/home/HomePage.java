package salesForceNew.test.pages.home;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import salesForceNew.test.pages.base.BasePage;

public class HomePage extends BasePage {

	@FindBy(id = "userNavLabel")
	WebElement userMenu;
	@FindBy(xpath = "//a[@title = 'Logout']")
	WebElement logout;
	@FindBy(xpath = "//a[@title = 'Home Tab']")
	WebElement homeTab;

	public HomePage(WebDriver driver) {
		super(driver);
	}

	public void logout() {

		waitUntilVisible(userMenu, "user menu");
		clickElement(userMenu, "User Menu");
		System.out.println("User menu text : " + userMenu.getText());
		waitUntilVisible(logout, "user menu logout");
		clickElement(logout, "Logout");

	}

	public String get_homepage_title() {
		return driver.getTitle();
	}

	public void waitUtillHomeTabVisible() {
		waitUntilVisible(homeTab, "Home Tab ");
	}
}
