package utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestResult;
import pageObject.DefaultDriver;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class SparkReporter {
    private ExtentSparkReporter extentSparkReporter;
    private ExtentReports extentReports;
    private ExtentTest extentTest;
    DefaultDriver defaultDriver;

    public SparkReporter() {
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
    public void configureReporter(int testStatus, String testName, Throwable reason, DefaultDriver defaultDriver) throws IOException {
        this.defaultDriver = defaultDriver;
        if (testStatus == ITestResult.FAILURE) {
            String absolutePath = "_";
            extentTest.log(Status.FAIL, "Test Case Failed is " + testName);
            extentTest.log(Status.FAIL, "Failed due to:-- " + reason);
            extentTest.log(Status.FAIL, "Checkout the screenshot below: ");
            //Open report from finder NOT from intellij project.
            File file = new File(defaultDriver.getScreenShot(testName));
            absolutePath = file.getAbsolutePath();
            String mode = Optional.ofNullable(System.getProperty("browserMode")).orElse("local");
            if (mode.equalsIgnoreCase("headless")){
                extentTest.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(absolutePath.substring(66)).build());
            }else {
                extentTest.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(absolutePath).build());
            }
        } else if (testStatus == ITestResult.SUCCESS) {
            extentTest.log(Status.PASS, "The test " +testName +" has been successfully executed.");
        } else {
            extentTest.log(Status.SKIP, "Unfortunately the test " +testName +" was skipped.");
        }
    }
    public void initializeReportInTest(String testName, String testDescription) {
        extentTest = extentReports.createTest(testName, testDescription);
    }

    public void tearDownReport() {
        extentReports.flush();
    }

}
