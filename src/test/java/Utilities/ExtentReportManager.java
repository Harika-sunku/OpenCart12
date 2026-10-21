package Utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testCases.BaseClass;

public class ExtentReportManager implements ITestListener {

    public ExtentSparkReporter sparkReporter;//Used to generate and configure the Extent Report HTML output file.
    public ExtentReports extent;//Used to create, manage, and flush the entire test execution report.
    public ExtentTest test;//Used to log individual test case steps, status (Pass/Fail/Skip), and details into the report.

    String repName;

    public void onStart(ITestContext testContext) {

        /*
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        Date dt = new Date();
        String currentdatetimeStamp = df.format(dt);
        */

        String timeStamp =
                new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
                        .format(new Date());

        repName = "Test-Report-" + timeStamp + ".html";

        sparkReporter =
                new ExtentSparkReporter(".\\reports\\" + repName);

        sparkReporter.config()
                .setDocumentTitle("opencart Automation Report");

        sparkReporter.config()
                .setReportName("opencart Functional Testing");

        sparkReporter.config()
                .setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);//Links the Spark Reporter (HTML report file) to ExtentReports so test results are written into the report.
//setSystemInfo() statements are used to add environment/project details to the Extent Report
        extent.setSystemInfo("Application", "opencart");
        extent.setSystemInfo("Module", "Admin");
        extent.setSystemInfo("Sub Module", "Customers");
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");
        
        String os = testContext.getCurrentXmlTest().getParameter("os");
        extent.setSystemInfo("Operating System", os);

        String browser = testContext.getCurrentXmlTest().getParameter("browser");
        extent.setSystemInfo("Browser", browser);

        List<String> includedGroups =
                testContext.getCurrentXmlTest().getIncludedGroups();

        if (!includedGroups.isEmpty()) {
            extent.setSystemInfo("Groups", includedGroups.toString());
        }
        }
//ITestResult result-ITestResult stores the execution details of a test case such as test name, status, groups, execution result, exceptions, and execution times.
        public void onTestSuccess(ITestResult result) {

            test = extent.createTest(result.getTestClass().getName());//Creates a new test entry in the report using the test class name.
            test.assignCategory(result.getMethod().getGroups()); // to display groups in report.Assigns TestNG groups (like Smoke, Regression, Sanity) to the test in the report.
            test.log(Status.PASS,
                    result.getName() + " got successfully executed");//Logs a PASS status with a success message for the test case.
        }
        
        public void onTestFailure(ITestResult result) {

            test = extent.createTest(result.getTestClass().getName());

            test.assignCategory(result.getMethod().getGroups());

            test.log(Status.FAIL,
                     result.getName() + " got failed");

            test.log(Status.INFO,
                     result.getThrowable().getMessage());//Logs the exception/error message that caused the test to fail.(org.openqa.selenium.NoSuchElementException)

            try {
                String imgPath = new BaseClass().captureScreen(result.getName());//Captures a screenshot at the point of failure and stores its file path.
                test.addScreenCaptureFromPath(imgPath);//Attaches the captured screenshot to the Extent Report.

            } catch (IOException e1) {

                e1.printStackTrace();//Handles any error that occurs while capturing or attaching the screenshot.

            }
        }

        public void onTestSkipped(ITestResult result) {

            test = extent.createTest(result.getTestClass().getName());

            test.assignCategory(result.getMethod().getGroups());

            test.log(Status.SKIP,
                     result.getName() + " got skipped");

            test.log(Status.INFO,
                     result.getThrowable().getMessage());
        }
        
        public void onFinish(ITestContext testContext) {

    	    extent.flush();

    	    String pathOfExtentReport =
    	            System.getProperty("user.dir")
    	            + "\\reports\\"
    	            + repName;
//    	    new File() → Locate report file
//    	    toURI() → Convert file path to browser-readable format
//    	    browse() → Open report in browser
//    	    catch() → Handle opening errors

    	    File extentReport = new File(pathOfExtentReport);//Creates a File object pointing to the generated Extent Report HTML file.

    	    try {
    	        Desktop.getDesktop().browse(extentReport.toURI());//Converts the report file path to a URI and opens it in the system's default web browser.

    	    } catch (IOException e) {

    	        e.printStackTrace();
    	    }
    	    
        }
    }

