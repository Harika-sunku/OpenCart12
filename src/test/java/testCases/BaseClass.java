package testCases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {

	public static WebDriver driver;
	public Logger logger;
	public Properties p;

//	@SuppressWarnings("deprecation")
	@BeforeClass
	@Parameters({  "browser" })
	public void setup(String br) throws IOException {
		FileInputStream f = new FileInputStream("./src/test/resources/config.properties");

		p = new Properties();
		p.load(f);

		logger = LogManager.getLogger(this.getClass());

//		if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {
//
//			DesiredCapabilities capability = new DesiredCapabilities();
//
//			// os
//			if (os.equalsIgnoreCase("windows")) {
//				capability.setPlatform(Platform.WIN11);
//			} else if (os.equalsIgnoreCase("mac")) {
//				capability.setPlatform(Platform.MAC);
//			} else {
//				System.out.println("No matching Operating system found");
//			}
//
//			// browser
//			switch (br.toLowerCase()) {
//
//			case "chrome":
//				capability.setBrowserName("chrome");
//				break;
//			case "edge":
//				capability.setBrowserName("MicrosoftEdge");
//				break;
//			case "firefox":
//				capability.setBrowserName("firefox");
//				break;
//			default:
//				System.out.println("Invalid browser");
//				return;
//
//			}
//			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capability);
//		}

		if (p.getProperty("execution_env").equalsIgnoreCase("local")) {

			switch (br.toLowerCase()) {
			case "chrome":
				driver = new ChromeDriver();
				break;
			case "edge":
				driver = new EdgeDriver();
				break;
			case "firefox":
				driver = new FirefoxDriver();
				break;
			default:
				System.out.println("Invalid browser");
				return;

			}
		}

		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.get(p.getProperty("appURL"));
		driver.manage().window().maximize();
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}

	public String randomString1() {
		String genertaedString = RandomStringUtils.randomAlphabetic(5);
		return genertaedString;
	}

	public String randomNumber() {
		String genertaedNumber = RandomStringUtils.randomNumeric(10);
		return genertaedNumber;
	}

	public String randomAlphaNumeric() {
		String genertaedString = RandomStringUtils.randomAlphabetic(3);
		String genertaedNumber = RandomStringUtils.randomNumeric(3);

		return genertaedString + "@" + genertaedNumber;
	}

	public String captureScreen(String tname) throws IOException {

		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;

		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

		String targetFilePath = System.getProperty("user.dir") + "\\screnshots\\" + tname + "_" + timeStamp + ".png";

		File targetFile = new File(targetFilePath);

		sourceFile.renameTo(targetFile);

		return targetFilePath;
	}

}
