package tests.onboarding;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pageObject.DefaultDriver;
import pageObject.HomeScreen;
import pageObject.LoginScreen;
import utility.SparkReporter;

import java.io.IOException;
import java.util.Optional;

public class LoginTest {
    private DefaultDriver defaultDriver;
    private LoginScreen loginScreen;
    private HomeScreen homeScreen;
    private SparkReporter sparkReporter;
    private ITestResult iTestResult;

    @BeforeTest
    public void setupDriverAndReport() {
        defaultDriver = new DefaultDriver();
    }

    @BeforeMethod
    public void setupBrowser() {
        WebDriver webDriver = defaultDriver.launchURL(Optional.ofNullable(System.getProperty("browserMode")));
        sparkReporter = new SparkReporter();
        loginScreen = new LoginScreen(webDriver);
        homeScreen = new HomeScreen(webDriver);
    }

    @Test(priority = 0)
    public void testLogin(){
        sparkReporter.initializeReportInTest("Login Test", "Test Login functionality");
        loginScreen.login();
        homeScreen.verifyHomeScreen();
    }

    @Test(priority = 1)
    public void testNearestBranch() {
        sparkReporter.initializeReportInTest("Nearest Bank Branch Test", "Test Nearest Bank Branch functionality");
        loginScreen.login();
        homeScreen.verifyHomeScreen();
        homeScreen.searchBranch();
    }

    @AfterMethod
    public void setTestResultAndCloseDriver(ITestResult iTestResult) throws IOException {
        this.iTestResult = iTestResult;
        sparkReporter.configureReporter(iTestResult.getStatus(), iTestResult.getName(), iTestResult.getThrowable(), defaultDriver);
        defaultDriver.tearDown();
    }

    @AfterTest
    public void closeReport() {
        sparkReporter.tearDownReport();
    }
}
