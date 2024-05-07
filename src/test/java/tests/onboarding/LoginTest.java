package tests.onboarding;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pageObject.DefaultDriver;
import pageObject.HomeScreen;
import pageObject.LoginScreen;

import java.io.File;
import java.io.IOException;

public class LoginTest {
    private DefaultDriver defaultDriver;
    private ExtentSparkReporter extentSparkReporter;
    private ExtentReports extentReports;
    private ExtentTest extentTest;
    private LoginScreen loginScreen;
    private HomeScreen homeScreen;

    @BeforeTest
    public void setupDriverAndReport() {
        defaultDriver = new DefaultDriver();
        extentSparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") +"/test-output/testReport.html");
        extentReports = new ExtentReports();
        extentReports.attachReporter(extentSparkReporter);

        extentSparkReporter.config().setOfflineMode(true);
        extentSparkReporter.config().setDocumentTitle("Sample Automation Report");
        extentSparkReporter.config().setReportName("Test Report");
        extentSparkReporter.config().setTheme(Theme.STANDARD);
        extentSparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
        extentSparkReporter.config().setEncoding("UTF-8");
    }

    @BeforeMethod
    public void setupBrowser() {
        WebDriver webDriver = defaultDriver.launchURL();
        loginScreen = new LoginScreen(webDriver);
        homeScreen = new HomeScreen(webDriver);
    }

    @Test(priority = 0)
    public void testLogin(){
        extentTest = extentReports.createTest("Login Test", "Test Login functionality");
        loginScreen.login();
        homeScreen.verifyHomeScreen();
    }

    @Test(priority = 1)
    public void testNearestBranch() {
        extentTest = extentReports.createTest("Nearest Bank Branch Test", "Test Nearest Bank Branch functionality");
        loginScreen.login();
        homeScreen.verifyHomeScreen();
        homeScreen.searchBranch();
    }

    @AfterMethod
    public void getResult(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            String absolutePath = "_";
            extentTest.log(Status.FAIL, "Test Case Failed is "+result.getName());
            extentTest.log(Status.FAIL, "Failed due to:-- "+result.getThrowable());
            extentTest.log(Status.FAIL, "Checkout the screenshot below: ");
            //Open report from finder NOT from intelij project.  File file = new File(System.getProperty("user.dir") + "/test-output/Screenshots/" + result.getName() + ".png");
            File file = new File(defaultDriver.getScreenShot(result.getName()));
            absolutePath = file.getAbsolutePath();
            extentTest.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(absolutePath).build());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            extentTest.log(Status.PASS, result.getTestName());
        } else {
            extentTest.log(Status.SKIP, result.getTestName());
        }
        defaultDriver.tearDown();
    }

    @AfterTest
    public void closeReport() {
        extentReports.flush();
    }
}
