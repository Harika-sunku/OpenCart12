package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(id="input-email")
	WebElement txtEmail;
	@FindBy(id="input-password")
	WebElement txtPswd;
	
	@FindBy(xpath="//input[@value='Login']")
	WebElement linkLogin;
	
	
	
	
	public void sendEmail(String email) {
		txtEmail.sendKeys(email);
	}
	public void sendPassword(String password) {
		txtPswd.sendKeys(password);
	}

	public void clickLogin() {
		linkLogin.click();;
	}
	
	
}
