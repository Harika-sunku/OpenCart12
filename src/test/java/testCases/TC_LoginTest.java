package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccount;

public class TC_LoginTest extends BaseClass {
	
	@Test
	public void verify_Login() {
		logger.info("**** Starting TC02 Login page");
		
		
		HomePage hm=new HomePage(driver);
		hm.clickMyAccount();
		hm.clickMyLoginr();
		
		LoginPage lp=new LoginPage(driver);
		lp.sendEmail(p.getProperty("email"));
		lp.sendPassword(p.getProperty("password"));
		lp.clickLogin();
		
		MyAccount ma=new MyAccount(driver);
		boolean targetPage=ma.isMyAccountPageExists();
		Assert.assertTrue(targetPage);
		
		
		logger.info("**** completed TC02 Login page");
		
		
	}
	

}
