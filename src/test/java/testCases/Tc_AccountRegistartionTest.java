package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;

public class Tc_AccountRegistartionTest extends BaseClass {

	@Test
	public void verify_account_registration() {
		try {
		
		logger.info("***** Starting the Tc_AccountRegistartionTest testcase ******* ");

		HomePage hm = new HomePage(driver);
		hm.clickMyAccount();
		logger.info("Clicked on my accont link");
		hm.clickMyRegister();
		
		logger.info("Clicked on  register link");

		AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
		
		logger.info("Providing customer details");
		regpage.setFirstName(randomString1().toUpperCase());
		regpage.setLastName(randomString1().toUpperCase());
		regpage.setEmail(randomString1() + "@gmail.com"); // randomly generated email
		regpage.setTelephone(randomNumber());

		String password = randomAlphaNumeric();

		regpage.setPassword(password);
		regpage.setConfirmPassword(password);

		regpage.setPrivacyPolicy();
		regpage.clickContinue();
		
		logger.info("Validating expected message....");

		String confMsg = regpage.getConfirmationMsg();

		Assert.assertEquals(confMsg, "Your Account Has Been Created!");
		}
		catch(Exception e) {
			logger.error("Test Failed....");
			logger.debug("Debug logs....");
			Assert.fail();
		}

	}

}
