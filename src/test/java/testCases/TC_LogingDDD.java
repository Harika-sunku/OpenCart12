package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import Utilities.DataProviders;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccount;

public class TC_LogingDDD extends BaseClass{
	
	
	@Test(dataProvider="LoginData",dataProviderClass=DataProviders.class)
	public void loginDDD(String email,String password,String exp) {
		
		
		HomePage hm=new HomePage(driver);
		hm.clickMyAccount();
		hm.clickMyLoginr();
		
		LoginPage lp=new LoginPage(driver);
		lp.sendEmail(email);
		lp.sendPassword(password);
		lp.clickLogin();
		
		MyAccount ma=new MyAccount(driver);
		boolean targetPage=ma.isMyAccountPageExists();
		
		
		if(exp.equalsIgnoreCase("Valid")) {
			if(targetPage==true) {
				ma.clickLogout();
				Assert.assertTrue(true);
				
				
			}
			
			else {
				Assert.assertTrue(false);	
			}
		}
		
		if(exp.equalsIgnoreCase("Invalid")) {
			if(targetPage==true) {
				ma.clickLogout();
				Assert.assertTrue(false);
				
				
			}
			else {
				Assert.assertTrue(true);	
			}
		}
		
	
		
	}
	

}
